import java.util.*;

public class Queen extends Piece {
    public Queen(Color c) {
        super(c);
    }
    // implement appropriate methods

    public String toString() {
        if (this.color().name() == "BLACK"){
            return "bq";
        } else{
            return "wq";
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

        boolean is_top_left_diag = true;
        boolean is_top_right_diag = true;
        boolean is_bottom_left_diag = true;
        boolean is_bottom_right_diag = true;

        for(int row = 1; row <= 8; row++){
            char curr_col_val_up = (char) (curr_col + row);
            int curr_row_val_up = curr_row + row;
            char curr_col_val_down = (char) (curr_col - row);
            int curr_row_val_down = curr_row - row;

            if ((curr_col_val_up <= 'h' && curr_col_val_up >= 'a')) {
                Piece curr_piece = b.getPiece(curr_col_val_up + String.valueOf(curr_row));
                if (curr_piece != null) {
                    is_top_left = false;
                    if (curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)) {
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
                    if (curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)) {
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
                    if (curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)) {
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
                    if (curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)) {
                        all_moves.add(curr_col + String.valueOf(curr_row_val_up));
                    }
                }
                if (is_bottom_right) {
                    all_moves.add(curr_col + String.valueOf(curr_row_val_up));
                }
            }

            if((curr_row_val_up <= 8 && curr_row_val_up >= 1) && (curr_col_val_up <= 'h' && curr_col_val_up >= 'a')){
                Piece curr_piece = b.getPiece(curr_col_val_up + String.valueOf(curr_row_val_up));
                if(curr_piece != null){
                    is_top_left_diag = false;
                    if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                        all_moves.add(curr_col_val_up + String.valueOf(curr_row_val_up));
                    }
                }
                if(is_top_left_diag){
                    all_moves.add(curr_col_val_up + String.valueOf(curr_row_val_up));
                }
            }

            if((curr_row_val_down <= 8 && curr_row_val_down >= 1) && (curr_col_val_down <= 'h' && curr_col_val_down >= 'a')){
                Piece curr_piece = b.getPiece(curr_col_val_down + String.valueOf(curr_row_val_down));
                if(curr_piece !=null) {
                    is_bottom_left_diag = false;
                    if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                        all_moves.add(curr_col_val_down + String.valueOf(curr_row_val_down));
                    }
                }
                if(is_bottom_left_diag) {
                    all_moves.add(curr_col_val_down + String.valueOf(curr_row_val_down));
                }
            }

            if((curr_row_val_down <= 8 && curr_row_val_down >= 1) && (curr_col_val_up <= 'h' && curr_col_val_up >= 'a')){
                Piece curr_piece = b.getPiece(curr_col_val_up + String.valueOf(curr_row_val_down));
                if(curr_piece != null) {
                    is_top_right_diag = false;
                    if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                        all_moves.add(curr_col_val_up + String.valueOf(curr_row_val_down));
                    }
                }
                if(is_top_right_diag) {
                    all_moves.add(curr_col_val_up + String.valueOf(curr_row_val_down));
                }
            }

            if((curr_row_val_up <= 8 && curr_row_val_up >= 1) && (curr_col_val_down <= 'h' && curr_col_val_down >= 'a')){
                Piece curr_piece = b.getPiece(curr_col_val_down + String.valueOf(curr_row_val_up));
                if(curr_piece != null) {
                    is_bottom_right_diag = false;
                    if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)) {
                        all_moves.add(curr_col_val_down + String.valueOf(curr_row_val_up));
                    }
                }
                if(is_bottom_right_diag) {
                    all_moves.add(curr_col_val_down + String.valueOf(curr_row_val_up));
                }
            }
        }

        return all_moves;


    }

}