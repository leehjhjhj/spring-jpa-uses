package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 필드 모든 것을 가지고 생성자를 똑같이 만들어준다?, 파이널이 있는 필드만 가지고 생성자를 만들어준다.
public class MemberService {
    private final MemberRepository memberRepository;


    //회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //  중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전체 조회

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long MemberId) {
        return memberRepository.findOne(MemberId);
    }
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
