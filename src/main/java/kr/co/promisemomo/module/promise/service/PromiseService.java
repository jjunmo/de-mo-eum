package kr.co.promisemomo.module.promise.service;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.repository.MemberRepository;
import kr.co.promisemomo.module.promise.entity.Promise;
import kr.co.promisemomo.module.promise.entity.PromiseMember;
import kr.co.promisemomo.module.promise.repository.PromiseMemberRepository;
import kr.co.promisemomo.module.promise.repository.PromiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromiseService {

    private final PromiseMemberRepository promiseMemberRepository;
    private final PromiseRepository promiseRepository;

    private final MemberRepository memberRepository;

    public List<Promise> getPromises(Long id) {

        List<PromiseMember> promiseMembers = promiseMemberRepository.findByMember_Id(id);
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
    public Promise addPromise(Long id ,Promise requestpromise){
        Optional<Member> memberOptional = memberRepository.findById(id);
        //로그인이 안되어있을경우 나중에 추가필요
        if(memberOptional.isEmpty()) return null;
        
        requestpromise.setMember(memberOptional.get());
        promiseRepository.save(requestpromise);

        return requestpromise;
    }





}
