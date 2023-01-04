package jrails;

import books.Animals;
import books.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class ModelTest {

    private Model model;
    private Book book;

    @Before
    public void setUp() throws Exception {
        model = new Model(){};
        Model.reset();
    }

    @Test
    public void id() {
        assertThat(model.id(), notNullValue());
    }

    @Test
    public void test_ids() {
        Model.reset();
        Book book1 = new Book();
        book1.title = "Book 1";
        book1.author = "Author 1";
        book1.num_copies = 3;

        Book book2 = new Book();
        book2.title = "Book 2";
        book2.author = "Author 2";
        book2.num_copies = 1;

        assertEquals(book1.id(), book2.id());
        assertNotEquals(book1.title, book2.title);
        assertNotEquals(book1.author, book2.author);
        assertNotEquals(book1.num_copies, book2.num_copies);

        book1.save();
        book2.save();

        assertNotEquals(book1.id(), book2.id());

        Book b3 = new Book();
        b3.title = "A";
        b3.author = "B";
        b3.num_copies = 10;

        assertNotNull(Model.find(Book.class, 1));
    }

    @Test
    public void test_multiple_saved() {
        Model.reset();
        for(int i = 0; i <= 100; i++){
            Animals a = new Animals();
            a.color = "WHITE"+i;
            a.name = "cat"+i;
            a.age = i+1;
            a.isSleeping = true;

            a.save();
        }

        Model.reset();
        assertEquals(Model.all(Animals.class).size(), 0);
    }

    @Test
    public void test_two_instances() {
        Model.reset();
        Animals a1 = new Animals();
        a1.color = "WHITE";
        a1.name = "cat";
        a1.age = 1;
        a1.isSleeping = true;

        Animals a2 = new Animals();
        a2.color = "WHITE";
        a2.name = "cat";
        a2.age = 1;
        a2.isSleeping = true;
        assertEquals(a1.id(), a2.id());

        a1.save();
        a2.save();

        assertNotEquals(a1.id(), a2.id());
    }


    @Test
    public void test_save_then_find() {
        Model.reset();

        Animals animal = new Animals();
        animal.color = "White";
        animal.name = "dog";
        animal.age = 1;
        animal.isSleeping = false;

        Animals animal_2 = new Animals();
        animal_2.color = "Black";
        animal_2.name = "cat";
        animal_2.age = 2;
        animal_2.isSleeping = true;

        animal.save();
        animal_2.save();


        assertEquals(animal.id(), 1);
        assertEquals(animal_2.id(), 2);
        assertEquals(Model.all(Animals.class).size(), 2);
    }

    @Test
    public void test_input_with_space() {
        Model.reset();

        Animals animal = new Animals();
        animal.color = "White";
        animal.name = " ";
        animal.age = 1;
        animal.isSleeping = false;

        animal.save();
        assertEquals(animal.id(), 1);
        assertNotNull(Model.find(Animals.class, 1));
    }

    @Test
    public void test_string_field_with_special_character() {
        Model.reset();

        Animals animal = new Animals();
        animal.color = "\"White'";
        animal.name = "'test\"";
        animal.age = 1;
        animal.isSleeping = false;

        animal.save();
        assertEquals(animal.id(), 1);
        assertNotNull(Model.find(Animals.class, 1));
    }

    @Test
    public void test_both_positive_and_negative_int() {
        Model.reset();

        Animals animal = new Animals();
        animal.color = "Yellow";
        animal.name = "dog";
        animal.age = 2;
        animal.isSleeping = true;
        animal.save();

        Animals animal_2 = new Animals();
        animal_2.color = "white";
        animal_2.name = "kat";
        animal_2.age = -1;
        animal_2.isSleeping = false;

        animal.save();
        animal_2.save();

        assertNotNull(Model.find(Animals.class, 1));
        assertNotNull(Model.find(Animals.class, 2));
    }

    @Test
    public void test_two_saved_model() {
        Model.reset();
        Animals animal = new Animals();
        animal.color = "Yellow";
        animal.name = "dog";
        animal.age = 2;
        animal.isSleeping = true;
        animal.save();

        Animals animal2 = new Animals();
        animal2.color = "asd";
        animal2.name = "asdasd";
        animal2.age = 2;
        animal2.isSleeping = true;
        animal2.save();

        assertEquals(Model.all(Animals.class).get(0).color, "asd");
    }

    @Test
    public void test_override_content_with_id() {
        Model.reset();
        Animals animal = new Animals();
        animal.color = "WHITE";
        animal.name = "cat";
        animal.age = 1;
        animal.isSleeping = false;
        animal.save();

        animal.color = "color";
        animal.name = "name";
        animal.age = 7;
        animal.isSleeping = true;
        animal.save();

        assertNotEquals(animal.color, "WHITE");
    }

    @Test
    public void test_multiple_models() {
        Model.reset();
        Animals a1 = new Animals();
        a1.color = "WHITE";
        a1.name = "cat";
        a1.age = 1;
        a1.isSleeping = true;

        Animals a2 = new Animals();
        a2.color = "color";
        a2.name = "name";
        a2.age = 10;
        a2.isSleeping = false;

        Animals a3 = new Animals();
        a3.color = "WHITE";
        a3.name = "cat";
        a3.age = 122;
        a3.isSleeping = true;

        a1.save();
        a2.save();
        a3.save();

        assertEquals(Model.find(Animals.class, 2).color, "color");
        assertEquals(Model.find(Animals.class, 2).name, "name");
        assertEquals(Model.find(Animals.class, 2).age, 10);

        assertEquals(Model.find(Animals.class, 1).color, "WHITE");
        assertEquals(Model.find(Animals.class, 1).name, "cat");
        assertEquals(Model.find(Animals.class, 1).age, 1);
    }

    @Test
    public void test_one_saved_model() {
        Model.reset();

        Animals a3 = new Animals();
        a3.color = "WHITE";
        a3.name = "cat";
        a3.age = 0;
        a3.isSleeping = true;

        a3.save();
        assertEquals(Model.find(Animals.class, 1).age, 0);
        assertEquals(Model.find(Animals.class, 1).color, "WHITE");
    }

    @Test
    public void test_destroy(){
        Model.reset();
        Animals a3 = new Animals();
        a3.color = "WHITE";
        a3.name = "cat";
        a3.age = 0;
        a3.isSleeping = true;

        a3.save();
        int instance_id = a3.id();
        assertNotNull(Model.find(Animals.class, instance_id));
        assertEquals(Model.find(Animals.class, instance_id).id(), 1);

        a3.destroy();
    }

    @Test
    public void test_new_line() {
        Model.reset();
        Animals a3 = new Animals();
        a3.color = "WHITE";
        a3.name = "cat";
        a3.age = 0;
        a3.isSleeping = true;
        a3.note = "test\n";

        a3.save();
        assertNotNull(Model.find(Animals.class, a3.id()));
        assertEquals(Model.find(Animals.class, a3.id()).color, "WHITE");
        assertEquals(Model.find(Animals.class, a3.id()).name, "cat");
        assertEquals(Model.find(Animals.class, a3.id()).age, 0);
        assertTrue(Model.find(Animals.class, a3.id()).isSleeping);
    }
    @After
    public void tearDown() throws Exception {
        Model.reset();
    }


}