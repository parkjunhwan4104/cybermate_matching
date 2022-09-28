package com.mate.cybermate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class StudyRoomBoard {   //각 스터디룸에서 회원들이 글을 작성하는 게시판

    @Id
    @Column(name="srBoardId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long srBoardId;

    private String boardName;

    private LocalDateTime regDate=LocalDateTime.now();

    @OneToMany(mappedBy = "studyRoomBoard",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Article> articleList=new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="srId")
    private Study_Room studyRoom;

    public static StudyRoomBoard createBoard(String boardName){
        StudyRoomBoard studyRoomBoard=new StudyRoomBoard();
        studyRoomBoard.boardName=boardName;

        return studyRoomBoard;
    }

    public void setStudyRoom(Study_Room studyRoom){
        this.studyRoom=studyRoom;
    }

}
