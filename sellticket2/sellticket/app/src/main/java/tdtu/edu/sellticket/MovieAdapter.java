package tdtu.edu.sellticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements Filterable {

    private final List<Movie> movieList; // Original list of movies
    private final List<Movie> movieListFull; // List for filtering

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
        this.movieListFull = new ArrayList<>(movieList); // Create a copy for filtering
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.textViewTitle.setText(movie.getTitle());
        holder.textViewActor.setText(movie.getActors());
        holder.textViewGenre.setText(movie.getGenre());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private final Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredMovies = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredMovies.addAll(movieListFull); // No filter applied
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Movie movie : movieListFull) {
                    // Check if title or genre matches the filter pattern
                    if (movie.getTitle().toLowerCase().contains(filterPattern) ||
                            movie.getGenre().toLowerCase().contains(filterPattern)) {
                        filteredMovies.add(movie);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredMovies;
            return results;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movieList.clear();
            movieList.addAll((List) results.values);
            notifyDataSetChanged(); // Notify the adapter to refresh the list
        }
    };

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;
        public TextView textViewActor;
        public TextView textViewGenre;
        public ImageView imageViewPoster; // ImageView for the movie poster

        public MovieViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle); // Assuming you have a TextView for title
            textViewActor = itemView.findViewById(R.id.textViewActor); // Assuming you have a TextView for actors
            textViewGenre = itemView.findViewById(R.id.textViewGenre); // Assuming you have a TextView for genre
        }
    }
}