package com.thilko.springdoc.model

import groovy.json.JsonOutput


class ModelInstance {
    def instance
    private ModelInstanceType[] defaultValues = [
                                   [accepts: { it == String.class }, value: { "a string" }],
                                   [accepts: { it == Long.class }, value: { 42L }]
                                    ] as ModelInstanceType[]

    static def fromClass(Class<?> aClass) {
        ModelInstance instance = new ModelInstance()
        instance.instance = aClass.newInstance()
        return instance
    }

    def toJson() {
        fillInstance()
        JsonOutput.prettyPrint(new JsonOutput().toJson(instance))
    }

    def fillInstance() {
        instance.class.declaredFields.each { field ->
            defaultValues.each {
                if(it.accepts(field.type)){
                    field.setAccessible(true)
                    field.set(instance, it.value())
                }
            }
        }
    }
}
