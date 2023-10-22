package com.example.demo;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor firstDoctor;

    private Patient firstPatient;

    private Room firstRoom;

    private Appointment firstAppointment;
    private Appointment secondAppointment;
    private Appointment thirdAppointment;

    @BeforeEach
    void setUp() {
        firstDoctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        firstPatient = new Patient("Anastasia", "Rubio Hernández", 55, "anastasia.rubio.hernandez@mail.com");
        firstRoom = new Room("Oncology");
    }


    @Test
    void shouldCreateDoctor() {
        firstDoctor.setId(1);
        assertThat(firstDoctor).isNotNull();
        assertThat(firstDoctor.getId()).isEqualTo(1);
        assertThat(firstDoctor.getFirstName()).isEqualTo("Juan");
        assertThat(firstDoctor.getLastName()).isEqualTo("Perez Dominguez");
        assertThat(firstDoctor.getAge()).isEqualTo(55);
        assertThat(firstDoctor.getEmail()).isEqualTo("juan.perez.dominguez@doctoresSalud.es");
    }

    @Test
    void shouldCreatePatient() {
        firstPatient.setId(1);
        assertThat(firstPatient).isNotNull();
        assertThat(firstPatient.getId()).isEqualTo(1);
        assertThat(firstPatient.getFirstName()).isEqualTo("Anastasia");
        assertThat(firstPatient.getLastName()).isEqualTo("Rubio Hernández");
        assertThat(firstPatient.getAge()).isEqualTo(55);
        assertThat(firstPatient.getEmail()).isEqualTo("anastasia.rubio.hernandez@mail.com");
    }

    @Test
    void shouldCreateRoom() {
        assertThat(firstRoom).isNotNull();
        assertThat(firstRoom.getRoomName()).isEqualTo("Oncology");
    }

    @Test
    void shouldCreateAppointment() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        Doctor tempDoctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Patient tempPatient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Room tempRoom = new Room("Oncology");

        firstAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoom,
                LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("19:00 24/04/2023", formatter));
        firstAppointment.setId(1);

        assertThat(firstAppointment).isNotNull();
        assertThat(firstAppointment.getId()).isEqualTo(1);
        assertThat(firstAppointment.getPatient()).isEqualTo(tempPatient);
        assertThat(firstAppointment.getDoctor()).isEqualTo(tempDoctor);
        assertThat(firstAppointment.getRoom()).isEqualTo(tempRoom);
        assertThat(firstAppointment.getStartsAt()).isEqualTo(LocalDateTime.parse("19:00 24/04/2023", formatter));
        assertThat(firstAppointment.getFinishesAt()).isEqualTo(LocalDateTime.parse("19:00 24/04/2023", formatter));
    }

    @Test
    void appointmentOverlapsDifferentRoomNames() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        Doctor tempDoctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Patient tempPatient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Room tempRoomOne = new Room("Oncology");
        Room tempRoomTwo = new Room("Dermatology");

        secondAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoomOne,
                LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("20:00 24/04/2023", formatter));

        thirdAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoomTwo,
                LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("20:00 24/04/2023", formatter));

        assertThat(secondAppointment.overlaps(thirdAppointment)).isFalse();

    }

    @Test
    void appointmentOverlapsSameRoomNamesDifferentTimes(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        Doctor tempDoctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Patient tempPatient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Room tempRoomOne = new Room("Oncology");
        Room tempRoomTwo = new Room("Oncology");

        secondAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoomOne,
                LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("20:00 24/04/2023", formatter));

        thirdAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoomTwo,
                LocalDateTime.parse("20:00 24/04/2023", formatter),
                LocalDateTime.parse("21:00 24/04/2023", formatter));

        assertThat(secondAppointment.overlaps(thirdAppointment)).isFalse();

    }

    @Test
    void appointmentOverlapsSameRoomNamesSameStartEndHour(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        Doctor tempDoctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Patient tempPatient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Room tempRoomOne = new Room("Oncology");
        Room tempRoomTwo = new Room("Oncology");

        secondAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoomOne,
                LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("20:00 24/04/2023", formatter));

        thirdAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoomTwo,
                LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("20:00 24/04/2023", formatter));

        assertThat(secondAppointment.overlaps(thirdAppointment)).isTrue();
    }


    @Test
    void appointmentOverlapsSameRoomNamesRoomOverlappingHoursOne(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        Doctor tempDoctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Patient tempPatient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Room tempRoomOne = new Room("Oncology");
        Room tempRoomTwo = new Room("Oncology");

        secondAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoomOne,
                LocalDateTime.parse("19:30 24/04/2023", formatter),
                LocalDateTime.parse("20:30 24/04/2023", formatter));

        thirdAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoomTwo,
                LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("20:00 24/04/2023", formatter));

        assertThat(secondAppointment.overlaps(thirdAppointment)).isTrue();
    }

    @Test
    void appointmentOverlapsSameRoomNamesRoomOverlappingHoursTwo(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        Doctor tempDoctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Patient tempPatient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Room tempRoomOne = new Room("Oncology");
        Room tempRoomTwo = new Room("Oncology");

        secondAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoomOne,
                LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("20:30 24/04/2023", formatter));

        thirdAppointment = new Appointment(tempPatient,
                tempDoctor,
                tempRoomTwo,
                LocalDateTime.parse("19:30 24/04/2023", formatter),
                LocalDateTime.parse("21:00 24/04/2023", formatter));

        assertThat(secondAppointment.overlaps(thirdAppointment)).isTrue();
    }


}
