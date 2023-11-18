package com.example.t2023it3_angelspizza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ProductAdd : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val itemsCollection = db.collection("items")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)

        val editTextItemPhotoUrl = findViewById<EditText>(R.id.editTextItemPhotoUrl)
        val itemNameEditText = findViewById<EditText>(R.id.editTextItemName)
        val itemDescriptionEditText = findViewById<EditText>(R.id.editTextItemDescription)
        val itemPriceEditText = findViewById<EditText>(R.id.editTextItemPrice)
        val addItemButton = findViewById<Button>(R.id.buttonAddItem)
        val btnBackToMenu = findViewById<Button>(R.id.btnBackToMenu)

        btnBackToMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        addItemButton.setOnClickListener {
            val name = itemNameEditText.text.toString()
            val description = itemDescriptionEditText.text.toString()
            val price = itemPriceEditText.text.toString().toDoubleOrNull()
            val photoUrl = editTextItemPhotoUrl.text.toString()


            if (name.isNotEmpty() && description.isNotEmpty() && price != null) {
                val newItem = hashMapOf(
                    "name" to name,
                    "description" to description,
                    "photoUrl" to photoUrl,
                    "price" to price
                )

                addItemToFirestore(newItem)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show()
            }

            Toast.makeText(this, "Item added successfully", Toast.LENGTH_LONG).show()

            // Clear input fields
            itemNameEditText.text.clear()
            itemDescriptionEditText.text.clear()
            itemPriceEditText.text.clear()
            editTextItemPhotoUrl.text.clear()

        }
    }

    private fun addItemToFirestore(item: Map<String, Any>) {
        itemsCollection.add(item)
            .addOnSuccessListener { documentReference ->
                Log.d("FirestoreData", "Item added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreData", "Error adding item: ${e.message}")
            }

    }
}