package com.mate.cybermate.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Article {

    @Id
    @Column(name="articleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    private String title;

    private String body;

    private String writerName;

    private LocalDateTime regDate=LocalDateTime.now();


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="srBoardId")
    private StudyRoomBoard studyRoomBoard;

    public static Article createArticle(String title, String body){
        Article article=new Article();
        article.title=title;
        article.body=body;
        article.writerName=article.getMember().getNickName();

        return article;

    }

    public void setWriterName(String writerName){
        this.writerName=writerName;
    }


    public void setMember(Member member){
        this.member=member;
        member.getArticleList().add(this);
    }

    public void setStudyRoomBoard(StudyRoomBoard studyRoomBoard){
        this.studyRoomBoard=studyRoomBoard;
        studyRoomBoard.getArticleList().add(this);

    }


}
