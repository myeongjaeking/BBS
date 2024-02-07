package javaspring.BBS.controller;

import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.service.BulletinBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
