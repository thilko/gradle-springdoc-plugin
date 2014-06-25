package com.thilko.springdoc

import com.thilko.springdoc.model.ModelInstance
import spock.lang.Specification


class ModelInstanceTest extends Specification{

    def "model instance returns the json of the model"(){
        when:
        def instance = ModelInstance.fromClass(User.class)

        then:
        instance.toJson() == """{
    "addresses": [
        {
            "street": "a string"
        }
    ],
    "credentials": {
        "user": "a string",
        "code": {
            "age": 41,
            "anotherValue": 77.7
        }
    },
    "anyIds": [
        42
    ],
    "firstName": "a string",
    "id": 42,
    "lastName": "a string",
    "dateOfBirth": "1970-01-01T00:00:00+0000"
}"""
    }
}
