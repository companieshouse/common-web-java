# common-web-java
Shared code and resources for Java web applications.

Things To consider
--

+ Always make sure any code changes you make get copied across to `main-8` (compatible with Java 8) also to `main` (compatible with Java 21)

###### Changes Specific to Java 8

+ Please create branch only from `main-8`
+ Please raise a PR to merge your changes only to [main-8](https://github.com/companieshouse/common-web-java/tree/main-8) branch
+ Use Java 8 Major tags generated from pipeline in your references (example : tags 1.x.x for java 8)

###### Changes Specific to Java 21

+ Please create branch only from `main`
+ Please merge your changes only to [main](https://github.com/companieshouse/common-web-java) branch
+ Use Java 21 Major tags generated from pipeline in your references (example : tags 3.x.x for java 21)

###### Pipeline

+ Please use this [Pipeline](https://ci-platform.companieshouse.gov.uk/teams/team-development/pipelines/common-web-java) and make sure respective `source-code-main` or `source-code-main-8` task gets started once the PR is created or after the PR is merged to `main` or `main-8` and once the pipeline tasks are complete then use the created tags respectively.

> [!NOTE]
> When using in a web application remember to include a dependency on Spring
> Security to protect against possible Cross Site Request Forgery attacks.
> Add either:
>
> + `spring-boot-security-starter`
> + `spring-security-core`
> + `spring-security-web`
>
> More Details:
>
> + About CSRF more generally:
>  <https://docs.spring.io/spring-security/reference/features/exploits/csrf.html>
> + About Spring Security's Mitigation:
>  <https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html>

## Requirements
In order to build the library locally you will need the following:
- [Java](https://www.oracle.com/java/technologies/downloads/)
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
