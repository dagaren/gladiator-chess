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

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import es.dagaren.gladiator.notation.Notation;
import es.dagaren.gladiator.representation.BitboardPosition;
import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.representation.Position;

/**
 * @author dagaren
 *
 */
public class ZobristKeyTest
{

   @BeforeClass
   public static void init()
   {
   }
   
   protected void checkPosition(Position position, int level)
   {
      if(level == 0)
         return;
         
      List<Movement> moves = position.getMovements();
      
      long key1 = position.getZobristKey().getKey();

      for(Movement move : moves)
      {  
         position.doMove(move);
         
         long key2 = position.getZobristKey().getKey();
   
         assertNotSame("La claves zobrist coincide antes y después de hacer un movimiento", key1, key2);
   
         this.checkPosition(position, level - 1);
         
         position.undoMove(move);
         
         key2 = position.getZobristKey().getKey();
         
         assertEquals("La clave zobrist no coincide antes y después", key1, key2);
      }
   }
   
   protected void checkPosition(String fen)
   {
      Position position = new BitboardPosition();
      position.loadFen(fen);
      
      this.checkPosition(position, 1);
   }
   
   
   @Test
   public void checkTest1()
   {
      this.checkPosition("k6R/b7/8/8/8/8/8/R6K b - - 0 1");
   }
   
   @Test
   public void checkTest2()
   {
      this.checkPosition("8/4p/5k2/8/3B4/8/8/K7 b - - 0 1");
   }
   
   @Test
   public void checkTest3()
   {
      this.checkPosition("3B4/4p/5k2/8/3B4/8/8/K7 b - - 0 1");
   }
   
   @Test
   public void checkTest4()
   {
      this.checkPosition("k7/1b6/8/8/8/5K2/4P/3b4 w - - 0 1");
   }
   
   @Test
   public void checkTest5()
   {
      this.checkPosition("k7/8/8/8/8/r4K2/4P/3b4 w - - 0 1");
   }
   
   @Test
   public void checkTest6()
   {
      this.checkPosition("k7/8/8/8/b3Q3/8/8/N6K b - - 0 1");
   }
   
   @Test
   public void checkTest7()
   {
      this.checkPosition("k6R/b7/8/8/8/8/8/N6K b - - 0 1");
   }
   
   @Test
   public void checkTest8()
   {
      this.checkPosition("k6R/b7/8/8/8/8/8/R6K b - - 0 1");
   }
   
   @Test
   public void checkTest9()
   {
      this.checkPosition("k7/8/8/8/b3Q3/8/8/R6K b - - 0 1");
   }
   
   @Test
   public void checkTest10()
   {
      this.checkPosition("3k4/8/8/8/8/5rK1/3P4/8 w - - 0 1");
   }
   
   @Test
   public void checkTest11()
   {
      this.checkPosition("rnb1k1nr/pppp1ppp/4p3/8/1b1Pq3/2NB4/PPP2PPP/R1BQK1NR w - - 0 1");
   }
   
   @Test
   public void checkTest12()
   {
      this.checkPosition("rnbqk1nr/p1pp1ppp/4Q3/1B6/4P3/8/PbPP1PPP/R1B1K1NR b - - 0 1");
   }
   
   @Test
   public void checkTest13()
   {
      this.checkPosition("rnb1kbnr/ppp1pppp/8/8/1q2N3/3P4/PPP2PPP/R1BQKBNR w - - 0 1");
   }
   
   @Test
   public void promotionTest1()
   {
      this.checkPosition("3K4/1P6/8/8/8/8/1p5k/R7 b - - 0 1");
   }
   
   @Test
   public void promotionTest2()
   {
      this.checkPosition("3K4/3P4/8/8/8/8/3p4/R6k b - - 0 1");
   }
   
   @Test
   public void promotionTest3()
   {
      this.checkPosition("r6K/3P4/8/8/8/8/8/3k4 w - - 0 1");
   }
   
   @Test
   public void promotionTest4()
   {
      this.checkPosition("6k1/1P6/8/8/8/8/8/1K6 w - - 0 1");
   }
   
   @Test
   public void promotionTest5()
   {
      this.checkPosition("6k1/8/8/8/8/8/4p3/1K6 b - - 0 1");
   }
   
   @Test
   public void positionTest1()
   {
      this.checkPosition("1nbqkb2/1pppppp1/r7/7r/3PP3/8/PPP3PP/RNB1K1NR w - - 0 1");
   }
   
   @Test
   public void positionTest2()
   {
      this.checkPosition("rnb1kbnr/ppp1pppp/8/8/1q2N3/3P1N2/PPPB1PPP/R2QKB1R w - - 0 1");
   }
   
   @Test
   public void positionTest3()
   {
      this.checkPosition("r3kbnr/ppp1pp1p/8/3qP1p1/3p1B2/N1nP1P1P/1QPKBP2/R6R w - - 0 1");
   }
   
   @Test
   public void positionTest4()
   {
      this.checkPosition("k7/8/8/6p1/5B2/8/8/2K5 w - - 0 1");
   }
   
   @Test
   public void positionTest5()
   {
      this.checkPosition("N4bnr/pp1kpppp/8/4P3/4b3/8/P2BBPPP/2R1K2R b - - 0 1");
   }
   
   @Test
   public void positionTest6()
   {
      this.checkPosition("r1b1kb1r/ppp1pppp/2n2n2/8/1q1PN3/2P2N2/PP3PPP/R1BQKB1R w - - 0 1");
   }
   
   @Test
   public void pinnedTest1()
   {
      this.checkPosition("k7/8/7q/8/5B2/8/8/2K5 w - - 0 1");
   }
   
   @Test
   public void checkmateTest1()
   {
      this.checkPosition("8/8/8/4K3/8/8/6Q1/1k5Q b - - 0 1");
   }
   
   @Test
   public void castlingTest1()
   {
      this.checkPosition("4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1");
   }
   
   @Test
   public void castlingTest2()
   {
      this.checkPosition("4k3/8/8/8/8/8/8/R3K2R w K - 0 1");
   }
   
   @Test
   public void castlingTest3()
   {
      this.checkPosition("3rk3/8/8/8/8/8/8/R3K2R w KQ - 0 1");
   }
   
   @Test
   public void castlingTest4()
   {
      this.checkPosition("2r1k3/8/8/8/8/8/8/R3K2R w KQ - 0 1");
   }
   
   @Test
   public void castlingTest5()
   {
      this.checkPosition("1r2k3/8/8/8/8/8/8/R3K2R w KQ - 0 1");
   }
   
   @Test
   public void castlingTest6()
   {
      this.checkPosition("4k3/4r3/8/8/8/8/8/R3K2R w KQ - 0 1");
   }
   
   @Test
   public void enPassantBlackTest1()
   {
      this.checkPosition("4k3/8/8/8/2pPp3/8/8/4K3 b - d3 0 2");
   }
   
   @Test
   public void enPassantBlackTest2()
   {
      this.checkPosition("4k3/8/8/8/2pPp3/8/4R3/4K3 b - d3 0 2");
   }
   
   @Test
   public void enPassantBlackTest3()
   {
      this.checkPosition("k7/8/8/8/2pPp3/8/6B1/4K3 b - d3 0 2");
   }
   
   @Test
   public void enPassantBlackTest4()
   {
      this.checkPosition("k7/8/2R5/8/2pPp3/8/6B1/4K3 b - d3 0 2");
   }
   
   @Test
   public void enPassantBlackTest5()
   {
      this.checkPosition("k7/8/8/8/2pPp3/5P2/6B1/4K3 b - d3 0 2");
   }
   
   @Test
   public void enPassantWhiteTest1()
   {
      this.checkPosition("4k3/8/8/2PpP3/8/8/8/4K3 w - d6 0 2");
   }
   
   @Test
   public void enPassantWhiteTest2()
   {
      this.checkPosition("4k3/8/8/1K1pP2r/8/8/8/8 w - d6 0 2");
   }
   
   @Test
   public void enPassantWhiteTest3()
   {
      this.checkPosition("4k3/8/8/1K1pP1Pr/8/8/8/8 w - d6 0 2");
   }
   
   @Test
   public void enPassantWhiteTest4()
   {
      this.checkPosition("4k3/8/8/1K2Pp1r/8/8/8/8 w - f6 0 2");
   }
   
   @Test
   public void enPassantWhiteTest5()
   {
      this.checkPosition("6bk/8/8/3Pp3/8/1K6/8/8 w - e6 0 2");
   }
   
   @Test
   public void enPassantWhiteTest6()
   {
      this.checkPosition("6bk/8/8/3Pp3/8/1K6/8/8 w - - 0 2");
   }
   
   @Test
   public void enPassantWhiteTest7()
   {
      this.checkPosition("6bk/5p2/8/3Pp3/8/1K6/8/8 w - e6 0 2");
   }
   
}
