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
        requestParamAnnotation()?.value()
    }

    def defaultValue(){
        def defaultValue = requestParamAnnotation()?.defaultValue()
        (defaultValue == ValueConstants.DEFAULT_NONE || defaultValue == null) ? "" : defaultValue
    }

    def required(){
        requestParamAnnotation()?.required()
    }

    private requestParamAnnotation(){
        ((RequestParam)variableElement.getAnnotation(RequestParam.class))
    }
}
