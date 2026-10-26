package com.auca.to_do_list.controller;

import com.auca.to_do_list.model.Desk;
import com.auca.to_do_list.service.DeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/desks")
public class DeskController {

    @Autowired
    private DeskService deskService;

    @PostMapping("/save")
    public ResponseEntity<String> saveDesk(@RequestBody Desk desk) {
        String message = deskService.saveDesk(desk);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Desk>> getAllDesks() {
        return new ResponseEntity<>(deskService.getAllDesks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeskById(@PathVariable UUID id) {
        Optional<Desk> desk = deskService.getDeskById(id);
        if (desk.isEmpty()) {
            return new ResponseEntity<>("The desk with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(desk.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDesk(@PathVariable UUID id, @RequestBody Desk desk) {
        String message = deskService.updateDesk(id, desk);
        if (message.equals("The desk with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDesk(@PathVariable UUID id) {
        String message = deskService.deleteDesk(id);
        if (message.equals("The desk with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // C2: desks that nobody uses
    @GetMapping("/free")
    public ResponseEntity<List<Desk>> getFreeDesks() {
        return new ResponseEntity<>(deskService.getFreeDesks(), HttpStatus.OK);
    }
}