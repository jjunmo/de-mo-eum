package com.example.springblog.service;

import com.example.springblog.model.entity.Board;
import com.example.springblog.model.entity.User;
import com.example.springblog.repository.BoardRepository;
import com.example.springblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }


    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("유저 찾기 실패 : 아이디를 찾을 수 없습니다."));
    }

    public List<Board> findAllBoardByUser(User user) {
       return boardRepository.findAllByUserOrderByIdDesc(user);
    }


    @Transactional
    public void changeRole(int userId, User roleUser) {
        userRepository
                .findById(userId).orElseThrow(()-> new IllegalArgumentException("회원 찾기 실패"))
                .setRole(roleUser.getRole());
    }

    // 토탈 조회수 구하기
    public int getTotalViewCount() {
        return boardRepository.findAll()
                .stream()
//                .map(board -> board.getCount())
                .map(Board::getCount)
                .reduce(0, Integer::sum);
    }

    // 전체 회원 수 구하기
    public int getTotalUsersCount() {
        return (int) userRepository.count();
    }



}
