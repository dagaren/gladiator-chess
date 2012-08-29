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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import es.dagaren.gladiator.evaluation.Evaluator;
import es.dagaren.gladiator.notation.Notation;
import es.dagaren.gladiator.representation.BitboardPosition;
import es.dagaren.gladiator.representation.BitboardSee;
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
   private Logger logger = Logger.getLogger(AlphaBetaSearcher.class);
   
   protected long numUpdates;
   
   //Constantes de búsqueda
   protected final int INITIAL_ALPHA = -10000 - 1;
   protected final int INITIAL_BETA =  10000 + 1;
   
   //Arrays de movimientos killer;
   protected Movement[][] killerMoves = new Movement[this.depthLimit][2];
   
   //Comparador MVV LVA
   protected Comparator<Movement> mvvLvaComparator = new MvvLvaMoveComparator();
   
   //Comparador de valor de movimeintos
   protected Comparator<Movement> moveValueComparator = new MoveValueComparator();
   
   //La variante principal
   protected PrincipalVariation principalVariation = new PrincipalVariation();
   
   protected int depth;
   protected int bestScore;
   
   //La tabla de transposicion
   protected Table transpositionTable = new Table(100000);   //(52428800);
   
   protected Movement transpositionMove = null;
   
   protected List<Movement> movesForDelete = new ArrayList<Movement>(30);
   
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
   //Número de búsquedas con ventana de aspiración que fallan por inestabilidad
   protected int aspirationSearchInstabilityFails = 0;
   //Número de cortes producidos por aciertos en la tabla de transposición
   protected int transpositionCutoffs = 0;
   //Número de aciertos con el primer movimiento en la búsqueda
   protected int firstMoveHits = 0;
   //Número de aciertos en pvs
   protected int pvsHits = 0;
   //Número de fallos de pvs
   protected int pvsFails = 0;
   //Búsquedas con movimiento de transposición
   protected int withTranspositionMove = 0;
   //Búsquedas sin movimiento de transposición
   protected int withoutTranspositionMove = 0;
   //Futility prunings
   protected int futilityPrunings = 0;
   
   
   
   int alpha = 0;
   int beta = 0;
   
   int windowSize = 50;
   
   @Override
   public void search()
   {
      numUpdates = 0;
      
      visitedNodes  = 0;
      cutoffs       = 0;
      qcutoffs      = 0;
      nodes         = 0;
      qnodes        = 0;
      firstMoveHits = 0;
      transpositionCutoffs = 0;
      
      aspirationSearchs                = 0;
      aspirationSearchsFails           = 0;
      aspirationSearchInstabilityFails = 0;
      
      pvsHits  = 0;
      pvsFails = 0;
      
      withTranspositionMove    = 0;
      withoutTranspositionMove = 0;
      
      futilityPrunings = 0;
      
      bestPrincipalVariation = new Movement[0];
      bestScore              = 0;
      
      int score = 0;
      
      //Se implementa la profundidad iterativa
      for(depth = 1; depth <= depthLimit; depth++)
      {
         logger.info("Comenzando búsqueda para profundidad " + depth);
         
         long iterationInitTime = System.currentTimeMillis();
         
         iterationNodes = 0;
         
         //Se calculan los valores de alpha y beta iniciales
         //para utilizando ventanas de aspiración
         alpha = score - windowSize;
         beta  = score + windowSize;
         
         aspirationSearchs++;
         
         score = alphaBeta(position, alpha, beta, 0, depth);
         
         if(score == EXIT_SCORE)
            break;

         if(score <= alpha || score >= beta) //Se ha producido un corte
         {
            //Se ha producido un corte en la búsqueda con ventanas 
            //de aspiración, es necesario repetir la búsqueda.
            aspirationSearchsFails++;
            
            if(score <= alpha) //Es un corte por lo bajo
            {
               alpha = INITIAL_ALPHA;
               beta  = score;
            }
            else if(score >= beta) //Es un corte por lo alto
            {
               alpha = score;
               beta  = INITIAL_BETA;
            }
            
            score = alphaBeta(position, alpha, beta, 0, depth);
            
            if(score == EXIT_SCORE)
               break;
         }
         
         if(score <= alpha || score >= beta) //Se ha producido segundo corte
         {
            aspirationSearchInstabilityFails++;
            
            //Se ha producido una situación extraña en la que la segunda
            //búsqueda de la ventana de aspiración también falla, debido
            //a la denominada 'inestabilidad de búsqueda'. Como solución 
            //se vuelve a repetir la búsqueda con los valores iniciales 
            //de alpha y beta
            alpha = INITIAL_ALPHA;
            beta  = INITIAL_BETA;
            
            score = alphaBeta(position, alpha, beta, 0, depth);
            
            if(score == EXIT_SCORE)
               break;
         }
         
         bestPrincipalVariation = principalVariation.getPrincipalVariation();
         bestScore              = score;
         
         String pv = "";
         for(Movement m : bestPrincipalVariation)
         {
            pv += " ";
            pv += Notation.toString(m);
         }
         logger.info("Variante principal en la profundidad " + depth + ": " + pv);
         logger.info("Mejor puntuación en la profundidad " + depth + ": " + bestScore);
         
         
         int nodesPercent = (nodes + qnodes) > 0 ? ((100 * nodes) / (nodes + qnodes)) : 0;
         int qnodesPercent = (nodes + qnodes) > 0 ? ((100 * qnodes) / (nodes + qnodes)) : 0;
         int cutoffsPercent = (cutoffs + nodes) > 0 ? ((100 * cutoffs) / (cutoffs + nodes)) : 0;
         int qcutoffsPercent = (qcutoffs + qnodes) > 0 ? ((100 * qcutoffs) / (qcutoffs + qnodes)) : 0;
         int aspirationFailsPercent = aspirationSearchs > 0 ? (100 * aspirationSearchsFails) / aspirationSearchs : 0;
         int pvsHitsPercent = (pvsHits + pvsFails) > 0 ? (100 * pvsHits) / (pvsHits + pvsFails) : 0;
         int withTranspositionPercent = (withTranspositionMove + withoutTranspositionMove) > 0 ? (100 * withTranspositionMove) / (withTranspositionMove + withoutTranspositionMove) : 0;
         
         logger.debug("Estadísticas búsquedas en profundidad " + depth + ":");
         logger.debug("------------------------------------------");
         logger.debug(" * Nodos recorridos: " + nodes + "(" + nodesPercent + "%)");
         logger.debug(" * Nodos quiescende recorridos: " + qnodes + "(" + qnodesPercent + "%)");
         logger.debug(" * Cortes producidos en nodos normales: " + cutoffs + "(" + cutoffsPercent  +"%)");
         logger.debug(" * Cortes producidos en nodos quiescence: " + qcutoffs + "(" + qcutoffsPercent  +"%)");
         logger.debug(" * Fallos en ventana de aspiración: " + aspirationSearchsFails + "(" + aspirationFailsPercent  +"%)");
         logger.debug(" * Fallos en ventana de aspiración por inestabilidad: " + aspirationSearchInstabilityFails);
         logger.debug(" * Tabla de tranposición: Aciertos: " + transpositionTable.getHits() + ", Fallos: " + transpositionTable.getMisses());
         logger.debug(" * Cortes por tabla de transposición: " + transpositionCutoffs);
         logger.debug(" * Aciertos PVS: " + pvsHits + "(" + pvsHitsPercent + "%)");
         logger.debug(" * Fallos   PVS: " + pvsFails + "(" + (100 - pvsHitsPercent) + "%)");
         logger.debug(" * Busquedas con transposicion: " + withTranspositionMove + "(" + withTranspositionPercent + "%)");
         logger.debug(" * Busquedas sin transposicion: " + withoutTranspositionMove + "(" + (100 - withTranspositionPercent) + "%)");
         logger.debug(" * Futility prunings: " + futilityPrunings);
         
         
         logger.debug(" * Tiempo iteración: " + ((System.currentTimeMillis() - iterationInitTime) / 1000));
         
         //Si el valor es mate se termina la búsqueda
         if(score == CHECKMATE_SCORE || score == -CHECKMATE_SCORE)
         {
            break;
         }  
      }
      
   }
   
   public int alphaBeta(Position position, int alpha, int beta, int currentDepth, int remainingPlies)
   {
      principalVariation.initDepth(currentDepth);
      
      //Se comprueba si la búsqueda permanece activa. En
      //caso contrario, se devuelve el valor de fin de búsqueda
      if(!search) return EXIT_SCORE;
      
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
            principalVariation.saveInDepth(transpositionEntry.getBestMove(), currentDepth);

            if(transpositionEntry.getType() != Type.EXACT)
            {
               transpositionCutoffs++;
               return value;
            }
         }
         if(transpositionEntry.getType() != Type.ALPHA)
            transpositionMove = transpositionEntry.getBestMove();
      }
      
      if(transpositionMove != null)
         withTranspositionMove++;
      else
         withoutTranspositionMove++;
      
      //TODO Comprobación en la tabla de finales
      
      //Se generan los movimientos
      List<Movement> moves = getMovesList(currentDepth); 
      
      position.getMoves(moves);
      
      if(moves.size() == 0)
      {
         //Si no hay ningún movimiento legal en la posición
         //quiere decir que la posición es tablas por ahogado
         //o jaque mate
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
      
      boolean isFirstMove = true; 
      
      int staticEval = 0;
      boolean onFutility = false;
      if(remainingPlies == 1)
      {
         onFutility = true;
         staticEval = evaluator.evaluate(position);
      }
     

      //Se itera a través de cada movimiento y se realiza
      //la búsqueda en las posiciones que resultan
      for(Movement move: moves)
      {  
         //Futility pruning en el último nivel antes de quiescence
         if(onFutility &&
            !isFirstMove && 
            move.getDestinationPiece() != null)
         {
            if((staticEval + 125) <= alpha)
            {
               futilityPrunings++;
               continue;
            }
         }
         
         //Se hace el siguiente movimiento en la lista
         position.doMove(move);
         
         
         //Principal Variation Search
         int score;
         
         if(isFirstMove)
         {
            score = - alphaBeta(position, -beta, -alpha, currentDepth + 1, remainingPlies - 1);
            
            isFirstMove = false;
         }
         else
         {
            score = - alphaBeta(position, -alpha -1 , -alpha, currentDepth + 1, remainingPlies - 1);
            
            if(score > alpha)
            {
               pvsFails++;
               score = - alphaBeta(position, -beta, -alpha, currentDepth + 1, remainingPlies - 1);
            }
            else
            {
               pvsHits++;  
            }
         }
         
         //Se deshace el movimiento
         position.undoMove(move);
         
         if(Math.abs(score) == EXIT_SCORE)
            return EXIT_SCORE;
         
         if(score >= beta)
         {
            //Al superar el valor de beta se produce un corte
            cutoffs++;
            
            //Se añade el movimiento como movimiento 'killer' para la profundidad
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
            
            //Se ha encontrado un movimiento mejor para la posición,
            //se actualiza el valor alfa y se guarda el movimiento
            alpha = score;
            
            numUpdates++;
            
            principalVariation.saveInDepth(move, currentDepth);
            
            if(currentDepth == 0)
            {
               //Se ha encontrado un mejor movimiento en la posicion
               //inicial de búsqueda, por lo que se publica
               bestScore = score;
               long time = System.currentTimeMillis();
               publishInfo((time - initTime) / 10, visitedNodes, depth, bestScore, principalVariation.getPrincipalVariation());
            }
         }
      }
      
      //Se guarda entrada en la tabla de transposición
      transpositionTable.save(position.getZobristKey().getKey(), 
                           remainingPlies, alpha, nodeType, 0, bestMove);
      
      //No se ha encontrado ningún movimiento que mejore el valor de alfa,
      //por lo que se devuelve
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
      List<Movement> moves = getMovesList(currentDepth); 

      position.getMoves(moves);
      
      if(moves.size() == 0)
      {
         //Si no hay ningún movimiento legal en la posición
         //quiere decir que la posición es tablas por ahogado
         //o jaque mate
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
      
      //Se intenta actualizar alfa con el puntaje devuelto
      //por la evaluación estática.
      //Esto es necesario ya que como solo se van a comprobar 
      //los movimientos de captura es necesario establecer 
      //este valor para que se vea si las capturas generan
      //mejor resultado, y si no devolver el valor de la puntuación 
      //estática.
      if(score >= beta)
      {  
         return beta;
      }
      if(score >= alpha)
      {
         alpha = score;
      }
      
      //Se ordenan los movimientos
      this.sortQuiescenceMoves(moves, position);

      //Se itera a través de cada movimiento de captura y se realiza
      //la búsqueda en las posiciones que resultan
      for(Movement move: moves)
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
      
      for(Movement move: moves)
      {
         //
         if(move.getDestinationPiece() != null)
         {
            //Si es una captura se calcula su valor see
            move.setValue(BitboardSee.getValue((BitboardPosition)position, 
                                                move.getSource(), 
                                                move.getDestination(), 
                                                move.getDestinationPiece().genericPiece));
         }
         else
         {
            //Se le asigna un valor al movimiento dependiente de la
            //casilla de destino y la pieza
            move.setValue(evaluator.evaluateMove(move));
         }
         
         //A los movimientos que esten en los killer se les da un valor alto
         if(move.equals(killerMoves[currentDepth][0]))
         {
            move.setValue(1000);
         }
         if(move.equals(killerMoves[currentDepth][1]))
         {
            move.setValue(1000);
         }
         
         //Si es el movimiento encontrado en la tabla de transposición se le da un
         //valor muy alto
         if(transpositionMove != null)
         {
            if(move.equals(transpositionMove))
            {
               move.setValue(20000);
            }
         }

      }
      
      //Se ordenan los movimientos en función del valor que se le ha asignado
      Collections.sort(moves, moveValueComparator);
      
   }
   
   public void sortQuiescenceMoves(List<Movement> moves, Position position)
   {

      movesForDelete.clear();
      for(Movement move: moves)
      {
         if(move.getDestinationPiece() != null)
         {
            //Si es una captura se calcula su valor see
            move.setValue(BitboardSee.getValue((BitboardPosition)position, 
                                                move.getSource(), 
                                                move.getDestination(), 
                                                move.getDestinationPiece().genericPiece));
            if(move.getValue() < 0)
               movesForDelete.add(move);
         }
         else
         {
            //Si no es una captura se elimina de la lista
            movesForDelete.add(move);
         }
      }
      moves.removeAll(movesForDelete);

      
    //Se ordenan los movimientos en función del valor que se le ha asignado
      Collections.sort(moves, moveValueComparator);
   }
   
   @Override
   public void setDepthLimit(int depthLimit)
   {
      super.setDepthLimit(depthLimit);
      
      killerMoves = new Movement[this.depthLimit][2];
   }
}
