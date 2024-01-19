package com.example.fixify.controllers;

import com.example.fixify.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final TicketService ticketService;

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
        model.addAttribute("tickets", ticketService.findAllActiveTickets());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
