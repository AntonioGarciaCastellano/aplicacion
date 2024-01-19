package com.example.aplicacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "peliculas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Titulo;
    private String Autor;
    private String Categoria;

    @ManyToMany
    @JoinTable(name = "usuario_peliculas",
            joinColumns = @JoinColumn(name = "id_pelicula", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    )
    private List<Usuario> UsuariosPeliculas = new ArrayList<>();

    @OneToMany(mappedBy = "pelicula")
    private List<Resenia> resenias = new ArrayList<>();

}
