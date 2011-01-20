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
package es.dagaren.gladiator.engine;

import java.util.LinkedList;
import java.util.List;

import es.dagaren.gladiator.representation.BitboardPosition;
import es.dagaren.gladiator.representation.Colour;
import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.representation.Position;
import es.dagaren.gladiator.search.AlphaBetaSearcher;
import es.dagaren.gladiator.search.SearchInfo;
import es.dagaren.gladiator.search.Searcher;
import es.dagaren.gladiator.search.SearcherObserver;

/**
 * @author dagaren
 *
 */
public class Engine implements SearcherObserver
{
   protected final String name    = "Gladiator";
   protected final String version = "0.2";
   protected final String rating  = "(Unknown)";
   
   
   
   protected boolean  active = true;
   
   protected Position position;
   protected Searcher searcher; //TODO descomentar
   
   private boolean publishSearchInfo = false;
   
   protected Colour ownTurn;
   protected Colour gameTurn;
   
   protected EngineObserver observer;

   protected int deepLimit;
   
   protected boolean debug;
   
   public Engine()
   {
      searcher = new AlphaBetaSearcher();
      searcher.setObserver(this);
      
      ownTurn = Colour.BLACK;
   }
   
   public synchronized void newGame()
   {
      searcher.stop();
      
      position = new BitboardPosition();
      position.setInitialPosition();
      
      System.err.println(position.toString());
      
      gameTurn = position.getTurn();
      
      if(gameTurn == ownTurn)
      {
         searcher.initSearch(this.position);
      }
   }
   
   public synchronized void move()
   {
      
   }
   
   public synchronized void stop()
   {
      //searcher.stop();
   }
   
   public synchronized void finish()
   {
      active = false;
      
      searcher.finish();
      
      this.notifyAll();
   }
   
   public synchronized void waitForFinish()
   {
      while(active == true)
      {
         try
         {
            this.wait();
         }
         catch(Exception ex)
         {}
      }
   }
   
   public synchronized void opponentMove(Movement move)
   {
      if(ownTurn != gameTurn)
      {
         if(!position.isValidMove(move))
         {
            observer.onIncorrectMove(move);
            
            return;
         }
         
         move = getFullMove(move);
         
         position.doMove(move);
      }
      
      if(position.isCheckmate())
      {
         String result = position.getTurn() == Colour.WHITE ? "0-1" : "1-0";
         observer.onGameFinished(result, "Jaque Mate");
      }
      else if(position.isStalemate())
      {
         observer.onGameFinished("1/2-1/2", "Ahogado");
      }
      
      //TODO falta comprobante de tablas normales
      
      gameTurn = position.getTurn();
      
      if(gameTurn == ownTurn)
      {
         searcher.initSearch(this.position);
      }
   }
   
   //TODO ver como quitar esto
   public Movement getFullMove(Movement move)
   {
      List<Movement> legalMoves = position.getMovements();
      
      Movement m = legalMoves.get(legalMoves.indexOf(move));
      
      return m;
   }

   
   
   
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.search.SearcherObserver#onPublishInformation(es.dagaren.gladiator.search.SearchInfo)
    */
   @Override
   public void onInformationPublished(SearchInfo info)
   {
      if(publishSearchInfo)
      {
         if(this.observer != null)
            this.observer.onPublishInfo(info);
      }
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.search.SearcherObserver#onSearchFinished(es.dagaren.gladiator.search.Searcher)
    */
   @Override
   public void onSearchFinished(Searcher searcher)
   {
      LinkedList<Movement> pv = searcher.getPrincipalVariation();
      
      if(pv != null && pv.size() > 0)
      {
         Movement selectedMove = pv.get(0);
         
         //TODO comprobar si el movimiento es legal
         position.doMove(selectedMove);
         
         System.err.println(position.toString());
         
         if(position.isCheckmate())
         {
            String result = position.getTurn() == Colour.WHITE ? "0-1" : "1-0";
            observer.onGameFinished(result, "Jaque mate");
         }
         else if(position.isStalemate())
         {
            observer.onGameFinished("1/2-1/2", "Ahogado");
         }
         
         //TODO falta comprobar por tablas normales
         gameTurn = position.getTurn();
         
         observer.onMoveDone(selectedMove);
      }
      else
      {
         System.err.println("[Engine.onSearchFinished] ERROR: LA VARIANTE PRINCIPAL ES NULA O VACIA");
         System.exit(0);
      }
   }

   /**
    * @param publishSearchInfo the publishSearchInfo to set
    */
   public void setPublishSearchInfo(boolean publishSearchInfo)
   {
      this.publishSearchInfo = publishSearchInfo;
   }

   /**
    * @return the publishSearchInfo
    */
   public boolean isPublishSearchInfo()
   {
      return publishSearchInfo;
   }
   
   public EngineObserver getObserver()
   {
      return observer;
   }

   public void setObserver(EngineObserver observer)
   {
      this.observer = observer;
   }
}
