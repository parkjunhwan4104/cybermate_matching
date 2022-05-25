package com.mate.cybermate.Config;


import com.mate.cybermate.Dao.BoardRepository;
import com.mate.cybermate.Dao.MemberRepository;
import com.mate.cybermate.domain.Board;
import com.mate.cybermate.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class Datainit {

    private final InitService initService;
    private final MemberRepository memberRepository;


    @PostConstruct
    public void init(){


        if(!memberRepository.existsByLoginId("cybermate")){
            initService.initBoardAndAdmin();
        }

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService{

        private final MemberRepository memberRepository;
        private final BoardRepository boardRepository;
        public void initBoardAndAdmin(){
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();



                    Member admin= Member.createMember(
                            "cybermate",
                            bCryptPasswordEncoder.encode("admin"),
                            "관리자",
                            Role.ADMIN
                    );

            memberRepository.save(admin);

            Board board1= Board.createBoard(
                    "스터디룸",
                    admin

            );
            boardRepository.save(board1);

            Board board2= Board.createBoard(
                    "신청목록",
                    admin

            );
            boardRepository.save(board2);
        }



    }
}
