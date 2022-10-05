package com.mate.cybermate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class TakeLectureHistory {

    @Id
    @Column(name="TLHId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TLHId;

    private Long lectureNum;

    private LocalDateTime regDate=LocalDateTime.now();

    private String regDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="srId")
    private Study_Room studyRoom;



    public static TakeLectureHistory createTakeLectureHistory(Long lectureNum){
        TakeLectureHistory takeLectureHistory=new TakeLectureHistory();
        takeLectureHistory.lectureNum=lectureNum;

        return takeLectureHistory;
    }

    public void setStudyRoom(Study_Room studyRoom){
        this.studyRoom=studyRoom;
        studyRoom.getTakeLectureHistories().add(this);
    }

    public void setMember(Member member){
        this.member=member;
        member.getTakeLectureHistories().add(this);
    }

    public void setLectureNum(Long lectureNum){
        this.lectureNum=lectureNum;
    }

    public void setRegDay(String regDay) {
     this.regDay=regDay;
    }


}
