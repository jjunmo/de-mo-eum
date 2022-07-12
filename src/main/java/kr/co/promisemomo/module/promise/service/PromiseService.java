package kr.co.promisemomo.module.promise.service;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.repository.MemberRepository;
import kr.co.promisemomo.module.promise.dto.request.PromiseCreateRequest;
import kr.co.promisemomo.module.promise.dto.response.PromiseCreateResponse;
import kr.co.promisemomo.module.promise.dto.response.host.PromiseData;
import kr.co.promisemomo.module.promise.dto.response.host.PromiseHostResponse;
import kr.co.promisemomo.module.promise.dto.response.participant.PromiseWaitData;
import kr.co.promisemomo.module.promise.entity.PARTICIPATE;
import kr.co.promisemomo.module.promise.entity.Promise;
import kr.co.promisemomo.module.promise.entity.PromiseMember;
import kr.co.promisemomo.module.promise.repository.PromiseMemberRepository;
import kr.co.promisemomo.module.promise.repository.PromiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromiseService {

    private final PromiseMemberRepository promiseMemberRepository;
    private final PromiseRepository promiseRepository;

    private final MemberRepository memberRepository;

    /**
     * 내가 참가중인 약속 리스트
     * @param memberId 회원 아이디
     * @return List<Promise>
     */
    public List<Promise> getParticipatePromises(Long memberId) {

        List<PromiseMember> promiseMembers = promiseMemberRepository.findByMember_Id(memberId);
        List<Long> promiseIds =
                promiseMembers
                        .stream()
                        .map(promiseMember -> promiseMember.getPromise().getId())
                        .distinct()
                        .collect(Collectors.toList());

        return promiseRepository.findByIdIn(promiseIds);


//        List<Long> promiseIds = new ArrayList<>();
//
//        for (PromiseMember promiseMember : promiseMembers) {
//
//            Long promiseMemberId = promiseMember.getId();
//
//            if (!promiseIds.contains(id)) {
//                promiseIds.add(id);
//            }
//        }
//
//        return promiseRepository.findByIdIn(promiseIds);
    }
    
    // TODO:약속 추가 로그인 상태에 따라 ..
    //사용자

    /**
     * 약속 저장
     * @param member 참가자
     * @param promiseCreateRequest Insert DTO (약속 정보 )
     * @return PromiseCreateResponse
     */
    @Transactional
    public PromiseCreateResponse addPromise(Member member , PromiseCreateRequest promiseCreateRequest){
        Promise promiseParam = promiseCreateRequest.dtoToEntity(member);

        Promise promise = promiseRepository.save(promiseParam);
        // List 빼서 값을 저장하고 promise set
        List<Long> paramPromiseMembers = promiseCreateRequest.getPromiseMember();

        List<PromiseMember> saveResultPromiseMembers = new ArrayList<>();
        paramPromiseMembers.forEach(
                id -> {
                    Optional<Member> optionalMember = memberRepository.findById(id);
                    if(optionalMember.isPresent()) {
                        PromiseMember promiseMember = new PromiseMember();
                        promiseMember.setMember(optionalMember.get());
                        promiseMember.setPromise(promise);
                        promiseMember.setParticipate(PARTICIPATE.WAIT);
                        saveResultPromiseMembers.add(promiseMemberRepository.save(promiseMember));
                    }
                }
        );
        return promise.entityToDto(saveResultPromiseMembers);
    }


    /**
     * 내가 주최한 약속리스트
     * @param memberId 주최자 아이디 번호
     * @return 주최한 약속의 정보들
     */
    public PromiseHostResponse myHostPromiseList (Long memberId) {

        List<Promise> promiseList = promiseRepository.findByMember_Id(memberId);
        int promiseSize = promiseList.size(); // 약속 개수

        PromiseHostResponse promiseHostResponse = new PromiseHostResponse();
        promiseHostResponse.setPromiseCnt(promiseSize); // 값 세팅

        if (promiseSize > 0) {
            List<PromiseData> promiseDataList = new ArrayList<>();

            for (Promise promise : promiseList) {// Promise Data 만드는 과정
                List<PromiseMember> promiseMemberList = promiseMemberRepository.findByPromise_Id(promise.getId());

                PromiseData promiseData = promise.entityToHostDto(promiseMemberList);

                promiseDataList.add(promiseData);
            }

            promiseHostResponse.setPromiseLists(promiseDataList);
        }

        return promiseHostResponse;
    }

    /**
     * 대기중인 약속 리스트
     * @param memberId 참가자번호
     * @return 대기중인 약속정보들
     */
    public List<PromiseWaitData> waitPromise(Long memberId){

        List<PromiseMember> promiseMembers = promiseMemberRepository.findByParticipateAndMember_Id(PARTICIPATE.WAIT,memberId);

        // Promise Data 추출
        List<PromiseWaitData> promiseWaitDataList = new ArrayList<>();

        for (PromiseMember promiseMember : promiseMembers) {
            promiseWaitDataList.add(promiseMember.entityToDto());
        }

        return promiseWaitDataList;
    }


    /**
     * 약속 수락 & 거절
     * @param promiseMemberId 참가자 번호
     * @param participate 약속상태
     * @return 참가자 정보
     */
    @Transactional
    public PromiseMember promiseAccept(Long promiseMemberId ,PARTICIPATE participate){
        // TODO: participate Controller 에서 WAIT 이면 ERROR 체크
        
        Optional<PromiseMember> promiseMemberOptional = promiseMemberRepository.findById(promiseMemberId);
        if(promiseMemberOptional.isEmpty()){
            return null;
        }
        PromiseMember promiseMember = promiseMemberOptional.get();
        promiseMember.setParticipate(participate);
        return promiseMember;
    }

    /**
     * 참가자의 약속 취소
     * @param promiseMemberId 참가자 번호
     * @return 참가자 정보
     */
    @Transactional
    public PromiseMember promiseMemberCancel(Long promiseMemberId){
        Optional<PromiseMember> promiseMemberOptional = promiseMemberRepository.findById(promiseMemberId);
        if(promiseMemberOptional.isEmpty()){
            return null;
        }
        PromiseMember promiseMember = promiseMemberOptional.get();
        promiseMember.setCancel("Y");
        return promiseMember;
    }


    

    /**
     * 주최자의 약속 파기
     * @param promiseId 약속 번호
     * @return 약속정보
     */
    @Transactional
    public Promise promiseCancel(Long promiseId){
        Optional<Promise> promiseOptional = promiseRepository.findById(promiseId);
        if(promiseOptional.isEmpty()){
            return null;
        }
        Promise promise = promiseOptional.get();
        promise.setCancel("Y");
        return promise;

    }


    /**
     * 약속 대기자 리스트 (주최자가 조회)
     * @param promiseId 약속번호
     * @return 대기자명단
     */
    public List<PromiseMember> waitMembers(Long promiseId){
        return promiseMemberRepository.findByParticipateAndPromise_Id(PARTICIPATE.WAIT,promiseId);
    }

    public String validationPromise (PromiseCreateRequest promiseCreateRequest) {

        if (promiseCreateRequest.getName().isEmpty() || promiseCreateRequest.getName().equals("")) {
            return "이름이 없습니다.";
        }

        if(promiseCreateRequest.getDay() == null) {

        }

        // 년,월,일로 올바른 날짜인지 체크

        String startTime = promiseCreateRequest.getStartTime();
        if(startTime == null){
            return "시작시간을 입력해주세요.";
        } else if (startTime.length() != 4) {
            return "시작시간이 올바르지 않습니다.";
        }

        String startTimeHour = startTime.substring(0, 1);
        String startTimeMinute = startTime.substring(2, 3);
        if (Integer.parseInt(startTimeHour) > 24 || Integer.parseInt(startTimeMinute) > 60) {
            return "시작시간을 다시 설정하세요.";
        }

        String endTime = promiseCreateRequest.getEndTime();
        if(endTime == null){
            return "종료시간을 입력해주세요.";
        } else if (endTime.length() != 4) {
            return "종료시간이 올바르지 않습니다.";
        }

        String endTimeHour = startTime.substring(0, 1);
        String endTimeMinute = startTime.substring(2, 3);
        if (Integer.parseInt(endTimeHour) > 24 || Integer.parseInt(endTimeMinute) > 60) {
            return "종료시간을 다시 설정하세요.";
        }

        return "OK";
    }

}
