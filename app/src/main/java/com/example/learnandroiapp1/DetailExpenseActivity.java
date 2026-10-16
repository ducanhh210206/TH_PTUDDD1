package com.example.learnandroiapp1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class DetailExpenseActivity extends AppCompatActivity {

    private Expense expense;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_expense);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        expense = (Expense) getIntent().getSerializableExtra("expense");

        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvAmount = findViewById(R.id.tvDetailAmount);
        TextView tvCategory = findViewById(R.id.tvDetailCategory);
        TextView tvDate = findViewById(R.id.tvDetailDate);
        TextView tvNote = findViewById(R.id.tvDetailNote);
        Button btnDelete = findViewById(R.id.btnDeleteExpense);

        if (expense != null) {
            tvName.setText(expense.getName());
            tvAmount.setText(decimalFormat.format(expense.getAmount()) + " đ");
            tvCategory.setText(expense.getCategory());
            tvDate.setText(expense.getDate());
            if (expense.getNote() != null && !expense.getNote().isEmpty()) {
                tvNote.setText(expense.getNote());
            } else {
                tvNote.setText("Không có ghi chú");
            }
        }

        btnDelete.setOnClickListener(v -> {
            if (expense != null) {
                ExpenseRepository.getInstance().deleteExpense(expense.getId());
                Toast.makeText(this, "Đã xóa khoản chi", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
