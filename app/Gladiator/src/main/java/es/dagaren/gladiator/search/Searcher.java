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
import java.util.LinkedList;
import java.util.List;

import es.dagaren.gladiator.evaluation.Evaluator;
import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.representation.Position;

/**
 * @author dagaren
 * 
 */
public class Searcher implements Runnable
{
   /* Variables de límites de la búsqueda */
   // Profundidad límite
   protected int              depthLimit;
   // Tiempo límite
   protected long             timeLimit;
   // Número de nodos límite
   protected long             nodesLimit;

   // Tiempo de inicio de la búsqueda
   protected long             initTime        = 0;

   protected volatile boolean search;
   protected volatile boolean exit;
   
   //Lista de listas de movimientos para cada profundidad
   List<List<Movement>>       movesLists;

   // La variante principal
   protected Movement[]       bestPrincipalVariation;
   
   //Puntaje de la variante principal
   protected int              bestScore;

   // El hilo dedicado a las búsquedas
   protected Thread           thread;

   // La posición de búsqueda
   protected Position         position;

   // El evaluador de posiciones estáticas
   protected Evaluator        evaluator;

   // Variables de recolección de estadísticas
   // Nodos visitados en la búsqueda
   protected long             visitedNodes;
   protected long             cutoffs;

   // Constantes
   protected final int        DRAW_SCORE      = 0;
   protected final int        CHECKMATE_SCORE = -10000;
   
   protected final int        EXIT_SCORE = 10001;

   protected final int        MIN_RATING      = -10000 - 1;
   protected final int        MAX_RATING      = 10000 + 1;

   protected SearcherObserver observer;

   public Searcher()
   {
      depthLimit = 100;
      timeLimit = -1;
      nodesLimit = -1;

      bestPrincipalVariation = new Movement[0];
      
      bestScore = 0;
      
      movesLists = new ArrayList<List<Movement>>(32);

      evaluator = new Evaluator();

      exit = false;
      search = false;

      thread = new Thread(this);
      thread.start();
   }

   public void run()
   {
      searchLoop();
   }

   public synchronized void initSearch(Position position)
   {
      this.position = position.getCopy();

      search = true;

      notifyAll();
   }

   public synchronized void finish()
   {
      exit = true;

      search = false;

      notifyAll();
   }

   public void searchLoop()
   {
      while (!exit)
      {
         synchronized (this)
         {
            // Se mantiene el hilo en espera
            while (!search && !exit)
            {
               try
               {
                  wait();
               }
               catch (Exception ex)
               {
               }
            }

            if (exit == true)
               break;

            // Se va a iniciar una nueva búsqueda,
            // se resetean los valores

            // Se limpia la variante principal
            bestPrincipalVariation = new Movement[0];
            bestScore              = 0;

            // Se resetea los nodos recorridos
            visitedNodes = 0;

            // Se inicia el tiempo de búsqueda
            initTime = System.currentTimeMillis();
         }

         // Se llama a la función de búsquda
         search();

         synchronized (this)
         {
            // La búsqueda ha finalizado
            search = false;

            if (observer != null)
               observer.onSearchFinished(this);
         }
      }
   }

   protected void publishInfo(long time, long nodes, long depth, long score,
         Movement[] principalVariation)
   {
      SearchInfo info = new SearchInfo();
      info.time = time; // (time - initTime) / 10;
      info.principalVariation = principalVariation;
      info.nodes = nodes;
      info.depth = depth;
      info.score = score;

      if (observer != null)
         observer.onInformationPublished(info);
   }

   public void search()
   {
   }

   public synchronized void stop()
   {
      search = false;
   }

   public int getDepthLimit()
   {
      return depthLimit;
   }

   public void setDepthLimit(int depthLimit)
   {
      this.depthLimit = depthLimit;
   }

   public long getTimeLimit()
   {
      return timeLimit;
   }

   public void setTimeLimit(long timeLimit)
   {
      this.timeLimit = timeLimit;
   }

   public long getNodesLimit()
   {
      return nodesLimit;
   }

   public void setNodesLimit(long nodesLimit)
   {
      this.nodesLimit = nodesLimit;
   }

   public Movement[] getPrincipalVariation()
   {
      return bestPrincipalVariation;
   }
   
   public int getScore()
   {
      return bestScore;
   }

   public Position getPosition()
   {
      return position;
   }

   public void setPosition(Position position)
   {
      this.position = position;
   }

   public Evaluator getEvaluator()
   {
      return evaluator;
   }

   public void setEvaluator(Evaluator evaluator)
   {
      this.evaluator = evaluator;
   }

   public SearcherObserver getObserver()
   {
      return observer;
   }

   public void setObserver(SearcherObserver observer)
   {
      this.observer = observer;
   }

   public long getVisitedNodes()
   {
      return visitedNodes;
   }
   
   public List<Movement> getMovesList(int depth)
   {
      List<Movement> list;
      
      try
      {
         list = movesLists.get(depth);
         if(list == null)
         {
            list = new ArrayList<Movement>(35);
            
            movesLists.add(depth, list);
         }
      }
      catch(IndexOutOfBoundsException e)
      {
         list = new ArrayList<Movement>(35);
         
         movesLists.add(depth, list);
      }
      
      list.clear();
      
      return list;
   }

}
