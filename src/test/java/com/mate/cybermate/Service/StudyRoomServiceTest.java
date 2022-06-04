package com.mate.cybermate.Service;

import com.mate.cybermate.Config.Role;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.StudyRoom;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
class StudyRoomServiceTest {




    @Test
    void saveStudyRoom() {
        List<Member> memberList=new ArrayList<>();
        Member member= Member.createMember(
                "user1", "123","유저1", Role.MEMBER
        );

        Member member2= Member.createMember(
                "user2", "1234","유저2", Role.MEMBER
        );

        memberList.add(member);
        memberList.add(member2);

        StudyRoom studyRoom=StudyRoom.createStudyRoom(memberList);

        assertEquals(2,studyRoom.getStudyRoomMember().size());
        assertTrue(studyRoom!=null);

    }

    @Test
    void updateGoal() {

        List<Member> memberList=new ArrayList<>();
        Member member= Member.createMember(
                "user1", "123","유저1", Role.MEMBER
        );

        Member member2= Member.createMember(
                "user2", "1234","유저2", Role.MEMBER
        );

        memberList.add(member);
        memberList.add(member2);

        StudyRoom studyRoom=StudyRoom.createStudyRoom(memberList);
        
        studyRoom.setGoal("1주일에 3개씩 강의 수강");

        assertTrue(studyRoom.getGoal()!=null);
    }
}*/