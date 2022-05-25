package com.mate.cybermate.Service;

import com.mate.cybermate.Dao.BoardRepository;
import com.mate.cybermate.Dao.MatchingApplyRepository;
import com.mate.cybermate.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;


    public Board getBoard(Long boardId){
        Optional<Board> findBoard=boardRepository.findById(boardId);
        findBoard.orElseThrow(

                ()-> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );

        Board board=findBoard.get();

        return board;

    }






}
