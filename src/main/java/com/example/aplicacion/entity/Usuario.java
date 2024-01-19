package com.example.aplicacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Nombre;
    @Column(name = "Contraseña")
    private String Contrasenia;

    @ManyToMany(mappedBy = "UsuariosPeliculas")
    private List<Pelicula> Peliculas = new ArrayList<>();

    @OneToMany(mappedBy = "usuarioResenias")
    private List<Resenia> resenias = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})}
    )
    private List<Role> roles;

    private boolean admin;
}
