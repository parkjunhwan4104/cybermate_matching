package com.mate.cybermate.Controller;

import com.mate.cybermate.CybermateApplication;
import com.mate.cybermate.DTO.ApplyHistory.ApplyHistoryDTO;
import com.mate.cybermate.DTO.StudyRoom.StudyRoomListDTO;
import com.mate.cybermate.Service.*;
import com.mate.cybermate.domain.ApplyHistory;
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
    private final ApplyHistoryService applyHistoryService;
    private final StudyRoomService studyRoomService;

    @GetMapping("/boards/1")
    public String showBoard(Model model, Principal principal){



        try{


            Long num=Long.valueOf(1);
            Board board=boardService.getBoard(num);
            Member member=memberService.getMember(principal.getName());


            if(board.getRoomList().size()!=0) {

                List<StudyRoomListDTO> studyRoomList = studyRoomService.getRoomListByBoardId(num);

                List<ApplyHistoryDTO> applyHistoryDTOList=null;

                for(int i=0;i<studyRoomList.size();i++){
                    //applyHistoryDTOList=applyHistoryService.getRoomListBySrId(studyRoomList.get(i).getId());
                    applyHistoryDTOList=applyHistoryService.getApplyHistoryAll();



                    for(int j=0;j<applyHistoryDTOList.size();j++){

                        /*System.out.println(i+"번쨰");
                        System.out.println("현재 스터디룸에 속한 회원 로그인아이디: "+applyHistoryDTOList.get(j).getMemberName());
                        System.out.println("현재 로그인한 회원"+member.getNickName());*/
                        if(applyHistoryDTOList.get(j).getSrId()==(studyRoomList.get(i).getId())){
                            if(applyHistoryDTOList.get(j).getMemberName().equals(member.getNickName())){
                                studyRoomList.get(i).setBelong(true);
                            }
                            else{
                                studyRoomList.get(i).setBelong(false);
                            }
                        }





                    }



                }

                model.addAttribute("applyHistoryList", studyRoomList);




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
