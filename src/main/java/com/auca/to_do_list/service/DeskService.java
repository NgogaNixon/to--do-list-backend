package com.auca.to_do_list.service;

import com.auca.to_do_list.model.Desk;
import com.auca.to_do_list.repository.DeskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeskService {

    @Autowired
    private DeskRepository deskRepository;

    public String saveDesk(Desk desk) {
        deskRepository.save(desk);
        return "Desk saved successfully";
    }

    public List<Desk> getAllDesks() {
        return deskRepository.findAll();
    }

    public Optional<Desk> getDeskById(UUID id) {
        return deskRepository.findById(id);
    }

    public String updateDesk(UUID id, Desk newDesk) {
        Optional<Desk> found = deskRepository.findById(id);
        if (found.isEmpty()) {
            return "The desk with that id does not exist";
        }
        Desk desk = found.get();
        desk.setDeskNumber(newDesk.getDeskNumber());
        desk.setName(newDesk.getName());
        deskRepository.save(desk);
        return "Desk updated successfully";
    }

    public String deleteDesk(UUID id) {
        Optional<Desk> found = deskRepository.findById(id);
        if (found.isEmpty()) {
            return "The desk with that id does not exist";
        }
        deskRepository.deleteById(id);
        return "Desk deleted successfully";
    }

    // C2: desks that nobody uses
    public List<Desk> getFreeDesks() {
        return deskRepository.findFreeDesks();
    }
}