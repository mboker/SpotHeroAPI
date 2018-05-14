package org.mboker.spothero.controller;

import org.mboker.spothero.model.Rate;
import org.mboker.spothero.model.RateForDaySet;
import org.mboker.spothero.repo.CostScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cost")
@EnableJpaRepositories("org.mboker.spothero.repo")
public class CostController {
    @Autowired
    CostScheduleRepo repo;

    @RequestMapping(method = RequestMethod.GET, params={"start","end"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getCost(@RequestParam("start")@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Calendar startTime,
                            @RequestParam("end")@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Calendar endTime) {

        Rate rate = repo.findByDayAndStartTimeBeforeAndEndTimeAfter(startTime.get(Calendar.DAY_OF_WEEK),
                                                            startTime.get(Calendar.HOUR_OF_DAY)*100 + startTime.get(Calendar.MINUTE) +1,
                                                            endTime.get(Calendar.HOUR_OF_DAY) *100 + endTime.get(Calendar.MINUTE)-1);

        Map<String, Object> returnMap = new HashMap<>();
        if (rate != null){
            returnMap.put("price", rate.getPrice());
        } else{
            returnMap.put("price","unavailable");
        }
        return returnMap;
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity replaceCurrentCosts(@RequestBody Map<String, List<RateForDaySet>> body) {
        List<RateForDaySet> rates = body.get("rates");

        repo.deleteAll();
        for (RateForDaySet rateForDaySet : rates){
            for (Integer day : rateForDaySet.getDays()) {
                Rate rate = new Rate();
                rate.setDay(day);
                rate.setPrice(rateForDaySet.getPrice());
                rate.setEndTime(rateForDaySet.getEndTime());
                rate.setStartTime(rateForDaySet.getStartTime());
                repo.save(rate);
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }


}
