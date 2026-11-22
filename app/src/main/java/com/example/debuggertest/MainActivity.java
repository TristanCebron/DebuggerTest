
package com.example.debuggertest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SimpleCalc";

    private EditText mOperandOneEditText;
    private EditText mOperandTwoEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);
                    return insets;
                });

        mOperandOneEditText = findViewById(R.id.etNum1);
        mOperandTwoEditText = findViewById(R.id.etNum2);
        mResultTextView = findViewById(R.id.tvResult);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSub = findViewById(R.id.btnSub);
        Button btnDiv = findViewById(R.id.btnDiv);
        Button btnMul = findViewById(R.id.btnMul);

        btnAdd.setOnClickListener(v -> onAdd());
        btnSub.setOnClickListener(v -> onSub());
        btnDiv.setOnClickListener(v -> onDiv());
        btnMul.setOnClickListener(v -> onMul());
    }

    private void onAdd() {
        compute(Calculator.Operator.ADD);
    }

    private void onSub() {
        compute(Calculator.Operator.SUB);
    }

    private void onDiv() {
        compute(Calculator.Operator.DIV);
    }

    private void onMul() {
        compute(Calculator.Operator.MUL);
    }

    private String getOperandText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private double getOperand(EditText editText) {
        String input = getOperandText(editText);
        return Double.parseDouble(input.replace(',', '.'));
    }

    private void compute(Calculator.Operator operator) {
        try {
            double operandOne = getOperand(mOperandOneEditText);
            double operandTwo = getOperand(mOperandTwoEditText);
            double result;

            switch (operator) {
                case ADD:
                    result = Calculator.add(operandOne, operandTwo);
                    break;

                case SUB:
                    result = Calculator.subtract(operandOne, operandTwo);
                    break;

                case DIV:
                    result = Calculator.divide(operandOne, operandTwo);
                    break;

                case MUL:
                    result = Calculator.multiply(operandOne, operandTwo);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unknown operator: " + operator);
            }

            if (Double.isNaN(result)) {
                mResultTextView.setText(
                        getString(R.string.result_prefix, "NaN"));

            } else if (Double.isInfinite(result)) {
                mResultTextView.setText(R.string.error_div_zero);

            } else {
                DecimalFormat format =
                        new DecimalFormat("0.##########");

                mResultTextView.setText(
                        getString(R.string.result_prefix,
                                format.format(result)));
            }

            Log.d(TAG, "operator=" + operator
                    + ", operandOne=" + operandOne
                    + ", operandTwo=" + operandTwo
                    + ", result=" + result);

        } catch (NumberFormatException e) {
            Log.e(TAG, "Napaka pri pretvorbi stevila", e);
            showInputError();
        }
    }

    private void showInputError() {
        String first = getOperandText(mOperandOneEditText);
        String second = getOperandText(mOperandTwoEditText);

        if (first.isEmpty()) {
            mOperandOneEditText.setError(
                    getString(R.string.error_required_field));
        }

        if (second.isEmpty()) {
            mOperandTwoEditText.setError(
                    getString(R.string.error_required_field));
        }

        int message = (first.isEmpty() || second.isEmpty())
                ? R.string.error_enter_numbers
                : R.string.error_invalid_number;

        mResultTextView.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
