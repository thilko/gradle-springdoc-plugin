package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestParam


class Parameter {

    private variableElement

    def static fromVariableElement(variableElement) {
        new Parameter(variableElement)
    }

    private Parameter(variableElement){
        this.variableElement = variableElement
    }

    def name(){
        ((RequestParam)variableElement.getAnnotation(RequestParam.class)).value()
    }
}
