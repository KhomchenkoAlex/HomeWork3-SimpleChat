package com.example.android.simplechat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.example.android.simplechat.message.FirstUserMessage
import com.example.android.simplechat.message.Message
import com.example.android.simplechat.message.SecondUserMessage

class MainActivity : AppCompatActivity() {

    private val messageList = mutableListOf<Message>()
    private val adapter = Adapter(messageList)
    private var firstUserMessageCounter = 0
    private var secondUserMessageCounter = 0

    private val editText by lazy { findViewById<EditText>(R.id.edit_text) }
    private val radioGroup by lazy {findViewById<RadioGroup>(R.id.radio_group)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ItemDecorator())

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val message = editText.text.toString()
            val user = getUser() ?: throw Exception("User not defined.")
            addMessageItem(user, message)
            updateUserCounter(user)
            recyclerView.adapter.notifyItemInserted(messageList.size)
            editText.text.clear()
       }
    }

    private fun getUser(): String? {
       val checked = radioGroup.checkedRadioButtonId
        var user: String? = null
        when (checked) {
            R.id.radio_button_A -> user = "UserA"
            R.id.radio_button_B -> user = "UserB"
        }
        return user
    }

    private fun addMessageItem(user: String, text: String){
        var item: Message? = null
        when (user){
            "UserA" -> item = FirstUserMessage().apply {
                this.user = user
                this.messageText = text
            }
            "UserB" -> item = SecondUserMessage().apply {
                this.user = user
                this.messageText = text
            }
        }
        messageList.add(item!!)
    }
   private fun removeMessageItem (message: Message) {
       messageList.remove(message)
   }
}
