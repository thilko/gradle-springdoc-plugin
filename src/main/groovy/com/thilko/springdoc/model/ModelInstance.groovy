package com.thilko.springdoc.model

import groovy.json.JsonOutput
import org.codehaus.groovy.reflection.ClassInfo

import java.lang.ref.SoftReference
import java.lang.reflect.ParameterizedType


class ModelInstance {
    def instance
    private ModelInstanceType[] defaultValues = [
            [accepts: { it == String.class }, value: { "a string" }],
            [accepts: { it == Long.class }, value: { 42L }],
            [accepts: { it == long.class }, value: { 42L }],
            [accepts: { it == Integer.class }, value: { 41 }],
            [accepts: { it == int.class }, value: { 41 }],
            [accepts: { it == Double.class }, value: { 77.7d }],
            [accepts: { it == double.class }, value: { 77.7d }],
            [accepts: { it == Date.class }, value: { new Date(0) }],
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
                if (it.accepts(field.type)) {
                    field.set(instance, it.value())
                    wasApplied = true
                }
            }

            if (!wasApplied) {
                if (field.type.is(List)) {
                    ParameterizedType t = (ParameterizedType) field.getGenericType();

                    def clazz = Class.forName(t.actualTypeArguments[0].typeName)
                    def defaultValue = defaultValues.find{it.accepts(clazz)}
                    if(!defaultValue){
                        def newInstance = clazz.newInstance()
                        fillInstance(newInstance)
                        field.set(instance, Arrays.asList(newInstance))
                    }else{
                        field.set(instance, Arrays.asList(defaultValue.value()))
                    }


                } else {
                    def newInstance = field.type.newInstance()
                    fillInstance(newInstance)
                    field.set(instance, newInstance);
                }
            }
        }
    }
}
