package com.example.aplicacion.controlers;

import com.example.aplicacion.entity.Pelicula;
import com.example.aplicacion.services.PeliculaService;
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

    @GetMapping
    public List<Pelicula> list(){
        return peliculaService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> view(@PathVariable Long id){
        Optional<Pelicula> peliculaOptional = peliculaService.findById(id);
        if(peliculaOptional.isPresent()){
            return ResponseEntity.ok(peliculaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Pelicula pelicula, BindingResult result){

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaService.save(pelicula));
    }

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
