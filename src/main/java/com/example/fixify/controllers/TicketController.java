package com.example.fixify.controllers;


import com.example.fixify.models.Customer;
import com.example.fixify.models.Device;
import com.example.fixify.models.Ticket;
import com.example.fixify.service.CustomerService;
import com.example.fixify.service.DeviceService;
import com.example.fixify.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Map;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);


    private final TicketService ticketService;
    private final CustomerService customerService;
    private final DeviceService deviceService;


    @Autowired
    public TicketController(TicketService ticketService, CustomerService customerService, DeviceService deviceService) {
        this.ticketService = ticketService;
        this.customerService = customerService;
        this.deviceService = deviceService;
    }

    @GetMapping
    public String listTickets(Model model) {
//        model.addAttribute("tickets", ticketService.findAllTickets());
        model.addAttribute("customers", customerService.findAllCustomer());
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("customer", new Customer()); // Agregar objeto Customer
        model.addAttribute("device", new Device());
        model.addAttribute("brands", deviceService.getAllDeviceBrands());
        model.addAttribute("categories", deviceService.getAllDeviceCategories());

        return "ticket"; // Nombre del archivo HTML de la vista
    }

    @PostMapping
    public ResponseEntity<?> addTicket(@ModelAttribute("ticket") Ticket ticket) {
        try {


            Ticket savedTicket = ticketService.saveTicket(ticket);
            logger.info("Ticket creado con éxito: {}", savedTicket);

            return ResponseEntity.ok(Map.of("message", "Ticket creado con éxito", "id", savedTicket.getId()));

        } catch (Exception e) {
            logger.error("Error al crear el ticket: {}", e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
