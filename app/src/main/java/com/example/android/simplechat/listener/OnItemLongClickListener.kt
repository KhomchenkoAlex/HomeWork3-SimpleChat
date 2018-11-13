package com.example.android.simplechat.listener

import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast

class OnItemLongClickListener(view: View): View.OnLongClickListener {
    override fun onLongClick(view: View?): Boolean {
        val context = view?.context ?: throw Exception()
        AlertDialog.Builder(context)
                .setTitle("What to do with this?")
                .setNegativeButton("Delete") {view, items -> Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show()}
                .setPositiveButton("Edit") { _, _ -> Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show() }
                .setNeutralButton("Cancel", null)
                .show()
        return true
    }
}