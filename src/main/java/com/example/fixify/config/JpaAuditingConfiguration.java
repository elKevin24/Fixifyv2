package com.example.fixify.config;

import com.example.fixify.models.Usuario;
import com.example.fixify.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfiguration {
    @Bean
    public AuditorAware<Usuario> auditorAware(UsuarioService usuarioService) {
        return new SpringSecurityAuditorAware(usuarioService);
    }
}

