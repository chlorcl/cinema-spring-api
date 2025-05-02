package me.chlorcl.cinemaapi.controller;

import me.chlorcl.cinemaapi.model.room.Room;
import me.chlorcl.cinemaapi.model.screening.Screening;
import me.chlorcl.cinemaapi.model.seat.Seat;
import me.chlorcl.cinemaapi.model.seat.SeatStatus;
import me.chlorcl.cinemaapi.model.seat.SeatType;
import me.chlorcl.cinemaapi.repository.RoomRepository;
import me.chlorcl.cinemaapi.repository.ScreeningRepository;
import me.chlorcl.cinemaapi.repository.SeatRepository;
import me.chlorcl.cinemaapi.security.annotation.AdminAuthorization;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SeatController {
    private final SeatRepository seatRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;

    public SeatController(SeatRepository seatRepository, RoomRepository roomRepository, ScreeningRepository screeningRepository) {
        this.seatRepository = seatRepository;
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
    }

    @QueryMapping
    public List<Seat> seats() {
        return seatRepository.findAll();
    }

    @QueryMapping
    public Seat seat(@Argument Integer id) {
        return seatRepository.findById(id).orElse(null);
    }

    @AdminAuthorization
    @MutationMapping
    public Seat createSeat(@Argument Integer row, @Argument Integer number, @Argument SeatType type, @Argument SeatStatus status, @Argument Float price) {
        Seat seat = new Seat(row, number, type, status, price);
        return seatRepository.save(seat);
    }

    @AdminAuthorization
    @MutationMapping
    public Seat updateSeat(@Argument Integer id, @Argument Integer row, @Argument Integer number, @Argument SeatType type, @Argument SeatStatus status, @Argument Float price) {
        Seat seat = seatRepository.findById(id).orElseThrow();
        seat.setRow(row);
        seat.setNumber(number);
        seat.setType(type);
        seat.setStatus(status);
        seat.setPrice(price);
        return seatRepository.save(seat);
    }

    @AdminAuthorization
    @MutationMapping
    public Seat deleteSeat(@Argument Integer id) {
        Seat seat = seatRepository.findById(id).orElseThrow();
        seatRepository.delete(seat);
        return seat;
    }

    @AdminAuthorization
    @MutationMapping
    public Seat createSeatForRoom(@Argument Integer row, @Argument Integer number, @Argument SeatType type, @Argument SeatStatus status, @Argument Float price, @Argument Integer roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        Seat seat = new Seat(row, number, type, status, price);
        seat.setRoom(room);
        return seatRepository.save(seat);
    }

}
