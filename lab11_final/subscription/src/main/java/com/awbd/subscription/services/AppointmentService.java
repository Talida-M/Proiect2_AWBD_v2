package com.awbd.subscription.services;

import com.awbd.subscription.exceptions.NotFoundException;
import com.awbd.subscription.model.Appointment;
import com.awbd.subscription.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class AppointmentService implements IAppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public Appointment findAppointmentByEmailPacient(String emailPacient) {
        return null;
    }

    @Override
    public Appointment findByEmailPacientAndAppointmentDate(String emailPacient, LocalDateTime date) {
        Appointment appointment = appointmentRepository.findByEmailPacientAndAppointmentDate(emailPacient,  date);
        return appointment;
    }

    @Override
    public Appointment save(Appointment appointment) {
        Appointment appointmentSave = appointmentRepository.save(appointment);
        return appointmentSave;
    }

    @Override
    public List<Appointment> findAll(){
        List<Appointment> appointments = new LinkedList<>();
        appointmentRepository.findAll().iterator().forEachRemaining(appointments::add);
        return appointments;
    }


    @Override
    public boolean delete(Long id){
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        if (! appointmentOptional.isPresent())
            throw new NotFoundException("Appointment " + id + " not found!");
        appointmentRepository.delete(appointmentOptional.get());
        return true;
    }

    @Override
    public Appointment findById(Long id) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        if (! appointmentOptional.isPresent())
            throw new NotFoundException("Appointment " + id + " not found!");
        return appointmentOptional.get();
    }


}
