package me.chlorcl.cinemaapi.model.ticket;

import jakarta.persistence.*;
import me.chlorcl.cinemaapi.model.seat.SeatType;

@Entity
@Table(name = "ticket_price_config")
public class TicketPriceConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    private Float basePrice;
    private Float weekendSurcharge; // Additional charge for weekend screenings
    private Float holidaySurcharge; // Additional charge for holiday screenings
    private String description;
    private Boolean isActive;

    public TicketPriceConfig() {
    }

    public TicketPriceConfig(SeatType seatType, TicketType ticketType, Float basePrice, Float weekendSurcharge, 
                            Float holidaySurcharge, String description, Boolean isActive) {
        this.seatType = seatType;
        this.ticketType = ticketType;
        this.basePrice = basePrice;
        this.weekendSurcharge = weekendSurcharge;
        this.holidaySurcharge = holidaySurcharge;
        this.description = description;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }

    public Float getWeekendSurcharge() {
        return weekendSurcharge;
    }

    public void setWeekendSurcharge(Float weekendSurcharge) {
        this.weekendSurcharge = weekendSurcharge;
    }

    public Float getHolidaySurcharge() {
        return holidaySurcharge;
    }

    public void setHolidaySurcharge(Float holidaySurcharge) {
        this.holidaySurcharge = holidaySurcharge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Float calculatePrice(Boolean isWeekend, Boolean isHoliday) {
        Float finalPrice = basePrice;
        if (isWeekend && weekendSurcharge != null) {
            finalPrice += weekendSurcharge;
        }
        if (isHoliday && holidaySurcharge != null) {
            finalPrice += holidaySurcharge;
        }
        return finalPrice;
    }
}