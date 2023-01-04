package jrails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import books.BookView;
import books.Book;
import jrails.View;


import static jrails.View.*;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.*;

public class ViewTest {

    @Test
    public void empty() {
        assertThat(View.empty().toString(), isEmptyString());
    }

    @Test
    public void test_book_view() {
        String s1 = View.div(strong(t("Hello World"))).toString();
        System.out.print(s1.equals("<div><b>Hello World</b></div>"));
        assertEquals(s1, "<div><strong>Hello World</strong></div>");

        String s2 = View.textarea("title", t("Old Title")).toString();
        assertEquals(s2, "<textarea name=\"title\">Old Title</textarea>");

        String s3 = View.p(View.t("Example view")).toString();
        assert(s3.equals("<p>Example view</p>"));

//        assertEquals(res, "<tr><td><a href=/destroy?id=1>Delete</a></td></tr>");
    }
    @Test
    public void test_show() {
        Html table_body = View.empty();
        table_body =
                table_body.tr(td(t("Title 1")).
                        td(t("Author 1")).
                        td(t(1)).
                        td(link_to("Show", "/show?id=" + 1)).
                        td(link_to("Edit", "/edit?id=" + 1)).
                        td(link_to("Delete", "/destroy?id=" + 1)));

        assertEquals(table_body.toString(), "<tr><td>Title 1</td><td>Author 1</td><td>1</td><td><a href=\"/show?id=1\">Show</a></td><td><a href=\"/edit?id=1\">Edit</a></td><td><a href=\"/destroy?id=1\">Delete</a></td></tr>");
    }

    @Test
    public void test_html_tags() {
        String res = View.p(strong(t("Title:")).t("Programming Languages: Build, Prove, and Compare")).
                p(strong(t("Author:")).t("Norman Ramsey")).
                p(strong(t("Copies:")).t(999)).
                t(link_to("Edit", "/edit?id=" + 1)).t(" | ").
                t(link_to("Back", "/")).toString();

        assertEquals(res, "<p><strong>Title:</strong>Programming Languages: Build, Prove, and Compare</p><p><strong>Author:</strong>Norman Ramsey</p><p><strong>Copies:</strong>999</p><a href=\"/edit?id=1\">Edit</a> | <a href=\"/\">Back</a>");
    }

    @Test
    public void test_br(){
        String s = View.br().toString();
        assertEquals(s, "<br/>");
    }

    @Test
    public void test_new() {
        String s = View.div(t("Title").textarea("title", t("Title"))).toString();
        assertEquals(s, "<div>Title<textarea name=\"title\">Title</textarea></div>");
    }

    @Test
    public void test_show_route() {
        Html s = View.p(strong(t("Title:")).t("Title 1")).
                p(strong(t("Author:")).t("Author 1")).
                p(strong(t("Copies:")).t(1)).
                t(link_to("Edit", "/edit?id=" + 1)).t(" | ").
                t(link_to("Back", "/"));
        assertEquals(s.toString(), "<p><strong>Title:</strong>Title 1</p><p><strong>Author:</strong>Author 1</p><p><strong>Copies:</strong>1</p><a href=\"/edit?id=1\">Edit</a> | <a href=\"/\">Back</a>");
    }

    private static Html the_form(String action, Book b) {
        return form(action,
                div(t("Title").textarea("title", t(b.title))).
                        div(t("Author").textarea("author", t(b.author))).
                        div(submit("Save")));
    }

    @Test
    public void test_new_view() {
        Book a = new Book();
        a.title = "Old Title";
        a.author = "Old Author";
        a.num_copies = 1;
        String s = the_form("/create", a).toString();

        String response = "<form action=\"/create\" accept-charset=\"UTF-8\" method=\"post\"><div>Title<textarea name=\"title\">Old Title</textarea></div><div>Author<textarea name=\"author\">Old Author</textarea></div><div><input type=\"submit\" value=\"Save\"/></div></form>";
        assertEquals(s, response);
    }

    @Test
    public void test_all_tags() {
        assertEquals(View.h1(t("Hello")).toString(), "<h1>Hello</h1>");
        assertEquals(View.p(t("Hellow")).toString(), "<p>Hellow</p>");
        assertEquals(View.br().toString(), "<br/>");
        assertEquals(View.div(strong(t(123))).toString(), "<div><strong>123</strong></div>");
        assertEquals(View.link_to("name", "https://www.test.co").toString(), "<a href=\"https://www.test.co\">name</a>");
        assertEquals(View.submit("submit").toString(), "<input type=\"submit\" value=\"submit\"/>");
        assertEquals(View.tr(t("test")).toString(), "<tr>test</tr>");
        assertEquals(View.th(t("head")).toString(), "<th>head</th>");
        assertEquals(View.td(t("testing td")).toString(), "<td>testing td</td>");
        assertEquals(View.thead(t("testing thead")).toString(), "<thead>testing thead</thead>");
        assertEquals(View.tbody(t("testing tbody")).toString(), "<tbody>testing tbody</tbody>");

    }
}