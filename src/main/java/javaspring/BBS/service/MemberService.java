package javaspring.BBS.service;

import javaspring.BBS.domain.Member;
import javaspring.BBS.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getMember_id();
    }
    public Optional<Member> login(String member_name,String member_password){
       return  memberRepository.findByMemberNameAndMemberPassword(member_name,member_password);
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findByMember_name(member.getMember_name())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                }); }
    public List<Member> findMembers(){return memberRepository.findAll();}
}
