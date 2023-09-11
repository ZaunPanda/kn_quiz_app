CREATE TABLE questions (
                           question_id INT auto_increment PRIMARY KEY,
                           topic VARCHAR(255),
                           difficulty INT,
                           question VARCHAR(255)
);

CREATE TABLE responses (
                           response_id INT auto_increment PRIMARY KEY,
                           question_id INT,
                           response_text VARCHAR(255),
                           is_correct BOOLEAN,
                           FOREIGN KEY (question_id) REFERENCES questions(question_id)
);