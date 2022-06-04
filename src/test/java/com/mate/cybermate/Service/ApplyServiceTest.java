package com.mate.cybermate.Service;

import com.mate.cybermate.Config.Role;
import com.mate.cybermate.Dao.MatchingApplyRepository;
import com.mate.cybermate.domain.MatchingApply;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.StudyRoom;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ApplyServiceTest {
/*
    Member member= Member.createMember(
            "user1", "123","유저1", Role.MEMBER
    );

    Member member2= Member.createMember(
            "user2", "1234","유저2", Role.MEMBER
    );

    @Test
    void saveApply() {

        Long age=Long.valueOf(20);
        String sex="남자";
        String subject="자바";
        String lectureName="컴프1";
        Long lectureContentNo=Long.valueOf(13);

        MatchingApply matchingApply=MatchingApply.createApply(
          age,sex,subject,lectureName,lectureContentNo
        );

        matchingApply.setMember(member);



        assertEquals("자바",matchingApply.getSubject());
        assertNotNull(matchingApply);

    }

    @Test
    void getApplyList(){
        List<MatchingApply> list=new ArrayList<>();

        Long age=Long.valueOf(20);
        String sex="남자";
        String subject="자바";
        String lectureName="컴프1";
        Long lectureContentNo=Long.valueOf(13);

        MatchingApply matchingApply=MatchingApply.createApply(
                age,sex,subject,lectureName,lectureContentNo
        );

        matchingApply.setMember(member);

        list.add(matchingApply);

        assertTrue(list.contains(matchingApply));
    }

    @Test
    void doMatching() {
        List<Member> studyRoomMember=new ArrayList<>();

        List<MatchingApply> applyList=new ArrayList<>();

        StudyRoom studyRoom=null;

        Long age=Long.valueOf(20);
        String sex="남자";
        String subject="자바";
        String lectureName="컴프1";
        Long lectureContentNo=Long.valueOf(13);

        MatchingApply matchingApply=MatchingApply.createApply(
                age,sex,subject,lectureName,lectureContentNo
        );

        matchingApply.setMember(member);

        Long age2=Long.valueOf(20);
        String sex2="남자";
        String subject2="자바";
        String lectureName2="컴프2";
        Long lectureContentNo2=Long.valueOf(10);

        MatchingApply matchingApply2=MatchingApply.createApply(
                age2,sex2,subject2,lectureName2,lectureContentNo2
        );

        matchingApply2.setMember(member2);

        applyList.add(matchingApply);
        applyList.add(matchingApply2);

        if(matchingApply.getSubject().equals(matchingApply2.getSubject())){
            studyRoomMember.add(matchingApply.getMember());
            studyRoomMember.add(matchingApply2.getMember());

            studyRoom=StudyRoom.createStudyRoom(studyRoomMember);

            applyList.remove(matchingApply);
            applyList.remove(matchingApply2);
        }

        assertEquals(2,studyRoom.getStudyRoomMember().size());



    }

 */
}