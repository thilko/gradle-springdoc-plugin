package com.thilko.springdoc.model

import groovy.json.JsonOutput


class ModelInstance {
    def instance

    static def fromClass(Class<?> aClass) {
        ModelInstance instance = new ModelInstance()
        instance.instance = aClass.newInstance()
        return instance
    }

    def toJson() {
        JsonOutput.prettyPrint(new JsonOutput().toJson(instance))
    }
}
