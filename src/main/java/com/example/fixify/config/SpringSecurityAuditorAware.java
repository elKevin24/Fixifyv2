package com.example.fixify.config;

import com.example.fixify.models.Usuario;
import com.example.fixify.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class SpringSecurityAuditorAware implements AuditorAware<Usuario> {
    private final UsuarioService usuarioService;

    @Autowired
    public SpringSecurityAuditorAware(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public Optional<Usuario> getCurrentAuditor() {
        // Asume que el nombre de usuario está establecido en el contexto de seguridad
        // Ajusta esta lógica si tu aplicación tiene diferentes requisitos
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Usuario usuario = (Usuario) usuarioService.loadUserByUsername(username);
            return Optional.of(usuario);
        } catch (UsernameNotFoundException e) {
            return Optional.empty();
        }
    }
}

