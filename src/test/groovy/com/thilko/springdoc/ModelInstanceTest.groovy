package com.thilko.springdoc

import com.thilko.springdoc.model.ModelInstance
import spock.lang.Specification


class ModelInstanceTest extends Specification{

    def "model instance returns the json of the model"(){
        when:
        def instance = ModelInstance.fromClass(User.class)

        then:
        instance.toJson() == """{
    "credentials": {
        "credentialsCode": {
            "age": 41
        },
        "user": \"a string\"
    },
    "firstName": \"a string\",
    "id": 42,
    "lastName": \"a string\",
    "dateOfBirth": \"1970-01-01T00:00:00+0000\"
}"""
    }
}
