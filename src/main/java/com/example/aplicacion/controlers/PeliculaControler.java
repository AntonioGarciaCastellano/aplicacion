package com.example.aplicacion.controlers;

import com.example.aplicacion.entity.Pelicula;
import com.example.aplicacion.services.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/peliculas")
public class PeliculaControler {

    @Autowired
    private PeliculaService peliculaService;

    @Operation(summary = "obtiene una lista de todas las peliculas")
    @GetMapping
    public List<Pelicula> list(){
        return peliculaService.findAll();
    }

    @Operation(summary = "obtiene una pelicula cuya id sea pasada por parametro")
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> view(@PathVariable Long id){
        Optional<Pelicula> peliculaOptional = peliculaService.findById(id);
        if(peliculaOptional.isPresent()){
            return ResponseEntity.ok(peliculaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "crea la pelicula")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Pelicula pelicula, BindingResult result){
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaService.save(pelicula));
    }

    @Operation(summary = "modifica los valores de la pelicula pasada por parametro")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Pelicula pelicula, BindingResult result){

        if (result.hasFieldErrors()) {
            return validation(result);
        }

        Optional <Pelicula> peliculaOptional = peliculaService.update(id, pelicula);
        if(peliculaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(peliculaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "borra la pelicula pasada por parametro")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Pelicula> peliculaOptional = peliculaService.delete(id);
        if(peliculaOptional.isPresent()){
            return ResponseEntity.ok(peliculaOptional.orElseThrow());
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
