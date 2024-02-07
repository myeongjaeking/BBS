package javaspring.BBS.service;

import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.repository.BulletinBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BulletinBoardService {
    private final BulletinBoardRepository bulletinBoardRepository;

    @Autowired //의존성 주입
    public  BulletinBoardService(BulletinBoardRepository bulletinBoardRepository){
        this.bulletinBoardRepository = bulletinBoardRepository;
    }
    public String create(BulletinBoard bulletinBoard){
        validateDuplicateTitle(bulletinBoard);
        bulletinBoardRepository.save(bulletinBoard);
        return bulletinBoard.getTitle();
    }

    public void update(BulletinBoard bulletinBoard) {
        bulletinBoardRepository.edit_bulletinboard(bulletinBoard.getId(), bulletinBoard);
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
