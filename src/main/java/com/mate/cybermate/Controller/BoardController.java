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
    public String showBoard(Model model, Principal principal) {   // 스터디룸 리스트들을 보여주는 게시판


        try {


            Long num = Long.valueOf(1);
            Board board = boardService.getBoard(num);


            if (board.getRoomList().size() != 0) {

                List<StudyRoomListDTO> studyRoomList = studyRoomService.getRoomListByBoardId(num);



                for (int i = 0; i < studyRoomList.size(); i++) {
                    //applyHistoryDTOList=applyHistoryService.getRoomListBySrId(studyRoomList.get(i).getId());

                    if(studyRoomList.get(i).getOwnerName().equals(principal.getName())){
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