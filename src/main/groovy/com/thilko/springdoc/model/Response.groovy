package com.thilko.springdoc.model

import groovy.json.JsonOutput

import javax.lang.model.type.TypeKind


class Response {

    def returnType

    static def fromReturnType(returnType) {
        new Response(returnType)
    }

    def className(){
        returnType.toString()
    }

    def asJson(){
        if(returnType.kind == TypeKind.VOID){
            return ""
        }

        def domainClass = this.class.classLoader.loadClass(className())
        JsonOutput.prettyPrint(new JsonOutput().toJson(domainClass.newInstance()))
    }

    private Response(returnType){
        this.returnType = returnType
    }


}
