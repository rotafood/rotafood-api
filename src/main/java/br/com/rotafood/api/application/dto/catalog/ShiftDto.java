package br.com.rotafood.api.application.dto.catalog;

import br.com.rotafood.api.domain.entity.catalog.Shift;

public record ShiftDto(
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
