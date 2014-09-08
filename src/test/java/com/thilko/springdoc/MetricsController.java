package com.thilko.springdoc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/metrics")
public class MetricsController {

    @RequestMapping(value = "/spo2", method = RequestMethod.GET)
    public List<Metric> getAllMetrics() {
        return null;
    }

    /*@RequestMapping(value = "/peep", method=RequestMethod.GET)
    @ResponseBody
    public List<Long> getSpecificMetrics(){
        return null;
    } */
}
