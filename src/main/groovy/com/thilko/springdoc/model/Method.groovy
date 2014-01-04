package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

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
        if (noApi()) {
            return ""
        }

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
        if (noApi()) {
            return ""
        }

        def value = requestMappingAnnotation().value()
        value.length == 0 ? "/" : value.first()
    }

    def queryParameter() {
        methodElement.parameters
                .findAll { it.getAnnotation(RequestParam.class) != null }
                .collect { QueryParameter.fromVariableElement(it) }
    }

    def noApi() {
        requestMappingAnnotation() == null
    }

    def url() {
        def base = "http://example.com${path()}"
        if (!queryParameter().isEmpty()) {
            def parameter = queryParameter().collect { "${it.name()}=" }.join("&")
            base += "?${parameter}"
        }
        return base
    }

    def queryParameter(name) {
        queryParameter().find { it.name() == name }
    }

    def response() {
        Response.fromReturnType(methodElement.returnType)
    }
}
