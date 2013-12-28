package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ValueConstants


class Parameter {

    private variableElement

    def static fromVariableElement(variableElement) {
        new Parameter(variableElement)
    }

    private Parameter(variableElement){
        this.variableElement = variableElement
    }

    def name(){
        requestMappingAnnotation().value()
    }

    def defaultValue(){
        def defaultValue = requestMappingAnnotation().defaultValue()
        defaultValue == ValueConstants.DEFAULT_NONE ? "" : defaultValue
    }

    def required(){
        requestMappingAnnotation().required()
    }

    private requestMappingAnnotation(){
        ((RequestParam)variableElement.getAnnotation(RequestParam.class))
    }
}
