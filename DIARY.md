# Final Year Project Diary

### 24th October 2023:
- There has been a delay in me making my first commit with the goal to initialize my repository.
- The reason for this delay is:
  - I have been busy with other modules and have not had the time to start my project.
  - I have realized the lack of knowledge I have on the implementation of OAuth2.0 and Spring Boot.
  - I have spent the last week researching OAuth2.0 and OpenID connect and how they work with Spring.
- Currently, my highest priority is to complete my Year In Industry Report, which is due on the 31st October 2023.
- The report is 80% of my overall Year In Industry grade.
- My goal for this week up until Sunday the 29th October 2023 is to do the first commit with the Spring Boot Initialized.
- I also want to create a plan of using GitLab's Issue board as a way to track features and bugs.
- By the end of this week, I also want to work on create an ER diagram for my database.

### 25th October 2023:
- I have Initialised the Spring Boot project.
  - The idea is to have both the front-end ReactJs application and the Spring Boot backend to be stored in the repository.
  - I will currently only focus on working on the Backend.

### 1st November 2023:
- I have been working on the Year In Industry Report and have not had the time to work on my project.
- Thankfully, the YINI reported has been submitted and I can now focus on my project.
- Goals for today:
  - Complete watching the following videos on Spring Security:
    - https://www.youtube.com/watch?v=nSu9ElsnNtY&list=PLEocw3gLFc8X_a8hGWGaBnSkPFJmbb8QP
    - https://www.youtube.com/watch?v=dFvbHZ8CuKM&list=PLEocw3gLFc8X_a8hGWGaBnSkPFJmbb8QP&index=2&pp=iAQB
    - https://www.youtube.com/watch?v=oKzeHshquCs&list=PLEocw3gLFc8X_a8hGWGaBnSkPFJmbb8QP&index=9&pp=iAQB
    - https://www.youtube.com/watch?v=ZIS4273AAGI&t=3579s
  - Create a plan of action for work for tomorrow.
  - From today onwards, I plan to dedicate minimum 3 hours a day to work on my project including weekends.
  - This way I will be able to achieve the objectives outlined in the project plan.
  - The plan for today is to finish an informal software requirements document that describes the features of the application.
- Goals for tomorrow:
  - Import all the required dependencies for the project.
  - Finish all the videos on Spring Security that I have listed today.
  - Create a data flow diagram that displays how the authentication process will work.
  - The goal is to create documentation first so the process of writing code is very smooth.

### 13th November 2023:
- According to goals set in the interim meeting I have managed to create connection between the spring boot application and the authorization server.
- I still haven't figured out how the data will be stored in the database.
- This is something that I need to do research on tomorrow.

### 15th November 2023:
- I am now finally on track to achieving my goals for this term with this project.
- The users/ resource owners can basically register and login to the application.
- if a user is "onboarded" they can access their dashboard.
- if the user is not "onboarded" they will be redirected to the onboarding page.
- My next step is to achieve role based access control such that on login, users can decide their role in the company.


### 19th November 2023:
- Onboarding rest controller class defined in UserDetailsAndRolesSetUp Branch since it makes sense to me.
- I will not be adding any code here. Code for the controller will be added in the onboarding branch.

### 21st November 2023:
- Worked extensively to understand how GrantedAuthority works in Spring Security.
- Created custom implementation of GrantedAuthority called CustomGrantedAuthority. 
- This class will make a lot of sense later on when trying to implement role based access control for method level security. For example, certain endpoints should only be hit by users with certain authorities.
- I have seperated roles and authorities to explain the proof of concept here. Roles are like a users "badge" ("Receptionist" etc.) and authorities are like "what doors that user can enter" (In an obvious office analogy where role based access control is used).
- Goal for tomorrow is to test these things out and see if they work!
- Also, in theory the admin will have access to an endpoint that will allow them to create roles and authorities, remove roles and authorities etc... So like an AdminController. This is something I will work on later.
- Goal for tomorrow is to create test cases for everything that is done, comment all the code and javadoc!



