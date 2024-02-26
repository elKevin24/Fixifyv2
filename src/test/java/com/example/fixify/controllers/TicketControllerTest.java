package com.example.fixify.controllers;

import com.example.fixify.config.SecurityConfig;
import com.example.fixify.models.Ticket;
import com.example.fixify.service.CustomerService;
import com.example.fixify.service.DeviceService;
import com.example.fixify.service.TicketService;
import com.example.fixify.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TicketController.class)
@WithUserDetails(value = "kcordon")
@Import(SecurityConfig.class) // Importa tu configuración de seguridad personalizada
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mockea solo los servicios necesarios para el endpoint bajo prueba
    @MockBean
    private TicketService ticketService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private DeviceService deviceService;
    @MockBean
    private UsuarioService userService;

    @Test
    public void addTicket_Success() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDescription("Issue with laptop");

        given(ticketService.saveTicket(any(Ticket.class))).willReturn(ticket);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "Issue with laptop")
                        .with(csrf()) // Incluir soporte para CSRF
                        .with(user("kcordon").password("1234").roles("USER"))) // Configura un usuario autenticado



                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Ticket creado con éxito"))
                .andExpect(jsonPath("$.id").value(ticket.getId()));
    }
}