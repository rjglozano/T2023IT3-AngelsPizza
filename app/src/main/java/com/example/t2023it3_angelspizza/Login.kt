package com.example.t2023it3_angelspizza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var btn_Login : Button
    lateinit var btn_Register : Button

    lateinit var input_Email : EditText
    lateinit var input_Password : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        input_Email = findViewById(R.id.input_Email)
        input_Password = findViewById(R.id.input_Password)
        btn_Login = findViewById(R.id.btn_Login)
        btn_Register = findViewById(R.id.btn_Register)

        btn_Login.setOnClickListener {

            val email = input_Email.text.toString()
            val password = input_Password.text.toString()
            val mainActivity = Intent(this, MainActivity::class.java)

            if (email != "" && password != ""){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext,
                                "Success",
                                Toast.LENGTH_SHORT,
                            ).show()
                            startActivity(mainActivity)

                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Wrong email or password!",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }else{
                Toast.makeText(
                    baseContext,
                    "Not Working!",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        btn_Register.setOnClickListener {
            finish()
            val registerActivity = Intent(this, Registration::class.java)
            startActivity(registerActivity)
        }





    }
}