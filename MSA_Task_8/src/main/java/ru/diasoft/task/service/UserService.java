package ru.diasoft.task.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.diasoft.task.entity.Role;
import ru.diasoft.task.entity.RoleEntity;
import ru.diasoft.task.entity.User;
import ru.diasoft.task.entity.UserEntity;
import ru.diasoft.task.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /*@PostConstruct
    public void init() {
        Optional<UserEntity> optional = userRepository.findByLoginEqualsAndStatusEquals("admin", "ACTIVE");

        if(optional.isPresent()) {
            UserEntity userEntity = optional.get();
            userEntity.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(userEntity);
        }
    }*/

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {

        Optional<UserEntity> optional = userRepository.findByLoginEqualsAndStatusEquals(username, "ACTIVE");

        if(optional.isPresent()) {

            UserEntity userEntity = optional.get();
            Boolean enabled = userEntity.getStatus().equalsIgnoreCase("active");

            List<GrantedAuthority> autorities = new ArrayList<GrantedAuthority>();

            for(RoleEntity role : userEntity.getRoles()) {
                autorities.add(new Role(role.getName()));
            }

            return User.builder()
                    .username(userEntity.getLogin())
                    .password(userEntity.getPassword())
                    .authorities(autorities)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(enabled)
                    .build();
        }
        else {
            throw new UsernameNotFoundException("user " + username + " was not found!");
        }

    }


}
