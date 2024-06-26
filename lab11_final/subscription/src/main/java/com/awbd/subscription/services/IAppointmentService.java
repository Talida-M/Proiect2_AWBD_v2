package com.awbd.subscription.services;


import com.awbd.subscription.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    Appointment findAppointmentByEmailPacient(String emailPacient);
    Appointment findByEmailPacientAndAppointmentDate(String email, LocalDateTime date);
    Appointment save(Appointment app);
    List<Appointment> findAll();
    boolean delete(Long id);
    Appointment findById(Long id);
}