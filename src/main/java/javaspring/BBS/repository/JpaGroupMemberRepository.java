package javaspring.BBS.repository;

import jakarta.persistence.EntityManager;
import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.domain.Group;
import javaspring.BBS.domain.GroupMember;
import javaspring.BBS.domain.Member;

import java.util.List;
import java.util.Optional;

public class JpaGroupMemberRepository implements GroupMemberRepository{

    private final EntityManager em;
    public JpaGroupMemberRepository(EntityManager em){this.em=em;}
    @Override
    public GroupMember groupMemberCreate(Long group_id,Long member_id){
        GroupMember groupMember = new GroupMember();
        Member member = em.find(Member.class,member_id);
        Group group = em.find(Group.class,group_id);
        if(member!=null&&group!=null){
            groupMember.setGroup(group);
            groupMember.setMember(member);
            groupMember.setGroup_role(true);
            em.persist(groupMember);
        }
        return groupMember;
    }
    @Override
    public void leaveGroup(Long group_id, Long member_id) {
        Group group = em.find(Group.class,group_id);
        // 그룹 멤버 테이블에서 주어진 그룹 ID와 멤버 ID에 해당하는 그룹 멤버를 검색
        Optional<GroupMember> groupMember = em.createQuery("SELECT gm FROM GroupMember gm WHERE gm.group.id = :group_id AND gm.member.id = :member_id", GroupMember.class)
                .setParameter("group_id", group_id)
                .setParameter("member_id", member_id)
                .getResultList().stream().findFirst();

        // 그룹 멤버를 찾은 경우 삭제
        groupMember.ifPresent(em::remove); //em.remove(groupMember.get())을 호출하는 대신에, Optional이 비어있지 않을 때만 em.remove() 메서드를 호출
        group.setGroup_num_people(group.getGroup_num_people() - 1);
    }
    @Override
    public List<Object[]> findGroupAndRoleByMemberId(Long memberId) {
        return em.createQuery("SELECT gm.group.group_name, gm.group_role, gm.group.group_id FROM GroupMember gm WHERE gm.member.id = :memberId", Object[].class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public z deleteGroupAndMember(Long id) {
        // 해당 그룹 ID에 해당하는 모든 그룹 멤버를 검색
        List<GroupMember> groupMembers = em.createQuery("SELECT gm FROM GroupMember gm WHERE gm.group.id = :groupId", GroupMember.class)
                .setParameter("groupId", id)
                .getResultList();
        // 검색된 모든 그룹 멤버를 삭제
        for (GroupMember member : groupMembers) {
            em.remove(member);
        }
    }

    @Override
    public GroupMember groupMemberJoin(Long group_id,Long member_id){
        GroupMember groupMember = new GroupMember();
        Member member = em.find(Member.class,member_id);
        Group group = em.find(Group.class,group_id);
        if(member!=null&&group!=null&&group.getGroup_max_people()!=group.getGroup_num_people()){
            group.setGroup_num_people(group.getGroup_num_people() + 1);
            groupMember.setGroup(group);
            groupMember.setMember(member);
            groupMember.setGroup_role(false);
            em.persist(groupMember);
        }
        return groupMember;
    }

    @Override
    public List<GroupMember> findMyGroup(Long member_id) {
        return null;
    }
    @Override
    public List<GroupMember> findByGroupMemberAll(){
        return em.createQuery("select gm from GroupMember gm",GroupMember.class)
                .getResultList();
    }


}
