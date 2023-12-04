# Final Year Project

## Introduction

This README document serves as a boilerplate description for the project and will describe what the goal of the project is.
As described in many instances including the presentation and the reports, this project aims at understanding how to implement OAuth2.0 and openID Connect.

Below is a diagram that summarizes how the code base achieves authentication and authorization.
![](.\documentation\figures\My%20Project%20Diagram.PNG)

This is a proof of concept for an Enterprise Resource Planning Human Capital Management System 
and hence will have endpoints and roles based on that.

## Running and Setup
- The project is maven based spring boot project and can be run with a simple spring boot run config as below:
![](.\documentation\figures\Run Configuration.PNG)
- For the project to run properly as of this submission the active-profile need to be set as 'dev'.
- A database named 'human_capital_management' needs to be set up as well, this database will make sure that data is persisted on the server.
- Finally, for obvious security reason, environment variables need to be set up to save client_id and client_secret values.
- The generation of those values is described in greater detail in the Interim Report.

## Testing
- As of now, there are no automated tests in place for TDD (Test Driven Development).
- All the tests are conducted manually using Postman.
  - Postman:
    - Postman is a tool that essentially acts like the browser and is able to send and receive HTTP requests/ response to/from the webserver.
    - There are some intricate security configurations that have been created for Postman to work but those will be described in the interim report.
    - For running authenticated requests from postman to we need to authenticate first from the browser, then steal the session data (cookie) and put in into postman. This is shown below:
    ![](.\documentation\figures\Cookie Stealing.PNG)
    - The cookie value is logged onto the console using a custom logging filter that I have created. This is what we need to steal.
    - Using the authenticated cookie, we can easily create REST API requests to and from the server using Postman.
- It is important to remember that the application configuration states the database will "create" on runtime. This means that the database will start fresh everytime.
- I have created an InitializeData.java file that initializes some important role values on startup.

## Interim Submission
- As of the interim submission, the codebase can only be run and tested using postman as postman gives a good demonstration of the project as is.