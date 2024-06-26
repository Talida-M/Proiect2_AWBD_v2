package com.awbd.subscription.controller;


import com.awbd.subscription.model.Appointment;
import com.awbd.subscription.model.Coupon;
import com.awbd.subscription.services.CouponServiceProxy;
import com.awbd.subscription.services.IAppointmentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AppointmentController {
    @Autowired
    IAppointmentService IAppointmentService;

    @Autowired
    CouponServiceProxy couponServiceProxy;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @GetMapping(value= "/appointment/list")
    public CollectionModel<Appointment> findAll() {

        List<Appointment> appointments = IAppointmentService.findAll();
        for (final Appointment appointment : appointments) {
            Link selfLink = linkTo(methodOn(AppointmentController.class).getAppointment(appointment.getId())).withSelfRel();
            appointment.add(selfLink);

            Link deleteLink = linkTo(methodOn(AppointmentController.class).deleteAppointment(appointment.getId())).withRel("deleteAppointment");
            appointment.add(deleteLink);

            Link postLink = linkTo(methodOn(AppointmentController.class).saveAppointment(appointment)).withRel("saveAppointment");
            appointment.add(postLink);

            Link putLink = linkTo(methodOn(AppointmentController.class).updateAppointment(appointment)).withRel("updateAppointment");
            appointment.add(putLink);
        }

        Link link = linkTo(methodOn(AppointmentController.class).findAll()).withSelfRel();
        CollectionModel<Appointment> result = CollectionModel.of(appointments, link);
        return result;
    }


    @GetMapping("/appointment/pacient/{emailPacient}/date/{date}")
    public Appointment findByEmailPacientAndAppointmentDate(@PathVariable String emailPacient,
                                                            @PathVariable LocalDateTime date){
        Appointment appointment = IAppointmentService.findByEmailPacientAndAppointmentDate(emailPacient, date);

        Link selfLink = linkTo(methodOn(AppointmentController.class).getAppointment(appointment.getId())).withSelfRel();
        appointment.add(selfLink);

        Coupon coupon = couponServiceProxy.findCoupon();
        logger.info(coupon.getSpecialities() + " " +  " " + coupon.getSocialCategory());
        appointment.setPrice(appointment.getPrice() * (100 - coupon.getMonth())/100);



        return appointment;
    }


    @PostMapping("/appointment")
    public ResponseEntity<Appointment> saveAppointment(@Valid @RequestBody Appointment appointment){
        Appointment savedAppointment = IAppointmentService.save(appointment);
        URI locationUri =ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{appointmentId}").buildAndExpand(savedAppointment.getId())
                .toUri();

//        Link selfLink = linkTo(methodOn(AppointmentController.class).getAppointment(savedAppointment.getId())).withSelfRel();
        Link selfLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AppointmentController.class)
                                .getAppointment(savedAppointment.getId()))
                .withSelfRel();
        savedAppointment.add(selfLink);

        return ResponseEntity.created(locationUri).body(savedAppointment);
    }

    @PutMapping("/appointment")
    public ResponseEntity<Appointment> updateAppointment(@Valid @RequestBody Appointment appointment){
        Appointment updatedAppointment = IAppointmentService.save(appointment);

        Link selfLink = linkTo(methodOn(AppointmentController.class).getAppointment(updatedAppointment.getId())).withSelfRel();
        updatedAppointment.add(selfLink);

        return ResponseEntity.ok(updatedAppointment);
    }

    @Operation(summary = "delete appointment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "appointment deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Appointment.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Appointment not found",
                    content = @Content)})
    @DeleteMapping("/appointment/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId){

        boolean deleted = IAppointmentService.delete(appointmentId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    @CircuitBreaker(name="couponById", fallbackMethod = "getAppointmentFallback")
    public Appointment getAppointment(@PathVariable Long appointmentId) {

        Appointment appointment = IAppointmentService.findById(appointmentId);

        Coupon coupon = couponServiceProxy.findCoupon();
        logger.info(coupon.getSpecialities() + " " +  " " + coupon.getSocialCategory());
        appointment.setPrice(appointment.getPrice() * (100 - coupon.getMonth())/100);

        return appointment;

    }


    Appointment getAppointmentFallback(Long appointmentId, Throwable throwable) {

        Appointment appointment = IAppointmentService.findById(appointmentId);
        return appointment;

    }

}