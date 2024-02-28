package com.example.aplicacion.controlers;


import com.example.aplicacion.entity.Usuario;
import com.example.aplicacion.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Operation(summary = "obtiene una lista de usuarios")
    @GetMapping
    public List<Usuario> list(){
        return usuarioService.findAll();
    }

    @Operation(summary = "crea un usuario (se necesita ser administrador)")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Usuario Usuario, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(Usuario));
    }

    @Operation(summary = "crea un usuario no administrador")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario Usuario, BindingResult result){
        Usuario.setAdmin(false);

        return create(Usuario, result);
    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> errors.put(err.getField(), "El campo "+ err.getField()+ " "+ err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
