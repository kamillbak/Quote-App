CREATE TABLE Quote (
    id int AUTO_INCREMENT,
    author varchar(255) NOT NULL,
    quote varchar(255) NOT NULL,
    postedBy int
);

CREATE TABLE Users (
    id int AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

INSERT INTO Users (username, password)
VALUES ('admin', '1234');

INSERT INTO Quote (author, quote, postedBy )
VALUES ('Mahatma Gandhi', 'Live as if you were to die tomorrow. Learn as if you were to live forever.', 1);

INSERT INTO Quote (author, quote, postedBy )
VALUES ('Elbert Hubbard', 'A friend is someone who knows all about you and still loves you.', 1);

INSERT INTO Quote (author, quote, postedBy )
VALUES ('Oscar Wilde ', 'To live is the rarest thing in the world. Most people exist, that is all.', 1);