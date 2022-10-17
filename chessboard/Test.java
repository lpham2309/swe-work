import java.util.ArrayList;

public class Test {

    // Run "java -ea Test" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)

    public static void test_pawn_moves() {
		Board b = Board.theBoard();
		Piece.registerPiece(new PawnFactory());
		Piece bp = Piece.createPiece("bp");
		Piece wp = Piece.createPiece("wp");
		b.addPiece(bp, "d7");
		b.addPiece(wp, "c2");

		b.movePiece("d7", "a8");

		assert b.getPiece("d7") == bp;
		assert b.getPiece("c2") == wp;

		assert bp.moves(Board.theBoard(), "d7").contains("d6");
		assert bp.moves(Board.theBoard(), "d7").contains("d5");
		assert bp.moves(Board.theBoard(), "d7").size() == 2;
		assert bp.moves(Board.theBoard(), "c2").size() == 2;

		b.clear();
    }

	public static void test_rookie_moves() {
		Board b = Board.theBoard();
		Piece.registerPiece(new RookFactory());
		Piece br = Piece.createPiece("br");
		b.addPiece(br, "a8");
		b.addPiece(br, "f5");
		b.addPiece(br, "b5");

		assert b.getPiece("a8") == br;
		assert b.getPiece("a5") == br;
		assert br.moves(b, "a8").contains("b8");
		assert br.moves(b, "a8").contains("a7");
		assert br.moves(b, "a8").size() == 14;
		b.clear();
	}

	public static void test_knight_moves(){
		Board b = Board.theBoard();
		Piece.registerPiece(new KnightFactory());
		Piece bn = Piece.createPiece("bn");
//		Piece wn = Piece.createPiece("wn");
		b.addPiece(bn, "a8");
//		b.addPiece(wn, "e5");
		System.out.println(bn.moves(b, "a8"));
//		assert b.getPiece("f3") == bn;
//		assert bn.moves(b, "f3").size() == 8;

		b.clear();
	}

	public static void test_bishop_moves(){
		Board b = Board.theBoard();
		Piece.registerPiece(new BishopFactory());
		Piece bb = Piece.createPiece("bb");
		b.addPiece(bb, "h6");
		b.addPiece(bb, "e4");
		b.addPiece(bb, "c6");
//		System.out.println(bb.moves(b, "c6"));
		assert b.getPiece("h6") == bb;
		assert bb.moves(b, "h6").size() == 7;

		b.movePiece("h6", "h8");
		assert bb.moves(b, "h8").size() == 7;

		b.clear();
	}

	public static void test_queen_moves(){
		Board b = Board.theBoard();
		Piece.registerPiece(new QueenFactory());
		Piece p = Piece.createPiece("bq");
		Piece wp = Piece.createPiece("wq");
		b.addPiece(p, "c3");
		b.addPiece(wp, "e5");
		assert b.getPiece("c3") == p;
		assert p.moves(b, "c3") != null;

		b.clear();
	}

	public static void test_king_moves(){
		Board b = Board.theBoard();
		Piece.registerPiece(new KingFactory());
		Piece p = Piece.createPiece("bk");
		Piece wk = Piece.createPiece("wk");
		b.addPiece(p, "e6");
		b.addPiece(p, "e5");
		System.out.println(p.moves(b, "e6"));
		assert b.getPiece("e6") == p;
		assert p.moves(b, "e6").size() == 3;

		b.clear();
	}

	public static void test_register_event_actions(){
		Board b = Board.theBoard();
		b.clear();
		b.registerListener(new Logger());
		b.removeListener(new Logger());
		assert b.listeners.isEmpty();
		b.registerListener(new Logger());
		b.removeAllListeners();
		assert b.listeners.isEmpty();
	}

	public static void test_pawn_color(){
		Board b = Board.theBoard();
		Piece.registerPiece(new PawnFactory());
		Piece bp = Piece.createPiece("bp");
		Piece wp = Piece.createPiece("wp");
		b.addPiece(bp, "d7");
		b.addPiece(bp, "d2");
		b.addPiece(bp, "e7");
		b.addPiece(wp, "c5");
		b.addPiece(bp, "d6");

		assert bp.moves(b, "d7").size() == bp.moves(b, "d2").size();
		assert bp.moves(b, "d7").contains("d6");
		assert bp.moves(b, "d7").contains("d5");
		assert bp.moves(b, "d2").contains("d3");
		assert bp.moves(b, "d2").contains("d4");
		b.movePiece("d7", "d5");
		b.movePiece("d2", "d4");
		b.movePiece("e7", "e5");
		assert bp.moves(b, "d5").size() == 0;
		assert bp.moves(b, "d4").size() == 0;
		assert bp.moves(b, "e5").contains("e4");
		assert bp.moves(b, "e5").contains("d4");

		b.clear();

	}

	public static void test_double_rookie_moves() {
		Board b = Board.theBoard();
		Piece.registerPiece(new RookFactory());
		Piece br = Piece.createPiece("br");
		b.addPiece(br, "c2");
		b.addPiece(br, "f2");

		System.out.println(br.moves(b, "c2"));
//		System.out.println(br.moves(b, "b5"));

		b.clear();
	}

	public static void test_king_surround(){
		Board b = Board.theBoard();
		Piece.registerPiece(new KingFactory());
		Piece.registerPiece(new PawnFactory());

		Piece bk = Piece.createPiece("bk");
		Piece bp = Piece.createPiece("bp");
		Piece wp = Piece.createPiece("wp");
		b.addPiece(bk, "f5");
		b.addPiece(bp, "e5");
		b.addPiece(wp, "g4");
		System.out.println(bk.moves(b, "f5"));
//		assert b.getPiece("e6") == p;
//		assert p.moves(b, "e6").size() > 0;

		b.clear();
	}

	public static void test_bishop_surround(){
		Board b = Board.theBoard();
		Piece.registerPiece(new BishopFactory());

		Piece bb = Piece.createPiece("bb");
		Piece wb = Piece.createPiece("wb");
		Piece bb2 = Piece.createPiece("bb");
		b.addPiece(bb, "e3");
		b.addPiece(wb, "c5");
		b.addPiece(bb2, "f4");

		assert b.getPiece("e3") != null;
		assert b.getPiece("c5") != null;
		assert b.getPiece("f4") != null;
		b.clear();
	}

	public static void test_queen_surround(){
		Board b = Board.theBoard();
		Piece.registerPiece(new QueenFactory());
		Piece.registerPiece(new PawnFactory());
		Piece bp = Piece.createPiece("bp");
		Piece wp = Piece.createPiece("wq");
		Piece bq = Piece.createPiece("bq");
		b.addPiece(bp, "d2");
		b.addPiece(wp, "f6");
		b.addPiece(bq, "d4");

		System.out.println(bq.moves(b, "d4"));
		System.out.println(b.getPiece("d2"));
//		assert b.getPiece("c3") == bp;
//		assert bp.moves(b, "c3") != null;
//		b.clear();
	}
	public static void main(String[] args) {
//		test_pawn_moves();
//		test_rookie_moves();
		test_knight_moves();
//		test_bishop_moves();
//		test_queen_moves();
//		test_king_moves();
//		test_register_event_actions();
//		test_pawn_color();
//		test_double_rookie_moves();
//		test_king_surround();
//		test_bishop_surround();
//		test_queen_surround();
    }

}