package com.example.t2023it3_angelspizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Registration : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val userCollectionName = "users"
    @SuppressLint("MissingInflatedId")

    lateinit var btn_RegLogin : Button
    lateinit var reg_Email : EditText
    lateinit var reg_NewPass : EditText
    lateinit var reg_ConfirmPass : EditText
    lateinit var reg_DisplayName : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setContentView(R.layout.activity_registration)
        val db = Firebase.firestore

        auth = Firebase.auth
        btn_RegLogin = findViewById(R.id.btn_RegLogin)
        reg_Email = findViewById(R.id.reg_Email)
        reg_NewPass = findViewById(R.id.reg_NewPass)
        reg_ConfirmPass = findViewById(R.id.reg_ConfirmPass)
        reg_DisplayName = findViewById(R.id.reg_DisplayName)

        btn_RegLogin.setOnClickListener {
            val email = reg_Email.text.toString()
            val password = reg_NewPass.text.toString()
            val confirmpass = reg_ConfirmPass.text.toString()
            val displayName = reg_DisplayName.text.toString()
            val mainActivity = Intent(this, MainActivity::class.java)

            if (email != "" && password != "" && password == confirmpass && displayName != ""){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val data = hashMapOf(
                                "email" to email,
                                "displayName" to displayName,
                            )

                            db.collection(userCollectionName).document()
                                .set(data)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        baseContext,
                                        "Registered Successfully!",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                    startActivity(mainActivity)
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        baseContext,
                                        "Registration Failed!",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }

                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Registration Failed!",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }

        }
    }
}