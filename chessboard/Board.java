import java.util.*;

public class Board {

    private Piece[][] pieces = new Piece[8][8];
    private static Board is_instance;
    public Map<String, BoardListener> listeners = new HashMap<>();
    private String class_name;
    private static String from_loc;
    private static String to_loc;
    private static Piece curr_piece;

    public Map<Integer,String> forward_mapping = new HashMap<>(){{
        this.put(1, "a");
        this.put(2, "b");
        this.put(3, "c");
        this.put(4, "d");
        this.put(5, "e");
        this.put(6, "f");
        this.put(7, "g");
        this.put(8, "h");
    }};

    public Map<Character,Integer> reverse_mapping = new HashMap<>(){{
        this.put('a', 1);
        this.put('b', 2);
        this.put('c', 3);
        this.put('d', 4);
        this.put('e', 5);
        this.put('f', 6);
        this.put('g', 7);
        this.put('h', 8);
    }};

    private Board() {
    }
    
    public static Board theBoard() {
        if(is_instance == null) {
            is_instance = new Board();
        }
        return is_instance;
    }

    public List location_parser(String loc){
        List<Integer> loc_list = new ArrayList<>();
        char col_character = loc.charAt(0);
        int row_character = Integer.parseInt(loc.substring(1));

        int col_number = col_character - 96;
        int row_number = row_character;

        loc_list.add(col_number-1);
        loc_list.add(row_number-1);

        return loc_list;
    }

    // Returns piece at given loc or null if no such piece
    // exists
    public Piece getPiece(String loc) {
        if(loc == null) {
            throw new NoSuchElementException();
        }
        List<Integer> loc_parse = location_parser(loc);

        int row = loc_parse.get(1);
        int col = loc_parse.get(0);

        if(this.pieces[row][col] != null){
            if( row != 8 || col != 8){
                return this.pieces[row][col];
            } else {
                return this.pieces[row-1][col-1];
            }
        }

        return null;
    }

    public void addPiece(Piece p, String loc) {
	    if(getPiece(loc) != null){
            throw new UnsupportedOperationException();
        }
        List<Integer> loc_parse = location_parser(loc);
        int row = loc_parse.get(1);
        int col = loc_parse.get(0);
        this.pieces[row][col] = p;
    }

    public void movePiece(String from, String to) {
        if(from == null && to == null){
            throw new NullPointerException();
        }
        List<Integer> from_loc_parse = location_parser(from);
        List<String> curr_piece_moves = new ArrayList<>();

        int from_row = from_loc_parse.get(1);
        int from_col = from_loc_parse.get(0);

        List<Integer> to_loc_parse = location_parser(to);

        int to_row = to_loc_parse.get(1);
        int to_col = to_loc_parse.get(0);

        from_loc = from;
        to_loc = to;
        curr_piece = this.pieces[from_row][from_col];

        if(curr_piece != null && !listeners.isEmpty()) {
            listeners.get(class_name).onMove(from_loc, to_loc, curr_piece);
        }

        curr_piece_moves = curr_piece.moves(this, from_loc);
        boolean is_valid_move = false;

        if(curr_piece_moves.contains(to)){
            is_valid_move = true;
        }

        if(is_valid_move) {
            if (this.pieces[to_row][to_col] == null) {
                this.pieces[to_row][to_col] = this.pieces[from_row][from_col];
                this.pieces[from_row][from_col] = null;
            } else {
                Piece attacker = this.pieces[from_row][from_col];
                Piece captured = this.pieces[to_row][to_col];
                listeners.get(class_name).onCapture(attacker, captured);
                if (attacker.toString().charAt(0) != captured.toString().charAt(1)) {
                    this.pieces[to_row][to_col] = this.pieces[from_row][from_col];
                    this.pieces[from_row][from_col] = null;
                }
            }
        } else {
            System.out.printf("Error: Invalid moves from %s to %s\n", from, to);
        }
    }

    public void clear() {
        for (int i = 0; i <= this.pieces.length-1; i++) {
            for (int j = 0; j <= this.pieces[0].length-1; j++) {
                if (this.pieces[i][j] instanceof Piece) {
                    this.pieces[i][j] = null;
                }
            }
        }
    }

    public void registerListener(BoardListener bl) {
        this.class_name = bl.getClass().getSimpleName();
        if(!listeners.containsKey(this.class_name)){
            listeners.put(this.class_name, bl);
        }
    }

    public void removeListener(BoardListener bl) {
	    if(listeners.containsKey(bl.getClass().getSimpleName())) {
            listeners.remove(bl.getClass().getSimpleName());
        }
    }

    public void removeAllListeners() {
	    listeners.clear();
    }

    public void iterate(BoardInternalIterator bi) {
        for(int i = 1; i < this.pieces.length + 1; i++){
            for(int j = 1; j < this.pieces[0].length + 1; j++){
                String curr_loc = forward_mapping.get(i) + j;
                Piece curr_piece = getPiece(curr_loc);
                bi.visit(curr_loc, curr_piece);
            }
        }
    }
}