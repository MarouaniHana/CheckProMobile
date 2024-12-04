package iset.project.checkinpromobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import iset.project.checkinpromobile.Model.LoginRequest;
import iset.project.checkinpromobile.Model.User;
import iset.project.checkinpromobile.User.Client;
import iset.project.checkinpromobile.User.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //final String BASE_URL = "http://10.0.2.2:8080";
        final String BASE_URL = "http://192.168.1.254:8080";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        TextView signupLink = findViewById(R.id.signupLink);
        TextView forgotPasswordLink = findViewById(R.id.forgotPasswordLink);

        UserService userService = Client.getRetrofit(BASE_URL).create(UserService.class);

        signupLink.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginRequest loginRequest = new LoginRequest(email, password);

            Call<User> call = userService.login(loginRequest);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User loggedInUser = response.body();
                        Log.d("LoginSuccess", "User: " + response.body().getEmail());
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, ListUser.class);
                        intent.putExtra("userName", loggedInUser.getUsername());
                        startActivity(intent);
                    } else {
                        Log.e("LoginError", "Code: " + response.code());
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("LoginFailure", "Error: " + t.getMessage());
                    Toast.makeText(LoginActivity.this, "Network error, please try again later", Toast.LENGTH_SHORT).show();
                }
            });

        });

        // Navigate to ResetPasswordActivity
        /*forgotPasswordLink.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });*/
    }
}
