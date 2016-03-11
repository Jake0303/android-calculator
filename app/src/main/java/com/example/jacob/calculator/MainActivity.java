package com.example.jacob.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class MainActivity extends AppCompatActivity {
    //Variables
    private String operationString = "";
    private String[]validOperators = {"X","+","-","÷","."};
    private boolean isFirst = true;
    private int operatorCount = 0;
    private TextView resultText;
    //When the app is initialized
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = (TextView)findViewById(R.id.resultTextView);
    }

    //Check if the last character in the operation string is a operator, if so then return true
    private boolean checkOperator()
    {
        operatorCount = 0;
        if(!operationString.isEmpty())
        {
            for(String j:validOperators)
            {
                if(operationString.substring(operationString.length() - 1).equals(j))
                {
                    operatorCount = 1;
                    return true;
                }

            }
        }
        return false;
    }

    //When the reset button is clicked, reset the text to 0
    public void resetButtonClicked(View view) {
        operationString = "";
        resultText.setText("0");
        isFirst = true;
    }

    //When any of the operator or number buttons are clicked
    public void onClick(View view) {
        boolean isOperator = false;

        Button b = (Button)view;
        String buttonText = b.getText().toString();



        //If the current selected button is a operator, put a flag that is indeed an operator
        for(String j:validOperators)
        {
            if(buttonText.equals(j))
            {
                isOperator = true;
            }
        }
        //Only append the operation string if we havent clicked equals and that the last character is not an operator
        if(!buttonText.equals("=") && ((isOperator && !checkOperator() && !isFirst) || !isOperator || (isOperator && !isFirst)))
        {
            //Make sure the last character of the string is not an operator
            if(operatorCount < 1)
            {
                //append the operation string with the selected button
                operationString = operationString + buttonText;
                resultText.setText(operationString);
                isFirst = false;
            }
        }
        else
        {
            //if the above has failed,reset text to 0
            if(operationString.isEmpty())
            {
                resultText.setText("0");
            }
            else
            {
                resultText.setText(operationString);
            }
        }


        //Once the user has pressed the equals button, evaluate the string and display the results on the calculator
        if(buttonText.equals("=") && !checkOperator() && !operationString.isEmpty())
        {
            String fixedString = operationString.replace("X", "*");
            fixedString = fixedString.replace("÷","/");
            Expression e = new ExpressionBuilder(fixedString).build();
            double result = e.evaluate();
            Log.d("TESTING!", String.valueOf(result));

            resultText.setText(String.valueOf(result));
            operationString = resultText.getText().toString();
        }
        operatorCount = 0;
    }
}



