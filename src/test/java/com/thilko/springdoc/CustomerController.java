package com.thilko.springdoc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("ALL")
@Controller
public class CustomerController {

    @RequestMapping("/customers/invoices/completed")
    public void getCompletedInvoices(@RequestParam(value = "amount", defaultValue = "200") String amount,
                      @RequestParam("customerid") Integer customerId
    ) {
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public void createNewCustomer() {
    }

    @RequestMapping(value = "/customers", method = RequestMethod.PUT)
    public User updateCustomer(@RequestParam(value = "name", required = false) String name) {
        return null;
    }

    @RequestMapping(value = "/customers/{customerid}", method = RequestMethod.DELETE)
    public void cancelSubscription() {
    }

    @RequestMapping(value = "/customers/{customerid}", method = RequestMethod.GET)
    public String getCustomerDetails() {
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public User all() {
        return null;
    }

    @RequestMapping(value = "/customers/{userId}/address", method = RequestMethod.GET)
    public User getAddress(@RequestParam("test") String test, Long id) {
        return null;
    }

    public void notAnApiMethod() { }

    @RequestMapping(method = RequestMethod.POST)
    public User upgradeCustomer(@RequestBody User userToTransform) {
        return null;
    }

    @RequestMapping(value = "/user/information", method = RequestMethod.POST)
    public ResponseEntity<InformationResponse> informUser() {
        return new ResponseEntity<InformationResponse>(new InformationResponse(), HttpStatus.OK);
    }

}
