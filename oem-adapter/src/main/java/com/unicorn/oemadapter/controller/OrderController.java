package com.unicorn.oemadapter.controller;

import com.unicorn.oemadapter.model.Order;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class OrderController {
    @GetMapping(value = "/oem", produces = {"application/json"})
    @ResponseBody
    public ArrayList<Order> getOrders() {

    }

    @PostMapping(value = "/oem/order", produces = {"application/json"})
    public boolean postOrder(@RequestParam int productId, @RequestParam int num) {

    }

}
