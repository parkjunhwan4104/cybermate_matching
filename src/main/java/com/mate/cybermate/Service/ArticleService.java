package com.mate.cybermate.Service;

import com.mate.cybermate.DTO.Article.ArticleASaveForm;
import com.mate.cybermate.Dao.ArticleRepository;
import com.mate.cybermate.domain.Article;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.StudyRoomBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void saveArticle(ArticleASaveForm articleASaveForm, Member member, StudyRoomBoard studyRoomBoard){
        Article article=Article.createArticle(
                articleASaveForm.getTitle(),
                articleASaveForm.getBody(),
                member
        );

        article.setMember(member);
        article.setStudyRoomBoard(studyRoomBoard);
        article.setWriterName(member.getNickName());

        articleRepository.save(article);
    }


    public List<Article> getArticleListBySrBoardId(Long srBoardId){
        List<Article> articleList=articleRepository.findAll();

        List<Article> findList=new ArrayList<>();

        for(int i=0;i<articleList.size();i++){
            if(articleList.get(i).getStudyRoomBoard().getSrBoardId()==srBoardId){
                findList.add(articleList.get(i));
            }

        }
        return findList;
    }
}
