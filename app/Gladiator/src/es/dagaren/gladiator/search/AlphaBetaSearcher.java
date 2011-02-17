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
package es.dagaren.gladiator.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.dagaren.gladiator.notation.Notation;
import es.dagaren.gladiator.representation.Colour;
import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.representation.Position;

/**
 * @author dagaren
 *
 */
public class AlphaBetaSearcher extends Searcher
{
   protected long numUpdates;
   
   //Constantes de búsqueda
   protected final int INITIAL_ALFA = -10000 - 1;
   protected final int INITIAL_BETA =  10000 + 1;
   
   //Comparador MVV LVA
   protected Comparator<Movement> mvvLvaComparator = new MvvLvaMoveComparator();
   
   //La variante principal
   protected Map<Integer, LinkedList<Movement>> principalVariations = new HashMap<Integer, LinkedList<Movement>>();
   protected LinkedList<Movement> currentPrincipalVariation;
   
   protected int depth;
   protected int bestScore;
   
   //Variables de estadísticas
   //Número de cortes producidos
   protected int cutoffs = 0;
   //Número de nodos recorridos en búsqueda normal
   protected int nodes = 0;
   //Número de nodos recorridos en búsqueda quiescence
   protected int qnodes = 0;
   //
   protected int iterationNodes = 0;
   
   @Override
   public void search()
   {
      numUpdates = 0;
      
      visitedNodes = 0;
      cutoffs = 0;
      nodes = 0;
      qnodes = 0;
      
      principalVariations.clear();
      
      int score = 0;
      
      //Se implementa la profundidad iterativa
      for(depth = 1; depth <= depthLimit; depth++)
      {
         iterationNodes = 0;
         
         long iterationInitTime = System.currentTimeMillis();
         
         currentPrincipalVariation = new LinkedList<Movement>();
         
         score = alphaBeta(position, depth, INITIAL_ALFA, INITIAL_BETA, currentPrincipalVariation);
         
         principalVariations.put(depth, currentPrincipalVariation);
         
         principalVariation = currentPrincipalVariation;
         
         System.err.println("Estadísticas búsquedas en profundidad " + depth + ":");
         System.err.println("------------------------------------------");
         System.err.println(" * Nodos recorridos: " + nodes);
         System.err.println(" * Nodos quiescende recorridos: " + qnodes);
         System.err.println(" * Cortes producidos: " + cutoffs);
         System.err.println(" * Tiempo iteración: " + ((System.currentTimeMillis() - iterationInitTime)/1000));
         if(principalVariation.size() > 0)
            System.err.println(" * Mejor movimiento encontrado: " + Notation.toString(principalVariation.get(0)));
         System.err.print(" * VP: ");
         for(Movement m : principalVariation)
         {
           System.err.print(" " + Notation.toString(m));
         }
         System.err.println("");System.err.println("");
      }
      
      if(position.getTurn() == Colour.BLACK)
      {
         score = -score;
      }
   }
   
   public int alphaBeta(Position position, int currentDeep, int alpha, int beta, LinkedList<Movement> parentPrincipalVariation)
   {
      Colour turn = position.getTurn();
      
      LinkedList<Movement> currentPrincipalVariation = new LinkedList<Movement>();
      
      //Se acualiza el número de nodos recorridos
      visitedNodes++;
      nodes++;
      iterationNodes++;
      
      //TODO Se chequean las tablas por repetición
      
      //TODO Se busca en las tablas de transposición
      
      //TODO Comprobación en la tabla de finales
      
      //Se generan los movimientos
      List<Movement> moves = position.getMovements();
      
      
      if(moves.size() == 0)
      {
         //Si no hay ningún movimiento legal en la posición
         //quiere decir que la posición es tablas por ahogado
         //o jaque mate dependiendo de si la posición es de jaque
        
         parentPrincipalVariation.clear();
         if(position.isInCheck(turn))
         {
            //Es jaque mate
            return CHECKMATE_SCORE;
         }
         else
         {
            //Son tablas por ahogado
            return DRAW_SCORE;
         }
      }
      
      //Si se ha llegado a la máxima profundidad
      //Se devuelve una búsqueda quiescence
      //para evitar el efecto horizonte
      if(currentDeep == 0)
      {
         return alphaBetaQuiescence(position, 8, alpha, beta, parentPrincipalVariation);
      }
      
      
      //Se ordenan los movimientos
      this.sortMoves(moves, position);
      
      //Se itera a través de cada movimiento y se realiza
      //la búsqueda en las posiciones que resultan
      for(Movement move: moves)
      {
         currentPrincipalVariation.clear();
         
         //Se realiza el siguiente movimiento en la lista
//Position clone = position.getCopy();
//String fenBefore = position.toFen();
         position.doMove(move);
         
         //Se calcula la evaluación del movimiento
         int score = - alphaBeta(position, currentDeep - 1, -beta, -alpha, currentPrincipalVariation);
      
         //Se deshace el movimiento
         position.undoMove(move);
//String fenAfter = position.toFen();
//if(!fenBefore.equals(fenAfter))
//{
//   System.err.println("ERROR FEN ANTES Y DESPUES DE HACER DESHACER NO QUEDA IGUAL: " + Notation.toString(move));
//   System.err.println("Position inicial:");
//   System.err.println("FEN inicial: " + fenBefore);
//   System.err.println(clone.toString());
//   System.err.println("FEN final: " + fenAfter);
//   System.err.println(position.toString());
//}
         
         if(score >= beta)
         {
            //Si la puntuación supera beta
            //se produce un corte
            cutoffs++;
            
            parentPrincipalVariation.clear();
            
            return beta;
         }
         if(score > alpha)
         {
            //si la puntuación devuelta supera alfa
            //se ha encontrado un movimiento mejor
            alpha = score;
            
            numUpdates++;
            
            parentPrincipalVariation.clear();
            parentPrincipalVariation.add(move);
            parentPrincipalVariation.addAll(currentPrincipalVariation);
            
            if(currentDeep == depth)
            {
               if(turn == Colour.WHITE)
                  bestScore = score;
               else
                  bestScore = -score;
               
               long time = System.currentTimeMillis();
               publishInfo((time - initTime) / 10, visitedNodes, depth, bestScore, parentPrincipalVariation);
            }
         }
      }
      
      return alpha;
   }
   
   public int alphaBetaQuiescence(Position position, int currentDeep, int alpha, int beta, LinkedList<Movement> parentPrincipalVariation)
   {
      Colour turn = position.getTurn();
      
      LinkedList<Movement> currentPrincipalVariation = new LinkedList<Movement>();
      
      //Se actualiza el número de posiciones recorridas
      visitedNodes++;
      qnodes++;
      
      //Se generan los movimientos
      List<Movement> moves = position.getMovements();
      List<Movement> captureMoves = position.getCaptureMovements();
      
      if(moves.size() == 0)
      {
         //Si no hay ningún movimiento legal en la posición
         //quiere decir que la posición es tablas por ahogado
         //o jaque mate dependiendo de si la posición es de jaque
        
         parentPrincipalVariation.clear();
         if(position.isInCheck(turn))
         {
            //Es jaque mate
            return CHECKMATE_SCORE;
         }
         else
         {
            //Son tablas por ahogado
            return DRAW_SCORE;
         }
      }
      
      //Se calcula la evaluación estática de la posicion
      int score = evaluator.evaluate(position);
      

      if(captureMoves.size() == 0)
      {
         parentPrincipalVariation.clear();
         return score;
      }
      
      //Se intenta actualizar alfa con el puntaje devuelto
      //Esto es necesario ya que como solo se van a comprobar 
      //los movimientos de captura es necesario establecer 
      //este valor para que se vea si las capturas generan
      //mejor resultado, y si no devolver el valor de la puntuación 
      //estática.
      if(score >= beta)
      {
         parentPrincipalVariation.clear();
         
         return beta;
      }
      if(alpha < score)
      {
         alpha = score;
      }
      
      
      //Se ordenan los movimientos
      this.sortQuiescenceMoves(captureMoves, position);
      
      
      //Se itera a través de cada movimiento de captura y se realiza
      //la búsqueda en las posiciones que resultan
      for(Movement move: captureMoves)
      {
         currentPrincipalVariation.clear(); 

         //Se realiza el siguiente movimiento en la lista
//Position clone = position.getCopy();
//String fenBefore = position.toFen();
         position.doMove(move);
      
         //Se calcula la puntuación del movimiento
         score = - alphaBetaQuiescence(position, currentDeep - 1, -beta, -alpha, currentPrincipalVariation);
         
         //Tras evaluar el movimiento se deshace
         position.undoMove(move);
//String fenAfter = position.toFen();
//if(!fenBefore.equals(fenAfter))
//{
//   System.err.println("ERROR FEN ANTES Y DESPUES DE HACER DESHACER NO QUEDA IGUAL: (quiescence) "  + Notation.toString(move));
//   System.err.println("Position inicial:");
//   System.err.println("FEN inicial: " + fenBefore);
//   System.err.println(clone.toString());
//   System.err.println("FEN final: " + fenAfter);
//   System.err.println(position.toString());
//   System.exit(0);
//}
         
         if(score >= beta)
         {
            cutoffs++;
            
            parentPrincipalVariation.clear();
            
            return beta;
         }
         if(score > alpha)
         {
            numUpdates++;
            
            parentPrincipalVariation.clear();
            parentPrincipalVariation.add(move);
            parentPrincipalVariation.addAll(currentPrincipalVariation);
            
            alpha = score;
         }
      }
      
      return alpha;
      
   }
   
   
   public void sortMoves(List<Movement> moves, Position position)
   {
      //Se ordenan si acaso vale la variante principal de la iteración anterior
      if(iterationNodes < depth)
      {
         List<Movement> im = principalVariations.get(depth - 1);
         
         Movement m = im.get(iterationNodes - 1);
         
         boolean rem = moves.remove(m);
         if(rem)
         {
            moves.add(0, m);
         }
      }
      //////////////////////////
   }
   
   public void sortQuiescenceMoves(List<Movement> moves, Position position)
   {
      Collections.sort(moves, mvvLvaComparator);
   }
}
