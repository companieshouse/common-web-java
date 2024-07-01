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
    <version>latest-version-number</version>
</dependency>
```

Projects using this must include the standard base layout by the use of this layout in the individual service:
```
<!DOCTYPE html>
<!-- pull in generic layout from common-web-java -->
<html lang="en" xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"```
      layout:decorate="~{layouts/chsBaseLayout (serviceName = 'oauth-web') }">
</html>
```
```serviceName``` should be set to the name of the service using the template e.g. ```oauth-web``` in this example

## Description 

A common-web dependency to contain reusable resources and fragments for SpringBoot web development, e.g: back-buttons,
headers, footers, continue buttons, etc.

Example usage of the standard layout and fragments can be found in ```authentication-service```, ```oauth-web```
and ```user.web.identity.ch.gov.uk```

Welsh language support is being added and requires the addition of ```localse/common-messages``` to the basenames of the
```MessageCongig``` class in the service eg:
```
messageSource.setBasenames("locales/messages", "locales/common-messages");
```

The node.js equivalent is ```ch-node-utils``` but this is still to be fully implemented

## More Info

### chsBaseLayout.html

"Standard" layout for CHS services.

This layout expects properties/environment variables to have been set accordingly.

| Property     | Environment   | Description                                        |
|--------------|---------------|----------------------------------------------------|
| cdn.url      | CDN_HOST      | Global environment variable for CDN                |
| chs.url      | CHS_URL       | Global environment variable for main CHS home page | 
| piwik.url    | PIWIK_URL     | Relevant Piwik/Matomo url for service              |
| piwik.siteId | PIWIK_SITE_ID | Relevant Piwik/Matomo id for service               |

Requires ```serviceName``` variable to be set to the name of the service using the template as described above.

The following fragments are used by this baseLayout depending on the setting of variables described in each fragment.

### piwikWithCookieCheck.html

CHS cookie permissions banner. Requires ```chs.url``` property listed above.

### header.html

Standard header for CHS services. Requires ```cdn.url``` & ```chs.url``` properties listed above.

Optional variables:

```headerText``` - if defined is displayed in header as the service name

```headerURL``` - must be defined if ```headerText``` exists as it defines the link used if user clicks on ```headerText```

### phaseBanner.html

Configurable fragment for alpha/beta phase banner above the main page content. Users are invited to give feedback via a survey.

```phaseBanner``` set to ```alpha``` or ```beta``` will display the phase banner. No banner if not set

```phaseBannerLink``` if set, a feedback link will be included in the phase banner with URL = phaseBannerLink

To set ```phaseBanner``` for all screens, use ```@ModelAttribute``` in the GlobalController class using ```@ControllerAdvice```

---
### Remaining fragments not yet fully integrated into chsBaseLayout 

### backButtonLink.html

Fragment that provides a button to go backwards in the journey. Requires a ```backButton``` parameter to be set. If the ```backButton``` model attribute is absent, the 'back' link won't appear.

### footer.html

Fragment that provides useful links to the user below the main page content. Links give information about our policies, Cookies, contacting Companies House and information specific to Developers. User's projects must include ```chs.url``` and ```developer.url``` urls in their ```application.properties```.

### globalErrors.html

Fragment that is used for global errors, displays information about the error if possible.

### piwik.html

Fragment that listens to user interactions. Contains a customisable field ```${moduleName}``` that needs to be set in the ```baseLayout.html```, as mentioned above. This fragment requires the ```piwik.url``` and ```piwik.siteId``` properties in your project's ```application.properties``` file.

### userBar.html

Fragment that contains several links and information for the user. Links to Your details, Your filings, Companies you follow and Sign out are available, as well as displaying the user's email address. The user must include ```monitorGui.url``` in the project's ```application.properties```.

### error.html

Generic error page that gives the user an option to email Companies House. For this to work there must be a ```baseLayout.html``` file within the ```layouts/```` directory.