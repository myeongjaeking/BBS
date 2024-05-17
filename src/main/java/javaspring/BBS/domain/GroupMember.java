package javaspring.BBS.domain;

import jakarta.persistence.*;

@Table(name="group_member")
@Entity
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long group_member_id;
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;
    @Column(name = "group_role")
    private boolean group_role;

    public Long getGroup_member_id() {
        return group_member_id;
    }

    public void setGroup_member_id(Long group_member_id) {
        this.group_member_id = group_member_id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isGroup_role() {
        return group_role;
    }

    public void setGroup_role(boolean group_role) {
        this.group_role = group_role;
    }
}
