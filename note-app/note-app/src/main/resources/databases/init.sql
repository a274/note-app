CREATE TABLE IF NOT EXISTS users
(
    user_id SERIAL PRIMARY KEY ,
    user_login TEXT NOT NULL,
	user_pswd TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS notes
(
    note_id SERIAL PRIMARY KEY ,
    user_id INTEGER NOT NULL,
    note TEXT NOT NULL,
	create_ts timestamp without time zone NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);