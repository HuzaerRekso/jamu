package com.swn.jamu.service;

import com.swn.jamu.constant.RoleConstant;
import com.swn.jamu.dto.BranchDTO;
import com.swn.jamu.dto.UserDTO;
import com.swn.jamu.mapper.UserMapper;
import com.swn.jamu.model.Role;
import com.swn.jamu.model.User;
import com.swn.jamu.repository.RoleRepository;
import com.swn.jamu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchService branchService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BranchService branchService,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.branchService = branchService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserDTO userDTO) {
        if (userDTO.getRole().equals(RoleConstant.ROLE_STAFF) && userDTO.getBranchId() == null) {
            throw new IllegalArgumentException("Branch is required for staff");
        }
        if (!userDTO.getRole().equals(RoleConstant.ROLE_STAFF)) {
            userDTO.setBranchId(null);
        }

        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setActive(true);

        if (userDTO.getBranchId() != null) {
            user.setBranch(branchService.findBranch(userDTO.getBranchId()));
        }

        Role role = roleRepository.findByName(userDTO.getRole());
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findUserByUsernameAndActive(String username) {
        return userRepository.findByUsernameAndActive(username, true);
    }

    public UserDTO findById(long id) {
        return userMapper.toUserDTO(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID not found")));
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findByActive(true);
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

    public void editUser(long id, UserDTO userDTO) {
        if (userDTO.getRole().equals(RoleConstant.ROLE_STAFF) && userDTO.getBranchId() == null) {
            throw new IllegalArgumentException("Branch is required for staff");
        }
        if (!userDTO.getRole().equals(RoleConstant.ROLE_STAFF)) {
            userDTO.setBranchId(null);
        }

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id not found"));
        Role role = roleRepository.findByName(userDTO.getRole());
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }

        if (StringUtils.hasLength(userDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        Role oldRole = null;
        if (user.getRoles() != null && user.getRoles().size() > 1) {
            oldRole = user.getRoles().get(0);
        }
        if (oldRole == null) {
            user.addRole(role);
        } else if (!role.equals(oldRole)) {
            user.addRole(role);
            user.removeRole(oldRole.getId());
        }

        if (userDTO.getBranchId() != null) {
            user.setBranch(branchService.findBranch(userDTO.getBranchId()));
        }

        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setFullname(userDTO.getFullname());
        user.setGender(userDTO.getGender());
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id not found"));
        userRepository.delete(user);
    }

    public Page<UserDTO> findPaginated(Pageable pageable, String name) {
        Page<User> userPage;
        if (StringUtils.hasLength(name)) {
            userPage = userRepository.findByFullnameContaining(name, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }

        List<UserDTO> dtoList = userPage.getContent().stream().map(userMapper::toUserDTO).toList();
        return new PageImpl<>(dtoList, pageable, userPage.getTotalElements());
    }

    public List<BranchDTO> findAllBranches() {
        return branchService.findAllBranch();
    }
}
