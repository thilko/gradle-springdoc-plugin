package com.thilko.springdoc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@SuppressWarnings("ALL")
@Controller
public class CustomerController {

    @RequestMapping
    public void askMe(@RequestParam("question") String question,
                      @RequestParam("priority") Integer priority) { }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public void uploadData() {
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public void createNewUser() {
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public void deleteUser() {
    }
}
