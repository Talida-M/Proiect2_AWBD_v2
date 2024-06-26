package com.awbd.subscription.repositories;

import com.awbd.subscription.model.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    Appointment findByEmailPacientAndAppointmentDate(String emailPacient, LocalDateTime date);
    Appointment findByEmailPacient(String emailPacient);
}
