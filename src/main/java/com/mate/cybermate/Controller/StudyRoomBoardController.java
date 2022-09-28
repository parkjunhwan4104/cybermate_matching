package com.mate.cybermate.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudyRoomBoardController {

    @GetMapping("/studyRoom/{srId}/board/{srBoardId}")
    public String getBoard(@PathVariable(name="srId")Long srId,@PathVariable(name="srBoardId")Long srBoardId){

        return "studyRoom/board/srBoard";

    }
}
