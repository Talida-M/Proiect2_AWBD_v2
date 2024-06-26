package com.awbd.discount.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Coupon {
    private String versionId;
    private int month;
    private String socialCategory;
    private String specialities;
}
