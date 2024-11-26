package com.ezen.boot_JPA.repository;

import com.ezen.boot_JPA.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {

    Page<Board> searchBoards(String type, String keyword, Pageable pageable);

}
