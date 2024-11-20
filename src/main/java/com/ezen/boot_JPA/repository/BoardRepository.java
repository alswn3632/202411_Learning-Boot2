package com.ezen.boot_JPA.repository;

import com.ezen.boot_JPA.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // JpaRepository<테이블명, id>
}
