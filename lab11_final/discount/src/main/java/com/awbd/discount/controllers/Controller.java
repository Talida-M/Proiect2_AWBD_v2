package com.awbd.discount.controllers;

import com.awbd.discount.config.PropertiesConfig;
import com.awbd.discount.model.Coupon;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Appointment API", description = "Endpoints for managing appointments")
public class Controller {
    @Autowired
    private PropertiesConfig configuration;

    @Operation(summary = "Create an empty appointment for a user", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/appointment")
    public Coupon getCoupon(){

        return new Coupon(configuration.getVersionId(), configuration.getMonth(), configuration.getSpecialities(), configuration.getSocialCategory());
    }
}
