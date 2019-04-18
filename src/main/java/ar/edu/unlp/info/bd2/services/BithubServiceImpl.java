package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BithubServiceImpl implements BithubService {

    @Override
    public User createUser(String email, String name) {
        return new User(email, name);
    }
/*
    public Optional<User> getUserByEmail(String email) {

    }*/


}
