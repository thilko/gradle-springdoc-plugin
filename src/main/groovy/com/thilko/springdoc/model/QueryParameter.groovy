package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ValueConstants


class QueryParameter {

    private variableElement

    def static fromVariableElement(variableElement) {
        new QueryParameter(variableElement)
    }

    private QueryParameter(variableElement){
        this.variableElement = variableElement
    }

    def name(){
        requestParamAnnotation().value()
    }

    def defaultValue(){
        def defaultValue = requestParamAnnotation().defaultValue()
        defaultValue == ValueConstants.DEFAULT_NONE ? "" : defaultValue
    }

    def required(){
        requestParamAnnotation().required()
    }

    private requestParamAnnotation(){
        ((RequestParam)variableElement.getAnnotation(RequestParam.class))
    }
}
