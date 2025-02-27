package me.chlorcl.cinemaapi.repository;

import me.chlorcl.cinemaapi.model.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
}