
# Workshop - CI/CD with Google Cloud Platform

This workshop repository contains exercises for a GCP DevOps CI/CD pipeline using mainly:


## Requirement

*   Google Cloud Platform User Account
*   Google Cloud Platform SDK (>= 331.0.0)
*	Docker
*	JAVA 1.8
*   Maven 3
*   Clone https://github.com/IngAnto93/workshopUs63 on local machine

## Exercise 11 - Run test with Maven, Junit and Mockito

First of all, let's see our *cloudbuild.yaml*:

```
steps:

  - name: 'gcr.io/cloud-builders/mvn'
    args: ['test']
```

There is Just a simple step running the command *mvn test* in a maven container.

This command is going to search for **Tests* files under the folder *src/test/java*. If any found, like in this case, it will execute found tests. Our test class is *BookRestControllerTests.java*:

```
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
```

There is only one test that will check if the result of a Rest Api is exactly equals to the one provided in test class. So, in order to see a failure test, first create a trigger as seen in previous exercises, listening on branch *exercise-12*, then change an *assertThat* condition, for example:

```
assertThat(result.get(2).getTitle()).isEqualTo(book3.getTitle()); --> assertThat(result.get(1).getTitle()).isEqualTo(book3.getTitle());
```

Now push this change and go to check the triggered build. You should see a result like this:

```
[INFO] Results:
[INFO] 
[ERROR] Errors: 
[ERROR]   BookRestControllerTests.testFindAll:56 » IndexOutOfBounds Index 3 out of bound...
[INFO] 
[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0
```

In order to pass again this test, revert the above change and push it. Now, checking the triggered build, you should see:

```
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
```

