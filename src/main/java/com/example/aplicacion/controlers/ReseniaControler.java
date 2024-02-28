package com.example.aplicacion.controlers;

import com.example.aplicacion.entity.Pelicula;
import com.example.aplicacion.entity.Resenia;
import com.example.aplicacion.entity.ReseniaDTO;
import com.example.aplicacion.entity.Usuario;
import com.example.aplicacion.repositories.UsuarioRepository;
import com.example.aplicacion.services.PeliculaService;
import com.example.aplicacion.services.ReseniaService;
import com.example.aplicacion.services.ReseniaService;
import com.example.aplicacion.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PeliculaService peliculaService;

    @Operation(summary = "obtiene una lista de todas las reseñas")
    @GetMapping
    public List<Resenia> list(){
        return reseniaService.findAll();
    }
    @Operation(summary = "obtiene una lista la reseña obtenida por parametro")
    @GetMapping("/{id}")
    public ResponseEntity<Resenia> view(@PathVariable Long id){
        Optional<Resenia> reseniaOptional = reseniaService.findById(id);
        if(reseniaOptional.isPresent()){
            return ResponseEntity.ok(reseniaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "crea una reseña")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReseniaDTO reseniadto, BindingResult result){

        if (result.hasErrors()) {
            return validation(result);
        }

        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Buscar el usuario por su nombre de usuario (o cualquier otro identificador que uses para autenticar)
        Usuario usuario = usuarioService.findbyNombre(username);
        // Buscar la película por su título
        System.out.println(reseniadto.getPelicula());
        Pelicula pelicula = peliculaService.findbyNombre(reseniadto.getPelicula());

        // Crear la reseña y asociarla al usuario autenticado
        Resenia resenia = new Resenia();
        resenia.setTexto(reseniadto.getTexto());
        resenia.setUsuarioResenias(usuario);
        resenia.setPelicula(pelicula);

        // Guardar la reseña en la base de datos
        Resenia savedResenia = reseniaService.save(resenia);

        return ResponseEntity.status(HttpStatus.CREATED).body("reseña creada");
    }

    @Operation(summary = "modifica una reseña pasada por parametro")
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

    @Operation(summary = "borra una reseña pasada por parametro")
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
