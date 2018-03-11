package com.cmpe277.make24_lab1;
import com.cmpe277.make24_lab1.databinding.ActivityMainBinding;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import android.support.design.widget.Snackbar;
import android.widget.Chronometer;
import android.view.Menu;
import android.view.MenuItem;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;
    Boolean timerResume = false;
    Chronometer cmTimer;
    long elapsedTime;
    static int skippedAttempts=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        cmTimer = findViewById(R.id.timer);
        ActionBar ab = getSupportActionBar();
        if(ab != null){ab.show();}
        //Set Succeeded Count to zero
        binding.succeeded.setText("0");
        binding.skipped.setText("0");
        startNewGame();


        binding.number1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.expressionView.append(binding.number1.getText());
                binding.number1.setEnabled(false);
            }

        });

        binding.number2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.expressionView.append(binding.number2.getText());
                binding.number2.setEnabled(false);
            }

        });


        binding.number3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.expressionView.append(binding.number3.getText());
                binding.number3.setEnabled(false);
            }
        });

        binding.number4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.expressionView.append(binding.number4.getText());
                binding.number4.setEnabled(false);
            }
        });

        binding.left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.expressionView.append(binding.left.getText());
            }
        });

        binding.right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.expressionView.append(binding.right.getText());
            }
        });


        binding.plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.expressionView.append(binding.plus.getText());
            }
        });


        binding.minus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.expressionView.append(binding.minus.getText());
            }
        });


        binding.divide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.expressionView.append(binding.divide.getText());
            }
        });

        binding.multiply.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.expressionView.append(binding.multiply.getText());
            }
        });



        binding.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CharSequence existingText = binding.expressionView.getText();
                int len = existingText.length();
                if(len>=1) {
                    binding.expressionView.setText(existingText.subSequence(0, len - 1));
                    char deletedChar = existingText.charAt(len-1);
                    if(binding.number1.getText().charAt(0)==deletedChar) binding.number1.setEnabled(true);
                    else if(binding.number2.getText().charAt(0)==deletedChar) binding.number2.setEnabled(true);
                    else if(binding.number3.getText().charAt(0)==deletedChar) binding.number3.setEnabled(true);
                    else if(binding.number4.getText().charAt(0)==deletedChar) binding.number4.setEnabled(true);
                    }
                }
        });


        binding.done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String expression = binding.expressionView.getText().toString();


               if(!binding.number1.isEnabled() && !binding.number2.isEnabled() && !binding.number3.isEnabled() && !binding.number4.isEnabled() && evaluateExpression(expression)) {
                   //Stop Timer
                   cmTimer.stop();
                   cmTimer.setText("00:00");
                   timerResume=false;

                   //Increment Succeeded
                   int successCount = Integer.parseInt(binding.succeeded.getText().toString());
                   binding.succeeded.setText(String.valueOf(++successCount));

                   //Show Bingo
                   new AlertDialog.Builder(MainActivity.this)
                           .setTitle("Bingo!!")
                           .setMessage(expression+" = 24")
                           .setCancelable(false)
                           .setPositiveButton("Next Puzzle", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   startNewGame();
                               }
                           }).show();
                }
                else {
                   int currentAttempts = Integer.parseInt(binding.attempts.getText().toString());
                   binding.attempts.setText(String.valueOf(++currentAttempts));
                   //Show SnackBar
                  Snackbar snackbar = Snackbar
                           .make(findViewById(android.R.id.content), "Incorrect. Please Try Again!!", Snackbar.LENGTH_LONG);
                  snackbar.show();
               }
                }

        });

    }

    boolean evaluateExpression(String expression){


                char[] tokens = expression.toCharArray();

                // Stack for numbers: 'values'
                Stack<Integer> values = new Stack<Integer>();

                // Stack for Operators: 'ops'
                Stack<Character> ops = new Stack<Character>();

                for (int i = 0; i < tokens.length; i++)
                {
                    // Current token is a whitespace, skip it
                    if (tokens[i] == ' ')
                        continue;

                    // Current token is a number, push it to stack for numbers
                    if (tokens[i] >= '1' && tokens[i] <= '9')
                    {
                      /*  StringBuffer sbuf = new StringBuffer();
                        // There may be more than one digits in number
                        while (i < tokens.length && tokens[i] >= '1' && tokens[i] <= '9')
                            sbuf.append(tokens[i++]);
                        values.push(Integer.parseInt(sbuf.toString()));
                        */

                        values.push(Integer.parseInt(Character.toString(tokens[i])));
                    }

                    // Current token is an opening brace, push it to 'ops'
                    else if (tokens[i] == '(')
                        ops.push(tokens[i]);

                        // Closing brace encountered, solve entire brace
                    else if (tokens[i] == ')')
                    {
                        while (ops.peek() != '(')
                            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                        ops.pop();
                    }

                    // Current token is an operator.
                    else if (tokens[i] == '+' || tokens[i] == '-' ||
                            tokens[i] == '*' || tokens[i] == '/')
                    {
                        // While top of 'ops' has same or greater precedence to current
                        // token, which is an operator. Apply operator on top of 'ops'
                        // to top two elements in values stack
                        while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                        // Push current token to 'ops'.
                        ops.push(tokens[i]);
                    }
                }

                // Entire expression has been parsed at this point, apply remaining
                // ops to remaining values
                while (!ops.empty())
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Top of 'values' contains result, return it
                int result = values.pop();
                System.out.println(result);
                return (result==24);
            }


            // Returns true if 'op2' has higher or same precedence as 'op1',
            // otherwise returns false.
        public static boolean hasPrecedence(char op1, char op2)
        {
            if (op2 == '(' || op2 == ')')
                return false;
            if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
                return false;
            else
                return true;
        }

        // A utility method to apply an operator 'op' on operands 'a'
        // and 'b'. Return the result.
        public static int applyOp(char op, int b, int a)
        {
            switch (op)
            {
                case '+':
                    return a + b;
                case '-':
                    return a - b;
                case '*':
                    return a * b;
                case '/':
                    if (b == 0)
                        throw new
                                UnsupportedOperationException("Cannot divide by zero");
                    return a / b;
            }
            return 0;
        }


    void startNewGame()
    {
        // generate 4 random numbers
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<10; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);

        binding.number1.setText(String.valueOf(list.get(0)));
        binding.number1.setEnabled(true);
        binding.number2.setText(String.valueOf(list.get(1)));
        binding.number2.setEnabled(true);
        binding.number3.setText(String.valueOf(list.get(2)));
        binding.number3.setEnabled(true);
        binding.number4.setText(String.valueOf(list.get(3)));
        binding.number4.setEnabled(true);

        //Reset Expression View
        binding.expressionView.setText("");

        //Reset Attempts
        binding.attempts.setText("1");

        cmTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer arg0) {
                if (!timerResume) {
                    long minutes = ((SystemClock.elapsedRealtime() - cmTimer.getBase())/1000) / 60;
                    long seconds = ((SystemClock.elapsedRealtime() - cmTimer.getBase())/1000) % 60;
                    elapsedTime = SystemClock.elapsedRealtime();
                    Log.d(TAG, "onChronometerTick: " + minutes + " : " + seconds);
                } else {
                    long minutes = ((elapsedTime - cmTimer.getBase())/1000) / 60;
                    long seconds = ((elapsedTime - cmTimer.getBase())/1000) % 60;
                    elapsedTime = elapsedTime + 1000;
                    Log.d(TAG, "onChronometerTick: " + minutes + " : " + seconds);
                }
            }
        });


        if (!timerResume) {
            cmTimer.setBase(SystemClock.elapsedRealtime());
            cmTimer.start();
        } else {
            cmTimer.start();
        }

    }


/*
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnStart:
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);

                if (!resume) {
                    cmTimer.setBase(SystemClock.elapsedRealtime());
                    cmTimer.start();
                } else {
                    cmTimer.start();
                }
                break;

            case R.id.btnStop:
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                cmTimer.stop();
                cmTimer.setText("");
                resume = true;
                btnStart.setText("Resume");
                break;

            case R.id.btnReset:
                cmTimer.stop();
                cmTimer.setText("00:00");
                resume = false;
                btnStop.setEnabled(false);
                break;
        }
    }
    */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }


     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
     //   binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
     //   cmTimer = findViewById(R.id.timer);
        switch(item.getItemId()) {
            case R.id.action_clear:
                binding.expressionView.setText("");
                binding.number1.setEnabled(true);
                binding.number2.setEnabled(true);
                binding.number3.setEnabled(true);
                binding.number4.setEnabled(true);
                return true;
            case R.id.action_skip:
                //Stop Timer
                cmTimer.stop();
                cmTimer.setText("00:00");
                timerResume=false;
                binding.skipped.setText(String.valueOf(++skippedAttempts));
                startNewGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

