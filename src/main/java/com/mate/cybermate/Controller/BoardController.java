package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.ApplyHistory.ApplyHistoryDTO;
import com.mate.cybermate.DTO.ApplyHistory.StudyRoomApplyDTO;
import com.mate.cybermate.DTO.StudyRoom.StudyRoomListDTO;
import com.mate.cybermate.Service.*;
import com.mate.cybermate.domain.*;
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

    private final StudyRoomService studyRoomService;

    private final StudyRoomApplyService studyRoomApplyService;

    private final MemberService memberService;

    @GetMapping("/boards/1")
    public String showBoard(Model model, Principal principal) {   // 스터디룸 리스트들을 보여주는 게시판(화면)


        try {
            Member member=memberService.getMember(principal.getName());

            List<StudyRoomApply> matchAutoApplyList=studyRoomApplyService.matchAutoApplyList();

            studyRoomService.doMatching(matchAutoApplyList);

            Long num = Long.valueOf(1);
            Board board = boardService.getBoard(num);


            if (board.getRoomList().size() != 0) {

                List<StudyRoomListDTO> studyRoomList = studyRoomService.getRoomListByBoardId(num);



                for (int i = 0; i < studyRoomList.size(); i++) {


                    if(studyRoomList.get(i).getOwnerName().equals(member.getNickName())){
                        studyRoomList.get(i).setBelong(true);
                    }
                    else{
                        studyRoomList.get(i).setBelong(false);
                    }

                    for(int j=0;j<studyRoomList.get(i).getSraList().size();j++){

                        if(studyRoomList.get(i).getSraList().get(j).getMember().getLoginId().equals(principal.getName())){

                            if(studyRoomList.get(i).getSraList().get(j).isAccept()==true){

                                studyRoomList.get(i).setBelong(true);
                                break;
                            }
                        }

                    }



                }
                model.addAttribute("size", board.getRoomList().size());
                model.addAttribute("applyHistoryList", studyRoomList);


            } else {
                model.addAttribute("size", board.getRoomList().size());
            }


            model.addAttribute("board", board);


            return "studyRoomList/detail";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/";

        }

    }



}