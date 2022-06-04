package com.mate.cybermate.Service;

import com.mate.cybermate.Config.Role;
import com.mate.cybermate.domain.Board;
import com.mate.cybermate.domain.Member;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
class BoardServiceTest {

    Member member= Member.createMember(
            "user1", "123","유저1", Role.MEMBER
    );

    Board board= Board.createBoard("신청목록",member);
    Board board2= Board.createBoard("게시판",member);

    @Test
    void getBoard() {

        List<Board> boardList=new ArrayList<>();

        boardList.add(board);
        boardList.add(board2);

        Board findBoard=null;

        for(int i=0;i<boardList.size();i++){
            if(boardList.get(i).getBoardName().equals("신청목록")){
                findBoard=boardList.get(i);
            }
        }

        assertEquals("신청목록",findBoard.getBoardName());


    }
}*/