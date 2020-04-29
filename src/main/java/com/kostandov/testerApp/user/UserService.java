package com.kostandov.testerApp.user;

import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUsername(userName);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public <S extends User> S save(S s) {
        return userRepository.save(s);
    }

    public List<User> findAll() {
        return new ArrayList<>((Collection<? extends User>) userRepository.findAll());
    }

    public long count() {
        return userRepository.count();
    }
}
