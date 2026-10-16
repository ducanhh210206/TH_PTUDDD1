package com.example.learnandroiapp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView tvError;
    private Button btnLogin;

    // Tài khoản cố định
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvError = findViewById(R.id.tvError);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 1. Không cho phép đăng nhập khi tên đăng nhập hoặc mật khẩu bị bỏ trống
                if (username.isEmpty() || password.isEmpty()) {
                    tvError.setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu");
                    tvError.setVisibility(View.VISIBLE);
                    return;
                }

                // 2. So sánh thông tin nhập với tài khoản cố định
                if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
                    // Đăng nhập thành công
                    tvError.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Gọi finish() để người dùng không quay lại màn hình đăng nhập bằng nút Back
                } else {
                    // 3. Sai tên đăng nhập hoặc mật khẩu
                    tvError.setText("Sai tên đăng nhập hoặc mật khẩu");
                    tvError.setVisibility(View.VISIBLE);
                    etPassword.setText("");
                    etPassword.requestFocus();
                }
            }
        });
    }
}
