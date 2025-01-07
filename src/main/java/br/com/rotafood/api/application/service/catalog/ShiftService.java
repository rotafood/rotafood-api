package br.com.rotafood.api.application.service.catalog;

import br.com.rotafood.api.application.dto.catalog.ShiftDto;
import br.com.rotafood.api.domain.entity.catalog.Shift;
import br.com.rotafood.api.domain.repository.ShiftRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Transactional
    public List<Shift> getShiftsByItem(UUID itemId) {
        return shiftRepository.findByItemId(itemId);
    }

    @Transactional
    public List<Shift> getShiftsByMerchant(UUID merchantId) {
        return shiftRepository.findByMerchantId(merchantId);
    }

    @Transactional
    public Shift updateOrCreate(ShiftDto shiftDto) {
        Shift shift = shiftDto.id() != null
                ? shiftRepository.findById(shiftDto.id())
                  .orElse(new Shift())
                : new Shift();

        shift.setStartTime(LocalTime.parse(shiftDto.startTime()));
        shift.setEndTime(LocalTime.parse(shiftDto.endTime()));
        shift.setMonday(shiftDto.monday());
        shift.setTuesday(shiftDto.tuesday());
        shift.setWednesday(shiftDto.wednesday());
        shift.setThursday(shiftDto.thursday());
        shift.setFriday(shiftDto.friday());
        shift.setSaturday(shiftDto.saturday());
        shift.setSunday(shiftDto.sunday());

        return shiftRepository.save(shift); 
    }

    @Transactional
    public List<Shift> updateOrCreateAll(List<ShiftDto> shiftDtos) {
        if (shiftDtos == null || shiftDtos.isEmpty()) {
            return List.of();
        }

        return shiftDtos.stream()
                .map(this::updateOrCreate)
                .toList();
    }

    @Transactional
    public void deleteShift(UUID shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new EntityNotFoundException("Shift não encontrado."));
        shiftRepository.delete(shift);
    }

    @Transactional
    public void deleteShiftsByRelatedEntity(UUID relatedId, String relatedType) {
        switch (relatedType.toLowerCase()) {
            case "item" -> shiftRepository.deleteByItemId(relatedId);
            case "merchant" -> shiftRepository.deleteByMerchantId(relatedId);
            default -> throw new IllegalArgumentException("Tipo de entidade relacionado inválido.");
        }
    }


    @Transactional
    public void deleteAll(List<Shift> shifts) {
        this.shiftRepository.deleteAll(shifts);
    }
}

