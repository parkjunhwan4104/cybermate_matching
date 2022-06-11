package com.mate.cybermate.Dao;

import com.mate.cybermate.domain.ApplyHistory;
import com.mate.cybermate.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyHistoryRepository extends JpaRepository<ApplyHistory,Long> {
}
