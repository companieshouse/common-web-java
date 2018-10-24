# common-web-java
Shared code and resources for Java web applications.

## Requirements
1. Pull the latest version of this branch
2. Run ```mvn clean install``` to put the .jar file in your .m2 repository so it is available to be used in relevant projects.
3. In your project add this dependency:
```
	<dependency>
	<groupId>uk.gov.companieshouse</groupId> 
	<artifactId>common-web-java</artifactId> 
	<version>0.0.1-SNAPSHOT</version> 
	</dependency>
``` (Once this code is released the version will be ${common-web-java.version}
4. There are currently two variables that are pulled into this project from your own.  These are in the ```header.html``` named ```${headerText}``` and in ```piwik.html``` within the js function named ```[[${moduleName}]]```. To stylise your own project and for this dependency to work it is required and you **must** make changes to the ```baseLayout.html``` file. 
	1. To dynamically set the header the following must be added to the ```fragment/header``` in parentheses:
	    ```<div th:replace="fragments/header :: header (headerText = 'Your service name here')">```. (It must be noted headerText can not be included if the user wants the header to be blank).
	2. To dynamically set the piwik fragment the following must be added to the ```fragment/piwik``` in parentheses:
		```<div th:replace="fragments/piwik :: piwik (moduleName = 'payment-service') "></div>```.




