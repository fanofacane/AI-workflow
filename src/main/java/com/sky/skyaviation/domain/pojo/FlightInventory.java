package com.sky.skyaviation.domain.pojo;
import lombok.Data;
import java.time.LocalDate;
@Data
public class FlightInventory {
    private Integer inventoryId;
    private String flightNumber;
    private String cabinType;
    private Integer totalSeats;
    private Integer remainingSeats;
    private LocalDate flightDate;

}