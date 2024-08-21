package kr.co.promisemomo.module.promise.controller;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.service.MemberService;
import kr.co.promisemomo.module.promise.dto.request.PromiseCreateRequest;
import kr.co.promisemomo.module.promise.entity.PARTICIPATE;
import kr.co.promisemomo.module.promise.entity.Promise;
import kr.co.promisemomo.module.promise.entity.PromiseMember;
import kr.co.promisemomo.module.promise.service.PromiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promise")
public class PromiseRestController {

    public final PromiseService promiseService;
    public final MemberService memberService;

    private final String noParticipate= "참가자 정보가 없습니다.";
    
    private final String noLogin = "로그인이 필요합니다.";

    // 수정을 했습니다.

    /**
     * 약속생성
     * @param memberId 멤버고유번호
     * @param promiseCreateRequest 약속생성요청
     * @return 약속생성
     */
    @PostMapping("/member")
    public HttpEntity<Object> addPromise(@RequestParam("member_id") Long memberId, @RequestBody PromiseCreateRequest promiseCreateRequest){

        Optional<Member> memberOptional = memberService.getMember(memberId);
        if (memberOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(noLogin);
        }
        // Validation Check
        String validationResult = promiseService.validationPromise(promiseCreateRequest);
        if (!validationResult.equals("OK") ) {
            return ResponseEntity.badRequest().body(validationResult);
        }
        return ResponseEntity.ok(promiseService.addPromise(memberOptional.get(), promiseCreateRequest));
    }

    /**
     *
     * @param memberId 약속리스트
     * @return 참가한 약속 리스트
     */
    @GetMapping("/member")
    public HttpEntity<Object> getPromises(@RequestParam("member_id") Long memberId){
        Optional<Member> memberOptional = memberService.getMember(memberId);
        if (memberOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(noLogin);
        }

        return ResponseEntity.ok().body(promiseService.getParticipatePromises(memberId));
    }

    /**
     * 
     * @param memberId 로그인된 아이디
     * @return 내가 주최한 약속정보
     */
    @GetMapping("/host")
    public HttpEntity<Object> getMyHostPromises(@RequestParam("member_id") Long memberId){
        Optional<Member> memberOptional = memberService.getMember(memberId);
        if (memberOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(noLogin);
        }

        return ResponseEntity.ok().body(promiseService.myHostPromiseList(memberId));
    }


    /**
     * 
     * @param memberId 로그인된 아이디
     * @return 수락 대기중인 약속 리스트
     * 
     */
    @GetMapping("/wait")
    public HttpEntity<Object> waitPromise(@RequestParam("member_id") Long memberId){
        Optional<Member> memberOptional = memberService.getMember(memberId);
        if (memberOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(noLogin);
        }
        return ResponseEntity.ok().body(promiseService.waitPromise(memberId));
    }

    /**
     * 수락 or 거절
     * @param promiseMemberId 약속멤버고유번호
     * @param participate 참여상태
     * @return 수락 or 거절
     */
    @PostMapping("/accept")
    public HttpEntity<Object> acceptPromise(@RequestParam("promise_member_id") Long promiseMemberId , @RequestParam("participate")PARTICIPATE participate){
        if(participate.equals(PARTICIPATE.WAIT)){
            return ResponseEntity.badRequest().body("상태를 골라주세요.");
        }

        PromiseMember promiseMember = promiseService.promiseAccept(promiseMemberId, participate);
        if(promiseMember==null){
            return ResponseEntity.badRequest().body(noParticipate);
        }

        return ResponseEntity.ok().body(promiseMember);
    }

    /**
     * 참가자 약속 취소
     * @param promiseMemberId 참가자 고유정보
     * @return 약속취소
     */
    @PostMapping("/member/cancel")
    public HttpEntity<Object> promiseMemberCancel(@RequestParam("promise_member_id") Long promiseMemberId){
        PromiseMember promiseMember = promiseService.promiseMemberCancel(promiseMemberId);
        if(promiseMember==null){
            return ResponseEntity.badRequest().body(noParticipate);
        }

        return ResponseEntity.ok().body(promiseMember);
    }

    /**
     * 주최자 약속 파기
     * @param promiseId 약속정보
     * @return 약속파기
     */
    @PostMapping("/cancel")
    public HttpEntity<Object> promiseCancel(@RequestParam("promise_id") Long promiseId){
        Promise promise = promiseService.promiseCancel(promiseId);

        if(promise==null){
            return ResponseEntity.badRequest().body("약속이 존재하지 않습니다.");
        }

        return ResponseEntity.ok(promise);
    }

    /**
     * 약속 대기자
     * @param promiseId 약속 정보
     * @return 대기자 리스트
     */
    @GetMapping("/wait/member")
    public HttpEntity<Object> waitMembers(@RequestParam("promise_id") Long promiseId){

        return ResponseEntity.ok(promiseService.waitMembers(promiseId));
    }



    
    
}
