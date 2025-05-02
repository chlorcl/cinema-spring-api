package me.chlorcl.cinemaapi.repository;

import me.chlorcl.cinemaapi.model.room.Room;
import me.chlorcl.cinemaapi.model.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    List<Screening> findByRoomAndDateAndTime(Room room, String date, String time);
    void deleteAllByRoomId(Integer roomId);
}
