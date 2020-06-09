# RateLimiter
Implementation of user based api rate limiting

# OVERVIEW
This project uses spring security for maintaining session for session for users.
Rate limiting logic is part of Interceptor which intercepts all URLS in the form /api/**.
Sliding window rate limiting algorithm has been implemented.
H2 in memory database is being used here as a cache for storing the request timestamps.
In a distributed environment Redis cache can be used to get the same effect.

USER DETAILS
username password role 
user_1  user1_password USER
user_2  user2_password USER
admin   admin_password USER,ADMIN

# HOW To RUN
Build the project using command: mvn package
Run the jar using command: java -jar target/ratelimiter-0.0.1-SNAPSHOT.jar

![alt text](https://github.com/atreyee169/RateLimiter/blob/master/UserRateLimiter.PNG)
