package com.android.presentationapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

     var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Order"
    }

    /**
     * This method is called when the order button is clicked.
     */
    fun submitOrder(view: View) {

        val hasWhippedCream = whipped_cream_checkbox.isChecked
        val hasChocolate = chocolate_checkbox.isChecked
        val takeName = name_edittext.text.toString()
        val price = calculatePrice(hasWhippedCream, hasChocolate)
        val message = createOrderSummary(price, hasWhippedCream, hasChocolate, takeName)
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "The order for $takeName")
        intent.putExtra(Intent.EXTRA_TEXT, message)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
    fun increment(view: View) {
        if (quantity < 100) {
            quantity += 1
        }
        displayQuantity(quantity)
    }

    fun decrement(view: View) {
        if (quantity > 1) {
            quantity -= 1
        }
        displayQuantity(quantity)
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private fun displayQuantity(number: Int) {

        quantity_text_view.text = number.toString()
    }
    private fun calculatePrice(hasWhippedCream: Boolean, hasChocolate: Boolean): Int {
        var price = 5
        if (hasWhippedCream) {
            price += 1
        }
        if (hasChocolate) {
            price += 2
        }
        return quantity * price
    }
    private fun createOrderSummary(
        price: Int,
        hasWhippedCream: Boolean,
        hasChocolate: Boolean,
        takeName: String): String {
        return "Name:$takeName \nAdd whipped cream? $hasWhippedCream\n" +
                "Add chocolate? $hasChocolate\nQuantity: $quantity\nTotal: $$price\nThank you!"
    }
}
