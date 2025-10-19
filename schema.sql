-- Database schema for Voting System

CREATE DATABASE IF NOT EXISTS voting_system;
USE voting_system;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('student', 'admin') NOT NULL
);

-- Candidates table
CREATE TABLE IF NOT EXISTS candidates (
    candidate_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL
);

-- Elections table (for future extension)
CREATE TABLE IF NOT EXISTS elections (
    election_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL
);

-- Votes table
CREATE TABLE IF NOT EXISTS votes (
    vote_id INT AUTO_INCREMENT PRIMARY KEY,
    voter_id INT NOT NULL,
    candidate_id INT NOT NULL,
    hash VARCHAR(255) NOT NULL,
    prev_hash VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    FOREIGN KEY (voter_id) REFERENCES users(user_id),
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id)
);

-- Insert sample data
INSERT INTO users (name, email, password, role) VALUES
('Admin User', 'admin@example.com', 'hashed_password', 'admin'),
('Student User', 'student@example.com', 'hashed_password', 'student');

INSERT INTO candidates (name, department) VALUES
('Candidate 1', 'Computer Science'),
('Candidate 2', 'Electrical Engineering');
