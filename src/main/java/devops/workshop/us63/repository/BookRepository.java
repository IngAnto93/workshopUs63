package devops.workshop.us63.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import devops.workshop.us63.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
