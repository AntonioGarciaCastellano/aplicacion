package com.example.aplicacion.controlers;

import com.example.aplicacion.entity.Resenia;
import com.example.aplicacion.services.ReseniaService;
import com.example.aplicacion.services.ReseniaService;
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
@RequestMapping("/api/reseñas")
public class ReseniaControler {

    @Autowired
    private ReseniaService reseniaService;

    @GetMapping
    public List<Resenia> list(){
        return reseniaService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Resenia> view(@PathVariable Long id){
        Optional<Resenia> reseniaOptional = reseniaService.findById(id);
        if(reseniaOptional.isPresent()){
            return ResponseEntity.ok(reseniaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Resenia resenia, BindingResult result){

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reseniaService.save(resenia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Resenia resenia, BindingResult result){

        if (result.hasFieldErrors()) {
            return validation(result);
        }

        Optional <Resenia> reseniaOptional = reseniaService.update(id, resenia);
        if(reseniaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reseniaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Resenia> reseniaOptional = reseniaService.delete(id);
        if(reseniaOptional.isPresent()){
            return ResponseEntity.ok(reseniaOptional.orElseThrow());
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
