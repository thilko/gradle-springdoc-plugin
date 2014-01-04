package com.thilko.springdoc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @RequestMapping("/user/month/average")
    public void userPerMonth() {
    }


    public void visitorPerMinute() {
    }

    @RequestMapping("/user/month/{year}")
    public void userPerYear(@PathVariable("year") int year) {
    }
}
