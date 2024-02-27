package javaspring.BBS.controller;

import jakarta.servlet.http.HttpSession;
import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.domain.Member;
import javaspring.BBS.repository.MemberRepository;
import javaspring.BBS.service.BulletinBoardService;
import javaspring.BBS.service.MemberService;
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
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @Autowired
    public BulletinBoardController(BulletinBoardService bulletinBoardService,MemberService memberService,MemberRepository memberRepository){
        this.memberService = memberService;
        this.bulletinBoardService = bulletinBoardService;
        this.memberRepository = memberRepository;
    }
    @GetMapping("/bulletinboards/new")
    public String createForm(){
        return "bulletinboards/createBulletinBoard";
    }

    @PostMapping("/bulletinboards/new")
    public String create(BulletinBoardForm form, HttpSession session){
        BulletinBoard bulletinBoard = new BulletinBoard();
        bulletinBoard.setTitle(form.getTitle());
        bulletinBoard.setContent(form.getContent());
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if(loggedInMember!=null){
            Long memberId = loggedInMember.getMember_id();
            bulletinBoardService.create(bulletinBoard,memberId);
            return "members/success";
        }else{
            return "redirect:/members/login";
        }

    }

    @GetMapping("bulletinboards")
    public String list(Model model){
        List<BulletinBoard> bulletinBoards = bulletinBoardService.findBulletinBoards();
        model.addAttribute("bulletinboards",bulletinBoards);
        return "bulletinboards/bulletinboardList";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        // ID를 사용하여 기존 게시판 정보를 가져옴
        Optional<BulletinBoard> optionalBulletinBoard = bulletinBoardService.findOne(id);
        // optionalBulletinBoard가 존재하는 경우 모델에 추가
        optionalBulletinBoard.ifPresent(bulletinBoard -> model.addAttribute("bulletinboard", bulletinBoard));
        return "bulletinboards/editBulletinBoard";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, BulletinBoardForm form) {
        // ID를 Long 타입으로 변환

        Optional<BulletinBoard> optionalBulletinBoard = bulletinBoardService.findOne(id);

        // optionalBulletinBoard가 존재하는 경우에만 수정 진행
        optionalBulletinBoard.ifPresent(bulletinBoard -> {
            // form에서 받아온 정보로 기존 게시판 정보 수정
            bulletinBoard.setTitle(form.getTitle());
            bulletinBoard.setContent(form.getContent());
            bulletinBoardService.update(bulletinBoard);
            // 수정된 게시판 정보를 저장
        });

        return "members/success";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable("id") Long id, Model model) {
        // ID를 사용하여 기존 게시판 정보를 가져옴
        Optional<BulletinBoard> optionalBulletinBoard = bulletinBoardService.findOne(id);

        // (Thyleaf템플릿에서 사용할 모델) optionalBulletinBoard가 존재하는 경우 모델에 추가
        optionalBulletinBoard.ifPresent(bulletinBoard -> model.addAttribute("bulletinboard", bulletinBoard));

        return "bulletinboards/deleteBulletinBoard";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        bulletinBoardService.delete(id);
        return "members/success";
    }
}
