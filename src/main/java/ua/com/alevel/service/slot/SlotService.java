package ua.com.alevel.service.slot;

import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;

public interface SlotService extends BaseCrudService<Slot> {
    List<Slot> findAll();
}
