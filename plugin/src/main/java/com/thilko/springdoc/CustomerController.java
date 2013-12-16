package com.thilko.springdoc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController {

    @RequestMapping
    public void askMe() {
    }

    @RequestMapping(method = RequestMethod.POST)
    public void uploadData() {
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void createNewUser() {
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteUser() {
    }
}
