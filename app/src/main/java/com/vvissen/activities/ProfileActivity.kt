package com.vvissen.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.vvissen.R
import com.vvissen.adapters.ExtraPicturesAdapter
import com.vvissen.adapters.listeners.ExtraPhotoClickListener
import com.vvissen.model.User
import com.vvissen.utils.launchActivityWithExtras
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), ExtraPhotoClickListener {

    val user by lazy {
        User().createFakeUser3()
    }

    val extrasAdapter by lazy {
        ExtraPicturesAdapter(this, user.photos.toMutableList())
    }

    var profilePhoto = user.photos[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        title = ""

        setUpUI()
    }

    private fun setUpUI() {
        tv_profile_name.text = user.name
        tv_profile_age.text = user.age.toString()
        tv_profile_gender.text = user.gender.toString()
        tv_profile_interest.text = user.interest.toString()
        tv_profile_about.text = user.shortDescription

        Picasso.with(this)
                .load(profilePhoto)
                .fit()
                .centerCrop()
                .into(civ_profile_photo)

        civ_profile_photo.setOnClickListener {
            launchActivityWithExtras<FullscreenActivity>(
                    FullscreenActivity::class,
                    arrayOf(FullscreenActivity.TITLE_EXTRA, FullscreenActivity.PHOTO_EXTRA),
                    arrayOf("", profilePhoto),
                    false)
        }

        rv_extra_photos.adapter = extrasAdapter

        fab.setOnClickListener {
            extrasAdapter.addPhoto(R.drawable.brunette)
        }
    }

    override fun onPhotoChosen(photo: Int) {
        profilePhoto = photo

        Picasso.with(this)
                .load(profilePhoto)
                .fit()
                .centerCrop()
                .into(civ_profile_photo)
    }

    override fun getActivity() = this

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()

        val startLoginActivity = Intent(this, LoginActivity::class.java)
        startLoginActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startLoginActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_sign_out) {
            signOut()
            return true
        } else if(id == R.id.action_edit) {
            launchActivityWithExtras<NewUserActivity>(
                    NewUserActivity::class,
                    arrayOf(NewUserActivity.EDIT_EXTRA),
                    arrayOf(true))
        }

        return super.onOptionsItemSelected(item)
    }
}
