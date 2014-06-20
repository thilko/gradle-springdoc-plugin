package com.thilko.springdoc

import com.thilko.springdoc.model.ModelInstance
import spock.lang.Specification


class ModelInstanceTest extends Specification{

    def "model instance returns the json of the model"(){
        when:
        def instance = ModelInstance.fromClass(User.class)

        then:
        instance.toJson() == """{
    "firstName": \"a string\",
    "id": 42,
    "lastName": \"a string\",
    "dateOfBirth": null
}"""
    }
}
