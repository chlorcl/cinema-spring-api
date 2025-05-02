package me.chlorcl.cinemaapi.model.screening;

import jakarta.persistence.*;
import me.chlorcl.cinemaapi.model.cinema.Cinema;
import me.chlorcl.cinemaapi.model.movie.Movie;
import me.chlorcl.cinemaapi.model.room.Room;
import me.chlorcl.cinemaapi.model.seat.Seat;
import me.chlorcl.cinemaapi.model.ticket.Ticket;
import org.hibernate.annotations.OnDelete;

import java.util.Set;

import static org.hibernate.annotations.OnDeleteAction.SET_NULL;

@Entity
@Table(name = "screening")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Cinema cinema;
    @ManyToOne
    @OnDelete(action = SET_NULL)
    private Room room;
    private String date;
    private String time;
    @OneToMany(mappedBy = "screening")
    private Set<Ticket> tickets;
    @Enumerated(EnumType.STRING)
    private ScreeningStatus status;

    public Screening(Movie movie, Cinema cinema, Room room, String date, String time, ScreeningStatus status) {
        this.movie = movie;
        this.cinema = cinema;
        this.room = room;
        this.date = date;
        this.time = time;
        this.status = status;
    }


    public Screening() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }


    public ScreeningStatus getStatus() {
        return status;
    }

    public void setStatus(ScreeningStatus status) {
        this.status = status;
    }

    /**
     * Returns the seats of the room associated with this screening.
     * This method is used by GraphQL to resolve the 'seats' field in the Screening type.
     * @return the seats of the room associated with this screening
     */
    public Set<Seat> getSeats() {
        return room != null ? room.getSeats() : null;
    }
}
