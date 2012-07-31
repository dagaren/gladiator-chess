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
package es.dagaren.gladiator.testing.representation;


import static org.junit.Assert.*;

import java.util.Random;

import org.junit.*;

import es.dagaren.gladiator.representation.BitboardPosition;
import es.dagaren.gladiator.representation.BitboardUtils;

/**
 * @author dagaren
 *
 */
public class GeneralTest
{
   @Test
   public void getBitboardBitTest()
   {
      Random r = new Random(System.currentTimeMillis());
      
      for(int i = 0; i < 100; i++)
      {
         long bitboard = r.nextLong() << 16;
         
         System.out.println("Bitboard original");
         System.out.println(BitboardUtils.toString(bitboard));
         
         long bitboardBit = bitboard & -bitboard;
         
         System.out.println("Bitboard de bit calculado");
         System.out.println(BitboardUtils.toString(bitboardBit));
         
         int bitCount = Long.bitCount(bitboardBit);
         
         if(bitboard != 0)
         {
            assertEquals("Recuperación de un bit de un bitboard fallido", 1, bitCount);
         
            long bitboardIntersection = bitboardBit & bitboard;
         
            assertTrue("Recuperación de un bit de un bitboard fallida", 0 != bitboardIntersection);
         }
      }
      
   }
}
