package javaspring.BBS.repository;

import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long > {

    Member create(Member member);
    Optional<Member> findByMember(String member_name);

}
