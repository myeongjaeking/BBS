package javaspring.BBS.repository;

import javaspring.BBS.domain.Group;
import javaspring.BBS.domain.GroupMember;

import java.util.List;

public interface GroupMemberRepository {
    GroupMember groupMemberCreate(Long group_id,Long member_id);
    GroupMember groupMemberJoin(Long group_id, Long member_id);
    List<GroupMember> findMyGroup(Long member_id);
    List<Object[]> findGroupAndRoleByMemberId(Long memberId);
    void deleteGroupAndMember(Long id);
    void leaveGroup(Long id,Long member_id);
    List<GroupMember> findByGroupMemberAll();

}
