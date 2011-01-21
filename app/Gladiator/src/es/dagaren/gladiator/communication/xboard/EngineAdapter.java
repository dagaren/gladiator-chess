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
package es.dagaren.gladiator.communication.xboard;

import es.dagaren.gladiator.engine.Engine;
import es.dagaren.gladiator.engine.EngineObserver;
import es.dagaren.gladiator.notation.Notation;
import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.search.SearchInfo;

/**
 * @author dagaren
 *
 */
public class EngineAdapter implements UserToEngine, EngineObserver
{

   private Engine engine;
   private EngineController engineController;
   
   
   /**
    * @param engineController the engineController to set
    */
   public void setEngineController(EngineController engineController)
   {
      this.engineController = engineController;
   }

   /**
    * @return the engineController
    */
   public EngineController getEngineController()
   {
      return engineController;
   }

   /**
    * @param engine the engine to set
    */
   public void setEngine(Engine engine)
   {
      this.engine = engine;
      this.engine.setObserver(this);
   }

   /**
    * @return the engine
    */
   public Engine getEngine()
   {
      return engine;
   }
   
   
   
   
   
   
   
   /** IMPLEMENTACIÓN DE MÉTODOS DE USERTOENGINE */
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#New()
    */
   @Override
   public synchronized void New()
   {
      engine.newGame();
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#analyze()
    */
   @Override
   public synchronized void analyze()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: analyze");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#bk()
    */
   @Override
   public synchronized void bk()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: bk");
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#white()
    */
   @Override
   public synchronized void white()
   {
      // TODO Implementar (comando no obsoleto en la versión 2 del protocolo 
      System.err.println("Comando no implementado: white");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#black()
    */
   @Override
   public synchronized void black()
   {
   // TODO Implementar (comando obsoleto en la versión 2 del protocolo
      System.err.println("Comando no implementado: black");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#computer()
    */
   @Override
   public synchronized void computer()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: computer");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#draw()
    */
   @Override
   public synchronized void draw()
   {
      //TODO Implementar
      System.err.println("Comando no implementado: draw");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#easy()
    */
   @Override
   public synchronized void easy()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: easy");
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#hard()
    */
   @Override
   public synchronized void hard()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: hard");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#edit()
    */
   @Override
   public synchronized void edit()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: edit");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#force()
    */
   @Override
   public synchronized void force()
   {
      engine.stop();
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#go()
    */
   @Override
   public synchronized void go()
   {
      engine.move();
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#hint()
    */
   @Override
   public synchronized void hint()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: hint");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#holding(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
    */
   @Override
   public synchronized void holding(String white, String black, String colour, String piece)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: holding");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#level(java.lang.String, java.lang.String, java.lang.String)
    */
   @Override
   public synchronized void level(String numMoves, String time, String increment)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: level");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#move(es.dagaren.gladiator.representation.Movement)
    */
   @Override
   public synchronized void move(Movement move)
   {
      if(move != null)
      {
         engine.opponentMove(move);
      }
      else
      {
         engineController.error("movimiento incorrecto", "move");
      }
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#moveNow()
    */
   @Override
   public synchronized void moveNow()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: moveNow");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#name(java.lang.String)
    */
   @Override
   public synchronized void name(String name)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: name");
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#post()
    */
   @Override
   public synchronized void post()
   {
      engine.setPublishSearchInfo(true);
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#nopost()
    */
   @Override
   public synchronized void nopost()
   {
      engine.setPublishSearchInfo(false);
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#otim(java.lang.String)
    */
   @Override
   public synchronized void otim(String n)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: otim");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#partner(java.lang.String)
    */
   @Override
   public synchronized void partner(String player)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: partner");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#partner()
    */
   @Override
   public synchronized void partner()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: partner");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#ptell(java.lang.String)
    */
   @Override
   public synchronized void ptell(String text)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: ptell");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#quit()
    */
   @Override
   public synchronized void quit()
   {
      engine.finish();
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#random()
    */
   @Override
   public synchronized void random()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: random");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#rating()
    */
   @Override
   public synchronized void rating()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: rating");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#remove()
    */
   @Override
   public synchronized void remove()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: remove");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#result(java.lang.String, java.lang.String)
    */
   @Override
   public synchronized void result(String result, String comment)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: result");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#sd(java.lang.String)
    */
   @Override
   public synchronized void sd(String deep)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: sd");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#st(java.lang.String)
    */
   @Override
   public synchronized void st(String time)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: st");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#time(java.lang.String)
    */
   @Override
   public synchronized void time(String n)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: time");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#undo()
    */
   @Override
   public synchronized void undo()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: undo");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#variant(java.lang.String)
    */
   @Override
   public synchronized void variant(String name)
   {
      // TODO Implementar
      System.err.println("Comando no implementado: variant");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#xboard()
    */
   @Override
   public synchronized void xboard()
   {
      // TODO Implementar
      System.err.println("Comando no implementado: xboard");
   }

   
   /** IMPLEMENTACIÓN DE METODOS DE LA INTERFAZ ENGINE_OBSERVER **/
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.engine.EngineObserver#onGameFinished(java.lang.String, java.lang.String)
    */
   @Override
   public void onGameFinished(String result, String reason)
   {
      engineController.result(result, reason);
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.engine.EngineObserver#onIncorrectMove(es.dagaren.gladiator.representation.Movement)
    */
   @Override
   public void onIncorrectMove(Movement move)
   {
      engineController.illegalMove(Notation.toString(move), "");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.engine.EngineObserver#onMoveDone(es.dagaren.gladiator.representation.Movement)
    */
   @Override
   public void onMoveDone(Movement selectedMove)
   {
      engineController.move(Notation.toString(selectedMove));
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.engine.EngineObserver#onPublishInfo(es.dagaren.gladiator.search.SearchInfo)
    */
   @Override
   public void onPublishInfo(SearchInfo info)
   {
      String sinfo = "";
      
      sinfo += info.deep;
      sinfo += " ";
      sinfo += info.score;
      sinfo += " ";
      sinfo += info.time;
      sinfo += " ";
      sinfo += info.nodes;
      
      for(Movement m : info.principalVariation)
      {
         sinfo += " ";
         sinfo += Notation.toString(m);
      }
      
      engineController.sendCommand(sinfo);
   }
   /** FIN IMPLEMENTACIÓN DE METODOS DE LA INTERFAZ ENGINE_OBSERVER **/

}