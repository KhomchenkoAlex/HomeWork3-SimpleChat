package com.example.android.simplechat

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.android.simplechat.message.FirstUserMessage
import com.example.android.simplechat.message.Message
import com.example.android.simplechat.message.SecondUserMessage

class Adapter(
        private val items: MutableList<Message>
) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private val firstUserView = 0
    private val secondUserView = 1
    private val headerView = 2

    override fun getItemViewType(position: Int): Int {
        if (items[position] is FirstUserMessage) return firstUserView
        if (items[position] is SecondUserMessage) return secondUserView
        return headerView
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val itemView = when (viewType) {
            firstUserView -> inflater.inflate(R.layout.first_user_message_layout, parent, false)
            else -> inflater.inflate(R.layout.second_user_message_layout, parent, false)
        }
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder?, position: Int) {
        val item = items[position]
        holder?.senderView?.text = item.user
        holder?.messageView?.text = item.messageText
        holder?.messageView?.setOnLongClickListener { holder.onLongClick(holder.messageView) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addMessageItem(user: String, text: String) {
        var item: Message? = null
        when (user) {
            "UserA" -> item = FirstUserMessage().apply {
                this.user = user
                this.messageText = text
            }
            "UserB" -> item = SecondUserMessage().apply {
                this.user = user
                this.messageText = text
            }
        }
        items.add(item!!)
        notifyItemInserted(items.size)
    }

    fun removeMessageItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(items.size)
    }

    fun editMessageItem(position: Int, messageView: TextView, editView: EditText) {
        messageView.visibility = View.GONE
        editView.visibility = View.VISIBLE
        editView.findFocus()
        editView.text = messageView.editableText
        editView.setOnClickListener {
            var editedText = editView.editableText.toString()
            if (editedText.isEmpty()) editedText = messageView.text.toString()
            messageView.text = editedText
            messageView.visibility = View.VISIBLE
            editView.visibility = View.GONE
            editView.text.clear()
        }
        notifyItemChanged(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {
        val messageView = itemView.findViewById<TextView>(R.id.message_view)
        val senderView = itemView.findViewById<TextView>(R.id.sender_view)
        val editView = itemView.findViewById<EditText>(R.id.edit_view)

        override fun onLongClick(view: View?): Boolean {
            val context = view?.context ?: throw Exception()
            AlertDialog.Builder(context)
                    .setTitle("What to do with this?")
                    .setNegativeButton("Delete") { dialog, which -> removeMessageItem(adapterPosition) }
                    .setPositiveButton("Edit") { dialog, which -> editMessageItem(adapterPosition, messageView, editView) }
                    .setNeutralButton("Cancel", null)
                    .show()
            return true
        }
    }
}