CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    client_database_id INTEGER UNIQUE NOT NULL,
    user_name VARCHAR(255),
    last_greeting_time TIMESTAMP DEFAULT NULL
);