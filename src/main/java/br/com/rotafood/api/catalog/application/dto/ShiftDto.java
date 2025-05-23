package br.com.rotafood.api.catalog.application.dto;

import java.util.UUID;

import br.com.rotafood.api.catalog.domain.entity.Shift;

public record ShiftDto(
    UUID id,
    String startTime,
    String endTime,
    boolean monday,
    boolean tuesday,
    boolean wednesday,
    boolean thursday,
    boolean friday,
    boolean saturday,
    boolean sunday
) {
    public ShiftDto(Shift shift) {
        this(
            shift.getId(),
            shift.getStartTime().toString(),
            shift.getEndTime().toString(),
            shift.getMonday(),
            shift.getTuesday(),
            shift.getWednesday(),
            shift.getThursday(),
            shift.getFriday(),
            shift.getSaturday(),
            shift.getSunday()
        );
    }
}
