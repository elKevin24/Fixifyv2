package com.example.fixify.controllers;

import com.example.fixify.models.TicketStatus;
import com.example.fixify.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {
    private TicketService ticketService;

    @Autowired
    public HomeController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @GetMapping("/index")
    public String index(Model model) {
          List<Object[]> countList = ticketService.countTicketsByStatus();
// Colores para cada posición
        String[] colors = new String[]{"red", "blue", "primary", "green"};



        model.addAttribute("ticketCounts", countList);
        model.addAttribute("colors", colors);
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
