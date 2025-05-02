package me.chlorcl.cinemaapi.model.room;

import jakarta.persistence.*;
import me.chlorcl.cinemaapi.model.cinema.Cinema;
import me.chlorcl.cinemaapi.model.seat.Seat;

import java.util.Set;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @Enumerated(EnumType.STRING)
    private RoomSizeType sizeType;
    private Integer capacity;

    @OneToMany(mappedBy = "room")
    private Set<Seat> seats;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    public Room(String name, RoomType type, RoomSizeType sizeType, Integer capacity) {
        this.name = name;
        this.type = type;
        this.sizeType = sizeType;
        this.capacity = capacity;
    }

    public Room() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public RoomSizeType getSizeType() {
        return sizeType;
    }

    public void setSizeType(RoomSizeType sizeType) {
        this.sizeType = sizeType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
}
