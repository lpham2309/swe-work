package jrails;

import books.Animals;
import books.Book;
import books.BookController;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JRouterTest {

    private JRouter jRouter;

    @Before
    public void setUp() throws Exception {
        jRouter = new JRouter();
    }

    @Test
    public void addRoute() {
        jRouter.addRoute("GET", "/", String.class, "index");
        jRouter.addRoute("GET", "/index", BookController.class, "index");
        jRouter.addRoute("GET", "/show", BookController.class, "show");
        jRouter.addRoute("GET", "/new", BookController.class, "new_book");
        jRouter.addRoute("GET", "/edit", BookController.class, "edi");
        jRouter.addRoute("POST", "/create", BookController.class, "create");
        jRouter.addRoute("POST", "/update", BookController.class, "update");
        jRouter.addRoute("GET", "/destroy", BookController.class, "destroy");
        jRouter.addRoute("RANDOM", "/hello", Animals.class, "test");
        jRouter.addRoute(null, null, Animals.class, "hello");
        jRouter.addRoute("HELLO", "WORLD", Animals.class, "hello");

        assertEquals(jRouter.getRoute("RANDOM", "/hello"), "books.Animals#test");
        assertThat(jRouter.getRoute("POST", "/create"), is("books.BookController#create"));
        assertThat(jRouter.getRoute("POST", "/update"), is("books.BookController#update"));
        assertThat(jRouter.getRoute("GET", "/"), is("java.lang.String#index"));
        assertThat(jRouter.getRoute(null, null), is("books.Animals#hello"));
    }

    @Test
    public void getEmptyRoute() {
        assertNull(jRouter.getRoute(null, null));
    }
}