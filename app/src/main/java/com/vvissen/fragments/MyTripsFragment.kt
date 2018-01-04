package com.vvissen.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.vvissen.R
import com.vvissen.activities.LoginActivity
import com.vvissen.adapters.TripsAdapter
import com.vvissen.adapters.listeners.TripListClickListener
import com.vvissen.model.Trip
import com.vvissen.utils.PagerController
import kotlinx.android.synthetic.main.fragment_my_trips.view.*


/**
 * A placeholder fragment containing a simple view.
 */
class MyTripsFragment : Fragment() {

    private var mListener: PagerController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_my_trips, container, false)

        val tripsAdapter = TripsAdapter(mListener as TripListClickListener,
                arrayListOf(
                Trip().createFakeTrip(),
                Trip().createFakeTrip2(),
                Trip().createFakeTrip3()))

        if(view is ConstraintLayout) {
            with(view) {
                rv_trips_list.adapter = tripsAdapter

                toolbar.inflateMenu(R.menu.menu_my_trips)
                toolbar.setOnMenuItemClickListener( { item ->
                    if(item.itemId == R.id.action_back) {
                        mListener?.setPage(1)
                    } else if(item.itemId == R.id.action_profile) {
                        FirebaseAuth.getInstance().signOut()
                        LoginManager.getInstance().logOut()

                        val startLoginActivity = Intent(activity, LoginActivity::class.java)
                        startLoginActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(startLoginActivity)
                    }
                    true
                })

                tv_empty.visibility = if(tripsAdapter.itemCount == 0) View.VISIBLE else View.GONE
            }
        }


        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is PagerController) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement PagerController")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
}
