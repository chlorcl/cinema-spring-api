package me.chlorcl.cinemaapi.controller;

import me.chlorcl.cinemaapi.model.movie.Movie;
import me.chlorcl.cinemaapi.repository.MovieRepository;
import me.chlorcl.cinemaapi.security.annotation.AdminAuthorization;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {
    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @QueryMapping
    @GetMapping("/movies")
    public List<Movie> movies() {
        return movieRepository.findAll();
    }

    @QueryMapping
    @GetMapping("/movie/{id}")
    public Movie movie(@Argument @PathVariable Integer id) {
        return movieRepository.findById(id).orElse(null);
    }

    @AdminAuthorization
    @MutationMapping
    public Movie createMovie(@Argument String title, @Argument String description, @Argument Integer duration, @Argument String genre, @Argument String releaseDate, @Argument String director, @Argument List<String> actors, @Argument Float rating, @Argument String poster, @Argument String trailer) {
        Movie movie = new Movie(title, description, duration, genre, releaseDate, director, actors, rating, poster, trailer);
        return movieRepository.save(movie);
    }

    @AdminAuthorization
    @MutationMapping
    public Movie updateMovie(@Argument Integer id, @Argument String title, @Argument String description, @Argument Integer duration, @Argument String genre, @Argument String releaseDate, @Argument String director, @Argument List<String> actors, @Argument Float rating, @Argument String poster, @Argument String trailer) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setDuration(duration);
        movie.setGenre(genre);
        movie.setReleaseDate(releaseDate);
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setRating(rating);
        movie.setPoster(poster);
        movie.setTrailer(trailer);
        return movieRepository.save(movie);
    }

    @AdminAuthorization
    @MutationMapping
    public Movie deleteMovie(@Argument Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movieRepository.delete(movie);
        return movie;
    }
}