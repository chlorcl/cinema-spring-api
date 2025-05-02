package me.chlorcl.cinemaapi.controller;

import me.chlorcl.cinemaapi.model.room.Room;
import me.chlorcl.cinemaapi.model.room.RoomSizeType;
import me.chlorcl.cinemaapi.model.room.RoomType;
import me.chlorcl.cinemaapi.repository.RoomRepository;
import me.chlorcl.cinemaapi.repository.ScreeningRepository;
import me.chlorcl.cinemaapi.repository.SeatRepository;
import me.chlorcl.cinemaapi.security.annotation.AdminAuthorization;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Controller
public class RoomController {
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;

    public RoomController(RoomRepository roomRepository, ScreeningRepository screeningRepository, SeatRepository seatRepository) {
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
    }

    @QueryMapping
    public List<Room> rooms() {
        return roomRepository.findAll();
    }

    @QueryMapping
    public Room room(@Argument Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    @AdminAuthorization
    @MutationMapping
    public Room createRoom(@Argument String name, @Argument RoomType type, @Argument RoomSizeType sizeType, @Argument Integer capacity) {
        Room room = new Room(name, type, sizeType, capacity);
        return roomRepository.save(room);
    }

    @AdminAuthorization
    @MutationMapping
    public Room updateRoom(@Argument Integer id, @Argument String name, @Argument RoomType type, @Argument RoomSizeType sizeType, @Argument Integer capacity) {
        Room room = roomRepository.findById(id).orElseThrow();
        room.setName(name);
        room.setType(type);
        room.setSizeType(sizeType);
        room.setCapacity(capacity);
        return roomRepository.save(room);
    }

    @AdminAuthorization
    @MutationMapping
    @Transactional
    public Room deleteRoom(@Argument Integer id) {
        Room room = roomRepository.findById(id).orElseThrow();
        screeningRepository.deleteAllByRoomId(id);
        seatRepository.deleteAllByRoomId(id);
        roomRepository.delete(room);
        return room;
    }
}
