package com.vvissen.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vvissen.R
import com.vvissen.activities.PackagesActivity
import com.vvissen.model.Package
import com.vvissen.model.PackageLuxury
import com.vvissen.model.PackagePremium
import com.vvissen.model.PackageVip
import kotlinx.android.synthetic.main.fragment_package_description.view.*
import org.parceler.Parcels


/**
 * A simple [Fragment] subclass.
 */
class PackageDescriptionFragment : Fragment() {

    companion object {
        val PACKAGE_EXTRA = "package"

        fun newInstance(tripPackage: Package): PackageDescriptionFragment {
            val fragment = PackageDescriptionFragment()
            val args = Bundle()
            args.putParcelable(PACKAGE_EXTRA, Parcels.wrap(tripPackage))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_package_description, container, false)
        val tripPackage = Parcels.unwrap(arguments?.getParcelable(PACKAGE_EXTRA)) as Package

        if(view is ConstraintLayout) {
            var selected = 0
            with(view) {
                // Inflate the layout for this fragment
                when(tripPackage.name) {
                    PackagePremium().name -> {
                        selected = 0
                    }
                    PackageLuxury().name -> {
                        selected = 1
                    }
                    PackageVip().name -> {
                        selected = 2
                    }
                }
                tv_package_type.text = tripPackage.name

                tv_select.setOnClickListener {
                    val resultIntent = Intent()
                    resultIntent.putExtra(PackagesActivity.SELECTED_EXTRA, selected)
                    activity?.setResult(Activity.RESULT_OK, resultIntent)
                    activity?.finish() }
            }
        }

        return view
    }

}// Required empty public constructor
