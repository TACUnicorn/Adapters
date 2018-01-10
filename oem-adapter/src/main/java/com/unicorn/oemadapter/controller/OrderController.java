package com.unicorn.oemadapter.controller;

import com.unicorn.oemadapter.model.Order;
import com.unicorn.oemadapter.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class OrderController {

    @Autowired
    public OrderService orderService;

    @GetMapping(value = "/oem/order", produces = {"application/json"})
    @ResponseBody
    public ArrayList<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping(value = "/oem/order", produces = {"application/json"})
    public boolean postOrder(@RequestParam int materialId, @RequestParam String materialName, @RequestParam int materialNo, @RequestParam String oem) {
        try {
            orderService.postOrder(materialId, materialName, materialNo, oem);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
