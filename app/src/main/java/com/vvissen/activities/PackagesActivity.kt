package com.vvissen.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vvissen.R
import com.vvissen.fragments.PackageDescriptionFragment
import com.vvissen.fragments.PackageImageFragment
import com.vvissen.model.Package
import com.vvissen.model.PackageLuxury
import com.vvissen.model.PackagePremium
import com.vvissen.model.PackageVip
import com.vvissen.utils.transact
import kotlinx.android.synthetic.main.activity_packages.*

class PackagesActivity : AppCompatActivity() {

    companion object {
        val SELECTED_EXTRA = "selected"
    }

    var selected = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packages)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        setUpUI()
    }

    private fun setUpUI() {

        PackageImageFragment.newInstance(PackagePremium()).transact(this, R.id.fl_premium_house)
        PackageImageFragment.newInstance(PackageLuxury()).transact(this, R.id.fl_luxury_house)
        PackageImageFragment.newInstance(PackageVip()).transact(this, R.id.fl_vip_house)

        fl_premium_house.setOnClickListener {
            if(selected != 0) {
                showDescription(fl_premium_house.id, PackagePremium())
                selected = 0
            }
        }

        fl_luxury_house.setOnClickListener {
            if(selected != 1) {
                showDescription(fl_luxury_house.id, PackageLuxury())
                selected = 1
            }
        }

        fl_vip_house.setOnClickListener {
            if(selected != 2) {
                showDescription(fl_vip_house.id, PackageVip())
                selected = 2
            }
        }

    }

    fun showDescription(container: Int, tripPackage: Package) {
        if(supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStack()
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(container, PackageDescriptionFragment.newInstance(tripPackage))
                .addToBackStack(null)
                .commit()
    }
}
