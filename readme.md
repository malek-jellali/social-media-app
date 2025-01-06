 # 📱 Spring Boot Full-Stack Social Media App


 This is a **simple social media app** built using **Spring Boot** for the backend, **Thymeleaf** for the frontend, and **MySQL** for the database. The app allows users to:

 - Create an account ✍️

 - Share notes and thoughts 💭

 - View posts from other users 📄


 ## 🎯 Features

 - **User Registration** and **Login** 🔑

 - **Create Notes** and **Share Thoughts** 📝

 - View posts from **Other Users** 👀

 - Clean and simple user interface 💻


 ## 🛠 Prerequisites


 Before deploying the app, ensure you have the following tools installed:

 - **Java 17** or higher ☕️

 - **Maven** for building the project ⚙️

 - **MySQL** for database management 💾

 - An **IDE** (e.g., IntelliJ IDEA or Eclipse) for running the app 💡


 ## 📥 Clone the Repository

 To get started, clone the repository to your local machine:


 ```bash

 git clone https://github.com/your_username/social-media-app.git

 cd social-media-app 
 ```


 ⚙️ Setup Instructions

 Step 1: Create MySQL Database 🗄️


 Create a MySQL database named socialMedia:


 CREATE DATABASE socialMedia;


 Step 2: Update MySQL Credentials 🔑


 In the src/main/resources/application.properties file, update the following properties with your MySQL username and password:

```bash
 spring.datasource.url=jdbc:mysql://localhost:3306/socialMedia

 spring.datasource.username=your_mysql_username

 spring.datasource.password=your_mysql_password

 spring.jpa.hibernate.ddl-auto=update

 spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

 spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

 Step 3: Build the Application 🚀


     Build the application using Maven:

```bash
 mvn clean install
```

 Run the Spring Boot application:

```bash
     mvn spring-boot:run
```

The app will start running on http://localhost:8083


 Step 4: Access the Application 🌐


     Open your web browser and navigate to http://localhost:8083

     You will land on the login page. From there, you can:

         Register if you're a new user.

         Log in if you already have an account.

     Once logged in, you can:

         Create and view posts on the homepage.

         Search for posts using the search bar.


 💡 Technologies Used


     Spring Boot: Backend framework to build the REST API and handle business logic.

     Thymeleaf: Template engine to render dynamic HTML on the frontend.

     MySQL: Relational database for storing users and posts.

     Maven: Build automation tool to manage project dependencies and build the app.


 📝 Database Schema


 The app uses the following database schema:


     Users: Stores user information (username, password, etc.).

     Posts: Stores the notes or thoughts shared by users (content, author, timestamp).


 🚀 How It Works


     Registration: Users can create a new account by providing their username, email, and password.

     Login: Registered users can log in with their credentials.

     Home Page: Once logged in, users can view the posts created by other users and share their own thoughts.

     Post Creation: Users can create new posts, which will be displayed on the homepage.

     Search: Users can search for posts by other users.


 📦 Deployment

 1. Prerequisites


 Make sure you have MySQL running locally, with a database named socialMedia. You also need Java 17, Maven, and an IDE.

 2. Run the Application


 Once you’ve set up your database and updated the application.properties file with your credentials, build and run the application:

```bash
 mvn clean install

 mvn spring-boot:run
```

 Now you can access the app at http://localhost:8080.

 🛠 Troubleshooting


Error: Cannot connect to the database: Ensure your MySQL server is running and the database socialMedia is created.

Login issues: Double-check your MySQL credentials in the application.properties file. i want u to give me all what is written au dessus in readme.md file format decriture pour le mettre dans mon readme file

    
