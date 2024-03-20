package com.example.fixify.controllers;

import com.example.fixify.models.Rol;
import com.example.fixify.models.Usuario;
import com.example.fixify.repository.RolRepository;
import com.example.fixify.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolRepository rolRepository;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, RolRepository rolRepository) {
        this.usuarioService = usuarioService;
        this.rolRepository = rolRepository;
    }
    @PostMapping("/insertar")
    @ResponseBody
    public ResponseEntity<Object> insertarDatos(@ModelAttribute Usuario usuario) {
        System.out.println("usuario = " + usuario);
        try {
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            nuevoUsuario.setPassword(null); // Por ejemplo, elimina la contraseña
            nuevoUsuario.setRoles(null); // Por ejemplo, elimina la contraseña

            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            // Maneja la excepción genérica y devuelve el detalle del error
            String errorMessage = "Error al crear el usuario: " + ex.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public String Usuarios(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        List<Rol> roles = rolRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuario",  new Usuario());

        return "users"; // Nombre de la plantilla Thymeleaf
    }

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> obtenerTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<Usuario> crearUsuario(@ModelAttribute Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}