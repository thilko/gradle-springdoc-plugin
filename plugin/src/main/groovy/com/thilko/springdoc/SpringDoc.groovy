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
        def builder = new MarkupBuilder(new PrintWriter(new File("index.html")))
        builder.html {
            head {
                title "Api documentation"
            }
            body {
                p "hello, I´e found " + classes.size() + " classes!"
            }
        }
    }

}
