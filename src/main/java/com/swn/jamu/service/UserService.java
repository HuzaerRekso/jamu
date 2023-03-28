package com.swn.jamu.service;

import com.swn.jamu.dto.UserDTO;
import com.swn.jamu.mapper.UserMapper;
import com.swn.jamu.model.Role;
import com.swn.jamu.model.User;
import com.swn.jamu.repository.RoleRepository;
import com.swn.jamu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findByName(userDTO.getRole());
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> {
            UserDTO userDTO = userMapper.toUserDTO(user);
            if (user.getRoles() != null && user.getRoles().size() > 0) {
                userDTO.setRole(user.getRoles().get(0).getName());
            }
            userDTOS.add(userDTO);
        });
        return userDTOS;
    }
}
