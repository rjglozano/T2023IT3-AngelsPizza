package com.example.t2023it3_angelspizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ProductDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        // Retrieve data from Intent extras
        val itemName = intent.getStringExtra("itemName")
        val itemDescription = intent.getStringExtra("itemDescription")
        val itemPrice = intent.getStringExtra("itemPrice")
        val imageUrl = intent.getStringExtra("imageUrl")

        // Display the details in TextViews and ImageView
        val itemNameTextView: TextView = findViewById(R.id.itemNameTextView)
        val itemDescriptionTextView: WebView = findViewById(R.id.itemDescriptionTextView)
        val itemPriceTextView: TextView = findViewById(R.id.itemPriceTextView)
        val itemImageView: ImageView = findViewById(R.id.itemImageView)

        itemNameTextView.text = itemName
        itemPriceTextView.text = itemPrice

        // Load the image using Glide
        Glide.with(this)
            .load(imageUrl)
            .into(itemImageView)

        itemDescriptionTextView.loadDataWithBaseURL(
            null,
            "<html><body style='text-align:justify;'>$itemDescription</body></html>",
            "text/html",
            "UTF-8",
            null
        )
    }
}