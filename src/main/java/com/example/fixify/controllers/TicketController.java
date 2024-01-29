package com.example.fixify.controllers;


import com.example.fixify.models.Customer;
import com.example.fixify.models.Device;
import com.example.fixify.models.Ticket;
import com.example.fixify.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);


    private final TicketService ticketService;
    private final CustomerService customerService;
    private final DeviceService deviceService;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private PdfGenerationService pdfGenerationService;

    @Autowired
    private UsuarioService userService;

    @Autowired
    public TicketController(TicketService ticketService, CustomerService customerService, DeviceService deviceService, UsuarioService userService) {
        this.ticketService = ticketService;
        this.customerService = customerService;
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Long> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        // Obtener el ticket existente
        System.out.println("id = " + id);
        System.out.println("id = " + ticket);

//        System.out.println("ticket = " + ticket);
//        Optional<Ticket> existingTicketOptional  = ticketService.findOneById(ticket.getId());
//        if (!existingTicketOptional.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//        Ticket existingTicket = existingTicketOptional.get();
//
//        // Obtener el usuario actual
//        String username = principal.getName();
//        UserDetails currentUser = userService.loadUserByUsername(username);
//
//
//        // Actualiza los campos de existingTicket con los de ticketDetails
//        existingTicket.setTechnicalReview(ticket.getTechnicalReview());
//        // ... otros campos según sea necesario ...
//        existingTicket.setUpdatedBy((Usuario) currentUser);
//
//        // Guardar el ticket actualizado
//        Ticket updatedTicket = ticketService.saveTicket(existingTicket);

        return ResponseEntity.ok(id); // Devuelve el ticket actualizado
        // o devolver el ticket actualizado si es necesario
    }

    @GetMapping("/ticketPdf/{ticketId}")
    public ResponseEntity<InputStreamResource> generatePdf(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.findOneById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket no encontrado con id: " + ticketId));

        Context context = new Context();
        context.setVariable("ticket", ticket);

        String html = templateEngine.process("ticketDetailsPdf", context);
        ByteArrayInputStream bis = pdfGenerationService.generatePdfFromHtml(html);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=ticket-" + ticketId + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
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

    @GetMapping("/{id}")
    public String getTicketById(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.findOneById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket no encontrado con id: " + id));
        model.addAttribute("ticket", ticket);
        return "ticketDetails"; // Nombre de la plantilla de Thymeleaf
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTicket(@PathVariable Long id) {
        System.out.println("id = " + id);
        try {
            // Se envía cero porque es el estado eliminado
            Long estado = 0L;
            ticketService.cambiarEstadoTicket(id, estado);
            return ResponseEntity.ok().build(); // Retornar éxito
        } catch (Exception e) {
            // Manejar la excepción y retornar un error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cambiar el estado del ticket: " + e.getMessage());
        }
    }
}
