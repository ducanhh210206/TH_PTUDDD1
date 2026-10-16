package com.example.learnandroiapp1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etName, etAmount, etNote;
    private Spinner spinnerCategory;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);

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

        etName = findViewById(R.id.etAddName);
        etAmount = findViewById(R.id.etAddAmount);
        etNote = findViewById(R.id.etAddNote);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        datePicker = findViewById(R.id.datePicker);
        Button btnSave = findViewById(R.id.btnSaveExpense);

        // 3. Danh mục Spinner gồm: Ăn uống, Đi lại, Học tập, Giải trí, Khác
        String[] categories = {"Ăn uống", "Đi lại", "Học tập", "Giải trí", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String amountStr = etAmount.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            String note = etNote.getText().toString().trim();

            // · Không cho phép lưu khi tên khoản chi bị bỏ trống
            if (name.isEmpty()) {
                etName.setError("Tên khoản chi không được để trống");
                etName.requestFocus();
                return;
            }

            // · Không cho phép lưu khi số tiền không hợp lệ (rỗng, bằng 0, âm)
            if (amountStr.isEmpty()) {
                etAmount.setError("Số tiền không được để trống");
                etAmount.requestFocus();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                etAmount.setError("Số tiền không hợp lệ");
                etAmount.requestFocus();
                return;
            }

            if (amount <= 0) {
                etAmount.setError("Số tiền phải lớn hơn 0");
                etAmount.requestFocus();
                return;
            }

            // 4. Ngày chi tiêu từ DatePicker
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1; // Month is 0-based
            int year = datePicker.getYear();
            String date = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month, year);

            String id = UUID.randomUUID().toString();
            Expense newExpense = new Expense(id, name, amount, category, date, note);
            ExpenseRepository.getInstance().addExpense(newExpense);

            Toast.makeText(this, "Thêm khoản chi thành công", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
