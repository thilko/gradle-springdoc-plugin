package com.thilko.springdoc

import groovy.xml.MarkupBuilder


class SpringDoc {

    public static withClasses(classes){
        return new SpringDoc(classes)
    }

    def classes = []

    private SpringDoc(classes){
        this.classes = classes
    }

    public generate(){
        builder().html {
            head {
                title "Api documentation"
                link(href:"http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css",rel:"stylesheet")
            }
            body {
                h1 "Api Documentation"
                endpoints().each{
                    h2 "$it"
                }

                script(src: "http://netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js")
            }
        }
    }

    private endpoints(){
        classes
    }
    private builder(){
        return new MarkupBuilder(new PrintWriter(new File("index.html")))
    }

}
