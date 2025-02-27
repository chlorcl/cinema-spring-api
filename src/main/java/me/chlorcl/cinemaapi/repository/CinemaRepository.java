package me.chlorcl.cinemaapi.repository;

import me.chlorcl.cinemaapi.model.cinema.Cinema;
import me.chlorcl.cinemaapi.security.annotation.AdminAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
}