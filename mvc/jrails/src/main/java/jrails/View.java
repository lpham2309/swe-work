package jrails;

public class View {
    public static Html empty() {
        Html new_html =  new Html();
        return new_html;
    }

    public static Html br() {
        Html new_instance = new Html();
        return new_instance.br();
    }

    public static Html t(Object o) {
        Html new_instance = new Html();
        String curr_t = o.toString();
        return new_instance.t(curr_t);
    }

    public static Html p(Html child) {
        Html new_instance = new Html();
        return new_instance.p(child);
    }

    public static Html div(Html child) {
        Html new_instance = new Html();
        return new_instance.div(child);
    }

    public static Html strong(Html child) {
        Html new_instance = new Html();
        return new_instance.strong(child);
    }

    public static Html h1(Html child) {
        Html new_instance = new Html();
        return new_instance.h1(child);
    }

    public static Html tr(Html child) {
        Html new_instance = new Html();
        return new_instance.tr(child);
    }

    public static Html th(Html child) {
        Html new_instance = new Html();
        return new_instance.th(child);
    }

    public static Html td(Html child) {
        Html new_instance = new Html();
        return new_instance.td(child);
    }

    public static Html table(Html child) {
        Html new_instance = new Html();
        return new_instance.table(child);
    }

    public static Html thead(Html child) {
        Html new_instance = new Html();
        return new_instance.thead(child);
    }

    public static Html tbody(Html child) {
        Html new_instance = new Html();
        return new_instance.tbody(child);
    }

    public static Html textarea(String name, Html child) {
        Html new_instance = new Html();
        return new_instance.textarea(name, child);
    }

    public static Html link_to(String text, String url) {
        Html new_instance = new Html();
        return new_instance.link_to(text, url);
    }

    public static Html form(String action, Html child) {
        Html new_instance = new Html();
        return new_instance.form(action, child);
    }

    public static Html submit(String value) {
        Html new_instance = new Html();
        return new_instance.submit(value);
    }
}