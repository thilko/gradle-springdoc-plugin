gradle-springdoc-plugin
=======================
[![Build Status](https://travis-ci.org/thilko/gradle-springdoc-plugin.png?branch=master)](https://travis-ci.org/thilko/gradle-springdoc-plugin)

A gradle plugin to generate rest api documentation from spring mvc annotations at build time. No
runtime dependencies.

Project is in an early development stage: ideas, issues and comments are highly appreciated.

Usage
=======================

Include the plugin in your gradle script:

```
buildscript {
    repositories {
      mavenCentral()
    }
    dependencies {
        classpath "com.thilko.spring:gradle-springdoc-plugin:0.4"
    }
}

apply plugin: 'springdoc'
```

You got a build task ```generateSpringDoc``` which produces HTML documentation in ```buildDir```.

Have a look at the [example](http://thilko.com/springdoc/index.html).

Not yet implemented / TODOs
=======================
- [ ] Proper support for all relevant spring mvc anotations
- [ ] Possibility to send requests to your api
- [ ] Proper styling
- [ ] Proper return type handling for @RequestMapping annotated methods

