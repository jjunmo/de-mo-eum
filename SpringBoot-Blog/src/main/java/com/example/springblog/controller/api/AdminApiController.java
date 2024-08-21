package com.example.springblog.controller.api;

import com.example.springblog.config.auth.PrincipalDetail;
import com.example.springblog.model.entity.RoleType;
import com.example.springblog.model.entity.User;
import com.example.springblog.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminApiController {
    private final AdminService adminService;

    // 관리자페이지에서 멤버 등급 바꾸기
    @PutMapping("/admin/manage/member/edit/{id}")
    public ResponseEntity<?> changeRole(@RequestBody User roleUser, @PathVariable("id") Integer id,
                                        @AuthenticationPrincipal PrincipalDetail principalDetail
//            ,UserDetails userDetails
    ) {
        // userDetails.getUsername(); => id
        // findById() <- member
        log.info("컨트롤러 role = " + roleUser);
        adminService.changeRole(id, roleUser);
        return new ResponseEntity<>(principalDetail.getUser(), HttpStatus.OK);
    }
}
