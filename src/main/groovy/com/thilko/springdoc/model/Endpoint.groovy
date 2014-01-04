package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestMapping

class Endpoint {

    private endpoint

    def methods = []

    static def create(def endpoint) {
        def docEndpoint = new Endpoint(endpoint)
        endpoint.accept(new EndpointVisitor(), docEndpoint)

        return docEndpoint
    }

    private Endpoint(def endpoint) {
        this.endpoint = endpoint
    }

    def methods() {
        this.methods
    }

    def name() {
        endpoint.simpleName
    }

    def applyExecutable(def executable) {
        if (!isConstructor(executable) && isApiMethod(executable)) {
            this.methods << createApiMethod(executable);
        }
    }

    private static isApiMethod(def executable) {
        executable.getAnnotation(RequestMapping.class) != null
    }

    private static def createApiMethod(executable) {
        Method.fromElement(executable)
    }

    private static boolean isConstructor(executable) {
        executable.simpleName.contentEquals("<init>")
    }

    int getMethodCount() {
        return methods.size();
    }
}
