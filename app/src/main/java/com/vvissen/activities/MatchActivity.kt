package com.vvissen.activities

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v7.app.AppCompatActivity
import com.vvissen.R
import com.vvissen.adapters.GroupPhotosPagerAdapter
import com.vvissen.adapters.UserPhotosPagerAdapter
import com.vvissen.model.*
import kotlinx.android.synthetic.main.activity_match.*
import org.parceler.Parcels


class MatchActivity : AppCompatActivity() {

    companion object {
        val TRIP_EXTRA = "trip"
        val TRIP_NEW = "new_trip"
        val CHANNEL_ID = "trip_status_changes"
        val NOTI_ID_PREMIUM = 1
        val NOTI_ID_LUXURY = 2
    }

    private var mSectionsPagerAdapterUser: UserPhotosPagerAdapter? = null
    private var mSectionsPagerAdapterGroup: GroupPhotosPagerAdapter? = null

    val users: Array<User> by lazy {
            arrayOf(
                    User().createFakeUser(),
                    User().createFakeUser2(),
                    User().createFakeUser3(),
                    User().createFakeUser4(),
                    User().createFakeUser5())
    }

    val groups: Array<ArrayList<User>> by lazy {
        arrayOf(arrayListOf(
                User().createFakeUser(),
                User().createFakeUser2(),
                User().createFakeUser3(),
                User().createFakeUser4(),
                User().createFakeUser5()))
    }

    val trip by lazy {
        Parcels.unwrap(intent.getParcelableExtra(TRIP_EXTRA)) as Trip
    }

    var newTrip = true
    var currentUser: User? = null
    var currentGroup: ArrayList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        newTrip = intent.getBooleanExtra(TRIP_NEW, true)

        setUpUI()
    }

    private fun setUpUI() {
        if(trip.groupType.name == GroupRandom().name) {
            currentUser = users[0]
            title = currentUser?.name
            toolbar.subtitle = String.format("%d years  •  %s", currentUser?.age, currentUser?.city)
            tv_face_friends.text = String.format("%d mutual friends", 26)

            val user = currentUser
            if(user != null) {
                mSectionsPagerAdapterUser = UserPhotosPagerAdapter(supportFragmentManager, user)

                vp_photos.adapter = mSectionsPagerAdapterUser
                vpi_photos_indicator.setViewPager(vp_photos)

                tv_user_description.text = user.shortDescription

                if (newTrip) {
                    Snackbar.make(cl_main, "New trip added as Pending", Snackbar.LENGTH_SHORT).show()
                    newTrip = false
                }

                fab_accept.setOnClickListener { _ ->
                    nextUser()
                }

                fab_deny.setOnClickListener { _ ->
                    nextUser()
                }
            }
        } else if(trip.groupType.name == GroupGroups().name) {
            currentGroup = groups[0]

            val group = currentGroup
            if(group != null) {
                title = group[0].name + " and 4 friends"
                mSectionsPagerAdapterGroup = GroupPhotosPagerAdapter(supportFragmentManager, group)

                vp_photos.adapter = mSectionsPagerAdapterGroup
                vpi_photos_indicator.setViewPager(vp_photos)

                if (newTrip) {
                    Snackbar.make(cl_main, "New trip added as Pending", Snackbar.LENGTH_SHORT).show()
                    newTrip = false
                }

                fab_accept.setOnClickListener { _ ->
                    nextGroup()
                }

                fab_deny.setOnClickListener { _ ->
                    nextGroup()
                }
            }
        }
    }

    fun nextUser() {
        val nextUser = users.indexOf(currentUser) + 1

        if(nextUser < users.size) {
            currentUser = users[nextUser]

            title = currentUser?.name
            toolbar.subtitle = String.format("%d years  •  %s", currentUser?.age, currentUser?.city)
            tv_user_description.text = currentUser?.shortDescription

            val user = currentUser
            if(user != null) {
                mSectionsPagerAdapterUser = UserPhotosPagerAdapter(supportFragmentManager, user)
                vp_photos.adapter = mSectionsPagerAdapterUser
                vpi_photos_indicator.setViewPager(vp_photos)
            }
        } else {
            onBackPressed()
        }
    }

    fun nextGroup() {
        val nextGroup = groups.indexOf(currentGroup) + 1

        if(nextGroup < groups.size) {
            currentGroup = groups[nextGroup]

            val group = currentGroup
            if(group != null) {
                title = group[0].name + " and 4 friends"
                mSectionsPagerAdapterGroup = GroupPhotosPagerAdapter(supportFragmentManager, group)
                vp_photos.adapter = mSectionsPagerAdapterGroup
                vpi_photos_indicator.setViewPager(vp_photos)
            }
        } else {
            onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotiChannel() {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // The id of the channel.
        // The user-visible name of the channel.
        val name = "Trip Status Changes"
        // The user-visible description of the channel.
        val description = "Receive alerts when the status of your trips change."
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        // Configure the notification channel.
        mChannel.description = description
        mChannel.enableLights(true)
        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.enableVibration(true)
        mNotificationManager.createNotificationChannel(mChannel)
    }

    private fun createNotification(notiId: Int, title: String, description: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotiChannel()
        }

        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_logo)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(description))
                .setDefaults(Notification.DEFAULT_ALL)

        // Creates an explicit intent for an Activity in your app
        val resultIntent = Intent(this, TripActivity::class.java)
        resultIntent.putExtra(TRIP_EXTRA, Parcels.wrap(trip))

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your app to the Home screen.
        val stackBuilder = TaskStackBuilder.create(this)
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity::class.java)
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntentWithParentStack(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        mBuilder.setContentIntent(resultPendingIntent)
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // mNotificationId is a unique integer your app uses to identify the
        // notification. For example, to cancel the notification, you can pass its ID
        // number to NotificationManager.cancel().
        mNotificationManager.notify(notiId, mBuilder.build())
    }

    override fun onBackPressed() {
        if(!newTrip) {
            when(trip.house.packageTier.name) {
                PackagePremium().name -> {
                    createNotification(NOTI_ID_PREMIUM, "Trip Status Changed", "Your Trip to Cancún was confirmed, now wait for everyone to get ready.")
                }
                PackageLuxury().name -> {
                    createNotification(NOTI_ID_LUXURY, "Trip Status Changed", "Your Trip to Rio de Janeiro is ready!")
                }
            }
        }
        super.onBackPressed()
    }
}
