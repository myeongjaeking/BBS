package javaspring.BBS.service;

import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.domain.Member;
import javaspring.BBS.repository.BulletinBoardRepository;
import javaspring.BBS.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class BulletinBoardService {
    private final BulletinBoardRepository bulletinBoardRepository;
    private final MemberRepository memberRepository;
    @Autowired //의존성 주입
    public  BulletinBoardService(BulletinBoardRepository bulletinBoardRepository, MemberRepository memberRepository){
        this.memberRepository = memberRepository;
        this.bulletinBoardRepository = bulletinBoardRepository;
    }
    public String create(BulletinBoard bulletinBoard,Long member_id){
        validateDuplicateTitle(bulletinBoard);
        Member member = memberRepository.findByMember_id(member_id)
                        .orElseThrow(()-> new IllegalStateException("error"));
        bulletinBoard.setMember(member);
        bulletinBoardRepository.save(bulletinBoard,member_id);
        return bulletinBoard.getTitle();
    }

    public void update(BulletinBoard bulletinBoard) {
        bulletinBoardRepository.edit_bulletinboard(bulletinBoard.getId(), bulletinBoard);
    }
    public  void delete(Long id){
        bulletinBoardRepository.deleteById(id);
    }


    private void validateDuplicateTitle(BulletinBoard bulletinBoard){
        bulletinBoardRepository.findByTitle(bulletinBoard.getTitle())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 제목입니다.");
                });
    }
    public List<BulletinBoard> findBulletinBoards(){
        return bulletinBoardRepository.findAll();
    }
    public Optional<BulletinBoard> findOne(String bulletinBoardTitle){
        return bulletinBoardRepository.findByTitle(bulletinBoardTitle);
    }
    public Optional<BulletinBoard> findOne(Long id){
        return bulletinBoardRepository.findById(id);
    }
}
