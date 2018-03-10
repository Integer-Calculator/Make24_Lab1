package com.cmpe277.make24_lab1;
import com.cmpe277.make24_lab1.databinding.ActivityMainBinding;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import android.support.design.widget.Snackbar;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        set_random();

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

               if(evaluateExpression(expression)) {
                   //Show Bingo
                   new AlertDialog.Builder(MainActivity.this)
                           .setTitle("Bingo!!")
                           .setMessage(expression+" = 24")
                           .setCancelable(false)
                           .setPositiveButton("Next Puzzle", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   //generateNewNumbers();
                               }
                           }).show();
                }
                else {
                   binding.expressionView.setText("FAILURE");
                   //Show SnackBar
                 // Snackbar snackbar = Snackbar
                   //        .make( findViewById(android.R.id.content), "Incorrect", Snackbar.LENGTH_LONG);
                  // snackbar.show();
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
