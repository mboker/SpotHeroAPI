package org.mboker.spothero.repo;

import org.mboker.spothero.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CostScheduleRepo  extends JpaRepository<Rate, Integer> {
//    List<Contact> findContactsByState(String state);
//
//    List<Contact> findContactsByCity(String city);
//
//    Contact findContactByPhoneNumber(String phone);
//
//    Contact findContactByEmailAddress(String email);
    Rate findByDayAndStartTimeBeforeAndEndTimeAfter(Integer day, Integer startTime, Integer endTime);
}
