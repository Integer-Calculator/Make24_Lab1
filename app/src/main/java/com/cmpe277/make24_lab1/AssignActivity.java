package com.cmpe277.make24_lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.view.View;


public class AssignActivity extends AppCompatActivity {

    int n1 =-1,n2 =-1,n3 =-1,n4 =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);


        //on clicking assign button send numbers to main activity
        Button mondayEdit= (Button)findViewById(R.id.assign);
        mondayEdit.setOnClickListener(new OnClickListener()
        {   public void onClick(View v)
        {
            Intent intent = new Intent(AssignActivity.this, MainActivity.class);

            intent.putExtra("n1", n1);
            intent.putExtra("n2", n2);
            intent.putExtra("n3", n3);
            intent.putExtra("n4", n4);

            startActivity(intent);
            finish();
        }
        });


        //Get the widgets reference from XML layout
        final TextView tv1 = (TextView) findViewById(R.id.tv1);
        NumberPicker np1 = (NumberPicker) findViewById(R.id.np1);
        final TextView tv2 = (TextView) findViewById(R.id.tv2);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.np2);
        final TextView tv3 = (TextView) findViewById(R.id.tv3);
        NumberPicker np3 = (NumberPicker) findViewById(R.id.np3);
        final TextView tv4 = (TextView) findViewById(R.id.tv4);
        NumberPicker np4 = (NumberPicker) findViewById(R.id.np4);

        //Set TextView text color
        tv1.setTextColor(Color.parseColor("#ffd32b3b"));
        tv2.setTextColor(Color.parseColor("#ffd32b3b"));
        tv3.setTextColor(Color.parseColor("#ffd32b3b"));
        tv4.setTextColor(Color.parseColor("#ffd32b3b"));

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np1.setMinValue(0);
        np2.setMinValue(0);
        np3.setMinValue(0);
        np4.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np1.setMaxValue(9);
        np2.setMaxValue(9);
        np3.setMaxValue(9);
        np4.setMaxValue(9);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np1.setWrapSelectorWheel(true);
        np2.setWrapSelectorWheel(true);
        np3.setWrapSelectorWheel(true);
        np4.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                n1 = newVal;
                tv1.setText("Selected Number : " + n1);
            }
        });

        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                n2 = newVal;
                tv2.setText("Selected Number : " + n2);
            }
        });
        np3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                n3 = newVal;
                tv3.setText("Selected Number : " + n3);
            }
        });
        np4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                n4 = newVal;
                tv4.setText("Selected Number : " + newVal);
            }
        });

    }
}
