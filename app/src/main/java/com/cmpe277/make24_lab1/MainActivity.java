package com.cmpe277.make24_lab1;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

//import com.cmpe277.make24_lab1.databinding.ActivityMainBinding;
import com.cmpe277.make24_lab1.util.MakeNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public class MainActivity extends AppCompatActivity
        implements ShowMeDialogListener{

  //  ActivityMainBinding binding;
    Boolean timerResume = false;
    Chronometer cmTimer;
    long elapsedTime;
    static int skippedAttempts = 0;
    Button number1;
    Button number2;
    Button number3;
    Button number4;
    Button plus;
    Button minus;
    Button multiply;
    Button divide;
    Button done;
    Button delete;
    Button left;
    Button right;
    TextView expressionView;
    TextView succeeded;
    TextView skipped;
    TextView attempts;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //binding = DataBindingUtil.s

        cmTimer = findViewById(R.id.timer);

        number1 = findViewById(R.id.number_1);
        number2 = findViewById(R.id.number_2);
        number3 = findViewById(R.id.number_3);
        number4 = findViewById(R.id.number_4);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        multiply = findViewById(R.id.multiply);
        divide = findViewById(R.id.divide);
        done = findViewById(R.id.done);
        delete = findViewById(R.id.delete);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);

        expressionView = findViewById(R.id.expressionView);
        succeeded = findViewById(R.id.succeeded);
        attempts = findViewById(R.id.attempts);
        skipped = findViewById(R.id.skipped);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.show();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);

        }
        //Set Succeeded Count to zero
        succeeded.setText("0");
        skipped.setText("0");


        if (this.getIntent().getExtras() != null ) {
            // intent is not null and your key is not null
            Intent mIntent = getIntent();
            int n1 = mIntent.getIntExtra("n1", 1);
            int n2 = mIntent.getIntExtra("n2", 1);
            int n3 = mIntent.getIntExtra("n3", 1);
            int n4 = mIntent.getIntExtra("n4", 1);

            Button btn = (Button) findViewById(R.id.number_1);
            btn.setText(String.valueOf(n1));

            Button btn1 = (Button) findViewById(R.id.number_2);
            btn1.setText(String.valueOf(n2));

            Button btn2 = (Button) findViewById(R.id.number_3);
            btn2.setText(String.valueOf(n3));

            Button btn3 = (Button) findViewById(R.id.number_4);
            btn3.setText(String.valueOf(n4));
            startNewGame(false);
        }
        else
        {
            startNewGame(true);
        }


        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        switch (menuItem.getItemId()) {
                            case R.id.nav_show_me:
                                menuItem.setChecked(true);
                                int num1 = Integer.parseInt(number1.getText().toString());
                                int num2 = Integer.parseInt(number2.getText().toString());
                                int num3 = Integer.parseInt(number3.getText().toString());
                                int num4 = Integer.parseInt(number4.getText().toString());
                                String solution = MakeNumber.getSolution(num1,num2,num3,num4);
                                DialogFragment showMeFragment = new ShowMeFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("solution", solution);
                                showMeFragment.setArguments(bundle);
                                showMeFragment.show(getFragmentManager(), "ShowMeFragment");
                                drawerLayout.closeDrawers();
                                return true;
                            case R.id.nav_assign_numbers:
                                menuItem.setChecked(true);
                                //Add Assign_numbers Code Here
                                Intent intent = new Intent(MainActivity.this, AssignActivity.class);
                                startActivity(intent);
                                finish();
                                drawerLayout.closeDrawers();
                                return true;
                            default:
                                drawerLayout.closeDrawers();
                                return true;
                        }
                    }
                });


        number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionView.append(number1.getText());
                number1.setEnabled(false);
            }

        });

        number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionView.append(number2.getText());
                number2.setEnabled(false);
            }

        });


        number3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionView.append(number3.getText());
                number3.setEnabled(false);
            }
        });

        number4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionView.append(number4.getText());
                number4.setEnabled(false);
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionView.append(left.getText());
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionView.append(right.getText());
            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionView.append(plus.getText());
            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionView.append(minus.getText());
            }
        });


        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionView.append(divide.getText());
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionView.append(multiply.getText());
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence existingText = expressionView.getText();
                int len = existingText.length();
                if (len >= 1) {
                    expressionView.setText(existingText.subSequence(0, len - 1));
                    char deletedChar = existingText.charAt(len - 1);
                    if (number1.getText().charAt(0) == deletedChar)
                        number1.setEnabled(true);
                    else if (number2.getText().charAt(0) == deletedChar)
                        number2.setEnabled(true);
                    else if (number3.getText().charAt(0) == deletedChar)
                        number3.setEnabled(true);
                    else if (number4.getText().charAt(0) == deletedChar)
                        number4.setEnabled(true);
                }
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expression = expressionView.getText().toString();


                if (!number1.isEnabled() && !number2.isEnabled() && !number3.isEnabled() && !number4.isEnabled() && evaluateExpression(expression)) {
                    //Stop Timer
                    cmTimer.stop();
                    cmTimer.setText("00:00");
                    timerResume = false;

                    //Increment Succeeded
                    int successCount = Integer.parseInt(succeeded.getText().toString());
                    succeeded.setText(String.valueOf(++successCount));

                    //Show Bingo
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Bingo!!")
                            .setMessage(expression + " = 24")
                            .setCancelable(false)
                            .setPositiveButton("Next Puzzle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startNewGame(true);
                                }
                            }).show();
                } else {
                    int currentAttempts = Integer.parseInt(attempts.getText().toString());
                    attempts.setText(String.valueOf(++currentAttempts));
                    //Show SnackBar
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "Incorrect. Please Try Again!!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });

    }

    boolean evaluateExpression(String expression) {


        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<Integer>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++) {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '1' && tokens[i] <= '9') {
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
            else if (tokens[i] == ')') {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/') {
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
        return (result == 24);
    }


    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static int applyOp(char op, int b, int a) {
        switch (op) {
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


    void startNewGame(boolean autoFlag) {

        if(autoFlag) {
            // generate 4 random numbers
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 1; i < 10; i++) {
                list.add(new Integer(i));
            }
            Collections.shuffle(list);

            number1.setText(String.valueOf(list.get(0)));
            number2.setText(String.valueOf(list.get(1)));
            number3.setText(String.valueOf(list.get(2)));
            number4.setText(String.valueOf(list.get(3)));
        }
        //Enable buttons
        number1.setEnabled(true);
        number2.setEnabled(true);
        number3.setEnabled(true);
        number4.setEnabled(true);

        //Reset Expression View
        expressionView.setText("");

        //Reset Attempts
        attempts.setText("1");

        cmTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer arg0) {
                if (!timerResume) {
                    long minutes = ((SystemClock.elapsedRealtime() - cmTimer.getBase()) / 1000) / 60;
                    long seconds = ((SystemClock.elapsedRealtime() - cmTimer.getBase()) / 1000) % 60;
                    elapsedTime = SystemClock.elapsedRealtime();
                    //Log.d(TAG, "onChronometerTick: " + minutes + " : " + seconds);
                } else {
                    long minutes = ((elapsedTime - cmTimer.getBase()) / 1000) / 60;
                    long seconds = ((elapsedTime - cmTimer.getBase()) / 1000) % 60;
                    elapsedTime = elapsedTime + 1000;
                    //Log.d(TAG, "onChronometerTick: " + minutes + " : " + seconds);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_clear:
                expressionView.setText("");
                number1.setEnabled(true);
                number2.setEnabled(true);
                number3.setEnabled(true);
                number4.setEnabled(true);
                return true;
            case R.id.action_skip:
                //Stop Timer
                cmTimer.stop();
                cmTimer.setText("00:00");
                timerResume = false;
                skipped.setText(String.valueOf(++skippedAttempts));
                startNewGame(true);
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogCancelClick(DialogFragment dialog) {
        // User touched the dialog's cancel button
        cmTimer.stop();
        cmTimer.setText("00:00");
        timerResume = false;
        skipped.setText(String.valueOf(++skippedAttempts));
        startNewGame(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
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



}

