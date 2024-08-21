package com.example.springblog.service;

import com.example.springblog.model.entity.Board;
import com.example.springblog.model.entity.Reply;
import com.example.springblog.model.entity.User;
import com.example.springblog.repository.BoardRepository;
import com.example.springblog.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public List<Board> allBoard() {
        return boardRepository.findAll();
    }

    @Transactional
    public Board 글쓰기(Board board, User user) { //title, content, count
        board.setCount(0); // 조회수
        board.setUser(user);
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }


    // 카테고리별 글 목록
    @Transactional(readOnly = true)
    public Page<Board> 카테고리글목록(Pageable pageable, String category) {
        return boardRepository.findByCategory(pageable, category);
    }

    @Transactional(readOnly = true)
    public List<Board> 작성글목록(User user) {
        return boardRepository.findAllByUserOrderByIdDesc(user);
    }


    @Transactional
    public Board 글상세보기(int id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다."));

        board.setCount(board.getCount() + 1);
        return board;
    }

    @Transactional
    public void 글삭제하기(int id) {
        boardRepository.deleteById(id);
    }


    @Transactional
    public void 글수정하기(int id, Board requestBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.")); // 영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        board.setCategory(requestBoard.getCategory());
        // 해당 함수(Service) 가 종료될 때 트랜잭션이 종료된다. 이때 더디체킹 - 자동 업데이트가 됨. flush
    }



    @Transactional
    public void 댓글쓰기(User user, int boardId, Reply requestReply) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패 : 게시글 ID를 찾을 수 없습니다."));
        requestReply.setUser(user);
        requestReply.setBoard(board);

        replyRepository.save(requestReply);
    }


    @Transactional
    public void 댓글삭제(int replyId) {
        replyRepository.deleteById(replyId);
    }


    public User 댓글주인(int replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("댓글 주인을 찾을 수 없습니다."));
        return reply.getUser(); // 댓글 주인

    }
}
