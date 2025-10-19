# Voting System

A Java-based Voting System for College Elections using OOP, JDBC, Swing, and a simplified blockchain.

## Features

- User Authentication (Admin/Student)
- Election Management
- Secure Voting with Blockchain
- Result Processing

## Setup

1. Install Java 17+, MySQL, JDBC driver.
2. Create MySQL database 'voting_system' with tables:
   - users(user_id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), password VARCHAR(255), role VARCHAR(50))
   - candidates(candidate_id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), department VARCHAR(255))
   - votes(vote_id INT AUTO_INCREMENT PRIMARY KEY, voter_id INT, candidate_id INT, hash VARCHAR(255), prev_hash VARCHAR(255), timestamp BIGINT)
3. Update db_config.properties with your DB credentials.
4. Compile and run Main.java.

## Usage

Run the application, login/register, vote or manage elections.
