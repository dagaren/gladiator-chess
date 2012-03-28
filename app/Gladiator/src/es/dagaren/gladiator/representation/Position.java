/*
 * Copyright (c) 2008-2011, David Garcinuño Enríquez <dagaren@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.dagaren.gladiator.representation;

import java.util.List;
import java.util.Map;

/**
 * @author dagaren
 *
 */
public interface Position {
   
   public Colour getTurn();
   public void setTurn(Colour colour);
   
   
   public void setMoveIndex(int moveIndex);
   public int getMoveIndex();
   
   
   public void setEnPassantSquare(Square square);
   public Square getEnPassantSquare();
   
   public void setFiftyMovesRuleIndex(int movesIndex);
   public int getFiftyMovesRuleIndex();
   

   public void setCastlingLong(Colour colour, boolean enabled);
   public boolean getCastlingLong(Colour colour);
   public void setCastlingShort(Colour colour, boolean enabled);
   public boolean getCastlingShort(Colour colour);
   
   public List<Movement> getMovements();
   public void getMoves(List<Movement> movesList);
   
   
   public void putGenericPiece(GenericPiece gp,Colour col, Square sq);
   public void putPiece(Piece pie, Square sq);
   public void removePiece(Square sq);
   
   public boolean doMove(Movement mov);
   public boolean undoMove(Movement mov);
   
   public boolean isInCheck(Colour col);
   public boolean isCheckmate();
   public boolean isStalemate();
   
   public void setInitialPosition();
   
   public void clean();
   
   public boolean hasPiece(Piece pie, Square sq);
   public boolean hasGenericPiece(GenericPiece gp, Colour col, Square sq);
   public boolean hasPiece(Piece pie, Rank ran);
   public boolean hasGenericPiece(GenericPiece gp, Colour col, Rank ran);
   public boolean hasPiece(Piece pie, File fil);
   public boolean hasPiece(GenericPiece fic,Colour col, File fil);
   
   public int getNumPieces(GenericPiece gp, Colour col);
   public int getNumPieces(Piece pie);
   
   public Square[] getPiecesSquares(Colour colour);
   
   public Piece getPieceInSquare(Square sq);
   public GenericPiece getGenericPieceInSquare(Square sq);
   public Colour getPieceColour(Square sq);
   
   public boolean loadFen(String fenPosition);
   public String toFen();

   public boolean isValidMove(Movement mov);
   public boolean isValidPosition();
   
   public Position getCopy();
   public int getMobility(Colour colour);
   
   public ZobristKey getZobristKey();
   
   public Map<Long,Integer> getPositionsHash();
   
}
