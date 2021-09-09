package com.example.mob3000_frisbeegolf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    val username: String = "admin"
    val password: String = "pass"

    lateinit var button: Button
    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var string: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this, LoginActivity::class.java)

        usernameInput = findViewById(R.id.loginUsername)
        passwordInput = findViewById(R.id.loginPassword)
        button = findViewById(R.id.loginButton)

        button.setOnClickListener {
            if (usernameInput.text.toString()==username && passwordInput.text.toString()==password){
                startActivity(intent)
            }else{
                val toast = Toast.makeText(
                    applicationContext,
                    "wrong combination",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }
}