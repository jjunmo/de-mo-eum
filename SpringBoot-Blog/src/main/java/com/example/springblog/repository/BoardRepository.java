package com.example.springblog.repository;

import com.example.springblog.model.entity.Board;
import com.example.springblog.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findAllByUserOrderByIdDesc(User user);
    Page<Board> findByCategory(Pageable pageable, String category);
}
