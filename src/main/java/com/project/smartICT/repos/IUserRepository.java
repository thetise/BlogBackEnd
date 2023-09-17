package com.project.smartICT.repos;

import com.project.smartICT.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
