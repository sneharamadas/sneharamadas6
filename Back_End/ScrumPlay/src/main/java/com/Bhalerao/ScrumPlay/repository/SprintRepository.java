package com.Bhalerao.ScrumPlay.repository;

import com.Bhalerao.ScrumPlay.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}