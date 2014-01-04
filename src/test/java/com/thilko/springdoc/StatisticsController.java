package com.thilko.springdoc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @RequestMapping("/user/month/average")
    public void userPerMonth() { }

    public void visitorPerMinute(){}
}
