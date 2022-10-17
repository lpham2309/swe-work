import java.util.*;

public class Rook extends Piece {
    public Rook(Color c) {
        super(c);
    }
    // implement appropriate methods

    public String toString() {
        if (this.color().name() == "BLACK"){
            return "br";
        } else{
            return "wr";
        }
    }

    public List<String> moves(Board b, String loc) {
        List<String> all_moves = new ArrayList<>();
        if (loc.isEmpty()) {
            return null;
        }
        Integer curr_row = Integer.parseInt(String.valueOf(loc.charAt(1)));
        char curr_col = loc.charAt(0);
        boolean is_top_left = true;
        boolean is_top_right = true;
        boolean is_bottom_left = true;
        boolean is_bottom_right = true;

        for (int i = 1; i <= 8; i++) {
            char curr_col_val_up = (char) (curr_col + i);
            int curr_row_val_up = curr_row + i;
            char curr_col_val_down = (char) (curr_col - i);
            int curr_row_val_down = curr_row - i;


            if ((curr_col_val_up <= 'h' && curr_col_val_up >= 'a')) {
                Piece curr_piece = b.getPiece(curr_col_val_up + String.valueOf(curr_row));
                if (curr_piece != null) {
                    is_top_left = false;
                    if (curr_piece.toString().charAt(0) != toString().charAt(0)) {
                        all_moves.add(curr_col_val_up + String.valueOf(curr_row));
                    }
                }
                if (is_top_left) {
                    all_moves.add(curr_col_val_up + String.valueOf(curr_row));
                }
            }

            if ((curr_col_val_down <= 'h' && curr_col_val_down >= 'a')) {
                Piece curr_piece = b.getPiece(curr_col_val_down + String.valueOf(curr_row));
                if (curr_piece != null) {
                    is_bottom_left = false;
                    if (curr_piece.toString().charAt(0) != toString().charAt(0)) {
                        all_moves.add(curr_col_val_down + String.valueOf(curr_row));
                    }
                }
                if (is_bottom_left) {
                    all_moves.add(curr_col_val_down + String.valueOf(curr_row));
                }
            }

            if ((curr_row_val_down <= 8 && curr_row_val_down >= 1) && (curr_col <= 'h' && curr_col >= 'a')) {
                Piece curr_piece = b.getPiece(curr_col + String.valueOf(curr_row_val_down));
                if (curr_piece != null) {
                    is_top_right = false;
                    if (curr_piece.toString().charAt(0) != toString().charAt(0)) {
                        all_moves.add(curr_col + String.valueOf(curr_row_val_down));
                    }
                }
                if (is_top_right) {
                    all_moves.add(curr_col + String.valueOf(curr_row_val_down));
                }
            }

            if ((curr_row_val_up <= 8 && curr_row_val_up >= 1) && (curr_col <= 'h' && curr_col >= 'a')) {
                Piece curr_piece = b.getPiece(curr_col + String.valueOf(curr_row_val_up));
                if (curr_piece != null) {
                    is_bottom_right = false;
                    if (curr_piece.toString().charAt(0) != toString().charAt(0)) {
                        all_moves.add(curr_col + String.valueOf(curr_row_val_up));
                    }
                }
                if (is_bottom_right) {
                    all_moves.add(curr_col + String.valueOf(curr_row_val_up));
                }
            }

        }

        return all_moves;
    }
}