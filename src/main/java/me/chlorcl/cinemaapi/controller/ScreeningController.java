package me.chlorcl.cinemaapi.controller;

import me.chlorcl.cinemaapi.model.screening.Screening;
import me.chlorcl.cinemaapi.model.screening.ScreeningStatus;
import me.chlorcl.cinemaapi.model.movie.Movie;
import me.chlorcl.cinemaapi.model.cinema.Cinema;
import me.chlorcl.cinemaapi.model.room.Room;
import me.chlorcl.cinemaapi.model.seat.Seat;
import me.chlorcl.cinemaapi.repository.ScreeningRepository;
import me.chlorcl.cinemaapi.repository.MovieRepository;
import me.chlorcl.cinemaapi.repository.CinemaRepository;
import me.chlorcl.cinemaapi.repository.OrderItemRepository;
import me.chlorcl.cinemaapi.repository.RoomRepository;
import me.chlorcl.cinemaapi.repository.SeatRepository;
import me.chlorcl.cinemaapi.repository.TicketRepository;
import me.chlorcl.cinemaapi.security.annotation.AdminAuthorization;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Controller
public class ScreeningController {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final OrderItemRepository orderItemRepository;

    public ScreeningController(ScreeningRepository screeningRepository, MovieRepository movieRepository, CinemaRepository cinemaRepository, RoomRepository roomRepository, SeatRepository seatRepository, TicketRepository ticketRepository, OrderItemRepository orderItemRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.cinemaRepository = cinemaRepository;
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.orderItemRepository = orderItemRepository;
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
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new IllegalArgumentException("Cinema not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (room.getCinema() == null || !room.getCinema().getId().equals(cinema.getId())) {
            throw new IllegalArgumentException("Room does not belong to the specified cinema");
        }

        List<Screening> overlappingScreenings = screeningRepository.findByRoomAndDateAndTime(room, date, time);
        if (!overlappingScreenings.isEmpty()) {
            throw new IllegalArgumentException("There is already a screening in this room at the specified time");
        }

        Screening screening = new Screening(movie, cinema, room, date, time, status);
        screening = screeningRepository.save(screening);

        return screening;
    }

    @AdminAuthorization
    @MutationMapping
    public Screening updateScreening(@Argument Integer id, @Argument Integer movieId, @Argument Integer cinemaId, @Argument Integer roomId, @Argument String date, @Argument String time, @Argument ScreeningStatus status) {
        Screening screening = screeningRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Screening not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new IllegalArgumentException("Cinema not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (room.getCinema() == null || !room.getCinema().getId().equals(cinema.getId())) {
            throw new IllegalArgumentException("Room does not belong to the specified cinema");
        }

        List<Screening> overlappingScreenings = screeningRepository.findByRoomAndDateAndTime(room, date, time);
        overlappingScreenings.removeIf(s -> s.getId().equals(id));
        if (!overlappingScreenings.isEmpty()) {
            throw new IllegalArgumentException("There is already a screening in this room at the specified time");
        }

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
    @Transactional
    public Screening deleteScreening(@Argument Integer id) {
        Screening screening = screeningRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Screening not found"));

        orderItemRepository.deleteByScreeningId(id);
        ticketRepository.deleteAllByScreeningId(id);
        screeningRepository.delete(screening);
        return screening;
    }

    @MutationMapping
    public Set<Seat> getScreeningSeats(@Argument Integer screeningId) {
        Screening screening = screeningRepository.findById(screeningId)
            .orElseThrow(() -> new IllegalArgumentException("Screening not found"));
        return screening.getSeats();
    }
}
