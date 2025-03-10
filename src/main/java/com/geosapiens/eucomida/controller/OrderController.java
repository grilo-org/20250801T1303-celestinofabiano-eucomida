package com.geosapiens.eucomida.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping
    public String createOrder() {
        return "Pedido criado com sucesso!";
    }

    @GetMapping("/status")
    public String getOrderStatus() {
        return "Status do pedido: Em andamento";
    }
}
