DROP TABLE IF EXISTS books;

CREATE TABLE books (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  author VARCHAR(250) NOT NULL,
  genre VARCHAR(250) DEFAULT NULL
);

INSERT INTO books (title, author, genre) VALUES
  ('Lord of the Rings', 'J.R.R. Tolkien', 'Fantasy'),
  ('Harry Potter', 'J. K. Rowling', 'Fantasy'),
  ('Moby Dick', 'Herman Melville', 'Adventure');