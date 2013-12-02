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
                h1 "hello, I found " + classes.size() + " classes!"


                script(src: "http://netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js")
            }
        }
    }

    private builder(){
        return new MarkupBuilder(new PrintWriter(new File("index.html")))
    }

}
