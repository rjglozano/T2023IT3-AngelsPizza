package com.example.t2023it3_angelspizza

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.marginTop
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore




class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()
        val itemsCollection = db.collection("items")

        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)

        itemsCollection.get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    val name = document.data["name"].toString()
                    val description = document.data["description"].toString()
                    val photoUrl = document.data["photoUrl"].toString()
                    val priceTemp = document.data["price"].toString()
                    val price = String.format("Php %.2f", priceTemp.toFloat())

                    // Create a CardView dynamically
                    val cardView = CardView(this)
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(16, 16, 16, 16)
                    cardView.layoutParams = layoutParams
                    cardView.cardElevation = 10f // Optional: Add elevation for a shadow effect
                    cardView.radius = 20f // Optional: Set corner radius for rounded corners
                    cardView.setContentPadding(32, 32, 32, 32) // Add padding to the content inside the CardView


                    // Create a vertical LinearLayout for the content
                    val contentLayout = LinearLayout(this)
                    contentLayout.orientation = LinearLayout.VERTICAL
                    cardView.addView(contentLayout)

                    // Create an ImageView for the image
                    val imageView = ImageView(this)
                    Glide.with(this).load(photoUrl).into(imageView)
                    val imageLayoutParams = LinearLayout.LayoutParams(
                        resources.getDimensionPixelSize(R.dimen.fixed_width),
                        resources.getDimensionPixelSize(R.dimen.fixed_height)
                    )

                    imageLayoutParams.gravity = Gravity.CENTER

                    imageView.layoutParams = imageLayoutParams
                    contentLayout.addView(imageView)


                    // Create a TextView for the name
                    val nameTextView = TextView(this)
                    val nameLayout = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, // or MATCH_PARENT, depending on your needs
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    nameTextView.text = "$name"
                    nameTextView.textSize = 28F
                    nameTextView.setTypeface(null, Typeface.BOLD)
                    nameLayout.gravity = Gravity.CENTER
                    nameTextView.layoutParams = nameLayout
                    contentLayout.addView(nameTextView)

                    // Create a TextView for the description
                    val descriptionWebView = WebView(this)
                    val descriptionLayout = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    descriptionWebView.loadDataWithBaseURL(null, "<html><body style='text-align:justify;'>$description</body></html>", "text/html", "UTF-8", null)
                    descriptionWebView.layoutParams = descriptionLayout
                    contentLayout.addView(descriptionWebView)


                    val priceDetails = LinearLayout(this)
                    priceDetails.orientation = LinearLayout.HORIZONTAL
                    priceDetails.gravity = Gravity.CENTER

                    contentLayout.addView(priceDetails)

                    // Create a TextView for the price
                    val priceTextView = TextView(this)
                    val priceLayout = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    priceTextView.setTypeface(null, Typeface.BOLD)
                    priceTextView.textSize = 20F
                    priceTextView.text = "$price"
                    priceTextView.layoutParams = priceLayout
                    priceDetails.addView(priceTextView)

                    // Add space between priceTextView and btnViewDetails
                    val spaceView = View(this)
                    spaceView.layoutParams = LinearLayout.LayoutParams(
                        resources.getDimensionPixelSize(R.dimen.space_width),
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    priceDetails.addView(spaceView)

                    val btnViewDetails = Button(this)
                    val btnLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    btnViewDetails.text = "View Details"
                    btnViewDetails.setOnClickListener {
                        val intent = Intent(this, ProductDetails::class.java).apply {
                            putExtra("itemName", name)
                            putExtra("itemDescription", description)
                            putExtra("itemPrice", price)
                            putExtra("imageUrl", photoUrl)
                        }
                        startActivity(intent)
                    }

                    btnViewDetails.layoutParams = btnLayoutParams
                    priceDetails.addView(btnViewDetails)

                    // Add the CardView to the LinearLayout
                    linearLayout.addView(cardView)
                    var btnAddItem = findViewById<Button>(R.id.btnAddItem)
                    btnAddItem.setOnClickListener {
                        val intent = Intent(this, ProductAdd::class.java)
                        startActivity(intent)
                    }



                    }
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreData", "Error getting documents: ${exception.message}")
            }


    }


}