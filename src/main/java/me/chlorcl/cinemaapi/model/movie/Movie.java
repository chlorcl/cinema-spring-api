package me.chlorcl.cinemaapi.model.movie;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Integer duration;
    private String genre;
    private String director;
    private String releaseDate;
    private Float rating;
    private String poster;
    private String trailer;

    @CollectionTable(name = "movie_actors", joinColumns = @JoinColumn(name = "movie_id"))
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> actors;

    public Movie(String title, String description, Integer duration, String genre, String releaseDate, String director, List<String> actors, Float rating, String poster, String trailer) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.director = director;
        this.actors = actors;
        this.rating = rating;
        this.poster = poster;
        this.trailer = trailer;
    }

    public Movie() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }
}
