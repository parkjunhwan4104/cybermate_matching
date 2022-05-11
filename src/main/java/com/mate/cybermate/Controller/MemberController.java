package com.mate.cybermate.Controller;


import com.mate.cybermate.DTO.member.CheckStatus;
import com.mate.cybermate.DTO.member.MemberLoginForm;
import com.mate.cybermate.DTO.member.MemberSaveForm;
import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/members/check/loginId")
    @ResponseBody
    public CheckStatus checkDupleLoginId(@RequestParam String loginId){
        boolean isExistId=memberService.isDupleLoginId(loginId);

        CheckStatus checkStatus=new CheckStatus(isExistId);

        return checkStatus;
    }

    @GetMapping("/members/check/nickName")
    @ResponseBody
    public CheckStatus checkDupleNickName(@RequestParam String nickName){
        boolean isExistNickName= memberService.isDupleNickName(nickName);
        CheckStatus checkStatus=new CheckStatus(isExistNickName);

        return checkStatus;
    }

    @GetMapping("/members/join")
    public String showJoin(Model model){
        model.addAttribute("memberSaveForm",new MemberSaveForm());
        return "member/join";
    }

    @PostMapping("/members/join")
    public String doJoin(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            return "member/join";
        }

        try{
            memberService.saveMember(memberSaveForm);

        }
        catch (Exception e){
            model.addAttribute("err_msg",e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String showLogin(Model model){

        model.addAttribute("memberLoginForm",new MemberLoginForm());
        return "member/login";
    }




}
