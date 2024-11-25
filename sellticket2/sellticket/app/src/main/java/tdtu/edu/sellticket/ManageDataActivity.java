package tdtu.edu.sellticket;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ManageDataActivity extends AppCompatActivity {
    private EditText editTextCinemaName, editTextCinemaAddress, editTextCinemaContact;
    private EditText editTextMovieTitle, editTextMovieGenre, editTextMovieDirector, editTextMovieDuration, editTextMovieActors;
    private EditText editTextUserFullName, editTextUserEmail, editTextUserPhoneNumber, editTextUserRole;
    private Button buttonAddCinema, buttonAddMovie, buttonAddUser ;
    private ListView listViewCinemas, listViewMovies, listViewUsers;
    private FirebaseFirestore db;
    private ArrayList<String> cinemaList, movieList, userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views for cinemas
        editTextCinemaName = findViewById(R.id.editTextCinemaName);
        editTextCinemaAddress = findViewById(R.id.editTextCinemaAddress);
        editTextCinemaContact = findViewById(R.id.editTextCinemaContact);
        buttonAddCinema = findViewById(R.id.buttonAddCinema);
        listViewCinemas = findViewById(R.id.listViewCinemas);
        cinemaList = new ArrayList<>();

        // Initialize views for movies
        editTextMovieTitle = findViewById(R.id.editTextMovieTitle);
        editTextMovieGenre = findViewById(R.id.editTextMovieGenre);
        buttonAddMovie = findViewById(R.id.buttonAddMovie);
        listViewMovies = findViewById(R.id.listViewMovies);
        movieList = new ArrayList<>();

        // Initialize views for users
        editTextUserFullName = findViewById(R.id.editTextUserName);
        editTextUserEmail = findViewById(R.id.editTextUserEmail);
        buttonAddUser  = findViewById(R.id.buttonAddUser );
        listViewUsers = findViewById(R.id.listViewUsers);
        userList = new ArrayList<>();

        // Load existing data
        loadCinemas();
        loadMovies();
        loadUsers();

        // Set click listeners for adding data
        buttonAddCinema.setOnClickListener(v -> addCinema());
        buttonAddMovie.setOnClickListener(v -> addMovie());
        buttonAddUser .setOnClickListener(v -> addUser ());
    }

    private void loadCinemas() {
        db.collection("cinemas").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cinemaList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            cinemaList.add(document.getString("name")); // Assuming 'name' is a field in your Firestore document
                        }
                        // Update ListView with cinemaList (you may need an adapter)
                    } else {
                        Toast.makeText(ManageDataActivity.this, "Error loading cinemas: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadMovies() {
        db.collection("movies").get()
                .addOnCompleteListener(this::onComplete);
    }


    private void loadUsers() {
        db.collection("users").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            userList.add(document.getString("fullName")); // Assuming 'fullName' is a field in your Firestore document
                        }
                        // Update ListView with userList (you may need an adapter)
                    } else {
                        Toast.makeText(ManageDataActivity.this, "Error loading users: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addCinema() {
        String name = editTextCinemaName.getText().toString();
        String address = editTextCinemaAddress.getText().toString();
        String contact = editTextCinemaContact.getText().toString();

        if (name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("cinemas").add(new Cinema(name, address, contact))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ManageDataActivity.this, "Cinema added successfully", Toast.LENGTH_SHORT).show();
                    loadCinemas(); // Refresh the list
                })
                .addOnFailureListener(e -> Toast.makeText(ManageDataActivity.this, "Error adding cinema: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void addMovie() {
        String title = editTextMovieTitle.getText().toString();
        String genre = editTextMovieGenre.getText().toString();
        String director = editTextMovieDirector.getText().toString();
        String duration = editTextMovieDuration.getText().toString();
        String actors = editTextMovieActors.getText().toString();

        if (title.isEmpty() || genre.isEmpty() || director.isEmpty() || duration.isEmpty() || actors.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("movies").add(new Movie(title, genre, director, duration, actors))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ManageDataActivity.this, "Movie added successfully", Toast.LENGTH_SHORT).show();
                    loadMovies(); // Refresh the list
                })
                .addOnFailureListener(e -> Toast.makeText(ManageDataActivity.this, "Error adding movie: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void addUser () {
        String fullName = editTextUserFullName.getText().toString();
        String email = editTextUserEmail.getText().toString();
        String phoneNumber = editTextUserPhoneNumber.getText().toString();
        String role = editTextUserRole.getText().toString();

        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || role.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users").add(new User(fullName, email, phoneNumber, role))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ManageDataActivity.this, "User  added successfully", Toast.LENGTH_SHORT).show();
                    loadUsers(); // Refresh the list
                })
                .addOnFailureListener(e -> Toast.makeText(ManageDataActivity.this, "Error adding user: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void onComplete(Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            movieList.clear(); // Clear the list to avoid duplicates
            for (QueryDocumentSnapshot document : task.getResult()) {
                // Add movie titles to the list
                movieList.add(document.getString("title")); // Assuming 'title' is a field in your Firestore document
            }
// Notify the adapter to update the ListView
        } else {
// Show a single error message
            Toast.makeText(ManageDataActivity.this, "Error loading movies: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
