package javaspring.BBS.repository;

import jakarta.persistence.EntityManager;
import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class JpaBulletinBoardRepository implements BulletinBoardRepository {
    private  final EntityManager em;


    public JpaBulletinBoardRepository(EntityManager em){this.em =em;}

    @Override
    public BulletinBoard save(BulletinBoard bulletinBoard,Long member_id) {
        Member member = em.find(Member.class,member_id);
        if(member!=null){
            bulletinBoard.setMember(member);
            member.getBulletinBoards().add(bulletinBoard);
            em.persist(bulletinBoard);
        }
        return null;
    }

    @Override
    public Optional<BulletinBoard> findById(Long id) {
        BulletinBoard bulletinBoard = em.find(BulletinBoard.class,id);
        return Optional.ofNullable(bulletinBoard);
    }

    @Override
    public Optional<BulletinBoard> findByTitle(String title) {
        return em.createQuery("select m from BulletinBoard m where m.title = :title", BulletinBoard.class)
                .setParameter("title", title)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<BulletinBoard> findByContent(String content) {
        return em.createQuery("select m from BulletinBoard m where m.content = :content", BulletinBoard.class)
                .setParameter("content", content)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<BulletinBoard> findAll() {
        return em.createQuery("select m from BulletinBoard m",BulletinBoard.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        BulletinBoard bulletinBoard = em.find(BulletinBoard.class, id);
        if (bulletinBoard != null) {
            em.remove(bulletinBoard);
        }
    }

    @Override
    public BulletinBoard edit_bulletinboard(Long id, BulletinBoard bulletinBoard) {
        BulletinBoard existingBulletinBoard = em.find(BulletinBoard.class, id);
        if (existingBulletinBoard != null) {
            existingBulletinBoard.setTitle(bulletinBoard.getTitle());
            existingBulletinBoard.setContent(bulletinBoard.getContent());
            // 다른 필요한 필드들도 수정 가능

            em.merge(existingBulletinBoard); // merge를 사용하여 수정된 엔티티를 데이터베이스에 반영
            return existingBulletinBoard;
        }
        return null; // 수정할 게시판이 없는 경우
    }
}
