package com.thilko.springdoc.model

import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import org.springframework.web.bind.annotation.RequestMapping

import javax.lang.model.type.TypeKind

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

    def parameter(name) {
        parameter().find { it.name() == name }
    }

    def response(){
        Response.fromReturnType(methodElement.returnType)
    }
}
