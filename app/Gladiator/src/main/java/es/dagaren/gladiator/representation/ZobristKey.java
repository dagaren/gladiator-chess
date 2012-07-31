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

import java.util.Random;

/**
 * @author dagaren
 *
 */
public class ZobristKey
{
   private static final long[][]   pieceRandoms = new long[12][64];
   private static final long[][]    castlingRandoms = new long[2][2];
   private static final long[]        turnRandoms = new long[2];
   private static final long[]   enPassantRandoms = new long[8];
   
   private long key;
   
   static
   {
      Random r = new Random();
      
      for(int i = 0; i < 12; i++)
      {
         for(int j = 0; j < 64; j++)
         {
            pieceRandoms[i][j] = r.nextLong();
         }
      }
      
      for(int i = 0; i < 2; i++)
      {
         for(int j = 0; j < 2; j++)
         {
            castlingRandoms[i][j] = r.nextLong();
         }
      }
      
      for(int i = 0; i < 2; i++)
      {
         turnRandoms[i] = r.nextLong();
      }
      
      for(int i = 0; i < 8; i++)
      {
         enPassantRandoms[i] = r.nextLong();
      }
   }
   
   public ZobristKey()
   {
      this.key = 0;
   }
   
   public long getKey()
   {
      return this.key;
   }
   
   public void setKey(long key)
   {
      this.key = key;
   }
   
   public void xorPiece(Piece pie, Square sq)
   {
      key ^= pieceRandoms[pie.index][sq.index];
   }
   
   public void xorEnPassant(Square sq)
   {
      if(sq != null)
         key ^= enPassantRandoms[sq.getRank().index];
   }
   
   public void xorCastlingLong(Colour col)
   {
      key ^= castlingRandoms[col.index][0];
   }
   
   public void xorCastlingShort(Colour col)
   {
      key ^= castlingRandoms[col.index][1];
   }
   
   public void xorTurn(Colour col)
   {
      key ^=  turnRandoms[col.index];
   }
}
