package com.thilko.springdoc


class Endpoint {
    def endpoint

    static def create(def endpoint) {
        return new Endpoint(endpoint)
    }

    private Endpoint(def endpoint){
        this.endpoint = endpoint
    }

    def operations(){
        endpoint.getEnclosedElements()
    }

    def className(){
        endpoint.simpleName
    }
}
