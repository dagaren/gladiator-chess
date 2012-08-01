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

import org.apache.log4j.Logger;
import org.junit.*;

import java.util.List;

import es.dagaren.gladiator.notation.Notation;
import es.dagaren.gladiator.representation.BitboardPosition;
import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.representation.Position;
import es.dagaren.gladiator.representation.Square;

/**
 * @author dagaren
 *
 */
public class BitboardPositionTest
{
   private Logger logger = Logger.getLogger(BitboardPositionTest.class);
   
   @BeforeClass
   public static void init()
   {
   }
   
   @Test
   public void loadFen1Test()
   {
      String originalFen = "3B4/4p3/5k2/8/3B4/8/8/K7 b - - 0 1";
      
      BitboardPosition position = new BitboardPosition();
      position.loadFen(originalFen);
      
      String fen = position.toFen();
      
      logger.debug("FEN original:   " + originalFen);
      logger.debug("FEN recuperado: " + fen);
      
      assertEquals("La posición original y la calculada no coinciden", originalFen, fen);
      
   }
   
   @Test
   public void loadFen2Test()
   {
      String originalFen = "4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1";
      
      BitboardPosition position = new BitboardPosition();
      position.loadFen(originalFen);
      
      String fen = position.toFen();
      
      logger.debug("FEN original:   " + originalFen);
      logger.debug("FEN recuperado: " + fen);
      
      assertEquals("La posición original y la calculada no coinciden", originalFen, fen);
      
   }
   
   @Test
   public void clonePositionTest()
   {
      Position originalPosition = new BitboardPosition();
      originalPosition.setInitialPosition();
      
      Position clonedPosition = originalPosition.getCopy();
      for(int i = 0; i < 64; i++)
      {
         if(originalPosition.getPieceInSquare(Square.fromIndex(i)) == null)
         {
            assertNull(clonedPosition.getPieceInSquare(Square.fromIndex(i)));
         }
         else
         {
            assertEquals(originalPosition.getPieceInSquare(Square.fromIndex(i)).index, clonedPosition.getPieceInSquare(Square.fromIndex(i)).index);
         }
      }
   
      List<Movement> originalMoves = originalPosition.getMovements();
      List<Movement> clonedMoves = clonedPosition.getMovements();
      

      for(Movement move : originalMoves)
      {
         boolean contains = clonedMoves.contains(move);
            
         assertTrue("En los movimientos de la posición clonada no se encuentra el movimiento " + Notation.toString(move), contains);
            
         clonedMoves.remove(move);
      }
         
      assertEquals("Se generan más movimientos de los posibles en la posición", 0, clonedMoves.size());
   }
}
