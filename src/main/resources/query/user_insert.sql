INSERT INTO users (client_database_id, user_name, last_greeting_time, dnd)
VALUES (?, ?, NULL, false)
ON CONFLICT (client_database_id) DO NOTHING;