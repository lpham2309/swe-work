import java.util.*;

public class Pawn extends Piece {
    public Pawn(Color c) {
        super(c);
    }
    // implement appropriate methods

    public String toString() {
        if (this.color().name() == "BLACK"){
            return "bp";
        } else{
            return "wp";
        }
    }

    public List<String> moves(Board b, String loc) {
        List<String> all_moves = new ArrayList<>();
        if(loc.isEmpty()) {
            return null;
        }
        Integer curr_row = Integer.parseInt(String.valueOf(loc.charAt(1)));
        char curr_col = loc.charAt(0);

        boolean is_black_left_diag = true;
        boolean is_black_right_diag = true;
        boolean is_white_left_diag = true;
        boolean is_white_right_diag = true;

        boolean is_black_forward = true;
        boolean is_white_forward = true;
        boolean is_black_forward_twice = true;
        boolean is_white_forward_twice = true;

        if (this.color().name() == "BLACK"){
            if(curr_row == 7) {
                if(curr_col >= 'a' && curr_col <= 'h'){
                    Piece curr_piece = b.getPiece((char)(curr_col+1) + Integer.toString(curr_row-1));
                    if(curr_piece != null){
                        is_black_left_diag = false;
                        if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                            all_moves.add((char)(curr_col+1) + Integer.toString(curr_row-1));
                        }
                    }
                    if(is_black_left_diag && curr_piece != null){
                        all_moves.add((char)(curr_col+1) + Integer.toString(curr_row-1));
                    }
                }
                if(curr_col <= 'h' && curr_col > 'a'){
                    Piece curr_piece = b.getPiece((char)(curr_col-1) + Integer.toString(curr_row-1));
                    if(curr_piece != null){
                        is_black_right_diag = false;
                        if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                            all_moves.add((char)(curr_col-1) + Integer.toString(curr_row-1));
                        }
                    }
                    if(is_black_right_diag && curr_piece != null){
                        all_moves.add((char)(curr_col-1) + Integer.toString(curr_row-1));
                    }
                }
                Piece curr_piece = b.getPiece(curr_col + Integer.toString(curr_row - 2));
                if(curr_piece != null){
                    is_black_forward_twice = false;
                    if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                        all_moves.add(curr_col + Integer.toString(curr_row - 2));
                    }
                }
                if(is_black_forward_twice){
                    all_moves.add(curr_col + Integer.toString(curr_row - 2));
                }
            }
            Piece curr_piece = b.getPiece(curr_col + Integer.toString(curr_row - 1));
            if(curr_piece != null){
                is_black_forward = false;
            }
            if(is_black_forward && curr_piece == null){
                all_moves.add(curr_col + Integer.toString(curr_row-1));
            }

            Piece curr_piece_left_diag = b.getPiece((char)(curr_col+1) + Integer.toString(curr_row-1));
            if(curr_piece_left_diag != null){
                is_black_left_diag = false;
                if(curr_piece_left_diag.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                    all_moves.add((char)(curr_col+1) + Integer.toString(curr_row-1));
                }
            }
            if(is_black_left_diag && curr_piece_left_diag != null){
                all_moves.add((char)(curr_col+1) + Integer.toString(curr_row-1));
            }

            Piece curr_piece_right_diag = b.getPiece((char)(curr_col-1) + Integer.toString(curr_row-1));
            if(curr_piece_right_diag != null){
                is_black_right_diag = false;
                if(curr_piece_right_diag.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                    all_moves.add((char)(curr_col-1) + Integer.toString(curr_row-1));
                }
            }
            if(is_black_right_diag && curr_piece_right_diag != null){
                all_moves.add((char)(curr_col-1) + Integer.toString(curr_row-1));
            }
        }
        if (this.color().name() == "WHITE"){
            if(curr_row == 2) {
                if(curr_col >= 'a' && curr_col <= 'h'){
                    Piece curr_piece = b.getPiece((char)(curr_col -1) + Integer.toString(curr_row+1));
                    if(curr_piece != null){
                        is_white_left_diag = false;
                        if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                            all_moves.add((char)(curr_col -1) + Integer.toString(curr_row+1));
                        }
                    }
                    if(is_white_left_diag && curr_piece != null){
                        all_moves.add((char)(curr_col -1) + Integer.toString(curr_row+1));
                    }
                }
                if(curr_col <= 'h' && curr_col > 'a'){
                    Piece curr_piece = b.getPiece((char)(curr_col +1) + Integer.toString(curr_row+1));
                    if(curr_piece != null){
                        is_white_right_diag = false;
                        if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                            all_moves.add((char)(curr_col + 1) + Integer.toString(curr_row+1));
                        }
                    }
                    if(is_white_right_diag && curr_piece != null){
                        all_moves.add((char)(curr_col + 1) + Integer.toString(curr_row+1));
                    }
                }
                Piece curr_piece = b.getPiece(curr_col + Integer.toString(curr_row + 2));
                if(curr_piece != null){
                    is_white_forward_twice = false;
                    if(curr_piece.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                        all_moves.add(curr_col + Integer.toString(curr_row + 2));
                    }
                }
                if(is_white_forward_twice){
                    all_moves.add(curr_col + Integer.toString(curr_row + 2));
                }
            }
            Piece curr_piece = b.getPiece(curr_col + Integer.toString(curr_row + 1));
            if(curr_piece != null){
                is_white_forward = false;
            }
            if(is_white_forward && curr_piece == null){
                all_moves.add(curr_col + Integer.toString(curr_row + 1));
            }
            Piece curr_piece_diag_left = b.getPiece((char)(curr_col -1) + Integer.toString(curr_row+1));
            if(curr_piece_diag_left != null){
                is_white_left_diag = false;
                if(curr_piece_diag_left.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                    all_moves.add((char)(curr_col -1) + Integer.toString(curr_row+1));
                }
            }
            if(is_white_left_diag && curr_piece_diag_left != null){
                all_moves.add((char)(curr_col -1) + Integer.toString(curr_row+1));
            }

            Piece curr_piece_diag_right = b.getPiece((char)(curr_col +1) + Integer.toString(curr_row+1));
            if(curr_piece_diag_right != null){
                is_white_right_diag = false;
                if(curr_piece_diag_right.toString().charAt(0) != b.getPiece(loc).toString().charAt(0)){
                    all_moves.add((char)(curr_col + 1) + Integer.toString(curr_row+1));
                }
            }
            if(is_white_right_diag && curr_piece_diag_right != null){
                all_moves.add((char)(curr_col + 1) + Integer.toString(curr_row+1));
            }
        }
        return all_moves;
    }
}