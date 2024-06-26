package com.awbd.subscription.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "appointment")
public class Appointment extends RepresentationModel<Appointment> {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="emailDoctor")
    @NotEmpty(message = "The email is mandatory!")
    private String emailDoctor;

    @Column(name="nameDoctor")
    @Size(max=100, message = "max 100 ch.")
    private String fullnameDoctor;

    @Column(name="emailPacient")
    @NotEmpty(message = "The email is mandatory!")
    private String emailPacient;

    @Column(name="namePacient")
    @Size(max=100, message = "max 100 ch.")
    private String fullnamePacient;

    @Column(name="speciality")
    private String specialty;

    @Column(name="socialCategory")
    private String socialCategory;

    @Column(name = "appointmentDate", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(name="phone")
    private String phone;

    @Column(name="price")
    private int price;

}