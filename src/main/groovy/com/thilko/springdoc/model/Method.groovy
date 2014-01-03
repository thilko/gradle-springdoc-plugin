package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestMapping

class Method {

    private methodElement

    def cssClasses = ["GET": "label label-primary",
            "POST": "label label-success",
            "PUT": "label label-info",
            "DELETE": "label label-danger"]

    static def fromElement(methodElement) {
        new Method(methodElement)
    }

    private Method(methodElement) {
        this.methodElement = methodElement
    }

    def name() {
        this.methodElement.simpleName
    }

    def httpMethod() {
        !hasRequestMethod() ? "GET" : requestMappingAnnotation().method().first().name()
    }

    private boolean hasRequestMethod() {
        requestMappingAnnotation().method().length > 0
    }

    private requestMappingAnnotation() {
        (RequestMapping) methodElement.getAnnotation(RequestMapping.class)
    }

    def httpMethodCssClass() {
        this.cssClasses[httpMethod()]
    }

    def path() {
        def value = requestMappingAnnotation().value()
        value.length == 0 ? "/" : value.first()
    }

    def parameter() {
        methodElement.parameters.collect {
            Parameter.fromVariableElement(it)
        }
    }

    def url() {
        def base = "http://example.com/${path()}"
        if (!parameter().isEmpty()) {
            def parameter = parameter().collect { "${it.name()}=" }.join("&")
            base += "?${parameter}"
        }
        return base
    }

    def parameter(name) {
        parameter().find { it.name() == name }
    }

    def response() {
        Response.fromReturnType(methodElement.returnType)
    }
}
