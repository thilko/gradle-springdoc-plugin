package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestMapping

import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

class Resource {

    private TypeElement resource

    private List<Method> methods = []

    static def create(def resource) {
        def doc = new Resource(resource)
        resource.accept(new ResourceVisitor(), doc)

        return doc
    }

    private Resource(def resource) {
        this.resource = resource
    }

    static def withController(List<Element> controllerAnnotations) {
        controllerAnnotations.collect {create(it)}.collect{it.methods}.flatten()
    }

    def methods() {
        this.methods
    }

    def name() {
        return hasRequestMapping() ? controllerPath() : resource.simpleName
    }

    def applyExecutable(def executable) {
        if (!isConstructor(executable) && isApiMethod(executable)) {
            this.methods << createApiMethod(executable);
        }
    }

    private static isApiMethod(def executable) {
        executable.getAnnotation(RequestMapping) != null
    }

    private def createApiMethod(executable) {
        def method = Method.fromElement(executable)
        if (hasRequestMapping()) {
            method.applyPathPrefix(controllerPath())
        }

        method
    }

    private def controllerPath() {
        requestMappingAnnotation().value().first()
    }

    def requestMappingAnnotation() {
        resource.getAnnotation(RequestMapping)
    }

    private def hasRequestMapping() {
        requestMappingAnnotation() != null
    }

    private static def isConstructor(executable) {
        executable.simpleName.contentEquals("<init>")
    }

    def getMethodCount() {
        return methods.size();
    }
}
