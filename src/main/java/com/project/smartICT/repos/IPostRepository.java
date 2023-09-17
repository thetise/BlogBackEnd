package com.project.smartICT.repos;

import com.project.smartICT.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {
}
