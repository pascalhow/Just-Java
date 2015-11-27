package com.pascalhow.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private final int priceOfCoffee = 5;
    private final int priceOfWhippedCream = 1;
    private final int priceOfChocolate = 2;

    private int numberOfCoffee = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox hasWhippedCream_checkBox = (CheckBox) findViewById(R.id.addWhippedCream_checkBox);
        boolean hasWhippedCream = hasWhippedCream_checkBox.isChecked();

        CheckBox hasChocolate_checkBox = (CheckBox) findViewById(R.id.addChocolate_checkBox);
        boolean hasChocolate = hasChocolate_checkBox.isChecked();

        String customerName = getCustomerName();
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String[] recipient = {"YourEmail@hotmail.com"};
        String subject = "Coffee Order Just For You";
        String message = createOrderSummary(customerName, hasWhippedCream, hasChocolate, price);

        //  Finally send the email to the customer
        sendEmail(recipient, subject, message);
    }

    /**
     * This method creates the email intent
     * @param recipient email address of the recipient
     * @param subject is the email subject
     * @param message is the body of the email
     */
    private void sendEmail(String[] recipient, String subject, String message)
    {
        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method calculates the price of the order
     *
     * @param hasWhippedCream check if customer selected whipped cream
     * @param hasChocolate    check if customer selected chocolate
     * @return total price of coffee order
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int totalPrice = priceOfCoffee;

        if (hasWhippedCream == true) {
            totalPrice += priceOfWhippedCream;
        }
        if (hasChocolate == true) {
            totalPrice += priceOfChocolate;
        }
        return numberOfCoffee * totalPrice;
    }

    /**
     * This method gets the text input from the Edit Text name field
     *
     * @return the name the customer entered in text field
     */
    private String getCustomerName() {
        EditText name_field = (EditText) findViewById(R.id.name_field);

        return name_field.getText().toString();
    }

    /**
     * This method displays the order summary for your coffee purchase
     *
     * @param name            customer name
     * @param hasWhippedCream is whether customer wants whipped cream
     * @param hasChocolate    is whether customer wants chocolate
     * @param price           is the cost of a cup of coffee
     */
    private String createOrderSummary(String name, boolean hasWhippedCream, boolean hasChocolate, int price) {
        String message = "Name: " + name;
        message += "\nAdd Whipped Cream? " + hasWhippedCream;
        message += "\nAdd Chocolate? " + hasChocolate;
        message += "\nQuantity: " + numberOfCoffee;
        message += "\nTotal: " + NumberFormat.getCurrencyInstance().format(price);
        message += "\n" + getString(R.string.thank_you);

        return message;
    }

    /**
     * This method is called when the '+' button is clicked
     */
    public void increment(View view) {
        if (numberOfCoffee >= 10) {
            Toast.makeText(getApplicationContext(), "You cannot select more than 10 coffees", Toast.LENGTH_SHORT).show();
        } else {
            numberOfCoffee++;
        }

        TextView quantity_textview = (TextView) findViewById(R.id.quantity_text_view);
        quantity_textview.setText("" + numberOfCoffee);
    }

    /**
     * This method is called when the '-' button is clicked
     */
    public void decrement(View view) {
        if (numberOfCoffee <= 1) {
            Toast.makeText(getApplicationContext(), "You cannot select less than 0 coffees", Toast.LENGTH_SHORT).show();
        } else {
            numberOfCoffee--;
        }
        TextView quantity_textview = (TextView) findViewById(R.id.quantity_text_view);
        quantity_textview.setText("" + numberOfCoffee);
    }

}
