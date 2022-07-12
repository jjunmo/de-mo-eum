package kr.co.promisemomo.module.promise.controller;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.service.MemberService;
import kr.co.promisemomo.module.promise.dto.PromiseCreateRequest;
import kr.co.promisemomo.module.promise.entity.Promise;
import kr.co.promisemomo.module.promise.service.PromiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PromiseRestController {

    public final PromiseService promiseService;
    public final MemberService memberService;

    @PostMapping("/member/{id}/promise")
    public HttpEntity<Object> addPromise(@PathVariable("id") Long id, @RequestBody PromiseCreateRequest promiseCreateRequest){
        Optional<Member> memberOptional = memberService.getMember(id);
        if (memberOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("아이디를 찾을수 없습니다.");
        }

        // Validation Check
        String validationResult = promiseService.validationPromise(promiseCreateRequest);
        if (!validationResult.equals("OK") ) {
            return ResponseEntity.badRequest().body(validationResult);
        }

        Promise promise = promiseService.addPromise(memberOptional.get(), promiseCreateRequest);
        return ResponseEntity.ok(promise);
    }

    @GetMapping("/member/{id}/promises")
    public List<Promise> getPromises(@PathVariable("id") Long id){
        return promiseService.getPromises(id);
    }
    
    
}
