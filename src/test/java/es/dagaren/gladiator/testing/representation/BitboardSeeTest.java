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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.apache.log4j.Logger;
import org.junit.Test;

import es.dagaren.gladiator.representation.BitboardPosition;
import es.dagaren.gladiator.representation.BitboardSee;
import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.representation.BitboardUtils;
import es.dagaren.gladiator.representation.GenericPiece;
import es.dagaren.gladiator.representation.Square;
import es.dagaren.gladiator.search.MoveValueComparator;
import es.dagaren.gladiator.notation.Notation;

/**
 * @author dagaren
 *
 */
public class BitboardSeeTest
{
   private Logger logger = Logger.getLogger(BitboardSeeTest.class);
   
   @Test
   public void seeTest()
   {  
      String originalFen = "1k1r4/1pp4p/p7/4p3/8/P5P1/1PP4P/2K1R3 w - - 0 1";
      
      BitboardPosition position = new BitboardPosition();
      position.loadFen(originalFen);
      
      int value = BitboardSee.getValue(position, Square.e1, Square.e5, GenericPiece.PAWN);
  
      int expectedValue = 100;
      
      assertEquals("Cálculo de see incorrecto", expectedValue, value);
      
   }
   
   @Test
   public void seeTest2()
   {  
      String originalFen = "1k1r4/1ppn3p/p4b2/1q2p2Q/8/P2N2P1/1PP1R2P/2K5 w - - 0 1";
      
      BitboardPosition position = new BitboardPosition();
      position.loadFen(originalFen);
      
      int value = BitboardSee.getValue(position, Square.d3, Square.e5, GenericPiece.PAWN);
  
      int expectedValue = -225;
      
      assertEquals("Cálculo de see incorrecto", expectedValue, value);
      
   }
   
   @Test
   public void seeTest3()
   {  
      String originalFen = "1k1r3q/1ppn3p/p4b2/4p3/8/P2N2P1/1PP1R2P/2K1Q3 w - - 0 1";
      
      BitboardPosition position = new BitboardPosition();
      position.loadFen(originalFen);
      
      int value = BitboardSee.getValue(position, Square.d3, Square.e5, GenericPiece.PAWN);
  
      int expectedValue = -225;
      
      assertEquals("Cálculo de see incorrecto", expectedValue, value);
      
   }
   
   @Test
   public void seeTest4()
   {
      String originalFen = "7k/p7/1p6/8/8/1Q6/8/7K w - - 0 1";
      
      BitboardPosition position = new BitboardPosition();
      position.loadFen(originalFen);
      
      int value = BitboardSee.getValue(position, Square.b3, Square.b6, GenericPiece.PAWN);
  
      int expectedValue = -900;
      
      assertEquals("Cálculo de see incorrecto", expectedValue, value);
   }
   
   @Test
   public void seeTest5()
   {
      String originalFen = "8/8/3kp3/3P4/3K4/8/8/8 b - - 0 1";
      
      BitboardPosition position = new BitboardPosition();
      position.loadFen(originalFen);
      
      int value = BitboardSee.getValue(position, Square.e6, Square.d5, GenericPiece.PAWN);
  
      int expectedValue = 100;
      
      assertEquals("Cálculo de see incorrecto", expectedValue, value);
   }
   
   @Test
   public void seeTest6()
   {
      String originalFen = "8/8/3kp3/3P4/2PK4/8/8/8 b - - 0 1";
      
      BitboardPosition position = new BitboardPosition();
      position.loadFen(originalFen);
      
      int value = BitboardSee.getValue(position, Square.e6, Square.d5, GenericPiece.PAWN);
  
      int expectedValue = 0;
      
      assertEquals("Cálculo de see incorrecto", expectedValue, value);
   }
   
   @Test
   public void seeTest7()
   {
      String originalFen = "rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 1";
      
      BitboardPosition position = new BitboardPosition();
      position.loadFen(originalFen);
      
      int value = BitboardSee.getValue(position, Square.e5, Square.d6, GenericPiece.PAWN);
  
      int expectedValue = 0;
      
      assertEquals("Cálculo de see incorrecto", expectedValue, value);
   }
   
   @Test
   public void seeTest8()
   {  
      String originalFen = "1k1r4/1ppn3p/p4b2/1q2p2Q/8/P2N2P1/1PP1R2P/2K5 w - - 0 1";
      
      BitboardPosition position = new BitboardPosition();
      position.loadFen(originalFen);
      
      int value = BitboardSee.getValue(position, Square.e2, Square.e5, GenericPiece.PAWN);
  
      int expectedValue = -400;
      
      assertEquals("Cálculo de see incorrecto", expectedValue, value);
      
   }
   
}