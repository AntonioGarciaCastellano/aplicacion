package com.example.aplicacion.services;

import com.example.aplicacion.entity.Resenia;
import com.example.aplicacion.entity.Usuario;
import com.example.aplicacion.repositories.ReseniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReseniaServiceImpl implements ReseniaService {
    @Autowired
    private ReseniaRepository reseniaRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Resenia> findAll() {
        return  reseniaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resenia> findById(Long id) {
        return reseniaRepository.findById(id);
    }

    @Override
    @Transactional
    public Resenia save(Resenia resenia) {
        return reseniaRepository.save(resenia);
    }

    @Override
    @Transactional
    public Optional <Resenia> update(Long id, Resenia resenia) {
        Optional <Resenia> reseniaOptional = reseniaRepository.findById(id);
        if(reseniaOptional.isPresent()){
            Resenia reseniaDb = reseniaOptional.orElseThrow();
            reseniaDb.setUsuarioResenias(resenia.getUsuarioResenias());
            reseniaDb.setPelicula(resenia.getPelicula());
            reseniaDb.setTexto(resenia.getTexto());
            return Optional.of(reseniaRepository.save(reseniaDb));
        }
        return reseniaOptional;
    }

    @Override
    @Transactional
    public Optional<Resenia> delete(Long id) {
        Optional <Resenia> reseniaOptional = reseniaRepository.findById(id);
        reseniaOptional.ifPresent( reseniaDb -> reseniaRepository.delete(reseniaDb));
        return reseniaOptional;
    }

}
