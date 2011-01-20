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
import es.dagaren.gladiator.representation.Piece;
import es.dagaren.gladiator.representation.Position;

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
   
   
   public int evaluate(Position position)
   {
      Colour turn = position.getTurn();
      int score = 0;
      
      score += evaluateMaterial(position);
      score += 0.5 * evaluateMobility(position);
      
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
      //TODO implementar
      int score = 0;
      
      return score;
   }
   
}
