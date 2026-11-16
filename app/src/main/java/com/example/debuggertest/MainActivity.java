package com.example.debuggertest;

import android.os.Bundle;
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

    private EditText etNum1;
    private EditText etNum2;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        tvResult = findViewById(R.id.tvResult);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSub = findViewById(R.id.btnSub);
        Button btnDiv = findViewById(R.id.btnDiv);
        Button btnMul = findViewById(R.id.btnMul);

        btnAdd.setOnClickListener(v -> performOperation('+'));
        btnSub.setOnClickListener(v -> performOperation('-'));
        btnDiv.setOnClickListener(v -> performOperation('/'));
        btnMul.setOnClickListener(v -> performOperation('*'));
    }

    private void performOperation(char op) {
        String str1 = etNum1.getText().toString().trim();
        String str2 = etNum2.getText().toString().trim();

        if (str1.isEmpty() || str2.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_enter_numbers), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(str1);
            double num2 = Double.parseDouble(str2);
            double result = 0;

            switch (op) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        tvResult.setText(getString(R.string.error_div_zero));
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            DecimalFormat df = new DecimalFormat("0.##########");
            tvResult.setText(getString(R.string.result_prefix, df.format(result)));
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.error_enter_numbers), Toast.LENGTH_SHORT).show();
        }
    }
}