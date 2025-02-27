package me.chlorcl.cinemaapi.model.seat;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import me.chlorcl.cinemaapi.model.room.Room;
import me.chlorcl.cinemaapi.model.screening.Screening;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer row;
    private Integer number;
    private SeatType type;
    private SeatStatus status;
    private Float price;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;

    public Seat(Integer row, Integer number, SeatType type, SeatStatus status, Float price) {
        this.row = row;
        this.number = number;
        this.type = type;
        this.status = status;
        this.price = price;
    }

    public Seat() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}