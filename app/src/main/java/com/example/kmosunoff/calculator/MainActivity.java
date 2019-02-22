package com.example.kmosunoff.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        TextView expression = findViewById(R.id.expression);
        TextView answer1 = findViewById(R.id.answer1);
        TextView answer2 = findViewById(R.id.answer2);
        switch (view.getId()) {
            case R.id.dig_zero : {
                expression.append("0");
                break;
            }
            case R.id.dig_one : {
                expression.append("1");
                break;
            }
            case R.id.dig_two : {
                expression.append("2");
                break;
            }
            case R.id.dig_three : {
                expression.append("3");
                break;
            }
            case R.id.dig_four : {
                expression.append("4");
                break;
            }
            case R.id.dig_five : {
                expression.append("5");
                break;
            }
            case R.id.dig_six : {
                expression.append("6");
                break;
            }
            case R.id.dig_seven : {
                expression.append("7");
                break;
            }
            case R.id.dig_eight : {
                expression.append("8");
                break;
            }
            case R.id.dig_nine : {
                expression.append("9");
                break;
            }
            case R.id.plus : {
                expression.append("+");
                break;
            }
            case R.id.minus : {
                expression.append("-");
                break;
            }
            case R.id.mul : {
                expression.append("*");
                break;
            }
            case R.id.div : {
                expression.append("/");
                break;
            }
            case R.id.dot : {
                expression.append(".");
                break;
            }
            case R.id.open_bracket : {
                expression.append("(");
                break;
            }
            case R.id.close_bracket : {
                expression.append(")");
                break;
            }
            case R.id.less : {
                expression.append("<");
                break;
            }
            case R.id.more : {
                expression.append(">");
                break;
            }
            case R.id.quest : {
                expression.append("?");
                break;
            }
            case R.id.colon : {
                expression.append(":");
                break;
            }
            case R.id.del: {
                if (expression.length() != 0) {
                    expression.setText(expression.getText().toString().substring(0, expression.length() - 1));
                }
                break;
            }
            case R.id.clear : {
                expression.setText("");
                answer1.setText("");
                answer2.setText("");
                break;
            }
            case R.id.equal : {
                String ans1 = Parser.evaluateFraction(expression.getText().toString());
                answer1.setText(ans1);
                String ans2 = Parser.evaluateDecimal(expression.getText().toString());
                answer2.setText(ans2);
                break;
            }
        }
    }
}
