package jrails;

import java.util.ArrayList;
import java.util.List;
public class Html {
    private Object curr_html = "";
    List<String> elements = new ArrayList<>();

    public String toString() {
        String res = "";
        for(String s : elements){
            res += s;
        }
        return res;
    }

    public Html seq(Html h) {
        if(h != null){
            elements.add(h.toString());
        } else {
            elements.add("");
        }
        return this;
    }

    public Html br() {
        elements.add("<br/>");
        return this;
    }

    public Html t(Object o) {
        if(o != null){
            elements.add(o.toString());
        } else {
            elements.add("");
        }
        return this;
    }

    public Html p(Html child) {
        if(child != null){
            elements.add("<p>" + child.toString() + "</p>");
        } else {
            elements.add("<p>" + "" + "</p>");
        }

        return this;
    }

    public Html div(Html child) {
        if(child != null) {
            elements.add("<div>" + child.toString() + "</div>");
        } else {
            elements.add("<div>" + "" + "</div>");
        }
        return this;
    }

    public Html strong(Html child) {
        if(child != null){
            elements.add("<strong>" + child.toString() + "</strong>");
        } else {
            elements.add("<strong>" + "" + "</strong>");
        }

        return this;
    }

    public Html h1(Html child) {
        if(child != null) {
            elements.add("<h1>" + child.toString() + "</h1>");
        } else {
            elements.add("<h1>" + "" + "</h1>");
        }
        return this;
    }

    public Html tr(Html child) {
        if(child != null) {
            elements.add("<tr>" + child.toString() + "</tr>");
        } else {
            elements.add("<tr>" + "" + "</tr>");
        }
        return this;

    }

    public Html th(Html child) {
        if(child != null) {
            elements.add("<th>" + child.toString() + "</th>");
        } else {
            elements.add("<th>" + "" + "</th>");
        }
        return this;
    }

    public Html td(Html child) {
        if(child != null) {
            elements.add("<td>" + child.toString() + "</td>");
        } else {
            elements.add("<td>" + "" + "</td>");
        }
        return this;
    }

    public Html table(Html child) {
        if(child != null) {
            elements.add("<table>" + child.toString() + "</table>");
        } else {
            elements.add("<table>" + "" + "</table>");
        }
        return this;
    }

    public Html thead(Html child) {
        if(child != null){
            elements.add("<thead>" + child.toString() + "</thead>");
        } else {
            elements.add("<thead>" + "" + "</thead>");
        }
        return this;
    }

    public Html tbody(Html child) {
        if(child == null){
            elements.add("<tbody>" + "" + "</tbody>");
        } else {
            elements.add("<tbody>" + child.toString() + "</tbody>");
        }
        return this;
    }

    public Html textarea(String name, Html child) {
        if(name == null) {
            name = "";
        }
        if(child == null) {
            elements.add("<textarea name=\"" + name + "\">" + "" + "</textarea>");
        } else {
            elements.add("<textarea name=\"" + name + "\">" + child.toString() + "</textarea>");
        }
        return this;
    }

    public Html link_to(String text, String url) {
        if(text == null){
            text = "";
        }
        if(url == null) {
            url = "";
        }
        elements.add("<a href=\"" + url + "\"" + ">" + text + "</a>");
        return this;
    }

    public Html form(String action, Html child) {
        if(action == null) {
            action = "";
        }
        if(child == null) {
            elements.add("<form action=\"" + action + "\" accept-charset=\"UTF-8\" method=\"post\">" +
                    "" +"</form>");
        } else {
            elements.add("<form action=\"" + action + "\" accept-charset=\"UTF-8\" method=\"post\">" +
                    child.toString() +"</form>");
        }
        return this;
    }

    public Html submit(String value) {
        if(value == null){
            value = "";
        }
        elements.add("<input type=\"submit\" value=\"" + value + "\"/>");
        return this;
    }
}