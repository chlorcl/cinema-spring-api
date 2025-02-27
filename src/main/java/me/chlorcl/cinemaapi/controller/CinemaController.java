package me.chlorcl.cinemaapi.controller;

import me.chlorcl.cinemaapi.model.cinema.Cinema;
import me.chlorcl.cinemaapi.repository.CinemaRepository;
import me.chlorcl.cinemaapi.security.annotation.AdminAuthorization;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CinemaController {
    private final CinemaRepository cinemaRepository;

    public CinemaController(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @QueryMapping
    public List<Cinema> cinemas() {
        return cinemaRepository.findAll();
    }

    @QueryMapping
    public Cinema cinema(@Argument Integer id) {
        return cinemaRepository.findById(id).orElse(null);
    }

    @AdminAuthorization
    @MutationMapping
    public Cinema createCinema(@Argument String name, @Argument String address, @Argument String city, @Argument String postalCode, @Argument String phoneNumber, @Argument String email) {
        Cinema cinema = new Cinema(name, address, city, postalCode, phoneNumber, email);
        return cinemaRepository.save(cinema);
    }

    @AdminAuthorization
    @MutationMapping
    public Cinema updateCinema(@Argument Integer id, @Argument String name, @Argument String address, @Argument String city, @Argument String postalCode, @Argument String phoneNumber, @Argument String email) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow();
        cinema.setName(name);
        cinema.setAddress(address);
        cinema.setCity(city);
        cinema.setPostalCode(postalCode);
        cinema.setPhoneNumber(phoneNumber);
        cinema.setEmail(email);
        return cinemaRepository.save(cinema);
    }

    @AdminAuthorization
    @MutationMapping
    public Cinema deleteCinema(@Argument Integer id) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow();
        cinemaRepository.delete(cinema);
        return cinema;
    }
}
