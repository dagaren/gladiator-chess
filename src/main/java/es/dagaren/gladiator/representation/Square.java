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
public class Square
{
   public static final Square a1 = new Square(0, "a1");
   public static final Square b1 = new Square(1, "b1");
   public static final Square c1 = new Square(2, "c1");
   public static final Square d1 = new Square(3, "d1");
   public static final Square e1 = new Square(4, "e1");
   public static final Square f1 = new Square(5, "f1");
   public static final Square g1 = new Square(6, "g1");
   public static final Square h1 = new Square(7, "h1");
   public static final Square a2 = new Square(8, "a2");
   public static final Square b2 = new Square(9, "b2");
   public static final Square c2 = new Square(10, "c2");
   public static final Square d2 = new Square(11, "d2");
   public static final Square e2 = new Square(12, "e2");
   public static final Square f2 = new Square(13, "f2");
   public static final Square g2 = new Square(14, "g2");
   public static final Square h2 = new Square(15, "h2");
   public static final Square a3 = new Square(16, "a3");
   public static final Square b3 = new Square(17, "b3");
   public static final Square c3 = new Square(18, "c3");
   public static final Square d3 = new Square(19, "d3");
   public static final Square e3 = new Square(20, "e3");
   public static final Square f3 = new Square(21, "f3");
   public static final Square g3 = new Square(22, "g3");
   public static final Square h3 = new Square(23, "h3");
   public static final Square a4 = new Square(24, "a4");
   public static final Square b4 = new Square(25, "b4");
   public static final Square c4 = new Square(26, "c4");
   public static final Square d4 = new Square(27, "d4");
   public static final Square e4 = new Square(28, "e4");
   public static final Square f4 = new Square(29, "f4");
   public static final Square g4 = new Square(30, "g4");
   public static final Square h4 = new Square(31, "h4");
   public static final Square a5 = new Square(32, "a5");
   public static final Square b5 = new Square(33, "b5");
   public static final Square c5 = new Square(34, "c5");
   public static final Square d5 = new Square(35, "d5");
   public static final Square e5 = new Square(36, "e5");
   public static final Square f5 = new Square(37, "f5");
   public static final Square g5 = new Square(38, "g5");
   public static final Square h5 = new Square(39, "h5");
   public static final Square a6 = new Square(40, "a6");
   public static final Square b6 = new Square(41, "b6");
   public static final Square c6 = new Square(42, "c6");
   public static final Square d6 = new Square(43, "d6");
   public static final Square e6 = new Square(44, "e6");
   public static final Square f6 = new Square(45, "f6");
   public static final Square g6 = new Square(46, "g6");
   public static final Square h6 = new Square(47, "h6");
   public static final Square a7 = new Square(48, "a7");
   public static final Square b7 = new Square(49, "b7");
   public static final Square c7 = new Square(50, "c7");
   public static final Square d7 = new Square(51, "d7");
   public static final Square e7 = new Square(52, "e7");
   public static final Square f7 = new Square(53, "f7");
   public static final Square g7 = new Square(54, "g7");
   public static final Square h7 = new Square(55, "h7");
   public static final Square a8 = new Square(56, "a8");
   public static final Square b8 = new Square(57, "b8");
   public static final Square c8 = new Square(58, "c8");
   public static final Square d8 = new Square(59, "d8");
   public static final Square e8 = new Square(60, "e8");
   public static final Square f8 = new Square(61, "f8");
   public static final Square g8 = new Square(62, "g8");
   public static final Square h8 = new Square(63, "h8");
   
   public int index;
   
   protected static Square[] squares;
   protected static Square[] squaresMirror;
   protected static String[] squaresNames;
   protected static Map<String, Square> squaresMap;
   
   private Square(int index, String name)
   {
      this.index = index;
      
      if(squares == null)
      {
         squares = new Square[64];
         squaresMirror = new Square[64];
         squaresNames = new String[64];
         squaresMap = new HashMap<String, Square>();
      }
      
      squares[index] = this;
      squaresMirror[(index + 56) - ((index >>> 3) << 4)] = this;
      squaresNames[index] = name;
      squaresMap.put(name, this);
   }
   
   public Rank getRank()
   {
      return Rank.fromIndex(index >>> 3);
   }
   
   public File getFile()
   {
      return File.fromIndex(index & 7);
   }
   
   public Square getNext()
   {
	   if(index >= Square.h8.index || index < Square.a1.index)
		   return null;
	   
	   return Square.fromIndex(index + 1);
   }
   
   public Square getPrevious()
   {	
	   if(index <= Square.a1.index || index > Square.h8.index)
		   return null;
	   
	   return Square.fromIndex(index - 1);
   }
  
   public Square getNextInRank()
   {
	   File file = getFile();
       if(file.index <= File.a.index || file.index >= File.h.index)
           return null;
       
       return Square.fromRankAndFile(getRank().index, file.index + 1);
   }
   
   public Square getPreviousInRank()
   {
      File file = getFile();
      if(file.index <= File.a.index || file.index > File.h.index)
          return null;
      
      return Square.fromRankAndFile(getRank().index, file.index - 1);
   }
   
   public Square getNextInFile()
   {
      Rank rank = getRank();   
      if(rank.index < Rank._1.index || rank.index >= Rank._8.index)
          return null;
      
      return Square.fromRankAndFile(rank.index + 1, getFile().index);
   }
   
   public Square getPreviousInFile()
   {   
      Rank rank = getRank();   
      if(rank.index <= Rank._1.index || rank.index > Rank._8.index)
         return null;
       
      return Square.fromRankAndFile(rank.index - 1, getFile().index);
   }
   
   
   public static Square fromRankAndFile(int rank,int file)
   {
      return squares[(rank << 3) + file];
   }
   
   public static Square fromIndex(int index)
   {
      return squares[index];
   }
   
   public Square mirror()
   {
      return squaresMirror[this.index];
   }
   
   public static Square valueOf(String name)
   {
      if(squaresMap.containsKey(name))
         return squaresMap.get(name);
      else
         return null;
   }
   
   public String name()
   {
      return squaresNames[this.index];
   }
   
   public String toString()
   {
      return name();
   }
}
