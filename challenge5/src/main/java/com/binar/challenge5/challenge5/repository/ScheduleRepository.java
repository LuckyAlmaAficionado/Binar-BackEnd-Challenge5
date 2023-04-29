package com.binar.challenge5.challenge5.repository;

import com.binar.challenge5.challenge5.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Long deleteByMovieFk(Long movieCode);

    Schedule findByMovieFk(Long movieFk);

    Schedule findByScheduleId(Long scheduleId);

}
