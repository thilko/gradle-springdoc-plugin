package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestMapping

class Method {

    def executableElement

    def cssClasses = ["GET": "label label-primary",
            "POST": "label label-success"]

    static def fromElement(executable) {
        new Method(executable)
    }

    private Method(executable) {
        this.executableElement = executable
    }

    def name() {
        this.executableElement.simpleName
    }

    def httpMethod() {
        !hasRequestMethod() ? "GET" : requestMappingAnnotation().method().first().name()
    }

    private boolean hasRequestMethod() {
        requestMappingAnnotation().method().length > 0
    }

    private requestMappingAnnotation() {
        (RequestMapping) executableElement.getAnnotation(RequestMapping.class)
    }

    def httpMethodCssClass() {
        this.cssClasses[httpMethod()]
    }

}
