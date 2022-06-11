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

public class Board {

    @Id
    @Column(name="boardId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String boardName;

    private LocalDateTime regDate=LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @OneToMany(mappedBy = "board",fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Study_Room> roomList=new ArrayList<>();

    public static Board createBoard(String boardName,Member member){
            Board board=new Board();
            board.boardName=boardName;
            board.member=member;

            return board;

    }
}
