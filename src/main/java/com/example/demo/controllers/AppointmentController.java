package com.example.demo.controllers;

import com.example.demo.repositories.*;
import com.example.demo.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {

        List<Appointment> appointments = new ArrayList<>(appointmentRepository.findAll());

        return (appointments.isEmpty()) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        return (appointment.isPresent()) ?
                new ResponseEntity<>(appointment.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/appointment")
    public ResponseEntity<List<Appointment>> createAppointment(@RequestBody Appointment appointment) {

        Appointment currentAppointment = new Appointment(appointment.getPatient(),
                appointment.getDoctor(),
                appointment.getRoom(),
                appointment.getStartsAt(),
                appointment.getFinishesAt());

        Predicate<List<Appointment>> checkAppointmentListsOverlapping = (List<Appointment> savedAppointments) -> savedAppointments
                .stream()
                .anyMatch(appointment::overlaps);

        Predicate<Appointment> checkDateRequestAppointment = (Appointment okAppointment) -> appointment
                .getStartsAt()
                .equals(appointment.getFinishesAt());

        if (checkDateRequestAppointment.test(currentAppointment)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            if (checkAppointmentListsOverlapping.test(appointmentRepository.findAll())) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
                appointmentRepository.save(currentAppointment);
                return new ResponseEntity<>(appointmentRepository.findAll(), HttpStatus.OK);
            }
        }

    }


    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable("id") long id) {

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        return (!appointment.isPresent()) ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/appointments")
    public ResponseEntity<HttpStatus> deleteAllAppointments() {
        appointmentRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
