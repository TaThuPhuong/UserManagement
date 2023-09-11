package com.ttp.usermanagement.common.custom.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ttp.usermanagement.R


class SpinnerCustom(context: Context, internal val items: List<String>) :
    ArrayAdapter<String>(context, R.layout.item_spiner, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.item_spinner_selected, parent, false)
        val textView = view.findViewById<TextView>(R.id.tv_item_spn_select)
        val iconImageView = view.findViewById<ImageView>(R.id.imv_spn_select)
        iconImageView.setImageResource(R.drawable.ic_drop_down)
        textView.text = items[position]
        return view
    }

//    override fun getDropDownView(
//        position: Int,
//        convertView: View?,
//        parent: ViewGroup
//    ): View {
//        val view = convertView ?: LayoutInflater.from(parent.context)
//            .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
////        val textView = view.findViewById<TextView>(R.id.tv_item_spn)
////        val line = view.findViewById<View>(R.id.line_item_spn)
////        textView.text = items[position]
////        if (position == 0) line.gone()
//
//        return view
//    }
}

