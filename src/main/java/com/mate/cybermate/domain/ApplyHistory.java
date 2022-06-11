package com.mate.cybermate.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ApplyHistory {

    @Id
    @Column(name="applyHistoryId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyHistoryId;



    private String roomName;

    private String memberName;

    private String subject;

    private Long age;

    private String sex;

    private boolean belong;

    private LocalDateTime regDate=LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="studyRoomId")
    private Study_Room studyRoom;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="studyRoomApplyId")
    private StudyRoomApply studyRoomApply;

    public static ApplyHistory createApplyHistory(String roomName,String memberName,String subject,Long age,String sex){
        ApplyHistory applyHistory=new ApplyHistory();
        applyHistory.roomName=roomName;
        applyHistory.memberName=memberName;
        applyHistory.subject=subject;
        applyHistory.age=age;
        applyHistory.sex=sex;

        return applyHistory;

    }



    public void setHistoryMember(Member member){
        this.member=member;

    }
    public void setHistoryStudyRoom(Study_Room studyRoom){
        this.studyRoom=studyRoom;
    }
    public void setHistoryApply(StudyRoomApply studyRoomApply){
        this.studyRoomApply=studyRoomApply;
    }
}
