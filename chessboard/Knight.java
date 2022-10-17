import java.util.*;

public class Knight extends Piece {
    public Knight(Color c) {
        super(c);
    }
    // implement appropriate methods

    public String toString() {
        if (this.color().name() == "BLACK"){
            return "bn";
        } else{
            return "wn";
        }
    }

    public List<String> moves(Board b, String loc) {
        List<String> all_moves = new ArrayList<>();
        if (loc.isEmpty()) {
            return null;
        }
        Integer curr_row = Integer.parseInt(String.valueOf(loc.charAt(1)));
        char curr_col = loc.charAt(0);

        int[][] possible_moves = {
                {2,1}, {2,-1}, {-2,1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        boolean is_valid_move = true;

        for(int i = 0; i < possible_moves.length; i ++) {
            int row_loc = possible_moves[i][0] + curr_row;
            int col_loc_int = possible_moves[i][1] + b.reverse_mapping.get(curr_col);

            if((row_loc <= 8 && row_loc >= 1) || (col_loc_int <= 8 && col_loc_int >= 1)) {
                Piece curr_piece = b.getPiece((b.forward_mapping.get(col_loc_int)) + String.valueOf(row_loc));
                if(curr_piece != null){
                    is_valid_move = false;
                    if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                        all_moves.add((b.forward_mapping.get(col_loc_int)) + String.valueOf(row_loc));
                    }
                } else{
                    all_moves.add((b.forward_mapping.get(col_loc_int)) + String.valueOf(row_loc));
                }
            }
        }
        return all_moves;
    }

}