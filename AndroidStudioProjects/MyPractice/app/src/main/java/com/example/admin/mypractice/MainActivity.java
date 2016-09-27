package com.example.admin.mypractice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;

/**
 * This app is just for practice
 */

public class MainActivity extends ActionBarActivity {

    /**
     * *this is a global variable
     */
    int quantity = 0;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    /**
     * The method to call the button
     **/

    public void submitOrder(View view) {
        //ask the user to enter their name
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String cusName = nameField.getText().toString();

        //ask if customer wants chocolate added to their coffee
        CheckBox chocolateBox = (CheckBox)findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateBox.isChecked();

        //ask if the customer wants whipped cream or not
        CheckBox whippedCreamBox = (CheckBox)findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamBox.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, cusName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));   //to be handled only by email apps
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee order for " + cusName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

        displayMessage(priceMessage);

    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;
        if(addWhippedCream){
            basePrice += 1;
        }

        if(addChocolate){
            basePrice += 2;
        }

        return quantity * basePrice;

    }

    /*Create a summary of the order,
    * @param price of the order
    * @param add whipped cream to the order or not
    * @return text summary*/

    private String createOrderSummary(int price , boolean addWhippedCream, boolean addChocolate, String cusName){
        String priceMessage = "Name : " + cusName;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nDo you want chocolate with your coffee? " + addChocolate;
        priceMessage +=" \nQuantity : " + quantity ;
        priceMessage += "\nTotal: R" + price;
        priceMessage += "\nThank you";

        return priceMessage;
    }

    public void increment(View view){
        if(quantity >= 10) {

            Toast.makeText(this,"You can only buy 100 cups of coffee or less", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        display(quantity);
    }



    public void decrement(View view) {
        if(quantity <= 1) {
            Toast.makeText(this,"You can only buy 1 cup of coffee or more", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        display(quantity);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayPrice(int number){
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(String.format("R" + number));
    }

    private void displayMessage(String message){
        TextView priceTextView= (TextView)findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.admin.mypractice/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.admin.mypractice/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}