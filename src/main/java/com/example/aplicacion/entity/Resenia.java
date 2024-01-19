package com.example.aplicacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Reseñas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;

    @ManyToOne
    @JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_usuario"))
    private Usuario usuarioResenias;

    @ManyToOne
    @JoinColumn(name = "id_pelicula", foreignKey = @ForeignKey(name = "fk_pelicula"))
    private Pelicula pelicula;

}
