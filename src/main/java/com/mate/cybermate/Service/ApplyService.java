package com.mate.cybermate.Service;

import com.mate.cybermate.DTO.apply.applyListDTO;
import com.mate.cybermate.DTO.apply.applySaveForm;
import com.mate.cybermate.Dao.MatchingApplyRepository;
import com.mate.cybermate.domain.Board;
import com.mate.cybermate.domain.MatchingApply;
import com.mate.cybermate.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyService {
    private final MatchingApplyRepository matchingApplyRepository;

    @Transactional
    public void saveApply(applySaveForm applysaveform, Member member, Board board){


        MatchingApply matchingApply=MatchingApply.createApply(
                applysaveform.getAge(),
                applysaveform.getSex(),
                applysaveform.getSubject(),
                applysaveform.getLectureName(),
                applysaveform.getLectureContentNo()
        );

        matchingApply.setBoard(board);
        matchingApply.setMember(member);

        matchingApplyRepository.save(matchingApply);

    }


    public List<applyListDTO> getApplyListByBoardId(Long id){
        List<MatchingApply> applyList=matchingApplyRepository.findAll();
        List<applyListDTO> applyListDTOList=new ArrayList<>();
        for(MatchingApply apply:applyList){
            if(apply.getBoard().getBoardId()==id){
                applyListDTO applyListDTO=new applyListDTO(apply);
                applyListDTOList.add(applyListDTO);
            }
        }
        return applyListDTOList;

    }







}
