import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class Piece {
    private final Color color;
    public static Map<Character, PieceFactory> mapper = new HashMap<>();
    public static void registerPiece(PieceFactory pf) {
        if(!mapper.containsKey(pf.symbol())){
            mapper.put(pf.symbol(), pf);
        }
    }

    public Piece (Color color){
        this.color = color;
    }

    public static Piece createPiece(String name) {
        if(name == null){
            throw new NullPointerException();
        }
        Pattern pattern =  Pattern.compile("([w|b])([\\w])");
        Matcher matcher = pattern.matcher(name);

        if(matcher.find()) {
            char piece_name = name.charAt(1);
            char piece_color = name.charAt(0);

            if(mapper.containsKey(piece_name)){
                return mapper
                        .get(piece_name)
                        .create(
                                piece_color == 'b'
                                        ? Color.BLACK
                                        : Color.WHITE
                        );
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public Color color() {
        return this.color;
    }

    abstract public String toString();

    abstract public List<String> moves(Board b, String loc);
}