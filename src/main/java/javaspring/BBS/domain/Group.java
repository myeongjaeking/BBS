package javaspring.BBS.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="group_table")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long group_id;
    private String group_name;
    private String group_password;
    private boolean group_private;
    private Long group_max_people;

    private String group_MemberName;

    public String getGroup_MemberName() {
        return group_MemberName;
    }

    public void setGroup_MemberName(String group_MemberName) {
        this.group_MemberName = group_MemberName;
    }

    public Long getGroup_max_people() {
        return group_max_people;
    }

    public void setGroup_max_people(Long group_max_people) {
        this.group_max_people = group_max_people;
    }

    public Long getGroup_num_people() {
        return group_num_people;
    }

    public void setGroup_num_people(Long group_num_people) {
        this.group_num_people = group_num_people;
    }

    private Long group_num_people=1L;

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_password() {
        return group_password;
    }

    public void setGroup_password(String group_password) {
        this.group_password = group_password;
    }

    public boolean isGroup_private() {
        return group_private;
    }

    public void setGroup_private(boolean group_private) {
        this.group_private = group_private;
    }


    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @OneToMany(mappedBy = "group")
    private List<GroupMember> groupMembers;
    public void addMemberToGroup(Member member, boolean groupRole) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroup(this);
        groupMember.setMember(member);
        groupMember.setGroup_role(groupRole);
        groupMembers.add(groupMember);
        member.getGroupMembers().add(groupMember);
    }
    public Group() {
        // 기본 생성자에서 groupMembers를 초기화합니다.
        this.groupMembers = new ArrayList<>();
    }
}
