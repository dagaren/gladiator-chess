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
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import es.dagaren.gladiator.notation.Notation;
import es.dagaren.gladiator.representation.BitboardPosition;
import es.dagaren.gladiator.representation.Movement;

/**
 * @author dagaren
 *
 */
public class MovesGenerationTest
{
   String fen;
   BitboardPosition position;
   Movement[] generatedMoves;
   Movement[] realMoves;
   
   @BeforeClass
   public static void init()
   {
   }
   
   private void initMoves(String fen)
   {
      this.fen = fen;
      this.position = new BitboardPosition();
      this.position.loadFen(this.fen);
      
      generatedMoves = position.getMovements().toArray(new Movement[0]);
   }
   
   private void loadRealMoves(String[] moves)
   {
      realMoves = new Movement[moves.length];
      
      for(int i = 0; i < moves.length; i++)
      {
         realMoves[i] = Notation.parseMove(moves[i]);
      }
   }
   
   private void printPosition()
   {
      if(this.position != null)
      {
         System.out.println(position.toString());
         System.out.println("Turno: " +position.getTurn().toString());
      }
   }
   
   private void printMoves()
   {
      System.out.print("* Movimientos generados: ");
      int index = 0;
      int numMoves = generatedMoves.length;
      for(Movement m : generatedMoves)
      {
         System.out.print(Notation.toString(m));
         if(++index < numMoves)
            System.out.print(", ");
      }
      System.out.println("");
      
      System.out.print("* Movimientos reales: ");
      index = 0;
      numMoves = realMoves.length;
      for(Movement m : realMoves)
      {
         System.out.print(Notation.toString(m));
         if(++index < numMoves)
            System.out.print(", ");
      }
      System.out.println("");
   }
   
   private void checkMoves()
   {
      List<Movement> generatedMovesList = new LinkedList<Movement>(Arrays.asList(generatedMoves));
      
      for(Movement move : realMoves)
      {
         boolean contains = generatedMovesList.contains(move);
         
         assertTrue("En los movimientos generados no se encuentra el movimiento " + Notation.toString(move), contains);
         
         generatedMovesList.remove(move);
      }
      
      assertEquals("Se generan más movimientos de los posibles en la posición", 0, generatedMovesList.size());
   }
   
   @Test
   public void checkTest1()
   {
      initMoves("k6R/b7/8/8/8/8/8/R6K b - - 0 1");
      
      String[] moves =
      {
         "a8b7"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest2()
   {
      initMoves("8/4p/5k2/8/3B4/8/8/K7 b - - 0 1");
      
      String[] moves =
      {
         "f6f5", "f6g5", "f6e6", "f6g6", "f6f7",
         "e7e5"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest3()
   {
      initMoves("3B4/4p/5k2/8/3B4/8/8/K7 b - - 0 1");
      
      String[] moves =
      {
         "f6f5", "f6g5", "f6e6", "f6g6", "f6f7"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest4()
   {
      initMoves("k7/1b6/8/8/8/5K2/4P/3b4 w - - 0 1");

      String[] moves =
      {
         "f3f2", "f3e3", "f3g3", "f3f4", "f3g4"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest5()
   {
      initMoves("k7/8/8/8/8/r4K2/4P/3b4 w - - 0 1");

      String[] moves =
      {
         "f3f2", "f3g2", "f3e4", "f3f4", "f3g4"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest6()
   {
      initMoves("k7/8/8/8/b3Q3/8/8/N6K b - - 0 1");

      String[] moves =
      {
         "a8a7", "a8b8",
         "a4c6"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest7()
   {
      initMoves("k6R/b7/8/8/8/8/8/N6K b - - 0 1");

      String[] moves =
      {
         "a8b7",
         "a7b8"
      };
      
      loadRealMoves(moves);
   
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest8()
   {
      initMoves("k6R/b7/8/8/8/8/8/R6K b - - 0 1");
   
      String[] moves =
      {
         "a8b7"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest9()
   {
      initMoves("k7/8/8/8/b3Q3/8/8/R6K b - - 0 1");
      
      String[] moves =
      {
         "a8a7",
         "a8b8"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void promotionTest1()
   {
      initMoves("3K4/1P6/8/8/8/8/1p5k/R7 b - - 0 1");
      
      String[] moves =
      {
         "h2g2", "h2g3", "h2h3", 
         "b2a1r", "b2a1n", "b2a1b", "b2a1q", "b2b1r", "b2b1n", "b2b1b", "b2b1q"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void promotionTest2()
   {
      initMoves("3K4/3P4/8/8/8/8/3p4/R6k b - - 0 1");

      String[] moves =
      {
         "h1h2", "h1g2", 
         "d2d1r", "d2d1n", "d2d1b", "d2d1q"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();;
   }
   
   @Test
   public void promotionTest3()
   {
      initMoves("r6K/3P4/8/8/8/8/8/3k4 w - - 0 1");

      String[] moves =
      {
         "h8g7", "h8h7", 
         "d7d8q", "d7d8r", "d7d8n", "d7d8b"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest10()
   {
      initMoves("3k4/8/8/8/8/5rK1/3P4/8 w - - 0 1");

      String[] moves =
      {
         "g3g2", "g3h2", "g3f3", "g3g4", "g3h4"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void promotionTest4()
   {
      initMoves("6k1/1P6/8/8/8/8/8/1K6 w - - 0 1");
      
      String[] moves =
      {
         "b1a1", "b1c1", "b1a2", "b1b2", "b1c2",
         "b7b8q", "b7b8r", "b7b8b", "b7b8n"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void promotionTest5()
   {
      initMoves("6k1/8/8/8/8/8/4p3/1K6 b - - 0 1");

      String[] moves =
      {
         "g8h8", 
         "e2e1q", "e2e1r", "e2e1b", "e2e1n",
         "g8f8", "g8f7", "g8g7", "g8h7"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest11()
   {
      initMoves("rnb1k1nr/pppp1ppp/4p3/8/1b1Pq3/2NB4/PPP2PPP/R1BQK1NR w - - 0 1");

      String[] moves =
      {
         "d3e4", "d3e2", 
         "g1e2", 
         "c1e3", 
         "d1e2",
         "e1d2", "e1f1"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest12()
   {
      initMoves("rnbqk1nr/p1pp1ppp/4Q3/1B6/4P3/8/PbPP1PPP/R1B1K1NR b - - 0 1");

      String[] moves =
      {
         "e8f8", 
         "f7e6", 
         "d8e7", 
         "g8e7"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
  
   
   @Test
   public void positionTest1()
   {
      initMoves("1nbqkb2/1pppppp1/r7/7r/3PP3/8/PPP3PP/RNB1K1NR w - - 0 1");
      
      String[] movesStrings =
      {
         "a2a3","a2a4",
         "b2b3","b2b4",
         "c2c3","c2c4",
         "d4d5","e4e5",
         "g2g3","g2g4",
         "h2h3","h2h4",
         "b1a3","b1c3","b1d2",
         "c1d2","c1e3","c1f4","c1g5","c1h6",
         "e1d1","e1d2","e1e2","e1f1","e1f2",
         "g1e2","g1f3","g1h3"
      };

      loadRealMoves(movesStrings);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest13()
   {
      initMoves("rnb1kbnr/ppp1pppp/8/8/1q2N3/3P4/PPP2PPP/R1BQKBNR w - - 0 1");

      String[] moves =
      {
         "e1e2", 
         "d1d2", 
         "c1d2", 
         "e4c3", 
         "c2c3",
         "e4d2"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest14()
   {
      initMoves("6R1/4r3/8/2N1Pk2/3P2p1/p4R1P/P4PKP/1r6 b - - 1 0");

      String[] moves =
      {
         "g4f3"
      };
      
      loadRealMoves(moves);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void positionTest2()
   {
      initMoves("rnb1kbnr/ppp1pppp/8/8/1q2N3/3P1N2/PPPB1PPP/R2QKB1R w - - 0 1");
      
      String[] moves =
      {
         "a2a3", "a2a4", 
         "b2b3", 
         "c2c3", "c2c4",
         "d3d4", 
         "g2g3", "g2g4", 
         "h2h3", "h2h4",
         "a1b1", "a1c1",
         "d1b1", "d1c1", "d1e2",
         "e1e2",
         "f1e2",
         "h1g1",
         "e4g3", "e4c3", "e4c5", "e4d6", "e4f6", "e4g5",
         "f3g1", "f3h4", "f3g5", "f3e5", "f3d4",
         "d2c3", "d2b4"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void positionTest3()
   {
      initMoves("r3kbnr/ppp1pp1p/8/3qP1p1/3p1B2/N1nP1P1P/1QPKBP2/R6R w - - 0 1");
      
      String[] movesStrings = 
      {
         "a1b1","a1c1","a1d1","a1e1","a1f1","a1g1","a1a2",
         "h1b1","h1c1","h1d1","h1e1","h1f1","h1g1","h1h2",
         "a3b5","a3c4","a3b1",
         "b2a2","b2b1","b2c1","b2b3","b2b4","b2b5","b2b6","b2c3","b2b7",
         "e5e6",
         "f4e3","f4g3","f4g5","f4h2",
         "h3h4",
         "e2d1","e2f1",
         "d2c1","d2e1"
      };

      loadRealMoves(movesStrings);

      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void pinnedTest1()
   {
      initMoves("k7/8/7q/8/5B2/8/8/2K5 w - - 0 1");
      
      String[] movesStrings =
      {
         "c1b1", "c1d1", "c1b2", "c1c2", "c1d2",
         "f4g5", "f4e3", "f4d2", "f4h6"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void positionTest4()
   {
      initMoves("k7/8/8/6p1/5B2/8/8/2K5 w - - 0 1");
      
      String[] movesStrings =
      {
         "c1b1", "c1d1", "c1b2", "c1c2", "c1d2",
         "f4g5", "f4e3", "f4d2", "f4g3", "f4h2","f4e5", "f4d6", "f4c7", "f4b8"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void positionTest5()
   {
      initMoves("N4bnr/pp1kpppp/8/4P3/4b3/8/P2BBPPP/2R1K2R b - - 0 1");
      
      String[] movesStrings =
      {
         "a7a6","a7a5",
         "b7b6","b7b5",
         "e7e6",
         "f7f6","f7f5",
         "g7g6","g7g5",
         "h7h6","h7h5",
         "g8f6","g8h6",
         "d7d8","d7e6","d7e8",
         "e4d3","e4c2","e4b1","e4f5","e4g6","e4g2","e4f3","e4d5","e4c6",
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest15()
   {
      initMoves("8/8/2R1p2r/1k6/P7/8/2P3pp/2K2n2 b - a3 0 0");
      
      String[] movesStrings =
      {
         "b5a4","b5a5", "b5c6", "b5b4"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest16()
   {
      initMoves("2r5/p2N2b1/1p2k2p/3N1P2/1R4n1/8/b1P3PP/2KR4 b - - 0 0");
      
      String[] movesStrings =
      {
         "e6d7", "e6f7", "e6d6", "e6f5"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest17()
   {
      initMoves("7r/2RN4/2p1p2p/3b1pk1/1P1P1bpP/8/2PB1PP1/7K b - h3 0 0");
      
      String[] movesStrings =
      {
         "g5h5", "g5h4", "g5g6",
         "g4h3"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest18()
   {
      initMoves("7k/1pp2b2/p6p/P1P3pP/1P5K/8/8/8 w - g6 0 0");
      
      String[] movesStrings =
      {
         "h4g4", "h4h3", "h4g3",
         "h5g6"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest19()
   {
      initMoves("8/1r2p3/pr1p2p1/3Pb1k1/PPR1pP2/2P1N1P1/7P/4R2K b - f3 0 0");
      
      String[] movesStrings =
      {
         "g5h5", "g5f6", "g5h6",
         "e5f4",
         "e4f3"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest20()
   {
      initMoves("1k6/1p3p2/p7/P1P3Pp/5RK1/8/7r/8 w - h6 0 0");
      
      String[] movesStrings =
      {
         "g4f3", "g4g3", "g4f5",
         "g5h6",
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest21()
   {
      initMoves("5k2/ppR2p2/8/6pP/1r2BK2/8/8/8 w - g6 0 0");
      
      String[] movesStrings =
      {
         "f4e3", "f4f5", "f4g4", "f4g3", "f4f3", "f4e5", "f4g5",
         "h5g6",
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest22()
   {
      initMoves("8/8/4P3/1kp2P2/1pP2R2/3R3K/4r1PP/8 b - c3 0 0");
      
      String[] movesStrings =
      {
         "b5a4", "b5a5", "b5a6", "b5b6", "b5c6", 
         "b4c3",
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest23()
   {
      initMoves("8/8/8/2k2b2/1Pp5/5p2/2N2P1P/7K b - b3 0 0");
      
      String[] movesStrings =
      {
         "c5b5", "c5b6", "c5c6", "c5d6", "c5d5", 
         "c4b3",
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void checkTest24()
   {
      initMoves("1k4r1/1p3p2/p7/P1P3Pp/5RK1/8/7r/8 w - h6 0 0");
      
      String[] movesStrings =
      {
         "g4f3", "g4g3", "g4f5"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Ignore
   @Test
   public void checkTest25()
   {
      initMoves("8/K4b2/3R1b2/5k2/5pPp/5N2/5R1P/1r6 b - g3 0 0");
      
      String[] movesStrings =
      {
         "f5g6", "f5e4", "f5g4",
         "f4g3",
         "h4g3"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Ignore
   @Test
   public void checkTest26()
   {
      initMoves("6b1/5r2/r1pbp3/p2p1k2/B2P1pPp/P2N3P/1PP2R2/4R2K b - g3 0 0");
      
      String[] movesStrings =
      {
         "f5g5", "f5g6", "f5f6",
         "h4g3"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   
   @Test
   public void checkmateTest1()
   {
      initMoves("8/8/8/4K3/8/8/6Q1/1k5Q b - - 0 1");
      
      String[] movesStrings = {};
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void positionTest6()
   {
      initMoves("r1b1kb1r/ppp1pppp/2n2n2/8/1q1PN3/2P2N2/PP3PPP/R1BQKB1R w - - 0 1");
      
      String[] movesStrings = 
      {
         "a2a3","a2a4",
         "b2b3",
         "c3b4",
         "d4d5",
         "g2g3","g2g4",
         "h2h3","h2h4",
         "e4c5","e4d2","e4f6","e4d6","e4g5","e4g3",
         "f3g1","f3d2","f3e5","f3g5","f3h4",
         "a1b1",
         "h1g1",
         "c1d2","c1e3","c1f4","c1g5","c1h6",
         "f1e2","f1d3","f1c4","f1b5","f1a6",
         "e1d2","e1e2",
         "d1c2","d1b3","d1a4","d1d2","d1d3","d1e2",
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void castlingTest1()
   {
      initMoves("4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1");
      
      String[] movesStrings = 
      {
         "e1f1","e1f2","e1e2","e1d2","e1d1","e1g1","e1c1",
         "a1a2","a1a3","a1a4","a1a5","a1a6","a1a7","a1a8","a1b1","a1c1","a1d1",
         "h1h2","h1h3","h1h4","h1h5","h1h6","h1h7","h1h8","h1g1","h1f1"                              
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void castlingTest2()
   {
      initMoves("4k3/8/8/8/8/8/8/R3K2R w K - 0 1");
      
      String[] movesStrings =
      {
         "e1f1","e1f2","e1e2","e1d2","e1d1","e1g1",
         "a1a2","a1a3","a1a4","a1a5","a1a6","a1a7","a1a8","a1b1","a1c1","a1d1",
         "h1h2","h1h3","h1h4","h1h5","h1h6","h1h7","h1h8","h1g1","h1f1"
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void castlingTest3()
   {
      initMoves("3rk3/8/8/8/8/8/8/R3K2R w KQ - 0 1");
      
      String[] movesStrings =
      {
         "e1f1","e1f2","e1e2","e1g1",
         "a1a2","a1a3","a1a4","a1a5","a1a6","a1a7","a1a8","a1b1","a1c1","a1d1",
         "h1h2","h1h3","h1h4","h1h5","h1h6","h1h7","h1h8","h1g1","h1f1"                        
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void castlingTest4()
   {
      initMoves("2r1k3/8/8/8/8/8/8/R3K2R w KQ - 0 1");
      
      String[] movesStrings =
      {
         "e1f1","e1f2","e1e2","e1d1","e1d2","e1g1",
         "a1a2","a1a3","a1a4","a1a5","a1a6","a1a7","a1a8","a1b1","a1c1","a1d1",
         "h1h2","h1h3","h1h4","h1h5","h1h6","h1h7","h1h8","h1g1","h1f1"                        
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void castlingTest5()
   {
      initMoves("1r2k3/8/8/8/8/8/8/R3K2R w KQ - 0 1");
      
      String[] movesStrings =
      {
         "e1f1","e1f2","e1e2","e1d1","e1d2","e1g1","e1c1",
         "a1a2","a1a3","a1a4","a1a5","a1a6","a1a7","a1a8","a1b1","a1c1","a1d1",
         "h1h2","h1h3","h1h4","h1h5","h1h6","h1h7","h1h8","h1g1","h1f1"                        
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void castlingTest6()
   {
      initMoves("4k3/4r3/8/8/8/8/8/R3K2R w KQ - 0 1");
      
      String[] movesStrings =
      {
         "e1f1","e1f2","e1d1","e1d2"                           
      };
      
      loadRealMoves(movesStrings);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   
   
   @Test
   public void enPassantBlackTest()
   {
      initMoves("4k3/8/8/8/2pPp3/8/8/4K3 b - d3 0 2");
      
      String[] moves =
      { 
         "e8f8", "e8f7", "e8e7", "e8d7", "e8d8",
         "c4c3", "c4d3",
         "e4e3", "e4d3",
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantBlackTest2()
   {
      initMoves("4k3/8/8/8/2pPp3/8/4R3/4K3 b - d3 0 2");
      
      String[] moves = 
      { 
         "e8f8", "e8f7", "e8e7", "e8d7", "e8d8",
         "c4c3", "e4e3", "c4d3"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantBlackTest3()
   {
      initMoves("k7/8/8/8/2pPp3/8/6B1/4K3 b - d3 0 2");
      
      String[] moves =
      { 
         "a8a7", "a8b7", "a8b8",
         "c4c3", "c4d3"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantBlackTest4()
   {
      initMoves("k7/8/2R5/8/2pPp3/8/6B1/4K3 b - d3 0 2");
      
      String[] moves = 
      {
         "a8a7", "a8b7", "a8b8",
         "c4c3", "c4d3", "e4e3", "e4d3"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantBlackTest5()
   {
      initMoves("k7/8/8/8/2pPp3/5P2/6B1/4K3 b - d3 0 2");
      
      String[] moves =
      {
         "a8a7", "a8b7", "a8b8",
         "c4c3", "c4d3", 
         "e4d3", "e4e3", "e4f3" 
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantWhiteTest()
   {
      initMoves("4k3/8/8/2PpP3/8/8/8/4K3 w - d6 0 2");
      
      String[] moves =
      {
         "e1f1", "e1f2", "e1e2", "e1d2", "e1d1",
         "c5c6", "e5e6", "e5d6", "c5d6"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantWhiteTest2()
   {
      initMoves("4k3/8/8/1K1pP2r/8/8/8/8 w - d6 0 2");
      
      String[] moves = 
      {
         "b5b6", "b5a6", "b5c6", "b5a5", "b5c5", "b5a4", "b5b4",
         "e5e6"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantWhiteTest3()
   {
      initMoves("4k3/8/8/1K1pP1Pr/8/8/8/8 w - d6 0 2");
      
      String[] moves =
      {
         "b5b6", "b5a6", "b5c6", "b5a5", "b5c5", "b5a4", "b5b4",
         "e5e6", "e5d6", 
         "g5g6"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantWhiteTest4()
   {
      initMoves("4k3/8/8/1K2Pp1r/8/8/8/8 w - f6 0 2");
      
      String[] moves = 
      {
         "b5b6", "b5a6", "b5c6", "b5a5", "b5c5", "b5a4", "b5b4", "b5c4",
         "e5e6"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantWhiteTest5()
   {
      initMoves("6bk/8/8/3Pp3/8/1K6/8/8 w - e6 0 2");
      
      String[] moves =
      {
         "b3a3", "b3a4", "b3a2", "b3b2", "b3b4", "b3c4", "b3c3", "b3c2",
         "d5e6"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantWhiteTest6()
   {
      initMoves("6bk/8/8/3Pp3/8/1K6/8/8 w - - 0 2");
      
      String[] moves =
      {
         "b3a3", "b3a4", "b3a2", "b3b2", "b3b4", "b3c4", "b3c3", "b3c2",
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
   
   @Test
   public void enPassantWhiteTest7()
   {
      initMoves("6bk/5p2/8/3Pp3/8/1K6/8/8 w - e6 0 2");
      
      String[] moves = 
      {
         "b3a3", "b3a4", "b3a2", "b3b2", "b3b4", "b3c4", "b3c3", "b3c2",
         "d5d6", "d5e6"
      };
      
      loadRealMoves(moves);
      
      printPosition();
      printMoves();
      
      checkMoves();
   }
}