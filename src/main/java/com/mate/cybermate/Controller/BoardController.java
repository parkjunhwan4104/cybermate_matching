package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.StudyRoom.StudyRoomListDTO;
import com.mate.cybermate.Service.*;
import com.mate.cybermate.domain.Board;
import com.mate.cybermate.domain.Study_Room;
import com.mate.cybermate.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final StudyRoomApplyService studyRoomApplyService;
    private final MemberService memberService;

    private final StudyRoomService studyRoomService;

    @GetMapping("/boards/1")
    public String showBoard(Model model, Principal principal){



        try{


            Long num=Long.valueOf(1);
            Board board=boardService.getBoard(num);
            Member member=memberService.getMember(principal.getName());


            if(board.getRoomList().size()!=0) {

                List<StudyRoomListDTO> studyRoomList = studyRoomService.getRoomListByBoardId(num);

                for(int i=0;i<studyRoomList.size();i++){




                    for(int j=0;j<studyRoomList.get(i).getTotal().get(i).size();j++){
                        System.out.println(i+"번의 사이즈" +studyRoomList.get(i).getTotal().get(i).size());

                        System.out.println("현재 스터디룸에 속한 회원 로그인아이디: "+studyRoomList.get(i).getTotal().get(i).get(j).getLoginId());
                        System.out.println("현재 로그인한 회원"+member.getLoginId());
                        if(studyRoomList.get(i).getTotal().get(i).get(j).getLoginId().equals(member.getLoginId())){
                            studyRoomList.get(i).setBelong(true);
                        }
                        else{
                            studyRoomList.get(i).setBelong(false);
                        }
                    }

                    System.out.println(studyRoomList.get(i).getBelong());
                }

                model.addAttribute("studyRoomList", studyRoomList);



            }
            else{
                model.addAttribute("size",board.getRoomList().size());
            }


            model.addAttribute("board", board);




            return "board/detail";

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return "redirect:/";

        }

    }

    @PostMapping("/boards/1/{srId}")
    public String doApply(@PathVariable(name="srId") Long srId,Principal principal){
        Study_Room makeRoom=studyRoomService.findById(srId);
        Member member=memberService.getMember(principal.getName());

        studyRoomApplyService.addStudyRoomApply(srId,member);

        return "redirect:/boards/1";

    }




}
