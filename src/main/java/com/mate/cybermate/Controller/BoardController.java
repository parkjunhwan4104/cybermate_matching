package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.apply.applyListDTO;
import com.mate.cybermate.Service.ApplyService;
import com.mate.cybermate.Service.BoardService;
import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.domain.Board;
import com.mate.cybermate.domain.MatchingApply;
import com.mate.cybermate.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ApplyService applyService;
    private final MemberService memberService;

    @GetMapping("/boards/1")
    public String showBoard(Model model, Principal principal){



        try{
            Long num=Long.valueOf(1);
            Board board=boardService.getBoard(num);
            Member member=memberService.getMember(principal.getName());
            Long srId=member.getStudyRoom().getSr_id();

            List<applyListDTO> applyDTOList = applyService.getApplyListByBoardId(num);

            model.addAttribute("board", board);
            model.addAttribute("applyList", applyDTOList);
            model.addAttribute("srId",srId);

            return "board/detail";

        }
        catch(Exception e){

            return "redirect:/";

        }

    }


}
