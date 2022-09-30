package com.mate.cybermate.Service;

import com.mate.cybermate.Dao.StudyRoomBoardRepository;
import com.mate.cybermate.domain.Board;
import com.mate.cybermate.domain.StudyRoomBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyRoomBoardService {

    private final StudyRoomBoardRepository studyRoomBoardRepository;

    public StudyRoomBoard getSrBoardById(Long srbId) throws IllegalStateException{
        Optional<StudyRoomBoard> findSrBoard=studyRoomBoardRepository.findById(srbId);
        findSrBoard.orElseThrow(

                ()-> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );

        StudyRoomBoard srBoard=findSrBoard.get();

        return srBoard;
    }
}
