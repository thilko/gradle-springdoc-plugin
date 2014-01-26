package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestMapping

class Resource {

    private resource

    def methods = []

    static def create(def resource) {
        def doc = new Resource(resource)
        resource.accept(new ResourceVisitor(), doc)

        return doc
    }

    private Resource(def resource) {
        this.resource = resource
    }

    def methods() {
        this.methods
    }

    def name() {
        return controllerHasRequestMapping() ? controllerPath() : resource.simpleName
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
        if (controllerHasRequestMapping()) {
            method.applyPathPrefix(controllerPath())
        }

        method
    }

    private String controllerPath() {
        resource.getAnnotation(RequestMapping).value().first()
    }

    private boolean controllerHasRequestMapping() {
        resource.getAnnotation(RequestMapping) != null
    }

    private static boolean isConstructor(executable) {
        executable.simpleName.contentEquals("<init>")
    }

    int getMethodCount() {
        return methods.size();
    }
}
