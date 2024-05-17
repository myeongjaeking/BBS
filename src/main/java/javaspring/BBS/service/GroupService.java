package javaspring.BBS.service;

import javaspring.BBS.domain.Group;
import javaspring.BBS.domain.GroupMember;
import javaspring.BBS.domain.Member;
import javaspring.BBS.repository.GroupMemberRepository;
import javaspring.BBS.repository.GroupRepository;
import javaspring.BBS.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final AlarmService alarmService;
    public String groupCreate(Group group,Long member_id)throws Exception{
        Member member = memberRepository.findByMember_id(member_id)
                .orElseThrow(()->new IllegalStateException("error"));
        //group.setMember(member);

        groupRepository.groupSave(group,member_id);
        groupMemberRepository.groupMemberCreate(group.getGroup_id(), member_id);
        return group.getGroup_name();
    }
    public void groupJoin(Long group_id, Long member_id)throws Exception{
        Optional<Group> optionalGroup = groupRepository.findByGroup_id(group_id);
        if(optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            Optional<GroupMember> existingMember = group.getGroupMembers().stream()
                    .filter(groupMember -> groupMember.getMember().getMember_id().equals(member_id))
                    .findAny();
            if (existingMember.isPresent()) {
                throw new IllegalStateException("해당 그룹에 이미 회원이 존재합니다.");
            }
            else{
                groupMemberRepository.groupMemberJoin(group_id,member_id);
            }
        }
    }
    public void groupDelete(Long group_id){
        groupMemberRepository.deleteGroupAndMember(group_id);
        groupRepository.deleteGroup(group_id);
    }
    public void groupLeave(Long group_id,Long member_id){
        groupMemberRepository.leaveGroup(group_id,member_id);
    }
    public List<Object[]> myGroup(Long member_id){return groupMemberRepository.findGroupAndRoleByMemberId(member_id);}
    public Optional<Group> findByGroup_id(Long id){
        return groupRepository.findByGroup_id(id);
    }
    public List<Group> findAll(){return groupRepository.findAll();}
}
