package me.chlorcl.cinemaapi.repository;

import me.chlorcl.cinemaapi.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
}