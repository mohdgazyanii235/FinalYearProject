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

### 27th November 2023:
- I have been trying to solve a very big problem with my project with postman not being able to set up an authenticated session with my spring boot application.
- This issue prevents me from testing my endpoints.
- Because my application works as intended when I test it on the browser, my solution is to conduct a "coolie stealing" operation from the browser and into my postman
- To do this I have set up some logging features, that allow me to steal the cookie from the browser and copy/paste them into postman.
- This is not a very elegant solution but something I can worry about in the winter break.

### 29th November 2023:
- I have now merged everything to main and added some onboarding endpoints.
- This is another place for me to show some role based access control.
  - Basically to access each api endpoint the user has to have a specific role (for example NON_ONBOARDED_USER_A).
  - This means that users that don't have that role can't change their details.
  - This means that after onboarding, the user won't be able to change certain details.
  - I have done this to show step by step secure onboarding process
- Once the user has completed onboarding, they will get the role (USER). so non onboarded users can't do anything other than onboard.

### 2nd December 2023:
- I have completed the company set up
- I have also completed a basic implementation of the onboarding process.
- I have shown a demonstration of role based access control.

### 3rd December 2023:
- I had introduces a major security issue yesterday where user could onboard another user.
- Fixed this issue by looking at security context holder and how it works.
- Research on this was quite intense as I had to get back to basics.
- The issue has now been fixed completely.
- I have to now have some security feature such that an admin can do tasks in his own company.
- This means I want to create annotations that check specifically for that. The reason I want to do that with annotations is because I want that done before the endpoint is hit, and it makes the code look smoother.
- Another issue that I had introduced with the RBAC based onboarding process was that every time a users role progressed, they authentication token would change and hence the logged-in users session would be lost.
- I have fixed this issue by creating a new authentication instance for the currently logged-in user and replacing that in the security context with the old authentication instance.
- The only thing that is different in the new authentication instance is the new granted authorities.
- I have now also completed the role based access control POC. I can now prepare for demo.

### 4th December 2023:
- There was a bug in user service in the "getFirstNameByEmail" function which returned an error if the users firstname was null. Fixed that!
- Updated README.MD so it says something meaningful.
- Removed unnecessary logging features from the custom logging filter.
- Code is now ready for Interim Review


### 10th February 2024:
- Was carried away catching up notes from last term and getting ready for the exam.
- I will now focus on the frontend of the application and make things look slightly nicer so the onboarding process can be through the browser rather than using postman
- For this I need to now learn ReactJs and will start with the following crash course: https://www.youtube.com/watch?v=w7ejDZ8SWv8
- I have a ReactJs Udemy course that I have used before that I will use to learn very specific details
- The ReactJs will send HTTP REST API requests to the backend...both the applications will be in the same repository so they can be packaged together later when I set up Docker and everything.


### 16th February 2024:
- Currently working on getting some inspiration from : https://www.baeldung.com/spring-security-login-react
- Turns out, I underestimated how complex it would be to connect frontend to backend securely.
- All the magic is in the authentication success handler:
  - When the user logs in, the success handler send the user information to the user this is the UserProfileDataDTO.
  - This is what I built today!
- Goals for today have all been met. Backend and frontend connected.
- Goal for tomorrow is to create a frontend for the user onboarding system.

### 18th February 2024:
- I had been having the issue of not being able to read the 'inf' cookie in react - just realised I had set it to HTTP Only Cookie.
- This issue was easily fixed.

### 20th February 2024:
- User onboarding system completed
- A complete and secure user onboarding process with React is now complete. When the user logs in now, they are shown the 'dashboard page!'
- After lots and lots of code an onboarding process that shows a modern authentication process and RBAC is now complete, both backend and frontend
- The next step in theory should be to move to building more API endpoints (features) but that is not the point of this project, so I will move onto focusing on two main aspects:
  - The Authorisation Server
  - Security Analysis and Finding more Secure methods

### 11th March 2024:
- Unfortunately not been able to focus on FYP since I have to catch up on the following things:
  - Job Applications
  - My assignments
  - My business
  - My personal portfolio
  - Other side projects
- I start my coding spree again for the next week:
  - Following are the goals:
    - Authorisation server ... read and learn about it.
    - Build it
    - Integrate with Microsoft Authenticator app
    - Implement front end for this!

### 13th March 2024:
- After 2 days of literally problem-solving every waking hour finally managed to get the authorization code exchange between client and auth server work properly!
- Very happy!!

### 18th March 2024:
- Authorization server implementation is basically completed.
- Resource server demonstration completed as well... Still the passwords in the authorization server are stored in plaintext.
- Today's security vulnerability that I solved was making sure there was no clash between oidc users... "what if two users with the same email but different authorization servers login?"... exactly!
  - How I solved this by adding an issuer field for each user in the database... so now, user exists lookup will also check for issuer making each user unique!  
- Authorization server users are also needing to be stored manually since I haven't really managed to make a registration endpoint there.
- This might be a luxury for later but for now, users in the resource server and authorization server will be loaded through a CSV file.