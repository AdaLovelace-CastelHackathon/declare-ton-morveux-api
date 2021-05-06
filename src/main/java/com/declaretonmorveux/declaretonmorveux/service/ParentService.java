package com.declaretonmorveux.declaretonmorveux.service;

import com.declaretonmorveux.declaretonmorveux.model.Parent;
import com.declaretonmorveux.declaretonmorveux.repository.ParentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ParentService implements UserDetailsService {

    private ParentRepository parentRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ParentService(ParentRepository parentRepository,
            PasswordEncoder passwordEncoder) {
        this.parentRepository = parentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Parent loadUserByUsername(String username) throws UsernameNotFoundException {
        Parent user = parentRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Utilisateur %s n'existe pas", username)));

        return user;
    }

    public boolean addNewUser(Parent user) {

        boolean usernameAlreadyExist = countUserByUsername(user.getUsername()) > 0;

        if (usernameAlreadyExist) {
            return false;
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            parentRepository.save(user);
            return true;
        } catch (DataAccessException e) {
            System.out.println("ERROR WHILE SAVING THE USER");
            return false;
        }
    }

    public boolean emailAlreadyExist(String email) {
        return parentRepository.findByEmail(email).isPresent();
    }

    public boolean usernameAlreadyExist(String username) {
        return parentRepository.findByUsername(username).isPresent();
    }

    public Integer countUserByUsername(String username) {
        return parentRepository.countUserByUsername(username);
    }

    public Parent getUserById(Long id){
        return parentRepository.getOne(id);
    }
    
}
