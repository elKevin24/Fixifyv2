package com.example.fixify.service;

import com.example.fixify.models.Usuario;
import com.example.fixify.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
// Importa las excepciones necesarias
import org.springframework.dao.DataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        return usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
    }

    public Usuario crearUsuario(Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder().encode(usuario.getPassword())); // Codificación de la contraseña
            return usuarioRepository.save(usuario);
        } catch (DataAccessException ex) {
            // Si hay un error al acceder a la base de datos, lanza una excepción personalizada
            throw new RuntimeException("Error al crear el usuario en la base de datos", ex);
        }
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = obtenerUsuarioPorId(id);

        // Lógica para actualizar campos específicos, incluyendo roles si es necesario
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setPassword(passwordEncoder().encode(usuario.getPassword()));
        usuarioExistente.setRoles(usuario.getRoles());

        return usuarioRepository.save(usuarioExistente);
    }

    public void eliminarUsuario(Long id) {
        // Lógica para eliminar un usuario
        usuarioRepository.deleteById(id);
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
