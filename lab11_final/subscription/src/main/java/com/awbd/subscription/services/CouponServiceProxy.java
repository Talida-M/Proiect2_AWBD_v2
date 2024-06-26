package com.awbd.subscription.services;

import com.awbd.subscription.model.Coupon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "coupon")
public interface CouponServiceProxy {
    @GetMapping("/coupon")
    Coupon findCoupon();
}
