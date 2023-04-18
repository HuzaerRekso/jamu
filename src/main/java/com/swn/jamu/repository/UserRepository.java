package com.swn.jamu.repository;

import com.swn.jamu.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameAndActive(String username, boolean active);

    List<User> findByActive(boolean active);

    Page<User> findByFullnameLike(String fullName, Pageable pageable);

    Page<User> findByFullnameContaining(String fullName, Pageable pageable);
}
