package com.cmpe277.make24_lab1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        set_random();

    }

    void set_random()
    {
        // generate 4 random numbers
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<10; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);


        Button btn = (Button) findViewById(R.id.number_1);
        btn.setText(String.valueOf(list.get(0)));


        Button btn1 = (Button) findViewById(R.id.number_2);
        btn1.setText(String.valueOf(list.get(1)));

        Button btn2 = (Button) findViewById(R.id.number_3);
        btn2.setText(String.valueOf(list.get(2)));


        Button btn3 = (Button) findViewById(R.id.number_4);
        btn3.setText(String.valueOf(list.get(3)));

    }
}
