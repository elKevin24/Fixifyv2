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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private static final Logger log = LoggerFactory.getLogger(TicketController.class);


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
        model.addAttribute("tickets", ticketService.findAllTickets());
        model.addAttribute("customers", customerService.findAllCustomer());
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("customer", new Customer()); // Agregar objeto Customer
        model.addAttribute("device", new Device());     // Agregar objeto Device


        return "ticket"; // Nombre del archivo HTML de la vista
    }

    @PostMapping
    public String addTicket(@ModelAttribute("ticket") Ticket ticket, Model model) {
        try {
            // Imprimir datos del ticket para depuración
            log.info("Guardando ticket: {}", ticket);
            log.info("Datos del Cliente: {}", ticket.getCustomer());
            log.info("Datos del Dispositivo: {}", ticket.getDevice());

            // Lógica existente para guardar el ticket
            Customer customer = ticket.getCustomer();
            Device device = ticket.getDevice();

            // Usar el método en CustomerService
            Customer savedOrExistingCustomer = customerService.getOrCreateCustomer(customer);
            ticket.setCustomer(savedOrExistingCustomer);

            Device deviceCreate = deviceService.saveDevice(device);
            ticket.setDevice(deviceCreate);

            log.info("Ticket construido: {}", ticket);
            ticketService.saveTicket(ticket);

            // Redirige al listado para ver el ticket agregado
            return "redirect:/tickets";
        } catch (Exception e) {
            // Log del error
            log.error("Error al guardar el ticket: {}", e.getMessage());

            // Agregar mensaje de error al modelo
            model.addAttribute("errorMessage", "Error al crear el ticket: " + e.getMessage());

            // Retornar la vista del formulario en caso de error
            return "ticket";
        }
    }
}
