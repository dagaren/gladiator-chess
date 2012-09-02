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

import java.util.HashMap;
import java.util.Map;

/**
 * @author dagaren
 *
 */
public class Piece
{  
   public static final Piece WHITE_PAWN = new Piece(0, GenericPiece.PAWN, Colour.WHITE, "WHITE_PAWN");
   public static final Piece BLACK_PAWN = new Piece(1, GenericPiece.PAWN, Colour.BLACK, "BLACK_PAWN");
   public static final Piece WHITE_ROOK = new Piece(2, GenericPiece.ROOK, Colour.WHITE, "WHITE_ROOK");
   public static final Piece BLACK_ROOK = new Piece(3, GenericPiece.ROOK, Colour.BLACK, "BLACK_ROOK");
   public static final Piece WHITE_KNIGHT = new Piece(4, GenericPiece.KNIGHT, Colour.WHITE, "WHITE_KNIGHT");
   public static final Piece BLACK_KNIGHT = new Piece(5, GenericPiece.KNIGHT, Colour.BLACK, "BLACK_KNIGHT");
   public static final Piece WHITE_BISHOP = new Piece(6, GenericPiece.BISHOP, Colour.WHITE, "WHITE_BISHOP");
   public static final Piece BLACK_BISHOP = new Piece(7, GenericPiece.BISHOP, Colour.BLACK, "BLACK_BISHOP");
   public static final Piece WHITE_QUEEN = new Piece(8, GenericPiece.QUEEN, Colour.WHITE, "WHITE_QUEEN");
   public static final Piece BLACK_QUEEN = new Piece(9, GenericPiece.QUEEN, Colour.BLACK, "BLACK_QUEEN");
   public static final Piece WHITE_KING = new Piece(10, GenericPiece.KING, Colour.WHITE, "WHITE_KING");
   public static final Piece BLACK_KING = new Piece(11, GenericPiece.KING, Colour.BLACK, "BLACK_KING");
   
   public int index;
   public Colour colour;
   public GenericPiece genericPiece;
   
   protected static Piece[] pieces;
   protected static String[] piecesNames;
   protected static Map<String, Piece> piecesMap;
   protected static Piece[][] piecesTable;
   
   private Piece(int index, GenericPiece gp, Colour c, String name)
   {
      this.index = index;
      this.genericPiece = gp;
      this.colour = c;
      
      if(pieces == null)
      {
         pieces = new Piece[12];
         piecesNames = new String[12];
         piecesMap = new HashMap<String, Piece>();
         piecesTable = new Piece[6][2];
      }
      
      pieces[index] = this;
      piecesNames[index] = name;
      piecesMap.put(name, this);
      piecesTable[gp.index][c.index] = this;
   }
   
   public static Piece get(GenericPiece f,Colour c)
   {
      return piecesTable[f.index][c.index];
   }
   
   public String toFenString()
   {
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
   
   public static Piece valueOf(String name)
   {
      if(piecesMap.containsKey(name))
         return piecesMap.get(name);
      else
         return null;
   }
   
   public String name()
   {
      return piecesNames[this.index];
   }
   
   public String toString()
   {
      return name();
   }
   
}
