package com.example.schedule.scheduke;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTasks {

    @Scheduled(fixedRate = 5000)
    public void reportTime() {
        System.out.println("Current time " + new Date());
    }

    /**
     *
     1.        Seconds
     2.        Minutes
     3.        Hours
     4.        Day-of-Month
     5.        Month
     6.        Day-of-Week
     7.        Year (可选字段)

     */
    @Scheduled(cron = "*/10 * * * * *")
    public void reportTimeByCron() {
        System.out.println("Current time from cron " + new Date());
    }
}
