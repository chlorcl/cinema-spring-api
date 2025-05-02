package me.chlorcl.cinemaapi.configuration;

import me.chlorcl.cinemaapi.model.seat.SeatType;
import me.chlorcl.cinemaapi.model.ticket.TicketPriceConfig;
import me.chlorcl.cinemaapi.model.ticket.TicketType;
import me.chlorcl.cinemaapi.repository.TicketPriceConfigRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TicketPriceConfigRepository ticketPriceConfigRepository;

    public DataInitializer(TicketPriceConfigRepository ticketPriceConfigRepository) {
        this.ticketPriceConfigRepository = ticketPriceConfigRepository;
    }

    @Override
    public void run(String... args) {
        Optional<TicketPriceConfig> existingConfig = ticketPriceConfigRepository
                .findBySeatTypeAndTicketTypeAndIsActiveTrue(SeatType.STANDARD, TicketType.STANDARD);

        if (existingConfig.isEmpty()) {
            TicketPriceConfig defaultConfig = new TicketPriceConfig(
                    SeatType.STANDARD,
                    TicketType.STANDARD,
                    10.0f,
                    2.0f,
                    3.0f,
                    "Default configuration for STANDARD seat type and STANDARD ticket type",
                    true
            );
            ticketPriceConfigRepository.save(defaultConfig);
            System.out.println("Created default ticket price configuration for STANDARD seat type and STANDARD ticket type");
        }

        existingConfig = ticketPriceConfigRepository
                .findBySeatTypeAndTicketTypeAndIsActiveTrue(SeatType.VIP, TicketType.STANDARD);

        if (existingConfig.isEmpty()) {
            TicketPriceConfig defaultConfig = new TicketPriceConfig(
                    SeatType.VIP,
                    TicketType.STANDARD,
                    15.0f,
                    3.0f,
                    4.0f,
                    "Default configuration for VIP seat type and STANDARD ticket type",
                    true
            );
            ticketPriceConfigRepository.save(defaultConfig);
            System.out.println("Created default ticket price configuration for VIP seat type and STANDARD ticket type");
        }

        existingConfig = ticketPriceConfigRepository
                .findBySeatTypeAndTicketTypeAndIsActiveTrue(SeatType.STANDARD, TicketType.VIP);

        if (existingConfig.isEmpty()) {
            TicketPriceConfig defaultConfig = new TicketPriceConfig(
                    SeatType.STANDARD,
                    TicketType.VIP,
                    12.0f,
                    2.5f,
                    3.5f,
                    "Default configuration for STANDARD seat type and VIP ticket type",
                    true
            );
            ticketPriceConfigRepository.save(defaultConfig);
            System.out.println("Created default ticket price configuration for STANDARD seat type and VIP ticket type");
        }

        existingConfig = ticketPriceConfigRepository
                .findBySeatTypeAndTicketTypeAndIsActiveTrue(SeatType.VIP, TicketType.VIP);

        if (existingConfig.isEmpty()) {
            TicketPriceConfig defaultConfig = new TicketPriceConfig(
                    SeatType.VIP,
                    TicketType.VIP,
                    18.0f,
                    4.0f,
                    5.0f,
                    "Default configuration for VIP seat type and VIP ticket type",
                    true
            );
            ticketPriceConfigRepository.save(defaultConfig);
            System.out.println("Created default ticket price configuration for VIP seat type and VIP ticket type");
        }
    }
}