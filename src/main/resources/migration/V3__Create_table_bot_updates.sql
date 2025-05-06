CREATE TABLE bot_updates (
    id SERIAL PRIMARY KEY,
    update_description TEXT NOT NULL,
    app_version VARCHAR(20) NOT NULL,
    sent BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
