package com.mate.cybermate.domain;
import com.mate.cybermate.CybermateApplication;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Study_Room {

    @Id
    @Column(name="srId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long srId;

    private String roomName;


    private String subject;

    private Long maxNum;

    private Long currentNum;

    private String requirement;

    private String description;

    private String goal;

    private Long goalLectureNo;

    private Long leftContent;

    private Long contentNo;

    private String isPermitAuto;

    private Long matesLectureNo;


    private float matesPercent;

    private LocalDateTime regDate=LocalDateTime.now();



    private static List<Member> memberList=new ArrayList<>();

    @OneToMany(mappedBy = "studyRoom" ,fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<StudyRoomApply> studyRoomApply=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @OneToMany(mappedBy = "studyRoom",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<TakeLectureHistory> takeLectureHistories=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boardId")
    private Board board;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="srBoardId")
    private StudyRoomBoard studyRoomBoard;

    public static Study_Room createRoom(String roomName,Long maxNum,String subject,String description, String requirement,Long contentNo,String isPermit){
        Study_Room studyRoom=new Study_Room();
        studyRoom.roomName=roomName;
        studyRoom.maxNum=maxNum;
        studyRoom.subject=subject;
        studyRoom.description=description;
        studyRoom.requirement=requirement;
        studyRoom.currentNum=Long.valueOf(1);
        studyRoom.contentNo=contentNo;
        studyRoom.isPermitAuto=isPermit;
        return studyRoom;
    }



    public void setBoard(Board board){
        this.board=board;
    }

    public void setGoal(String goal){
        this.goal=goal;
    }


    public void setContentNo(Long contentNo){
        this.contentNo=contentNo;
    }



    public void setCurrentNum(){

        this.currentNum=this.currentNum+1;


    }

    public void setMember(Member member){
        this.member=member;
    }

    public void setSrBoard(StudyRoomBoard studyRoomBoard){
        this.studyRoomBoard=studyRoomBoard;
    }


    public void setMatesLectureNo(Long no){


        this.matesLectureNo = no;

    }



    public void setMatesPercent(float no){
        this.matesPercent=no;
    }








}
