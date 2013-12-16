package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestMapping

class Method {

    def name
    def httpMethod
    def httpMethodCssClass

    static def fromElement(executable) {
        new Method(executable)
    }

    private Method(executable) {
        def requestAnnotation = (RequestMapping) executable.getAnnotation(RequestMapping.class)
        this.name = executable.simpleName
        this.httpMethod = requestAnnotation.method().length == 0 ? "GET" : requestAnnotation.method().first().name()
        this.httpMethodCssClass = "label-primary"
    }

}
