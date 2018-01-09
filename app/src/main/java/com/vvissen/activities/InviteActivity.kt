package com.vvissen.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.vvissen.R
import com.vvissen.model.GroupFriends
import com.vvissen.model.GroupGroups
import com.vvissen.model.Trip
import com.vvissen.utils.launchActivityWithExtras
import kotlinx.android.synthetic.main.activity_invite_friends.*
import org.parceler.Parcels

class InviteActivity : AppCompatActivity() {

    var selected2 = false
    var selected3 = false
    var selected4 = false
    var selected5 = false
    var selected6 = false
    var selected7 = false
    var selected8 = false
    var selected9 = false
    var selected10 = false

    val trip by lazy {
        Parcels.unwrap(intent.getParcelableExtra(MatchActivity.TRIP_EXTRA)) as Trip
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(trip.groupType.name == GroupGroups().name) {
            setContentView(R.layout.activity_invite_group)
        } else {
            setContentView(R.layout.activity_invite_friends)
        }

        setSupportActionBar(toolbar)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        setUpUI()

        fab_accept.setOnClickListener { view ->
            if(trip.groupType.name == GroupGroups().name) {
                launchActivityWithExtras<MatchActivity>(
                        MatchActivity::class,
                        arrayOf(MatchActivity.TRIP_EXTRA),
                        arrayOf(trip),
                        false)
                finish()
            } else {
                Toast.makeText(this, "New trip added as Pending", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setUpUI() {
        iv_friend_1.setImageResource(R.drawable.blonde)
        tv_friend1_name.setText("Marcella")

        iv_friend_2.setOnClickListener {
            selected2 = !selected2
            if(selected2) {
                iv_friend_2.setImageResource(R.drawable.brunette)
                tv_friend2_name.setText("Anna")
            } else {
                iv_friend_2.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
                tv_friend2_name.setText("friend 2")
            }
            check()
        }

        iv_friend_3.setOnClickListener {
            selected3 = !selected3
            if(selected3) {
                iv_friend_3.setImageResource(R.drawable.brunette2)
                tv_friend3_name.setText("Vanessa")
            } else {
                iv_friend_3.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
                tv_friend3_name.setText("friend 3")
            }
            check()
        }

        iv_friend_4.setOnClickListener {
            selected4 = !selected4
            if(selected4) {
                iv_friend_4.setImageResource(R.drawable.brunette4)
                tv_friend4_name.setText("Angelina")
            } else {
                iv_friend_4.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
                tv_friend4_name.setText("friend 4")
            }
            check()
        }

        iv_friend_5.setOnClickListener {
            selected5 = !selected5
            if(selected5) {
                iv_friend_5.setImageResource(R.drawable.brunette5)
                tv_friend5_name.setText("Lenna")
            } else {
                iv_friend_5.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
                tv_friend5_name.setText("friend 5")
            }
            check()
        }

        if(trip.groupType.name == GroupFriends().name) {
            iv_friend_6.setOnClickListener {
                selected6 = !selected6
                if(selected6) {
                    iv_friend_6.setImageResource(R.drawable.japa)
                    tv_friend6_name.setText("Kelly")
                } else {
                    iv_friend_6.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
                    tv_friend6_name.setText("friend 6")
                }
                check()
            }

            iv_friend_7.setOnClickListener {
                selected7 = !selected7
                if(selected7) {
                    iv_friend_7.setImageResource(R.drawable.brunette6)
                    tv_friend7_name.setText("Sabrina")
                } else {
                    iv_friend_7.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
                    tv_friend7_name.setText("friend 7")
                }
                check()
            }

            iv_friend_8.setOnClickListener {
                selected8 = !selected8
                if(selected8) {
                    iv_friend_8.setImageResource(R.drawable.red)
                    tv_friend8_name.setText("Mayra")
                } else {
                    iv_friend_8.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
                    tv_friend8_name.setText("friend 8")
                }
                check()
            }

            iv_friend_9.setOnClickListener {
                selected9 = !selected9
                if(selected9) {
                    iv_friend_9.setImageResource(R.drawable.blonde)
                    tv_friend9_name.setText("Karol")
                } else {
                    iv_friend_9.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
                    tv_friend9_name.setText("friend 9")
                }
                check()
            }

            iv_friend_10.setOnClickListener {
                selected10 = !selected10
                if(selected10) {
                    iv_friend_10.setImageResource(R.drawable.brunette2)
                    tv_friend10_name.setText("Gabriella")
                } else {
                    iv_friend_10.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
                    tv_friend10_name.setText("friend 10")
                }
                check()
            }
        }

    }

    fun check() {
        if(trip.groupType.name == GroupFriends().name&&selected2&&selected3&&selected4&&selected5&&selected6&&selected7&&selected8&&selected9&&selected10) {
            fab_accept.visibility = View.VISIBLE
        } else if(trip.groupType.name == GroupGroups().name&&selected2&&selected3&&selected4&&selected5) {
            fab_accept.visibility = View.VISIBLE
        } else {
            fab_accept.visibility = View.GONE
        }
    }

}
