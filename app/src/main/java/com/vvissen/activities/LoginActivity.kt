package com.vvissen.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.vvissen.R
import com.vvissen.utils.launchActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class LoginActivity : AppCompatActivity(), FacebookCallback<LoginResult> {

    companion object {
        val RC_SIGN_IN = 0
    }

    val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    // Build a GoogleSignInClient with the options specified by gso.
    val mGoogleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(this, gso)
    }

    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()
    }

    val mCallbackManager: CallbackManager by lazy {
        CallbackManager.Factory.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginManager.getInstance().registerCallback(mCallbackManager, this)

        svdb_facebook_button.setOnClickListener {
            signInWithFacebook()
        }

        svdb_google_button.setOnClickListener {
            signInWithGoogle()
        }
    }


    override fun onSuccess(result: LoginResult) {
        firebaseAuthWithFace(result.accessToken)
    }

    override fun onError(error: FacebookException) {
        Toast.makeText(this, "Facebook sign in fail: " + error.message, Toast.LENGTH_SHORT).show()
    }

    override fun onCancel() {
        Toast.makeText(this, "Facebook sign in canceled", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        checkSignedIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Google sign in fail: " + e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        checkSignedIn()
                    } else {
                        Toast.makeText(this, "Google sign in fail: " + task.exception, Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun firebaseAuthWithFace(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        checkSignedIn()
                    } else {
                        Toast.makeText(this, "Facebook sign in fail: " + task.exception, Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun checkSignedIn() {
        val user = FirebaseAuth.getInstance().currentUser

        if(user != null) {
            launchActivity(MainActivity::class)
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
    }
}
