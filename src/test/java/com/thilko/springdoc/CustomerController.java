package com.thilko.springdoc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@SuppressWarnings("ALL")
@Controller
public class CustomerController {

    @RequestMapping("/ask")
    public void askMe(@RequestParam(value = "question", defaultValue = "How much is the fish?") String question,
                      @RequestParam("priority") Integer priority
    ) {
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public void uploadData() {
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public User createNewUser(@RequestParam(value = "name", required = false) String name) {
        return null;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public void deleteUser() {
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public String getUserById() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    public User all() {
        return null;
    }

    @RequestMapping(value = "/user/nubs/{userId}", method = RequestMethod.GET)
    public User nubs(@RequestParam("test") String test, Long id) {
        return null;
    }

    public void notAnApiMethod() {
    }

    @RequestMapping(method = RequestMethod.POST)
    public User transform(@RequestBody User userToTransform) {
        return null;
    }

    @RequestMapping(value = "/user/information", method = RequestMethod.POST)
    public ResponseEntity<InformationResponse> informUser() {
        return new ResponseEntity<InformationResponse>(new InformationResponse(), HttpStatus.OK);
    }
}
