# CODINGCONTEST_BACKEND
This Spring Boot application manages a contest leaderboard where users can register, update their scores, and earn badges based on their performance. The application includes RESTful APIs for CRUD operations on user data and implements business logic for badge awards and leaderboard sorting.

# Features
- User Registration: Register new users with a unique User ID and Username. Score is initialized to 0, and badges are empty initially.
- Score Update: Update a user's score through PUT requests.
- Badge Awards: Award badges to users based on their score ranges:
- 1 <= Score <= 30: Code Ninja
- 31 <= Score <= 60: Code Champ
- 61 <= Score <= 100: Code Master
- Leaderboard: Retrieve a sorted leaderboard based on user scores.
- User Retrieval: Retrieve details of specific users or list all registered users.
- User Deregistration: Deregister a specific user from the contest.
# Technologies Used
- Java
- Spring Boot
- MongoDB
- JUnit for testing
- Postman for API testing
# Setup Instructions
1. Clone the repository to your local machine.
2. Set up MongoDB and configure the connection details in application.properties.
3. Build and run the Spring Boot application using Gradle.
# API Documentation
The API endpoints are as follows:

- GET /users: Retrieve a list of all registered users.
- GET /users/{userId}: Retrieve the details of a specific user.
- POST /users: Register a new user to the contest.
- PUT /users/{userId}: Update the score of a specific user.
- DELETE /users/{userId}: Deregister a specific user from the contest
