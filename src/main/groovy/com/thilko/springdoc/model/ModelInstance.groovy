package com.thilko.springdoc.model

import groovy.json.JsonOutput
import org.codehaus.groovy.reflection.ClassInfo

import java.lang.ref.SoftReference
import java.lang.reflect.Field


class ModelInstance {
    def instance
    private ModelInstanceType[] defaultValues = [
            [accepts: { Field field -> field.type == String.class }, value: { "a string" }],
            [accepts: { Field field -> field.type == Long.class }, value: { 42L }],
            [accepts: { Field field -> field.type == Integer.class }, value: { 41 }],
            [accepts: { it.type == Date.class }, value: { new Date(0) }],
    ] as ModelInstanceType[]

    private ModelInstanceType[] fieldsToIgnore = [
            [accepts: { it.type == ClassInfo.class }],
            [accepts: { it.type == MetaClass.class }],
            [accepts: { it.type == SoftReference.class }],
            [accepts: { it.type == Class.class }],
            [accepts: { it.name.startsWith("_") }]
    ] as ModelInstanceType[]

    static def fromClass(Class<?> aClass) {
        ModelInstance instance = new ModelInstance()
        instance.instance = aClass.newInstance()
        return instance
    }

    def toJson() {
        fillInstance(instance)
        JsonOutput.prettyPrint(new JsonOutput().toJson(instance))
    }

    def fillInstance(def instance) {
        instance.class.declaredFields.toList().findAll { fieldToFilter ->
            !fieldsToIgnore.any { it.accepts(fieldToFilter) }
        }.each { field ->
            boolean wasApplied = false
            field.setAccessible(true)
            defaultValues.each {
                if (it.accepts(field)) {
                    field.set(instance, it.value())
                    wasApplied = true

                }
            }

            if (!wasApplied) {
                def newInstance = field.type.newInstance()
                fillInstance(newInstance)
                field.set(instance, newInstance);
            }
        }
    }
}
