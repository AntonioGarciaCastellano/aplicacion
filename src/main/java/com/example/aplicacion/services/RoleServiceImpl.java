package com.example.aplicacion.services;

import com.example.aplicacion.entity.Role;
import com.example.aplicacion.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return  roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Optional <Role> update(Long id, Role role) {
        Optional <Role> roleOptional = roleRepository.findById(id);
        if(roleOptional.isPresent()){
            Role roleDb = roleOptional.orElseThrow();
            roleDb.setName(role.getName());
            return Optional.of(roleRepository.save(roleDb));
        }
        return roleOptional;
    }

    @Override
    @Transactional
    public Optional<Role> delete(Long id) {
        Optional <Role> roleOptional = roleRepository.findById(id);
        roleOptional.ifPresent( roleDb -> roleRepository.delete(roleDb));
        return roleOptional;
    }
}
