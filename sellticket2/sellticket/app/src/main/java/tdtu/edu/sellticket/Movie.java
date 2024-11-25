package tdtu.edu.sellticket;

public class Movie {
    private String documentId; // Unique ID for the document in Firestore
    private String title;       // The title of the movie
    private String actors;      // The actors in the movie
    private String director;    // The director of the movie
    private String duration;// The duration of the movie in minutes
    private String genre;       // The genre of the movie
    private String posterUrl;   // URL of the movie poster
    private String releaseDate; // The release date of the movie
    private String synopsis;     // A brief synopsis of the movie

    // Constructor
    public Movie(String documentId, String title, String actors, String director,
                 String duration, String genre, String posterUrl, String releaseDate,
                 String synopsis) {
        this.documentId = documentId;
        this.title = title;
        this.actors = actors;
        this.director = director;
        this.duration = String.valueOf(duration);
        this.genre = genre;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
    }

    public Movie() {

    }

    public Movie(String title, String genre, String director, String duration, String actors) {

        this.title = title;
        this.genre = genre;
        this.director = director;
        this.duration = duration;
        this.actors = actors;
    }

    // Getters and Setters
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
