package me.chlorcl.cinemaapi.controller;

import me.chlorcl.cinemaapi.model.screening.Screening;
import me.chlorcl.cinemaapi.model.screening.ScreeningStatus;
import me.chlorcl.cinemaapi.model.movie.Movie;
import me.chlorcl.cinemaapi.model.cinema.Cinema;
import me.chlorcl.cinemaapi.model.room.Room;
import me.chlorcl.cinemaapi.repository.ScreeningRepository;
import me.chlorcl.cinemaapi.repository.MovieRepository;
import me.chlorcl.cinemaapi.repository.CinemaRepository;
import me.chlorcl.cinemaapi.repository.RoomRepository;
import me.chlorcl.cinemaapi.security.annotation.AdminAuthorization;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class ScreeningController {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final RoomRepository roomRepository;

    public ScreeningController(ScreeningRepository screeningRepository, MovieRepository movieRepository, CinemaRepository cinemaRepository, RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.cinemaRepository = cinemaRepository;
        this.roomRepository = roomRepository;
    }

    @QueryMapping
    public List<Screening> screenings() {
        return screeningRepository.findAll();
    }

    @QueryMapping
    public Screening screening(@Argument Integer id) {
        return screeningRepository.findById(id).orElse(null);
    }

    @AdminAuthorization
    @MutationMapping
    public Screening createScreening(@Argument Integer movieId, @Argument Integer cinemaId, @Argument Integer roomId, @Argument String date, @Argument String time, @Argument ScreeningStatus status) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow();
        Room room = roomRepository.findById(roomId).orElseThrow();
        Screening screening = new Screening(movie, cinema, room, date, time, status);
        return screeningRepository.save(screening);
    }

    @AdminAuthorization
    @MutationMapping
    public Screening updateScreening(@Argument Integer id, @Argument Integer movieId, @Argument Integer cinemaId, @Argument Integer roomId, @Argument String date, @Argument String time, @Argument ScreeningStatus status) {
        Screening screening = screeningRepository.findById(id).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow();
        Room room = roomRepository.findById(roomId).orElseThrow();
        screening.setMovie(movie);
        screening.setCinema(cinema);
        screening.setRoom(room);
        screening.setDate(date);
        screening.setTime(time);
        screening.setStatus(status);
        return screeningRepository.save(screening);
    }

    @AdminAuthorization
    @MutationMapping
    public Screening deleteScreening(@Argument Integer id) {
        Screening screening = screeningRepository.findById(id).orElseThrow();
        screeningRepository.delete(screening);
        return screening;
    }
}