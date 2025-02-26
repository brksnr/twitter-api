# 🚀 Twitter API
A Twitter clone project where users can register, log in, tweet, comment on tweets, like, and retweet posts. This application interacts with the Twitter API and mimics the functionality of a typical social media platform.

## **🛠️ Technologies Used**  
- **Spring Boot** – For backend development  
- **Spring Security** – For authentication and authorization  
- **JPA (Hibernate)** – For database management  
- **PostgreSQL** – As the database  
- **Validation** – For user input validation  


🔑 API Endpoints
Method	Endpoint	Description

## **🔑 API Endpoints**  

| Method  | Endpoint | Description |
|---------|--------------------------------|------------------------------|
| **POST** | `/register` | Register a new user |
| **POST** | `/login` | User login |
| **POST**  | `/tweets/create` | Create a tweet |
| **POST** | `/comments/user/{userID}/tweet/{tweetId}` | Create a comment |
| **DELETE** | `/comments/user/{userID}/tweet/{tweetId}/comment/{commentId}` | Delete a comment |
| **PUT** | `/comments/user/{userID}/tweet/{tweetId}/comment/{commentId}` | Update a comment |
| **POST** | `/likes/tweet/{tweetId}/user/{userID}` | Like a tweet |
| **DELETE** | `/likes/tweet/{tweetId}/user/{userID}` | Delete like |
| **POST** | `/retweet/tweet/{tweetId}/user/{userID}` | Retweet a tweet |
| **DELETE** | `/retweet/deleteTweet/{tweetId}/user/{userID}` | Delete a retweet |
| **GET** | `/tweets/tweet/{tweetId}` | Get tweet by ID |
| **DELETE** | `/tweets/user/{userId}/tweet/{tweetId}` | Delete a tweet |
| **PUT** | `/comments/tweets/user/{userId}/updatetweet/{tweetId}` | Update a tweet |

## **👨‍💻 Berk Şener**  
🔗 [LinkedIn](https://www.linkedin.com/in/berksener)

