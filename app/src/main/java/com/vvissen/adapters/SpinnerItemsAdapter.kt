package com.vvissen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.vvissen.R

class SpinnerItemsAdapter(context: Context, resource: Int, objects: Array<String>, private val hasFixedSize: Boolean) : ArrayAdapter<String>(context, resource, objects) {

    private var selectedPosition = 0

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.spinner_dropdown_item, parent, false)

        val itemName = view.findViewById(R.id.ctv_drop_text) as TextView

        val item = getItem(position)
        itemName.text = item

//        if (position == selectedPosition) {
//            itemName.setTextColor(context.resources.getColor(R.color.colorAccent))
//        } else {
//            itemName.setTextColor(context.resources.getColor(android.R.color.white))
//        }

        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return if (hasFixedSize) {
            super.getView(position, convertView, parent)
        } else super.getView((parent as Spinner).selectedItemPosition, convertView, parent)
    }

    fun setSelectedPosition(selectedPosition: Int) {
        this.selectedPosition = selectedPosition
    }
}