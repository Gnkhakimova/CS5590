package com.example.karthik.myorder;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 10;
    final int PEPERONNI_PRICE = 5;
    final int ONIONS_PRICE = 3;
    final int SPINACH_PRICE = 2;
    final int MUSHROOM_PRICE = 4;
    int quantity = 2;
    String message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button butinsert = (Button) findViewById(R.id.Calendar);
        butinsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //insert();
                openCalendar();
            }
        });
    }
    public void openCalendar(){
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
    @SuppressLint("NewApi")
    public void insert() {
        Intent intent = new Intent(Intent.ACTION_INSERT,
                CalendarContract.Events.CONTENT_URI);
        // Add the calendar event details
        intent.putExtra(CalendarContract.Events.TITLE, "Launch!");
        intent.putExtra(CalendarContract.Events.DESCRIPTION,
                "Learn Java Android Coding");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                "UMKC.com");
        Calendar startTime = Calendar.getInstance();
        startTime.set(2017, 12, 1);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                startTime.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        // Use the Calendar app to add the new event.
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

       /* Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:47.6,-122.3"));
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }*/

//        get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();
//        check if whipped cream is selected
        CheckBox peperonni = (CheckBox) findViewById(R.id.peperonni_checked);
        boolean hasPeperonni = peperonni.isChecked();
        //        check if chocolate is selected
        CheckBox onions = (CheckBox) findViewById(R.id.onions_checked);
        boolean hasOnions = onions.isChecked();

        CheckBox mushroom = (CheckBox) findViewById(R.id.mushroom_checked);
        boolean hasMushroom = mushroom.isChecked();

        CheckBox spinach = (CheckBox) findViewById(R.id.spinach_checked);
        boolean hasSpinach = spinach.isChecked();
//        calculate and store the total price
        float totalPrice = calculatePrice(hasMushroom, hasOnions,hasPeperonni, hasSpinach);
//        create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasMushroom, hasOnions, hasPeperonni, hasSpinach, totalPrice);
// Write the relevant code for making the buttons work(i.e impelement the implicit and explicit intents
        message = orderSummaryMessage;
        Intent intent = new Intent(getBaseContext(), OrderSummary.class);
        intent.putExtra("EXTRA_SESSION_ID", orderSummaryMessage);
        startActivity(intent);

    }
    private String boolToString(boolean bool){
        return bool?(getString(R.string.yes)):(getString(R.string.no));

    }

    public void sendEmail(View v)
    {
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();
//        check if whipped cream is selected
        CheckBox peperonni = (CheckBox) findViewById(R.id.peperonni_checked);
        boolean hasPeperonni = peperonni.isChecked();
        //        check if chocolate is selected
        CheckBox onions = (CheckBox) findViewById(R.id.onions_checked);
        boolean hasOnions = onions.isChecked();

        CheckBox mushroom = (CheckBox) findViewById(R.id.mushroom_checked);
        boolean hasMushroom = mushroom.isChecked();

        CheckBox spinach = (CheckBox) findViewById(R.id.spinach_checked);
        boolean hasSpinach = spinach.isChecked();
//        calculate and store the total price
        float totalPrice = calculatePrice(hasMushroom, hasOnions,hasPeperonni, hasSpinach);
//        create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasMushroom, hasOnions, hasPeperonni, hasSpinach, totalPrice);
// Write the relevant code for making the buttons work(i.e impelement the implicit and explicit intents
        //message = orderSummaryMessage;
        //String message = createOrderSummary()
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_SUBJECT, "Pizza order");
        email.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    private String createOrderSummary(String userInputName, boolean hasMushroom, boolean hasOnions,boolean hasPeperonni, boolean hasSpinach, float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name,userInputName) +"\n"+
                getString(R.string.order_summary_peperonni,boolToString(hasPeperonni))+"\n"+
                getString(R.string.order_summary_onions,boolToString(hasOnions)) +"\n"+
                getString(R.string.order_summary_mushroom,boolToString(hasMushroom)) +"\n"+
                getString(R.string.order_summary_spinach,boolToString(hasSpinach)) +"\n"+
                getString(R.string.order_summary_quantity,quantity)+"\n"+
                getString(R.string.order_summary_total_price,price) +"\n"+
                getString(R.string.thank_you);
        return orderSummaryMessage;

    }


    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasPeperonni, boolean hasOnions,boolean hasSpinach, boolean hasMushroom) {
        int basePrice = PIZZA_PRICE;
        if (hasMushroom) {
            basePrice += MUSHROOM_PRICE;
        }
        if (hasOnions) {
            basePrice += ONIONS_PRICE;
        }
        if (hasPeperonni) {
            basePrice += PEPERONNI_PRICE;
        }
        if (hasSpinach) {
            basePrice += SPINACH_PRICE;
        }
        return quantity * basePrice;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }


    /**
     * This method increments the quantity of coffee cups by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {

            Log.i("Pizza", "Please select less than one hundred count of pizza");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;

        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {

            Log.i("MainActivity", "Please select atleast one pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;


        }
    }
}
