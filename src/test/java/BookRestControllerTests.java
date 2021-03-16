package devops.workshop.us63.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import devops.workshop.us63.model.Book;
import devops.workshop.us63.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class BookRestControllerTests {
	
	    @InjectMocks
	    BookRestController bookController;
	     
	    @Mock
	    BookRepository bookDAO;
	     
	    @Test
	    public void testFindAll() 
	    {
	        // given
	        Book book1 = new Book(Long.parseLong("1"), "Lord of the Rings", "J.R.R. Tolkien", "Fantasy");
	        Book book2 = new Book(Long.parseLong("2"), "Harry Potter", "J. K. Rowling", "Fantasy");
	        Book book3 = new Book(Long.parseLong("3"), "Moby Dick", "Herman Melville", "Adventure");
	        List<Book> books = new ArrayList<Book>();
	        books.add(book1);
	        books.add(book2);
	        books.add(book3);
	 
	        when(bookDAO.findAll()).thenReturn(books);
	 
	        // when
	        List<Book> result = bookController.findAll();
	 
	        // then
	        assertThat(result.size()).isEqualTo(3);
	         
	        assertThat(result.get(0).getTitle())
	                        .isEqualTo(book1.getTitle());
	         
	        assertThat(result.get(1).getTitle())
            				.isEqualTo(book2.getTitle());
	        
	        assertThat(result.get(2).getTitle())
							.isEqualTo(book3.getTitle());
	    }

}
