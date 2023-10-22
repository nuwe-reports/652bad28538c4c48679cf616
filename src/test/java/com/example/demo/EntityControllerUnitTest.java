
package com.example.demo;

import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetNotDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoDoctors() throws Exception {
        Doctor doctor1 = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        Doctor doctor2 = new Doctor("Ana", "Fuentes Martín", 58, "ana.fuentes.martin@doctoresSalud.es");
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor1, doctor2));
        mockMvc.perform(get("/api/doctors"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldGetDoctorById() throws Exception {
        Doctor doctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        doctor.setId(1);

        Optional<Doctor> optionalDoctor = Optional.of(doctor);

        assertThat(optionalDoctor).isPresent();
        assertThat(optionalDoctor.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(optionalDoctor);
        mockMvc.perform(get("/api/doctors/" + doctor.getId()))
            .andExpect(status().isOk());
    }

    @Test
    void shouldGetNotDoctorByID() throws Exception {
        final long INCORRECT_ID = 2;

        Doctor doctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        doctor.setId(1);

        Optional<Doctor> doctorOptional = Optional.of(doctor);

        when(doctorRepository.findById(doctor.getId())).thenReturn(doctorOptional);
        mockMvc.perform(get("/api/doctors/" + INCORRECT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateDoctor() throws Exception{
        Doctor doctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(doctor)))
            .andExpect(status().isCreated());
    }

    @Test
    void shouldNotDeleteDoctorById() throws Exception{
        final long INCORRECT_ID = 2;
        Doctor doctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");

        doctor.setId(1);

        Optional<Doctor> doctorOptional = Optional.of(doctor);

        assertThat(doctorOptional).isPresent();
        assertThat(doctorOptional.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(doctorOptional);
        mockMvc.perform(delete("/api/doctors/" + INCORRECT_ID))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteDoctorById() throws Exception {
        Doctor doctor = new Doctor("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        doctor.setId(1);

        Optional <Doctor> doctorOptional = Optional.of(doctor);

        assertThat(doctorOptional).isPresent();
        assertThat(doctorOptional.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(doctorOptional);
        mockMvc.perform(delete("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAllDoctors() throws Exception{
        List<Doctor> doctors = new ArrayList<>();

        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(delete("/api/doctors"))
            .andExpect(status().isOk());
    }

}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetNotPatients() throws Exception {
        List<Patient> patients = new ArrayList<>();
        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoPatients() throws Exception {
        Patient patient1 = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@pacientesSalud.es");
        Patient patient2 = new Patient("Ana", "Fuentes Martín", 58, "ana.fuentes.martin@doctoresSalud.es");
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetPatientsById() throws Exception {
        Patient patient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        patient.setId(1);

        Optional<Patient> optionalPatient = Optional.of(patient);

        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(optionalPatient);
        mockMvc.perform(get("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetNotPatientByID() throws Exception {
        final long INCORRECT_ID = 2;

        Patient patient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        patient.setId(1);

        Optional<Patient> optionalPatient = Optional.of(patient);

        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(optionalPatient);
        mockMvc.perform(get("/api/patients/" + INCORRECT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreatePatient() throws Exception{
        Patient patient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotDeletePatientById() throws Exception{
        final long INCORRECT_ID = 2;
        Patient patient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");

        patient.setId(1);

        Optional<Patient> optionalPatient = Optional.of(patient);

        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(optionalPatient);
        mockMvc.perform(delete("/api/patients/" + INCORRECT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeletePatientById() throws Exception {
        Patient patient = new Patient("Juan", "Perez Dominguez", 55, "juan.perez.dominguez@doctoresSalud.es");
        patient.setId(1);

        Optional <Patient> optionalPatient = Optional.of(patient);

        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(optionalPatient);
        mockMvc.perform(delete("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAllPatients() throws Exception{
        List<Patient> patients = new ArrayList<>();

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }
}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetNotPatients() throws Exception {
        List<Room> rooms = new ArrayList<>();
        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoPatients() throws Exception {
        Room room1 = new Room("Oncology");
        Room room2 = new Room("Dermatology");
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room1, room2));
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetPatientsById() throws Exception {
        Room room = new Room("Oncology");

        Optional<Room> optionalRoom = Optional.of(room);

        assertThat(optionalRoom).isPresent();
        assertThat(optionalRoom.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("Oncology");

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(optionalRoom);
        mockMvc.perform(get("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetNotPatientByID() throws Exception {
        final String INCORRECT_ROOM_NAME = "Dermatology";
        Room room = new Room("Oncology");

        Optional<Room> optionalRoom = Optional.of(room);

        assertThat(optionalRoom).isPresent();
        assertThat(optionalRoom.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("Oncology");

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(optionalRoom);
        mockMvc.perform(get("/api/rooms/" + INCORRECT_ROOM_NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreatePatient() throws Exception{
        Room room = new Room("Oncology");
        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotDeletePatientById() throws Exception{
        final String INCORRECT_ROOM_NAME = "Dermatology";
        Room room = new Room("Oncology");

        Optional<Room> optionalPatient = Optional.of(room);

        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("Oncology");

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(optionalPatient);
        mockMvc.perform(delete("/api/rooms/" + INCORRECT_ROOM_NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeletePatientById() throws Exception {
        Room room = new Room("Oncology");

        Optional <Room> optionalPatient = Optional.of(room);

        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("Oncology");

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(optionalPatient);
        mockMvc.perform(delete("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAllPatients() throws Exception{
        List<Room> rooms = new ArrayList<>();

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }

}
