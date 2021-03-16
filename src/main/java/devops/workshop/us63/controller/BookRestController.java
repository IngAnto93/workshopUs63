package devops.workshop.us63.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(value ="", method = RequestMethod.GET)
	public void test (HttpServletResponse response) throws IOException {
		response.sendRedirect("/workshop-us63/frontend/index.html");
    }
	
	@GetMapping("/books/{genere}")
	public List<Book> findAllByGenre(@PathVariable String genere) {
		LOGGER.debug("Getting all available books");
		List<Book> books = bookRepository.findAll();
		List<Book> booksToReturn = new ArrayList<Book>();
		books.forEach((book) -> {
			if (book.getGenre().equalsIgnoreCase(genere)) {
				booksToReturn.add(book);
			}
		});
		return booksToReturn;
	}

}