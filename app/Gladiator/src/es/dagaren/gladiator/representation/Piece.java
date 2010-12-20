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
public enum Piece
{
   WHITE_PAWN(0, GenericPiece.PAWN, Colour.WHITE),
   BLACK_PAWN(1, GenericPiece.PAWN, Colour.BLACK),
   WHITE_ROOK(2, GenericPiece.ROOK, Colour.WHITE),
   BLACK_ROOK(3, GenericPiece.ROOK, Colour.BLACK),
   WHITE_KNIGHT(4, GenericPiece.KNIGHT, Colour.WHITE),
   BLACK_KNIGHT(5, GenericPiece.KNIGHT, Colour.BLACK),
   WHITE_BISHOP(6, GenericPiece.BISHOP, Colour.WHITE),
   BLACK_BISHOP(7, GenericPiece.BISHOP, Colour.BLACK),
   WHITE_QUEEN(8, GenericPiece.QUEEN, Colour.WHITE),
   BLACK_QUEEN(9, GenericPiece.QUEEN, Colour.BLACK),
   WHITE_KING(10, GenericPiece.KING, Colour.WHITE),
   BLACK_KING(11, GenericPiece.KING, Colour.BLACK);
   
   public int index;
   public Colour colour;
   public GenericPiece genericPiece;
   
   Piece(int index, GenericPiece gp, Colour c)
   {
      this.index = index;
      this.genericPiece = gp;
      this.colour = c;
   }
   
   public static Piece get(GenericPiece f,Colour c){
      //TODO cambiar implementacion para que lo devuelva sin realizar búsqueda
      for(Piece pieza: values()){
         if(pieza.genericPiece == f && pieza.colour == c)
            return pieza;
      }
      return null;
   }
   
   public String toFenString(){
      switch(index){
         case 0:
            return "P";
         case 1:
            return "p";
         case 2:
            return "R";
         case 3:
            return "r";
         case 4:
            return "N";
         case 5:
            return "n";
         case 6:
            return "B";
         case 7:
            return "b";
         case 8:
            return "Q";
         case 9:
            return "q";
         case 10:
            return "K";
         case 11:
            return "k";
         default:
            return "";  
      }
   }
   
}
