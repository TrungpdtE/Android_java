package tdtu.edu.sellticket;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextFullName, editTextPassword, editTextPhoneNumber;
    private FirebaseFirestore db;
    private String documentId; // Document ID of the user to be edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get the document ID from the intent (passed from previous activity)
        documentId = getIntent().getStringExtra("documentId");

        // Initialize UI components
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Load user data (fetch from Firestore)
        loadUserDataFromFirestone();

        // Set up save button click listener
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    private void loadUserDataFromFirestone() {
        // Fetch user data from Firestore using the document ID
        db.collection("users").document(documentId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && task.getResult().exists()) {
                            // Populate the fields with existing user data
                            editTextEmail.setText(task.getResult().getString("email"));
                            editTextFullName.setText(task.getResult().getString("full_name"));
                            editTextPassword.setText(task.getResult().getString("password"));
                            editTextPhoneNumber.setText(task.getResult().getString("phone_number"));
                        }
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUser() {
        String email = editTextEmail.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        // Validate input
        if (email.isEmpty() || fullName.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update user data in Firestore
        db.collection("users").document(documentId)
                .update("email", email, "full_name", fullName, "password", password, "phone_number", phoneNumber)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}