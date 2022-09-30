package com.mate.cybermate.Controller;

import com.mate.cybermate.Service.ArticleService;
import com.mate.cybermate.Service.StudyRoomBoardService;
import com.mate.cybermate.domain.Article;
import com.mate.cybermate.domain.StudyRoomBoard;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyRoomBoardController {

    private final ArticleService articleService;
    private final StudyRoomBoardService studyRoomBoardService;

    @GetMapping("/studyRoom/{srId}/board/{srBoardId}")
    public String getBoard(@PathVariable(name="srId")Long srId, @PathVariable(name="srBoardId")Long srBoardId, Model model){

        List<Article> articleList= articleService.getArticleListBySrBoardId(srBoardId);

        StudyRoomBoard studyRoomBoard=studyRoomBoardService.getSrBoardById(srId);

        model.addAttribute("boardName",studyRoomBoard.getBoardName());
        model.addAttribute("srId",srId);
        model.addAttribute("srBoardId",srBoardId);
        model.addAttribute("articleList",articleList);

        return "studyRoom/board/srBoard";

    }
}
