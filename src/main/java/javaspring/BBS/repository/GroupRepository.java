package javaspring.BBS.repository;

import javaspring.BBS.domain.Group;
import javaspring.BBS.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    Group groupSave(Group group,Long member_id);

    Optional<Group> findByGroup_id(Long groud_id);
    Optional<Group> findByGroup_name(String group_name);
    List<Group> findAll();
    void addMemberToGroup(Group group, Long memberId);
    void deleteGroup(Long id);

}
