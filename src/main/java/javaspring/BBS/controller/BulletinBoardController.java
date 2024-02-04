package javaspring.BBS.controller;

import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.service.BulletinBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class BulletinBoardController {
    private final BulletinBoardService bulletinBoardService;
    @Autowired
    public BulletinBoardController(BulletinBoardService bulletinBoardService){
        this.bulletinBoardService = bulletinBoardService;
    }
    @GetMapping("/bulletinboards/new")
    public String createForm(){
        return "bulletinboards/createBulletinBoard";
    }
    @PostMapping("/bulletinboards/new")
    public String create(BulletinBoardForm form){
        BulletinBoard bulletinBoard = new BulletinBoard();
        bulletinBoard.setTitle(form.getTitle());
        bulletinBoard.setContent(form.getContent());
        bulletinBoardService.create(bulletinBoard);
        return "redirect:/";
    }
    @GetMapping("bulletinboards")
    public String list(Model model){

        List<BulletinBoard> bulletinBoards = bulletinBoardService.findBulletinBoards();
        model.addAttribute("bulletinboards",bulletinBoards);
        return "bulletinboards/bulletinboardList";
    }
    @GetMapping("bulletinboardList/{id}")
    public String showBulletinBoard(@PathVariable Long id, Model model) {
        // id에 해당하는 게시판 내용을 서비스에서 가져오는 로직을 구현
        Optional<BulletinBoard> optionalBulletinBoard = bulletinBoardService.findOne(id);
        System.out.println(344);
        // 게시판이 존재하는지 확인
        if (optionalBulletinBoard.isPresent()) {
            BulletinBoard bulletinBoard = optionalBulletinBoard.get();

            // 모델에 데이터 추가
            model.addAttribute("bulletinboard", bulletinBoard);

            // 해당하는 뷰 이름을 반환
            return "bulletinboards/showBulletinBoard";
        } else {
            // 게시판이 존재하지 않는 경우, 적절하게 처리할 수 있도록 핸들링
            // 예를 들어, 에러 페이지로 리다이렉션하거나 에러 메시지를 표시할 수 있음
            return "redirect:/error"; // 에러 페이지로 리다이렉션
        }
    }

}
