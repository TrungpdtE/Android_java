package tdtu.edu.sellticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonBack;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonBack = findViewById(R.id.buttonBack);

        // Set up the login button click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser ();
            }
        });

        // Set up the back button click listener
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will close the activity
            }
        });
    }

    private void loginUser () {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Query the Firestore database for users
        db.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                // User found, retrieve user data
                                String documentId = document.getId(); // Get the document ID
                                String fullName = document.getString("full_name");
                                String phoneNumber = document.getString("phone_number");
                                String role = document.getString("role");
                                String accountCreationDate = document.getString("account_creation_date");

                                // Create an Intent to start the next activity
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("documentId", documentId); // Pass the document ID
                                intent.putExtra("full_name", fullName);
                                intent.putExtra("phone_number", phoneNumber);
                                intent.putExtra("role", role);
                                intent.putExtra("account_creation_date", accountCreationDate);
                                startActivity(intent);
                                finish(); // Close the login activity
                                return; // Exit the loop as we found the user
                            }
                        } else {
                            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}