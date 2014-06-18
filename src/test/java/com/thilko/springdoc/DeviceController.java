package com.thilko.springdoc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/")
public class DeviceController {

    @RequestMapping("/foo")
    public void foo(){ }
}
