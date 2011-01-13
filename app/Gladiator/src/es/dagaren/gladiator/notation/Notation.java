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
package es.dagaren.gladiator.notation;

import es.dagaren.gladiator.representation.Colour;
import es.dagaren.gladiator.representation.GenericPiece;
import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.representation.Piece;
import es.dagaren.gladiator.representation.Square;

/**
 * @author dagaren
 *
 */
public class Notation
{
 
   /**
    * Parsea una cadena y lo devuelve como movimiento.
    * De momento solo detecta movimientos en notación de coordenadas
    * //TODO Ampliar para soportar más tipos de notación
    * @param moveString
    * @return
    */
   public static Movement parseMove(String moveString)
   {
      Movement move = new Movement();

      try
      {
         int index = 0;
         
         int sourceFile = moveString.charAt(index++);
         if(!(sourceFile >= 'a' && sourceFile <= 'h'))
            return null;
         int sourceRank = moveString.charAt(index++);
         if(!(sourceRank >= '1' && sourceRank <= '8'))
            return null;
         
         move.setSource(Square.fromRankAndFile(sourceRank - '1', sourceFile - 'a'));
         
         int destinationFile = moveString.charAt(index++);
         if(!(destinationFile >= 'a' && destinationFile <= 'h'))
            return null;
         int destinationRank = moveString.charAt(index++);
         if(!(destinationRank >= '1' && destinationRank <= '8'))
            return null;
         
         move.setDestination(Square.fromRankAndFile(destinationRank - '1', destinationFile - 'a'));
         
         if(moveString.length() > index)
         {
            int promotion = moveString.charAt(index++);
            switch(promotion)
            {
               case 'q':
                  move.setPromotionPiece(Piece.WHITE_QUEEN);
                  break;
               case 'b':
                  move.setPromotionPiece(Piece.WHITE_BISHOP);
                  break;
               case 'n':
                  move.setPromotionPiece(Piece.WHITE_KNIGHT);
                  break;
               case 'r':
                  move.setPromotionPiece(Piece.WHITE_ROOK);
                  break;
               default:
                  return null;
            }
         }
      }
      catch(Exception ex)
      {
         return null;
      }
      
      return move;
   }
   
   
   /**
    * Convierte un movimiento a una cadena en notación de coordenadas
    * //TODO Ampliar para soportar más tipos de notación
    * @param moveString
    * @return
    */
   public static String toString(Movement move)
   {
    
      String moveString = "";
      
      Square sourceSquare = move.getSource();
      if(sourceSquare == null)
         return "";
      moveString += sourceSquare.name();
      
      Square destinationSquare = move.getDestination();
      if(destinationSquare == null)
         return "";
      moveString += destinationSquare.name();
      
      if(move.getPromotionPiece() != null)
      {
         GenericPiece gp = move.getPromotionPiece().genericPiece;
         if(gp == GenericPiece.QUEEN)
            moveString += "q";
         else if(gp == GenericPiece.ROOK)
            moveString += "r";
         else if(gp == GenericPiece.KNIGHT)
            moveString += "n";
         else if(gp == GenericPiece.BISHOP)
            moveString += "b";
         else
            return "";
      }
      
      return moveString;
   }
}
