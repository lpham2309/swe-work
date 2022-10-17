import java.util.*;

public class King extends Piece {
    public King(Color c) {
        super(c);
    }
    // implement appropriate methods

    public String toString() {
	    if (this.color().name() == "BLACK"){
            return "bk";
        } else{
            return "wk";
        }
    }

    public List<String> moves(Board b, String loc) {
        List<String> all_moves = new ArrayList<>();
        if(loc.isEmpty()) {
            return null;
        }
        Integer curr_row = Integer.parseInt(String.valueOf(loc.charAt(1)));
        char curr_col = loc.charAt(0);

        int[][] possible_moves = {
                {1, 0},{-1, 0}, {1, -1}, {1, 1}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}
        };

        for(int idx = 0; idx < possible_moves.length; idx++){
                int row_loc = possible_moves[idx][0] + curr_row;
                if(row_loc > 8 || row_loc < 1){
                    continue;
                }
                int col_loc_int = possible_moves[idx][1] + b.reverse_mapping.get(curr_col);
                if(col_loc_int > 8 || col_loc_int < 1){
                    continue;
                }
                Piece curr_piece = b.getPiece((b.forward_mapping.get(col_loc_int)) + String.valueOf(row_loc));
                if(curr_piece != null){
                    if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                        all_moves.add((b.forward_mapping.get(col_loc_int)) + String.valueOf(row_loc));
                    }
                } else {
                    all_moves.add((b.forward_mapping.get(col_loc_int)) + String.valueOf(row_loc));
                }
        }
        return all_moves;
    }

}