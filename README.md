# ChatterPlay

## Setup
***
1. Setup Database

   1.1 Use `docs/chatterplay.sql` to initialize the database

   1.2 Enter the database credentials in `backend/src/main/resources/db.env` (example for the URL: jdbc:mysql://localhost:3306/chatterplay)

   1.3 The database contains some example data

2. Execute `backend/src/main/java/RestServer` to start the backend
3. Use `app/src/main/java/com/example/chatterplay/MainActivity` to build the app
4. Login with a user id (which exists in the database) and the ip address of the backend device

**Warning: You may need to configure your firewall to connect to the backend**

## General Information
***
### Team Members
- Luca Außem [3576632] (Lead)
- Viktor Veselinov [3654011]

### Introduction
This project is part of the "Mobile Applikationen mit Android" lecture at the FH Aachen in the winter semester of 2024/25.

### Description
ChatterPlay is a messaging app for communication between multiple users. User can create own chatrooms and play games against other users (or an AI) like "Tic-Tac-Toe" or "Connect Four".

The Frontend is written in Kotlin while the backend (server and database connection) is written in TBD.


## Contact
***

<luca.aussem@rwth-aachen.de>

<viktor.veselinov@rwth-aachen.de>
