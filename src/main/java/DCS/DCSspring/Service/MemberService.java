package DCS.DCSspring.Service;

import DCS.DCSspring.Domain.Member;
import DCS.DCSspring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
        validDuplicateMember(member); //중복 email 검증 메소드.
        memberRepository.save(member);
        return member.getId();
    }

    public Long getMemberId(Member member){
        return member.getId();
    }

    private void validDuplicateMember(Member member) {
        Optional<Member> result = memberRepository.findByEmail(member.getEmail());
        result.ifPresent(m ->{
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        });
    }

    public List<Member> findMembers(){ //전체 회원 조회.
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findOne(String email){
        return memberRepository.findByEmail((email));
    }

    public Member findOneMember(String email){
        return memberRepository.findByEmail(email).get();
    }
}
