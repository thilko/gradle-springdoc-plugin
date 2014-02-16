package com.thilko.springdoc.model

import groovy.json.JsonOutput

import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror


class Response {

    TypeMirror returnType

    static def fromReturnType(returnType) {
        new Response(returnType)
    }

    def className() {
        if (returnType.toString() =~ /.*<.*?>/) {
            return ((DeclaredType) returnType).typeArguments[0].toString()

        }
        returnType.toString()
    }

    def asJson() {
        if (returnType.kind == TypeKind.VOID) {
            return ""
        }

        def domainClass = this.class.classLoader.loadClass(className())
        JsonOutput.prettyPrint(new JsonOutput().toJson(domainClass.newInstance()))
    }

    private Response(returnType) {
        this.returnType = returnType
    }


}
