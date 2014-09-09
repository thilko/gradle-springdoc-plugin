package com.thilko.springdoc.model

import groovy.json.JsonOutput
import org.codehaus.groovy.reflection.ClassInfo

import java.lang.ref.SoftReference
import java.lang.reflect.Modifier
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
            [accepts: { it == boolean.class }, value: { true }],
            [accepts: { it == Boolean.class }, value: { true }],
    ] as ModelInstanceType[]

    private ModelInstanceType[] fieldsToIgnore = [
            [accepts: { it.type == ClassInfo.class }],
            [accepts: { it.type == MetaClass.class }],
            [accepts: { it.type == SoftReference.class }],
            [accepts: { it.type == Class.class }],
            [accepts: { it.name == "serialVersionUID"}],
            [accepts: { it.name.startsWith("_") }],
            [accepts: { Modifier.isFinal(it.modifiers)}]
    ] as ModelInstanceType[]

    static def fromClass(Class<?> aClass) {
        ModelInstance instance = new ModelInstance()
        if (aClass.is(List)) {
            instance.instance = new ArrayList<>()
        } else {
            instance.instance = aClass.newInstance()
        }

        return instance
    }

    def toJson() {
        fillInstance(instance)
        JsonOutput.prettyPrint(new JsonOutput().toJson(instance))
    }

    def fillInstance(Object instance) {
        println "Parsing field type ${instance.class}"

        instance.class.declaredFields.toList().findAll { fieldToFilter ->
            println "--- check field to ignore ${fieldToFilter}"
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
                println "Field type is ${field.type}"
                if (field.type.is(List)) {
                    println "Field with name ${field.name} is a list"
                    ParameterizedType t = (ParameterizedType) field.getGenericType();

                    def clazz = Class.forName(((Class) t.actualTypeArguments[0]).name)
                    println "Type of the list is ${clazz.name}"
                    def defaultValue = defaultValues.find { it.accepts(clazz) }
                    if (!defaultValue) {
                        println "We don´t found a default value for ${field.type.name}, creating instance of it"
                        def newInstance = clazz.newInstance()
                        println "Fill new instance with type ${newInstance.class.name} for a list"
                        fillInstance(newInstance)
                        field.set(instance, Arrays.asList(newInstance))
                    } else {
                        println "Found the default value ${defaultValue.value()} for ${field.name}"
                        field.set(instance, Arrays.asList(defaultValue.value()))
                    }
                } else {
                    def newInstance = field.type.newInstance()

                    println "Fill new instance with type ${newInstance.class.name}"
                    fillInstance(newInstance)
                    field.set(instance, newInstance);
                }
            }
        }
    }
}
