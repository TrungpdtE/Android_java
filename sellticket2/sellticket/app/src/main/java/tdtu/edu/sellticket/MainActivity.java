package tdtu.edu.sellticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private ImageView settingsIcon;
    private ImageView profileImage;
    private TextView admin; // Add this line
    private FirebaseFirestore db;
    private LinearLayout movieListLayout;
    private LinearLayout expandMenu;
    private ImageView settingsIconMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the document ID from the intent
        String documentId = getIntent().getStringExtra("documentId");

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        settingsIcon = findViewById(R.id.settings_icon);
        profileImage = findViewById(R.id.profile_image);
        movieListLayout = findViewById(R.id.movie_list_layout);
        expandMenu = findViewById(R.id.expand_menu);
        settingsIconMenu = findViewById(R.id.settings_icon_menu);
        admin = findViewById(R.id.admin); // Initialize admin TextView

        // Set click listener for settings icon
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandMenu.setVisibility(View.VISIBLE);
            }
        });

        // Set click listener for settings_icon_menu to hide the expand_menu
        settingsIconMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandMenu.setVisibility(View.GONE);
            }
        });

        // Set click listener for profile_image to navigate to EditProfileActivity
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                intent.putExtra("documentId", documentId);
                startActivity(intent);
            }
        });

        // Set click listener for admin TextView to navigate to ManageDataActivity
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManageDataActivity.class);
                startActivity(intent);
            }
        });

        loadMovies();
    }

    private void loadMovies() {
        db.collection("movies").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Extract movie details
                            String documentId = document.getId(); // Get document ID
                            String title = document.getString("title");
                            String duration = document.getString("duration");
                            String releaseDate = document.getString("release_date");
                            String actors = document.getString("actors");
                            String director = document.getString("director");
                            String genre = document.getString("genre");
                            String posterUrl = document.getString("poster_url");
                            String synopsis = document.getString("synopsis");
                            String trailerUrl = document.getString("trailer_url");

                            // Create a movie card layout dynamically
                            createMovieCard(documentId, title, duration, releaseDate, actors, director, genre, posterUrl, synopsis, trailerUrl);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error fetching movies: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createMovieCard(String documentId, String title, String duration, String releaseDate, String actors, String director, String genre, String posterUrl, String synopsis, String trailerUrl) {
        // Inflate the movie card layout
        View movieCard = getLayoutInflater().inflate(R.layout.movie_card_layout, null);

        // Find views in the movie card layout
        TextView movieName = movieCard.findViewById(R.id.movie_name);
        TextView movieDuration = movieCard.findViewById(R.id.movie_duration);
        TextView movieDate = movieCard.findViewById(R.id.movie_date);
        Button bookTicketButton = movieCard.findViewById(R.id.book_ticket_button);

        // Set movie details
        movieName.setText(title);
        movieDuration.setText(duration);
        movieDate.setText(releaseDate);

        // Set layout parameters for spacing
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 32);

        // Add the movie card to the main layout with margins
        movieListLayout.addView(movieCard, params);

        // Set click listener for the book ticket button
        bookTicketButton.setOnClickListener(v -> {
            // Create an Intent to start BookTicketActivity
            Intent intent = new Intent(MainActivity.this, BookTicketActivity.class);

            // Put all movie details into the intent
            intent.putExtra("documentId", documentId);
            intent.putExtra("title", title);
            intent.putExtra("duration", duration);
            intent.putExtra("release_date", releaseDate);
            intent.putExtra("actors", actors);
            intent.putExtra("director", director);
            intent.putExtra("genre", genre);
            intent.putExtra("poster_url", posterUrl);
            intent.putExtra("synopsis", synopsis);
            intent.putExtra("trailer_url", trailerUrl);

            // Start the BookTicketActivity
            startActivity(intent);
        });
    }
}