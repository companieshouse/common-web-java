# common-web-java
Shared code and resources for Java web applications.

## Requirements
In order to build the library locally you will need the following:
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

## Getting started

The library is built using maven.  For example, to build and install in the local maven repo, run the following:
```
mvn clean install
```

The library can be imported as a maven dependency:
```
<dependency>
    <groupId>uk.gov.companieshouse</groupId>
    <artifactId>common-web-java</artifactId>
    <version>1.0.0-rc1</version>
</dependency>
```

Projects using this must implement changes to the ```baseLayout.html``` file.

1. ```<div th:replace="fragments/header :: header (headerText = 'Your service name here')">```
2. ```<div th:replace="fragments/piwik :: piwik (moduleName = 'payment-service') "></div>```

## Description 

A common-web dependency to contain reusable resources and fragments for SpringBoot web development, e.g: back-buttons, headers, footers, continue buttons, etc.
