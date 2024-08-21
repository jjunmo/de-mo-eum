package com.example.springblog.controller.api;

import com.example.springblog.config.auth.PrincipalDetail;
import com.example.springblog.handler.MyForbiddenException;
import com.example.springblog.handler.MyInternalServerException;
import com.example.springblog.model.entity.Board;
import com.example.springblog.model.entity.Reply;
import com.example.springblog.model.entity.User;
import com.example.springblog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // 글쓰기처리
    @PostMapping("/api/board")
    public ResponseEntity<Object> save(
            @RequestBody Board board,
            @AuthenticationPrincipal PrincipalDetail principalDetail) {

        // Valid
        if (principalDetail == null) {
            throw new MyForbiddenException("로그인을 해주세요.");
        }

        if (board.getTitle() == null || board.getTitle().equals("")) {
            throw new MyInternalServerException("Title 입력해주세요.", board.toString());
        }


        boardService.글쓰기(board, principalDetail.getUser());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Integer id) {
        boardService.글삭제하기(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/board/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id, @RequestBody Board board) {
        boardService.글수정하기(id, board);
        return ResponseEntity.ok().build();
    }

    // 데이터를 받을 때, 원래 컨트롤러에서 dto를 만들어서 받는게 좋음
    // dto를 사용하지 않는 이유는, 큰 프로젝트에서는 데이터가 오고가는게 많으니 꼭 써야함
    // 댓글쓰기
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseEntity<Object> replySave(@PathVariable("boardId") Integer boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principalDetail) {

        boardService.댓글쓰기(principalDetail.getUser(), boardId, reply);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseEntity<Object> replyDelete(@AuthenticationPrincipal PrincipalDetail principalDetail ,@PathVariable("boardId") Integer boardId, @PathVariable("replyId") Integer replyId) {

        User replyWriter = boardService.댓글주인(replyId);
        if(replyWriter.getId().intValue() == principalDetail.getUser().getId()) {
            boardService.댓글삭제(replyId);
            return ResponseEntity.ok().build();
        } else {
            throw new MyForbiddenException("로그인을 해주세요.");
        }

    }
}
