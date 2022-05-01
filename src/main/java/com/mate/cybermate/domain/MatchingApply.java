package com.mate.cybermate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter

public class MatchingApply {

    @Id
    @Column(name="applyId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    private Long age;

    private String sex;

    private String subject;

    private String lectureName;

    private Long lectureContentNo;

    private LocalDateTime regDate=LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boardId")
    private Board board;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    public static MatchingApply createApply(Long age,String sex,String subject, String lectureName,Long lectureContentNo){
        MatchingApply matchingApply=new MatchingApply();
        matchingApply.age=age;
        matchingApply.sex=sex;
        matchingApply.subject=subject;
        matchingApply.lectureName=lectureName;
        matchingApply.lectureContentNo=lectureContentNo;

        return matchingApply;
    }

    public void setMember(Member member){
        this.member=member;
        member.setApply(this);

    }

    public void setBoard(Board board){
        this.board=board;
        board.getApplyList().add(this);
    }








}
