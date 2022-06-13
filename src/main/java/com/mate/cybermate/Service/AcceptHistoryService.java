package com.mate.cybermate.Service;

import com.mate.cybermate.Dao.AcceptHistoryRepository;
import com.mate.cybermate.domain.AcceptHistory;
import com.mate.cybermate.domain.StudyRoomApply;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AcceptHistoryService {

    private final AcceptHistoryRepository acceptHistoryRepository;





}
