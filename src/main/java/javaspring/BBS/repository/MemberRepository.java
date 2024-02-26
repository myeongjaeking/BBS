package javaspring.BBS.repository;

import javaspring.BBS.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByMember_id(Long member_id);
    Optional<Member> findByMember_name(String member_name);
    List<Member> findAll();

    Optional<Member> findByMemberNameAndMemberPassword(String member_name,String member_password);
}
