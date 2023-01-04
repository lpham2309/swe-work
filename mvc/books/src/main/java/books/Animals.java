package books;
import jrails.Column;
import jrails.Model;

public class Animals extends Model{
    @Column
    public String color;

    @Column
    public String name;

    @Column
    public int age;

    @Column
    public boolean isSleeping;

    @Column
    public String note;
}
