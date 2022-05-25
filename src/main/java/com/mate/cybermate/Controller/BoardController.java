package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.apply.applyListDTO;
import com.mate.cybermate.Service.ApplyService;
import com.mate.cybermate.Service.BoardService;
import com.mate.cybermate.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ApplyService applyService;

    @GetMapping("/boards/2")
    public String showBoard(Model model){



        try{
            Long num=Long.valueOf(2);
            Board board=boardService.getBoard(num);

            List<applyListDTO> applyDTOList=applyService.getApplyListByBoardId(num);





            model.addAttribute("board",board);
            model.addAttribute("applyList",applyDTOList);

            return "board/detail";

        }
        catch(Exception e){

            return "redirect:/";

        }

    }


}
