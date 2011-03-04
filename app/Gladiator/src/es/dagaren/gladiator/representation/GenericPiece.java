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
public class GenericPiece
{   
   public static final GenericPiece PAWN = new GenericPiece(0, 100, "PAWN");
   public static final GenericPiece ROOK = new GenericPiece(1, 500, "ROOK");
   public static final GenericPiece KNIGHT = new GenericPiece(2, 300, "KNIGHT");
   public static final GenericPiece BISHOP = new GenericPiece(3, 300, "BISHOP");
   public static final GenericPiece QUEEN = new GenericPiece(4, 900, "QUEEN");
   public static final GenericPiece KING = new GenericPiece(5, 10000, "KING");
   
   public int index;
   public int value;
   
   protected static GenericPiece[] genericPieces;
   protected static String[] genericPiecesNames;
   protected static Map<String, GenericPiece> genericPiecesMap;
   
   
   private GenericPiece(int index, int value, String name)
   {
      this.index = index;
      this.value = value;
      
      if(genericPieces == null)
      {
         genericPieces = new GenericPiece[6];
         genericPiecesNames = new String[6];
         genericPiecesMap = new HashMap<String, GenericPiece>();
      }
      
      genericPieces[index] = this;
      genericPiecesNames[index] = name;
      genericPiecesMap.put(name, this);
   }
   
   public static GenericPiece valueOf(String name)
   {
      if(genericPiecesMap.containsKey(name))
         return genericPiecesMap.get(name);
      else
         return null;
   }
   
   public String name()
   {
      return genericPiecesNames[this.index];
   }
   
   public String toString()
   {
      return name();
   }
}
