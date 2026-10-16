package com.example.learnandroiapp1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvExpenses;
    private TextView tvTotalAmount;
    private Button btnAddExpense, btnLogout;
    private ExpenseAdapter expenseAdapter;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

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

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        rvExpenses = findViewById(R.id.rvExpenses);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnLogout = findViewById(R.id.btnLogout);

        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        expenseAdapter = new ExpenseAdapter(this, ExpenseRepository.getInstance().getExpenseList());
        rvExpenses.setAdapter(expenseAdapter);

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        if (expenseAdapter != null) {
            expenseAdapter.notifyDataSetChanged();
        }
        double total = ExpenseRepository.getInstance().getTotalAmount();
        tvTotalAmount.setText(decimalFormat.format(total) + " đ");
    }
}
