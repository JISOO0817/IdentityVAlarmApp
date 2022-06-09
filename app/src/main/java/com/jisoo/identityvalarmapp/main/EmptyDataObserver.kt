package com.jisoo.identityvalarmapp.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmptyDataObserver(rv: RecyclerView, tv: TextView) : RecyclerView.AdapterDataObserver() {

    private var emptyView: TextView? = null

    private var recyclerView: RecyclerView? = null

    init {
        recyclerView = rv
        emptyView = tv
        checkIsEmpty()
    }

    private fun checkIsEmpty() {
        if (emptyView != null && recyclerView!!.adapter != null) {
            val emptyViewVisible = recyclerView!!.adapter!!.itemCount == 0

            emptyView!!.visibility =
                if (emptyViewVisible) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            recyclerView!!.visibility =
                if (emptyViewVisible) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
        }
    }

    override fun onChanged() {
        super.onChanged()
        checkIsEmpty()
    }

}