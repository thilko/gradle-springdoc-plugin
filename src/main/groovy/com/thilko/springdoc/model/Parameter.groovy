package com.thilko.springdoc.model


class Parameter {

    private variableElement

    def static fromVariableElement(variableElement) {
        new Parameter(variableElement)
    }

    private Parameter(variableElement){
        this.variableElement = variableElement
    }
}
