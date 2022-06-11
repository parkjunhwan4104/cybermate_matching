package com.mate.cybermate.Service;

import com.mate.cybermate.Dao.ApplyHistoryRepository;
import com.mate.cybermate.domain.ApplyHistory;
import com.mate.cybermate.domain.Study_Room;
import com.mate.cybermate.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyHistoryService {

    private final ApplyHistoryRepository applyHistoryRepository;

    @Transactional
    public void saveApplyHistory(Member member, Study_Room makeRoom){
        ApplyHistory applyHistory=ApplyHistory.createApplyHistory(
                makeRoom.getRoomName(),
                member.getNickName(),
                makeRoom.getSubject(),
                member.getAge(),
                member.getSex()
        );

        applyHistoryRepository.save(applyHistory);
    }
}
