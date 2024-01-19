package com.example.aplicacion.controlers;

import com.example.aplicacion.entity.Role;
import com.example.aplicacion.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleControler {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> list(){
        return roleService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Role> view(@PathVariable Long id){
        Optional<Role> roleOptional = roleService.findById(id);
        if(roleOptional.isPresent()){
            return ResponseEntity.ok(roleOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Role role, BindingResult result){

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Role role, BindingResult result){

        if (result.hasFieldErrors()) {
            return validation(result);
        }

        Optional <Role> roleOptional = roleService.update(id, role);
        if(roleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(roleOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Role> roleOptional = roleService.delete(id);
        if(roleOptional.isPresent()){
            return ResponseEntity.ok(roleOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err ->
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
