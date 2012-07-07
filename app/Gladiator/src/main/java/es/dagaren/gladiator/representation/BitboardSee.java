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

/**
 * @author dagaren
 *
 */
public class BitboardSee
{
   private static int gain[] = new int[32];
   private static int[] pieceValues = new int[6];
   
   static
   {
      pieceValues[GenericPiece.PAWN.index]   = 100;
      pieceValues[GenericPiece.BISHOP.index] = 325;
      pieceValues[GenericPiece.KNIGHT.index] = 325;
      pieceValues[GenericPiece.ROOK.index]   = 500;
      pieceValues[GenericPiece.QUEEN.index]  = 1000;
      pieceValues[GenericPiece.KING.index]   = 10000;
   }
   
   public static int getValue(BitboardPosition position, Square sourceSquare, Square targetSquare, GenericPiece capture)
   {
      int depth = 0;
      
      long bbXRayPieces      = position.bbPiecesOccupation[Piece.BLACK_BISHOP.index] |
                               position.bbPiecesOccupation[Piece.BLACK_ROOK.index]   |
                               position.bbPiecesOccupation[Piece.BLACK_QUEEN.index]  |
                               position.bbPiecesOccupation[Piece.WHITE_BISHOP.index] |
                               position.bbPiecesOccupation[Piece.WHITE_ROOK.index]   |
                               position.bbPiecesOccupation[Piece.WHITE_QUEEN.index];
      long bbNextPiece       = position.bbSquare[sourceSquare.index];
      int  nextPieceIndex    = sourceSquare.index;
      GenericPiece nextPiece = position.pieceInSquare[sourceSquare.index].genericPiece;
      Colour nextPieceColour = position.pieceInSquare[sourceSquare.index].colour;
      long bbInvolvedPieces  = position.bbAttackerToSquare[targetSquare.index];

      //con el valor de la pieza que se encuentra en la casilla
      gain[depth] = pieceValues[capture.index];
      
      do
      {
         //Se incrementa la profundidad
         depth++;
         
         gain[depth] = pieceValues[nextPiece.index] - gain[depth - 1];
         
         if(Math.max(-gain[depth - 1], gain[depth]) < 0) break;
         
         //Se quita del bit de casillas involucradas la pieza tratada
         bbInvolvedPieces ^= bbNextPiece; 
         bbXRayPieces     ^= bbNextPiece;
                  
         //Despues de quitar la pieza que se esta teniendo en cuenta
         //del bitboard de piezas involucradas, se añaden las posibles
         //piezas que formaban rayos x con ella en la dirección a la
         //casilla objetivo.
         bbInvolvedPieces |= bbXRayPieces & 
                             position.bbAttackerToSquare[nextPieceIndex] &
                             position.bbFullConnection[targetSquare.index][nextPieceIndex];
        
         nextPieceColour = nextPieceColour.opposite();
         //Se calcula la siguiente pieza a tener en cuenta
         for(GenericPiece piece : GenericPiece.genericPieces)
         {
            bbNextPiece = bbInvolvedPieces & position.bbPiecesOccupation[Piece.get(piece, nextPieceColour).index];
            if(bbNextPiece != 0)
            {
               bbNextPiece    &= -bbNextPiece;
               nextPiece       = piece;
               nextPieceIndex  = Long.numberOfTrailingZeros(bbNextPiece);
               
               break;
            }
         }

      }
      while(bbNextPiece != 0);
      
      
      while(--depth != 0)
      {
         gain[depth - 1] = - Math.max(-gain[depth - 1], gain[depth]);
      }
      
      return gain[0];
   }
   
}
