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

1. ```<div th:replace="fragments/header :: header (headerText = 'Your service name here')"></div>```
2. ```<div th:replace="fragments/piwik :: piwik (moduleName = 'your-module-name-here')"></div>```

## Description 

A common-web dependency to contain reusable resources and fragments for SpringBoot web development, e.g: back-buttons, headers, footers, continue buttons, etc.


## More Info

### backButtonLink.html

Fragment that provides a button to go backwards in the journey. Requires a ```backButton``` parameter to be set. If the ```backButton``` model attribute is absent, the 'back' link won't appear.

### betaBanner.html

Fragment that makes it clear that the service is in beta above the main page content. Users are invited to give feedback via a survey.

### footer.html

Fragment that provides useful links to the user below the main page content. Links give information about our policies, Cookies, contacting Companies House and information specific to Developers. User's projects must include ```chs.url``` and ```developer.url``` urls in their ```application.properties```.

### globalErrors.html

Fragment that is used for global errors, displays information about the error if possible.

### header.html

Fragments that acts as the header for the webpage. Gives information about the service. Contains a customisable field that displays the name of the service being used. As mentioned above this is set in ```baseLayout.html```.

### piwik.html

Fragment that listens to user interactions. Contains a customisable field ```${moduleName}``` that needs to be set in the ```baseLayout.html```, as mentioned above. This fragment requires the ```piwik.url``` and ```piwik.siteId``` properties in your project's ```application.properties``` file.

### userBar.html

Fragment that contains several links and information for the user. Links to Your details, Your filings, Companies you follow and Sign out are available, as well as displaying the user's email address. The user must include ```monitorGui.url``` in the project's ```application.properties```.

### error.html

Generic error page that gives the user an option to email Companies House. For this to work there must be a ```baseLayout.html``` file within the ```layouts/```` directory.