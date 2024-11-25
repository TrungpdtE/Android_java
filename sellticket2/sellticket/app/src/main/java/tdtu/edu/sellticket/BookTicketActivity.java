package tdtu.edu.sellticket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BookTicketActivity extends AppCompatActivity {

    private TextView filmNameTextView; // Declare the TextView for film name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);

        // Initialize the TextView
        filmNameTextView = findViewById(R.id.film_name_book);

        // Retrieve data from the intent
        Intent intent = getIntent();
        String documentId = intent.getStringExtra("documentId");
        String title = intent.getStringExtra("title");
        String duration = intent.getStringExtra("duration");
        String releaseDate = intent.getStringExtra("release_date");
        String actors = intent.getStringExtra("actors");
        String director = intent.getStringExtra("director");
        String genre = intent.getStringExtra("genre");
        String posterUrl = intent.getStringExtra("poster_url");
        String synopsis = intent.getStringExtra("synopsis");
        String trailerUrl = intent.getStringExtra("trailer_url");

        // Set the movie title to the TextView
        filmNameTextView.setText(title);

        // Use the other retrieved data as needed
    }
}