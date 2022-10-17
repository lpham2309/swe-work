import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chess {
	public static Map<String, String> parse_file(String file_name, String regex_pattern) {
		Map<String, String> mapper = new HashMap<>();
		try {
			FileReader file = new FileReader(file_name);
			BufferedReader file_input = new BufferedReader(file);
			String curr_line;
			try {
				while((curr_line=file_input.readLine()) != null){
					Pattern pattern =  Pattern.compile(regex_pattern);
					Matcher matcher = pattern.matcher(curr_line);

					if(matcher.find()) {
						mapper.put(matcher.group().substring(0, 2),
								matcher.group().substring(3));
					}
				}
			} catch (IOException error) {
				System.out.println(error);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Exception: " + e);
		}
		return mapper;
	}

    public static void main(String[] args) {
	if (args.length != 2) {
	    System.out.println("Usage: java Chess layout moves");
	}
	Piece.registerPiece(new KingFactory());
	Piece.registerPiece(new QueenFactory());
	Piece.registerPiece(new KnightFactory());
	Piece.registerPiece(new BishopFactory());
	Piece.registerPiece(new RookFactory());
	Piece.registerPiece(new PawnFactory());
	Board.theBoard().registerListener(new Logger());
	// args[0] is the layout file name
	// args[1] is the moves file name
	// Put your code to read the layout file and moves files
	// here.
	Map<String, String> layout = parse_file(args[0], "([a-h][1-8])=([w | b])([k, q, n, b, r, p])");
	Map<String, String> moves = parse_file(args[1], "(\\w+)-(\\w+)");

	for(Map.Entry<String, String> entry : layout.entrySet()) {
		String piece_loc = entry.getKey();
		String piece_name = entry.getValue();

		Piece curr_piece = Piece.createPiece(piece_name);
		Board.theBoard().addPiece(curr_piece, piece_loc);
	}

	for(Map.Entry<String, String> entry_loc : moves.entrySet()) {
		String piece_current_loc = entry_loc.getKey();
		String piece_new_loc = entry_loc.getValue();
		Board.theBoard().movePiece(piece_current_loc, piece_new_loc);
	}

	System.out.println("Final board:");
	Board.theBoard().iterate(new BoardPrinter());
    }
}