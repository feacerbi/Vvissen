package com.vvissen.fragments


import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vvissen.R
import com.vvissen.model.Package
import com.vvissen.model.PackageLuxury
import com.vvissen.model.PackagePremium
import com.vvissen.model.PackageVip
import kotlinx.android.synthetic.main.fragment_package_image.view.*
import org.parceler.Parcels


/**
 * A simple [Fragment] subclass.
 */
class PackageImageFragment : Fragment() {

    companion object {
        val PACKAGE_EXTRA = "package"

        fun newInstance(tripPackage: Package): PackageImageFragment {
            val fragment = PackageImageFragment()
            val args = Bundle()
            args.putParcelable(PACKAGE_EXTRA, Parcels.wrap(tripPackage))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_package_image, container, false)
        val tripPackage = Parcels.unwrap(arguments?.getParcelable(PACKAGE_EXTRA)) as Package

        if(view is ConstraintLayout) {
            with(view) {
                when(tripPackage.name) {
                    PackagePremium().name -> {
                        iv_house.setImageResource(R.drawable.cancun)
                    }
                    PackageLuxury().name -> {
                        iv_house.setImageResource(R.drawable.rio)
                    }
                    PackageVip().name -> {
                        iv_house.setImageResource(R.drawable.maresias)
                    }
                }

                tv_package_type.text = tripPackage.name
            }
        }

        return view
    }

}// Required empty public constructor
