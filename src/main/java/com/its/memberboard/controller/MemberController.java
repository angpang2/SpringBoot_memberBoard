package com.its.memberboard.controller;

import com.its.memberboard.dto.MemberDTO;
import com.its.memberboard.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String memberSaveForm(){
        return "/memberPages/memberLogin";
    }
    @PostMapping("/save")
    public String memberSave(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "index";
    }

    @PostMapping("/dup-check")
    public ResponseEntity emailDuplicateCheck(@RequestParam("memberEmail") String memberEmail) {
        String checkResult = memberService.emailDuplicateCheck(memberEmail);
        if (checkResult != null) {
            return new ResponseEntity<>("사용해도 됩니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("사용할 수 없습니다.", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public @ResponseBody String memberLogin(@RequestParam("memberEmail")String memberEmail
            , @RequestParam("memberPassword")String memberPassword, HttpSession session){
        MemberDTO result = memberService.memberLogin(memberEmail,memberPassword);
        if(result!=null){
            session.setAttribute("member",result);
            return "ok";
        }else {
            return "no";
        }
    }

    @GetMapping("/logout")
    public String memberLogout(HttpSession session){
        session.invalidate();
        return "index";
    }

    @GetMapping("/mypage")
    public String memberMypage(){
        return "memberPages/myPage";
    }



}
