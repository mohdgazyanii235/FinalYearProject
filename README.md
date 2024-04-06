# Final Year Project

## Introduction

This README document serves as a boilerplate description for the project and will describe what the goal of the project is.
As described in many instances including the presentation and the reports, this project aims at understanding how to implement OAuth2.0 and openID Connect.

Below is a diagram that summarizes how the code base achieves authentication and authorization. This diagram is specific to the goal of achieving SSO with Google. The demonstration of how this is done with the "Authorisation Server" is shown in the report.
![](./documentation/figures/My%20Project%20Diagram.PNG)

This is a proof of concept for an Enterprise Resource Planning Human Capital Management System 
and hence will have endpoints and roles based on that.

## Running and Setup
- The project is maven based spring boot project and can be run with a simple spring boot run config as below:
![](./documentation/figures/Run%20Configuration.PNG)
- For the project to run properly as of this submission the active-profile need to be set as 'dev'.
- A database named 'human_capital_management' needs to be set up as well, this database will make sure that data is persisted on the server.
- Finally, for obvious security reason, environment variables need to be set up to save client_id and client_secret values.
- The generation of those values is described in greater detail in the Interim Report.
- Furthermore, the run configuration for the Authorisation Server and the Resource Server is shown below:
  ![](./documentation/figures/Run%20Configuration%20Authorisation%20Server.PNG)
  ![](./documentation/figures/Run%20Configuration%20Resource%20Server.PNG)

## Testing
- As of now, there are no automated tests in place for TDD (Test Driven Development).
- All the tests are conducted manually using Postman.
- It is important to remember that the application configuration states the database will "create" on runtime. This means that the database will start fresh everytime.
- I have created an InitializeData.java file that initializes some important role values on startup.

## Running the application
- To run the application the steps outlined in the report demonstrate how to get the client id and client secret from Google.
- Once these two are set up as environment variables in running environment the application is ready to run. Following are the variable names corresponding to their respective Auth servers:
  - AUTH_SERVER_CLIENT_ID - Setting up the client id on the local auth server implementation. (To be set up in the authorisation server side)
  - AUTH_SERVER_SECRET - Setting up the client secret on the local auth server implementation. (To be set up in the authorisation server side)

  - AUTH_SERVER_SECRET - Setting up the client secret on the local auth server implementation. (To be set up in the environment of the client application)
  - CLIENT_ID - Setting up the client id for the Google authorisation server. (To be set up environment for the client application)
  - CLIENT_SECRET - Setting up the client secret for the Google authorisation server. (To be set up in the environment for the client application)

- Once these environment variables are properly set up, the next step is to run the applications in the following order:
  - Authorisation Server
  - Resource Server
  - erp-api - (The client application)
  - The front end application:
    - Run the command "npm install" in the same directory as the React application (erp-app).
    - Run the command "npm start" in the same directory as the React application (erp-app).

