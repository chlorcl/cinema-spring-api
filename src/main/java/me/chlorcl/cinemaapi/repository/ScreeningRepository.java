package me.chlorcl.cinemaapi.repository;

import me.chlorcl.cinemaapi.model.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
}