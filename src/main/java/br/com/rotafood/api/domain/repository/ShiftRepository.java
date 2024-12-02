package br.com.rotafood.api.domain.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.Shift;

public interface ShiftRepository extends JpaRepository<Shift, UUID> {

    List<Shift> findByItemId(UUID itemId);

    List<Shift> findByPizzaId(UUID pizzaId);

    List<Shift> findByStartTimeBeforeAndEndTimeAfter(LocalTime startTime, LocalTime endTime);

    List<Shift> findByMondayTrue();
    List<Shift> findByTuesdayTrue();
    List<Shift> findByWednesdayTrue();
    List<Shift> findByThursdayTrue();
    List<Shift> findByFridayTrue();
    List<Shift> findBySaturdayTrue();
    List<Shift> findBySundayTrue();
}
