package javaspring.BBS.repository;

import jakarta.persistence.EntityManager;
import javaspring.BBS.domain.Group;
import javaspring.BBS.domain.Member;

import java.util.List;
import java.util.Optional;

public class JpaGroupRepository implements GroupRepository{
    private final EntityManager em;

    public JpaGroupRepository(EntityManager em){this.em=em;}


    @Override
    public Group groupSave(Group group,Long member_id){
        Member member = em.find(Member.class,member_id);
        if(member!=null){
            group.addMemberToGroup(member,true);
            em.persist(group);
            em.flush();
        }
        return group;
    }

    @Override
    public void deleteGroup(Long id){
        Group group = em.find(Group.class,id);
        if(group!=null){
            em.remove(group);
        }
    }



    @Override
    public Optional<Group> findByGroup_id(Long id){
        Group group = em.find(Group.class,id);
        return Optional.ofNullable(group);
    }
    @Override
    public Optional<Group> findByGroup_name(String group_name){
        List<Group> result = em.createQuery("select g from Group g where g.group_name= :group_name",Group.class)
                .setParameter("group_name",group_name)
                .getResultList();
        return result.stream().findAny();
    }
    @Override
    public List<Group> findAll(){
        return em.createQuery("select g from Group g",Group.class)
                .getResultList();
    }


    @Override
    public void addMemberToGroup(Group group, Long memberId) {
        Member member = em.find(Member.class, memberId);
        if (member != null) {
            group.addMemberToGroup(member,true);
        }
    }
    @Override
    public void edit_group(Group group){
        em.merge(group);
    }

}
