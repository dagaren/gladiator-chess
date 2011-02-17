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
package es.dagaren.gladiator.evaluation;

import es.dagaren.gladiator.representation.Colour;
import es.dagaren.gladiator.representation.GenericPiece;
import es.dagaren.gladiator.representation.Piece;
import es.dagaren.gladiator.representation.Position;
import es.dagaren.gladiator.representation.Square;

/**
 * @author dagaren
 *
 */
public class Evaluator
{
   //VALOR DE LAS PIEZAS
   public int PAWN_SCORE   = 100;
   public int KNIGHT_SCORE = 300;
   public int BISHOP_SCORE = 300;
   public int ROOK_SCORE   = 500;
   public int QUEEN_SCORE  = 900;
   
   public final int[] pawnPieceSquare = {  
         0,   0,   0,   0,   0,   0,   0,   0,
         5,  10,  10, -20, -20,  10,  10,  10,
         5,  -5, -10,   0,   0, -10,  -5,   5,
         0,   0,   0,  20,  20,   0,   0,   0,
         5,   5,  10,  25,  25,  10,   5,   5,
        10,  10,  20,  30,  30,  20,  10,  10,
        50,  50,  50,  50,  50,  50,  50,  50,
         0,   0,   0,   0,   0,   0,   0,   0
                                        };
   
   public final int[] knightPieceSquare = {  
       -50, -40, -30, -30, -30, -30, -40, -50,
       -40, -20,   0,   0,   0,   0, -20, -40,
       -30,   0,  10,  15,  15,  10,   0, -30,
       -30,   5,  15,  20,  20,  15,   5, -30,
       -30,   5,  15,  20,  20,  15,   5, -30,
       -30,   0,  10,  15,  15,  10,   0, -30,
       -40, -20,   0,   0,   0,   0, -20, -40,
       -50, -40, -30, -30, -30, -30, -40, -50
                                        };
   
   public final int[] bishopPieceSquare = {  
       -20, -10, -10, -10, -10, -10, -10, -20,
       -10,   5,   0,   0,   0,   0,   5, -10,
       -10,  10,  10,  10,  10,  10,  10, -10,
       -10,   0,  10,  10,  10,  10,   0, -10,
       -10,   5,   5,  10,  10,   5,   5, -10,
       -10,   0,   5,  10,  10,   5,   0, -10,
       -10,   0,   0,   0,   0,   0,   0, -10,
       -20, -10, -10, -10, -10, -10, -10, -20
                                        };
   
   public final int[] rookPieceSquare = {  
         0,   0,   0,   5,   5,   0,   0,   0,
        -5,   0,   0,   0,   0,   0,   0,  -5,
        -5,   0,   0,   0,   0,   0,   0,  -5,
        -5,   0,   0,   0,   0,   0,   0,  -5,
        -5,   0,   0,   0,   0,   0,   0,  -5,
        -5,   0,   0,   0,   0,   0,   0,  -5,
         5,  10,  10,  10,  10,  10,  10,   5,
         0,   0,   0,   0,   0,   0,   0,   0
                                          };
   
   public final int[] queenPieceSquare = {  
       -20, -10, -10,  -5,  -5, -10, -10, -20,
       -10,   0,   5,   0,   0,   0,   0, -10,
       -10,   5,   5,   5,   5,   5,   0, -10,
         0,   0,   5,   5,   5,   5,   0,  -5,
        -5,   0,   5,   5,   5,   5,   0,  -5,
       -10,   0,   5,   5,   5,   5,   0, -10,
       -10,   0,   0,   0,   0,   0,   0, -10,
       -20, -10, -10,  -5,  -5, -10, -10, -20
                                          };
   
   public final int[] kingPieceSquare = {  
         -30, -40, -40, -50, -50, -40, -40, -30,
         -30, -40, -40, -50, -50, -40, -40, -30,
         -30, -40, -40, -50, -50, -40, -40, -30,
         -30, -40, -40, -50, -50, -40, -40, -30,
         -20, -30, -30, -40, -40, -30, -30, -20,
         -10, -20, -20, -20, -20, -20, -20, -10,
          20,  20,   0,   0,   0,   0,  10,  20,
          20,  30,  10,   0,   0,  10,  30,  20
                                            };
   
   public final int[] endKingPieceSquare = {  
         -50, -30, -30, -30, -30, -30, -30, -50,
         -30, -30,   0,   0,   0,   0, -30, -30,
         -30, -10, -20,  30,  30,  20, -10, -30,
         -30, -10,  30,  40,  40,  30, -10, -30,
         -30, -10,  30,  40,  40,  30, -10, -30,
         -30, -10,  20,  30,  30,  20, -10, -30,
         -30, -20, -10,   0,   0, -10, -20, -30,
         -50, -40, -30, -20, -20, -30, -40, -50
                                            };
   
   
   public int evaluate(Position position)
   {
      Colour turn = position.getTurn();
      int score = 0;
      
      score += evaluateMaterial(position);
      score += 0.2 * evaluateMobility(position);
      score += evaluatePieceSquareTables(position);
      
      if(turn == Colour.WHITE)
         return score;
      else
         return -score;
   }
   
   private int evaluateMaterial(Position position)
   {
      int score = 0;
      
      score += position.getNumPieces(Piece.WHITE_PAWN)   * PAWN_SCORE;
      score += position.getNumPieces(Piece.WHITE_ROOK)   * ROOK_SCORE;
      score += position.getNumPieces(Piece.WHITE_BISHOP) * BISHOP_SCORE;
      score += position.getNumPieces(Piece.WHITE_KNIGHT) * KNIGHT_SCORE;
      score += position.getNumPieces(Piece.WHITE_QUEEN)  * QUEEN_SCORE;
      
      score -= position.getNumPieces(Piece.BLACK_PAWN)   * PAWN_SCORE;
      score -= position.getNumPieces(Piece.BLACK_ROOK)   * ROOK_SCORE;
      score -= position.getNumPieces(Piece.BLACK_BISHOP) * BISHOP_SCORE;
      score -= position.getNumPieces(Piece.BLACK_KNIGHT) * KNIGHT_SCORE;
      score -= position.getNumPieces(Piece.BLACK_QUEEN)  * QUEEN_SCORE;
      
      return score;
   }
   
   private int evaluateMobility(Position position)
   {
      int score = 0;
      
      score += position.getMobility(Colour.WHITE);
      
      score -= position.getMobility(Colour.BLACK);
      
      return score;
   }
   
   private int evaluatePieceSquareTables(Position position)
   {
      int score = 0;
      
      Square[] whitePieces = position.getPiecesSquares(Colour.WHITE);
      for(Square s: whitePieces)
      {
         GenericPiece p = position.getPieceInSquare(s).genericPiece;
         switch(p)
         {
            case PAWN:
               score += pawnPieceSquare[s.index];
               break;
            case KNIGHT:
               score += knightPieceSquare[s.index];
               break;
            case BISHOP:
               score += bishopPieceSquare[s.index];
               break;
            case ROOK:
               score += rookPieceSquare[s.index];
               break;
            case QUEEN:
               score += queenPieceSquare[s.index];
               break;
            case KING:
               score += kingPieceSquare[s.index];
               break;
         }
      }
      
      Square[] blackPieces = position.getPiecesSquares(Colour.BLACK);
      for(Square s: blackPieces)
      {
         GenericPiece p = position.getPieceInSquare(s).genericPiece;
         switch(p)
         {
            case PAWN:
               score -= pawnPieceSquare[s.mirror().index];
               break;
            case KNIGHT:
               score -= knightPieceSquare[s.mirror().index];
               break;
            case BISHOP:
               score -= bishopPieceSquare[s.mirror().index];
               break;
            case ROOK:
               score -= rookPieceSquare[s.mirror().index];
               break;
            case QUEEN:
               score -= queenPieceSquare[s.mirror().index];
               break;
            case KING:
               score -= kingPieceSquare[s.mirror().index];
               break;
         }
      }
      
      return score;
   }
}
