package com.example.android.simplechat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.example.android.simplechat.message.Message

class MainActivity : AppCompatActivity() {

    private val messageList = mutableListOf<Message>()
    private val adapter = Adapter(messageList)

    private val editText by lazy { findViewById<EditText>(R.id.edit_text) }
    private val radioGroup by lazy { findViewById<RadioGroup>(R.id.radio_group) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ItemDecorator())

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val message = editText.text.toString()
            val user = getUser() ?: throw Exception("User not defined.")
            adapter.addMessageItem(user, message)
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
}
