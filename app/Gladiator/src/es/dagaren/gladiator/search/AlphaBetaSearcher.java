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
import es.dagaren.gladiator.transposition.Entry;
import es.dagaren.gladiator.transposition.Table;
import es.dagaren.gladiator.transposition.Entry.Type;

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
   
   //Arrays de movimientos killer;
   protected Movement[][] killerMoves = new Movement[this.depthLimit][2];
   
   //Comparador MVV LVA
   protected Comparator<Movement> mvvLvaComparator = new MvvLvaMoveComparator();
   
   //La variante principal
   protected PrincipalVariation principalVariation = new PrincipalVariation();
   
   protected int depth;
   protected int bestScore;
   
   //La tabla de transposicion
   protected Table transpositionTable = new Table(52428800);
   
   protected Movement transpositionMove = null;
   
   //Variables de estadísticas
   
   //Número de cortes producidos
   protected int cutoffs = 0;
   //Número de cortes producidos en nodos quiescence
   protected int qcutoffs = 0;
   //Número de nodos recorridos en búsqueda normal
   protected int nodes = 0;
   //Número de nodos recorridos en búsqueda quiescence
   protected int qnodes = 0;
   //
   protected int iterationNodes = 0;
   //Número de búsquedas con ventana de aspiración
   protected int aspirationSearchs = 0;
   //Número de búsquedas con ventana de aspiración que fallan
   protected int aspirationSearchsFails = 0;
   
   protected int transpositionCutoffs = 0;
   
   
   
   
   
   
   int alfa = 0;
   int beta = 0;
   
   int windowSize = 50;
   
   @Override
   public void search()
   {
      numUpdates = 0;
      
      visitedNodes = 0;
      cutoffs = 0;
      qcutoffs = 0;
      nodes = 0;
      qnodes = 0;
      transpositionCutoffs = 0;
      
      aspirationSearchs = 0;
      aspirationSearchsFails = 0;
      
      bestPrincipalVariation = new Movement[0];
      
      int score = 0;
      
      //Se implementa la profundidad iterativa
      for(depth = 1; depth <= depthLimit; depth++)
      {
         System.err.println("Inicio de la iteración " + depth);
         iterationNodes = 0;
         
         long iterationInitTime = System.currentTimeMillis();
         
         alfa = score - windowSize;
         beta = score + windowSize;
         
         aspirationSearchs++;
         score = alphaBeta(position, alfa, beta, 0, depth);

         if(score <= alfa || score >= beta)
         {
            aspirationSearchsFails++;
            
            alfa = INITIAL_ALFA;
            beta = INITIAL_BETA;
            
            score = alphaBeta(position, alfa, beta, 0, depth);
         }
         
         bestPrincipalVariation = principalVariation.getPrincipalVariation();
         
         int nodesPercent = (nodes + qnodes) > 0 ? ((100 * nodes) / (nodes + qnodes)) : 0;
         int qnodesPercent = (nodes + qnodes) > 0 ? ((100 * qnodes) / (nodes + qnodes)) : 0;
         int cutoffsPercent = (cutoffs + nodes) > 0 ? ((100 * cutoffs) / (cutoffs + nodes)) : 0;
         int qcutoffsPercent = (qcutoffs + qnodes) > 0 ? ((100 * qcutoffs) / (qcutoffs + qnodes)) : 0;
         int aspirationFailsPercent = aspirationSearchs > 0 ? (100 * aspirationSearchsFails) / aspirationSearchs : 0;
         System.err.println("Estadísticas búsquedas en profundidad " + depth + ":");
         System.err.println("------------------------------------------");
         System.err.println(" * Nodos recorridos: " + nodes + "(" + nodesPercent + "%)");
         System.err.println(" * Nodos quiescende recorridos: " + qnodes + "(" + qnodesPercent + "%)");
         System.err.println(" * Cortes producidos en nodos normales: " + cutoffs + "(" + cutoffsPercent  +"%)");
         System.err.println(" * Cortes producidos en nodos quiescence: " + qcutoffs + "(" + qcutoffsPercent  +"%)");
         System.err.println(" * Fallos en ventana de aspiración: " + aspirationSearchsFails + "(" + aspirationFailsPercent  +"%)");
         System.err.println(" * Tabla de tranposición: Aciertos: " + transpositionTable.getHits() + ", Fallos: " + transpositionTable.getMisses());
         System.err.println(" * Cortes por tabla de transposición: " + transpositionCutoffs);
         
         System.err.println(" * Tiempo iteración: " + ((System.currentTimeMillis() - iterationInitTime) / 1000));
         /*
         if(principalVariation.size() > 0)
            System.err.println(" * Mejor movimiento encontrado: " + Notation.toString(principalVariation.get(0)));
         System.err.print(" * VP: ");
         for(Movement m : principalVariation)
         {
           System.err.print(" " + Notation.toString(m));
         }
         System.err.println("");
         */
         
         //Si el valor es mate se devuelve
         if(score == CHECKMATE_SCORE || score == -CHECKMATE_SCORE)
         {
            System.err.println("Checkmate score");
            break;
         }
         
      }
      System.err.println("Fin de busqueda");
      
   }
   
   public int alphaBeta(Position position, int alpha, int beta, int currentDepth, int remainingPlies)
   {
      principalVariation.initDepth(currentDepth);
      
      transpositionMove = null;
      
      Entry.Type nodeType = Entry.Type.ALPHA;
      Movement bestMove = null;
      
      Colour turn = position.getTurn();
      
      //Se comprueban las tablas por repetición de posiciones
      if(position.getPositionsHash().get(position.getZobristKey().getKey()) >=3)
        return 0;
      
      //Se busca en las tablas de transposición
      Entry transpositionEntry = transpositionTable.get(position.getZobristKey().getKey());
      if(transpositionEntry != null)
      {
         Integer value = transpositionEntry.probe(remainingPlies, alpha, beta);
         if(value != null)
         {
            //principalVariation.saveInDepth(transpositionEntry.getBestMove(), currentDepth);

            if(transpositionEntry.getType() != Type.EXACT)
            {
               transpositionCutoffs++;
               return value;
            }
         }
         if(transpositionEntry.getType() != Type.ALPHA)
            transpositionMove = transpositionEntry.getBestMove();
      }
      
      //TODO Comprobación en la tabla de finales
      
      //Se generan los movimientos
      List<Movement> moves = position.getMovements();
      
      if(moves.size() == 0)
      {
         //Si no hay ningún movimiento legal en la posición
         //quiere decir que la posición es tablas por ahogado
         //o jaque mate dependiendo de si la posición es de jaque
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
      if(remainingPlies == 0)
      {
         return alphaBetaQuiescence(position, alpha, beta, currentDepth, 8);
      }
      
      //Se acualiza el número de nodos recorridos
      visitedNodes++;
      nodes++;
      iterationNodes++;
      
      //Se ordenan los movimientos
      this.sortMoves(moves, position, currentDepth);
      
      //Se itera a través de cada movimiento y se realiza
      //la búsqueda en las posiciones que resultan
      for(Movement move: moves)
      {  
         position.doMove(move);
         
         //Se calcula la evaluación del movimiento
         int score = - alphaBeta(position, -beta, -alpha, currentDepth + 1, remainingPlies - 1);
      
         //Se deshace el movimiento
         position.undoMove(move);
         
         if(score >= beta)
         {
            //Si la puntuación supera beta
            //se produce un corte
            cutoffs++;
            
            //Se añade el movimiento como movimiento 'killer para la profundidad'
            if(!move.equals(killerMoves[currentDepth][0]) && !move.equals(killerMoves[currentDepth][1]))
            {
               killerMoves[currentDepth][1] = killerMoves[currentDepth][0];
               killerMoves[currentDepth][0] = move;
            }
            ///////
            
            //Se añade el resultado a la tabla hash
            transpositionTable.save(position.getZobristKey().getKey(), 
                           remainingPlies, score, Entry.Type.BETA, 0, move);
            
            return beta;
         }
         if(score > alpha)
         {
            //
            nodeType = Entry.Type.EXACT;
            bestMove = move;
            
            //si la puntuación devuelta supera alfa
            //se ha encontrado un movimiento mejor
            alpha = score;
            
            numUpdates++;
            
            principalVariation.saveInDepth(move, currentDepth);
            
            
            if(currentDepth == 0)
            {
               bestScore = score;
               long time = System.currentTimeMillis();
               publishInfo((time - initTime) / 10, visitedNodes, depth, bestScore, principalVariation.getPrincipalVariation());
            }
         }
      }
      
      //Se guarda entrada en la tabla de transposición
      transpositionTable.save(position.getZobristKey().getKey(), 
                           remainingPlies, alpha, nodeType, 0, bestMove);
      
      return alpha;
   }
   
   public int alphaBetaQuiescence(Position position, int alpha, int beta, int currentDepth, int remainingPlies)
   {
      principalVariation.initDepth(currentDepth);
      
      Colour turn = position.getTurn();
      
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
         //Se realiza el siguiente movimiento en la lista
         position.doMove(move);
      
         //Se calcula la puntuación del movimiento
         score = - alphaBetaQuiescence(position, -beta, -alpha, currentDepth + 1, remainingPlies - 1);
         
         //Tras evaluar el movimiento se deshace
         position.undoMove(move);
         
         if(score >= beta)
         {
            qcutoffs++;
            
            return beta;
         }
         if(score > alpha)
         {
            numUpdates++;
            
            principalVariation.saveInDepth(move, currentDepth);
            
            alpha = score;
         }
      }
      
      return alpha;
      
   }
   
   
   public void sortMoves(List<Movement> moves, Position position, int currentDepth)
   {    
      //Se ordenan las capturas por MVV/LVA
      List<Movement> captures = position.getCaptureMovements();
      List<Movement> nonCaptures = position.getNonCaptureMovements();
      
      sortQuiescenceMoves(captures, position);
      
      moves.clear();
      moves.addAll(captures);
      moves.addAll(nonCaptures);
      
      //Se ordenan por killer moves
      Movement m = null;
      if(moves.contains(killerMoves[currentDepth][0]))
      {
         m = moves.remove(moves.lastIndexOf(killerMoves[currentDepth][0]));
         //System.err.println("===> Se ordena un movimiento killer 0 en pronfundidad " + currentDepth + " : " + Notation.toString(m));         
         moves.add(0, m);
      }
      if(moves.contains(killerMoves[currentDepth][1]))
      {
 
         m = moves.remove(moves.lastIndexOf(killerMoves[currentDepth][1]));
         //System.err.println("===> Se ordena un movimiento killer 1 en pronfundidad " + currentDepth + " : " + Notation.toString(m)); 
         moves.add(0, m);
      }
      ///////////////////////////////
      
      //Se añade el movimiento de la tabla de tranposicion en caso de que haya
      if(transpositionMove != null)
      {
         if(moves.contains(transpositionMove))
         {
            //System.err.println("** Se pone movimiento de la tabla de tranposicion primero **");
            m = moves.remove(moves.lastIndexOf(transpositionMove));
            moves.add(0, m);
         }
      }
      
   }
   
   public void sortQuiescenceMoves(List<Movement> moves, Position position)
   {
      Collections.sort(moves, mvvLvaComparator);
   }
   
   @Override
   public void setDepthLimit(int depthLimit)
   {
      super.setDepthLimit(depthLimit);
      
      killerMoves = new Movement[this.depthLimit][2];
   }
}
