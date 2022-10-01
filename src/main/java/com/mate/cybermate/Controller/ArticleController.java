package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.Article.ArticleASaveForm;
import com.mate.cybermate.Service.ArticleService;
import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.Service.StudyRoomBoardService;
import com.mate.cybermate.domain.Article;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.StudyRoomBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final StudyRoomBoardService studyRoomBoardService;

    @GetMapping("/studyRoom/{srId}/board/{srBoardId}/articles/add")
    public String showArticleAdd(Model model, @PathVariable(name="srId")Long srId,@PathVariable(name="srBoardId")Long srBoardId){
        model.addAttribute("articleSaveForm",new ArticleASaveForm());
        model.addAttribute("srId",srId);
        model.addAttribute("srBoardId",srBoardId);

        return "studyRoom/article/addArticle";

    }

    @PostMapping("/studyRoom/{srId}/board/{srBoardId}/articles/add")
    public String doAdd(Principal principal, @Validated ArticleASaveForm articleASaveForm, BindingResult bindingResult, @PathVariable(name="srId")Long srId, @PathVariable(name="srBoardId")Long srBoardId, Model model){
        if(bindingResult.hasErrors()){

            model.addAttribute("srId",srId);
            model.addAttribute("srBoardId",srBoardId);

            return "studyRoom/article/addArticle";
        }
        try{
            Member findMember=memberService.findByLoginId(principal.getName());
            StudyRoomBoard studyRoomBoard=studyRoomBoardService.getSrBoardById(srBoardId);

            articleService.saveArticle(articleASaveForm,findMember,studyRoomBoard);


        }
        catch (IllegalStateException e){
            model.addAttribute("error_msg",e.getMessage());

            return "studyRoom/article/addArticle";
        }


        return "redirect:/studyRoom/"+srId+"/board/"+srBoardId;
    }


    @GetMapping("/board/{srBoardId}/articles/{id}")
    public String showArticleDetail(Model model,@PathVariable(name="id")Long id,@PathVariable(name="srBoardId")Long srBoardId){

        List<Article> articleList=articleService.getArticleListBySrBoardId(srBoardId);

        Article findArticle=null;
        for(int i=0;i<articleList.size();i++){
            if(articleList.get(i).getArticleId()==id){
                findArticle=articleList.get(i);
            }
        }
        model.addAttribute("title",findArticle.getTitle());
        model.addAttribute("body",findArticle.getBody());
        model.addAttribute("srBoardId",srBoardId);
        model.addAttribute("srId",findArticle.getStudyRoomBoard().getStudyRoom().getSrId());

        return "studyRoom/article/articleDetail";

    }
}
