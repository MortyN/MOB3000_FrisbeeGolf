package com.example.mob3000_frisbeegolf.activities.Login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mob3000_frisbeegolf.R
import com.example.mob3000_frisbeegolf.activities.Main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.gson.Gson


class LoginActivity : AppCompatActivity() {
    val username: String = "admin"
    val password: String = "pass"

    lateinit var button: Button
    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var string: String
    lateinit var signInButton: SignInButton
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var googleAccount: GoogleSignInAccount
    lateinit var signInIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this, MainActivity::class.java)

        usernameInput = findViewById(R.id.loginUsername)
        passwordInput = findViewById(R.id.loginPassword)
        button = findViewById(R.id.loginButton)

        button.setOnClickListener {
//            usernameInput.text.toString()==username && passwordInput.text.toString()==password
            if (true){
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

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope("https://www.googleapis.com/auth/user.phonenumbers.read"))
            .requestProfile()
            .build();

        mGoogleSignInClient = getClient(this, gso)

        val getGoogleData = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                var account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val gson = Gson()
                    val string = gson.toJson(account)
                    Log.d(TAG, string)
                    //Send info to api for creating or updating the user
                    startActivity(intent)
                } else Log.d(TAG, "Account is null")
            } catch (e: Exception) {
                Log.d(TAG, "Failed to get user ${e}")
            }
        })

        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener {
            signInIntent = mGoogleSignInClient.signInIntent;
            getGoogleData.launch(signInIntent)
        }

    }
}