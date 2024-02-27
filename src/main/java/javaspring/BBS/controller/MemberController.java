package javaspring.BBS.controller;

import jakarta.servlet.http.HttpSession;
import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.domain.Member;
import javaspring.BBS.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MemberController {
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService){this.memberService=memberService;}
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(MemberForm memberForm){
        Member member = new Member();
        member.setMember_name(memberForm.getMember_name());
        member.setMember_password(memberForm.getMember_password());
        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping("/members/login")
    public String loginForm(){return "members/login";}

    @PostMapping("/members/login")
    public String login(MemberForm form, Model model, HttpSession session){

        Optional<Member> foundMember = memberService.login(form.getMember_name(),form.getMember_password());

        if(foundMember.isPresent()){
            Member loggedInMember = foundMember.get();
            session.setAttribute("loggedInMember",loggedInMember);
            return "/members/success";
        }else{
            model.addAttribute("error","Invalid username or password");
            return "redirect:/";
        }
    }
}
