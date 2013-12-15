package com.thilko.springdoc

class Endpoint {

    def endpoint

    def methods = []

    static def create(def endpoint) {
        def docEndpoint = new Endpoint(endpoint)
        endpoint.accept(new EndpointVisitor(), docEndpoint)

        return docEndpoint
    }

    private Endpoint(def endpoint) {
        this.endpoint = endpoint
    }

    def operations() {
        this.methods
    }

    def className() {
        endpoint.simpleName
    }

    def applyExecutable(def executable) {
        if (!constructor(executable)) {
            this.methods << apiMethod(executable);
        }
    }

    def apiMethod(executable) {
        Method.fromElement(executable)
    }

    private static boolean constructor(executable) {
        executable.simpleName.contentEquals("<init>")
    }
}
