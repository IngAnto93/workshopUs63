package devops.workshop.us63.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import devops.workshop.us63.model.Book;
import devops.workshop.us63.repository.BookRepository;

@RestController
public class BookRestController {

	@Autowired
	private BookRepository bookRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);

	@GetMapping("/books")
	public List<Book> findAll() {
		LOGGER.debug("Getting all available books");
		return bookRepository.findAll();
	}

}