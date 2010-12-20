package es.dagaren.gladiator.representation;

public class IrreversibleState {
	public boolean[] longCastling = new boolean[2];
	public boolean[] shortCastling = new boolean[2];
	
	public Square enPassantSquare = null;
	
	public int fiftyMovesCounter = 0;
}
