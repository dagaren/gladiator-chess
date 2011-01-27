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
import es.dagaren.gladiator.representation.Colour;
import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.representation.Position;
import es.dagaren.gladiator.search.SearchInfo;

/**
 * @author dagaren
 *
 */
public class EngineAdapter implements UserToEngine, EngineObserver
{

   private Engine engine;
   private EngineController engineController;
   
   private boolean ponderingActive = false;
   
   private States state = States.OBSERVING;
   
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
      engine.stop();
      
      engine.setOwnTurn(Colour.BLACK);
      
      //TODO asociar, resetear y parar los relojes
      
      //TODO no ponderar en este momento
      
      engine.resetDepthLimit();
      
      engine.newGame();
      
      state = States.WAITING;
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
      engine.setOwnTurn(Colour.BLACK);
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#black()
    */
   @Override
   public synchronized void black()
   {
      engine.setOwnTurn(Colour.WHITE);
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
   
   @Override
   public synchronized void pause()
   {
      engine.stop();
   }
   
   @Override
   public synchronized void resume()
   {
      engine.resume();
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.communication.xboard.UserToEngine#ping(java.lang.String)
    */
   @Override
   public void ping(String n)
   {
      // TODO Auto-generated method stub  
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
      //Se hace que el motor no juegue ningún color
      
      //TODO se paran los relojes
      
      engine.stop();
      engine.setOwnTurn(null);
      
      state = States.OBSERVING;
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#go()
    */
   @Override
   public synchronized void go()
   {
      if(state == States.OBSERVING)
      {
         Colour turn = engine.getPosition().getTurn();
         
         engine.setOwnTurn(turn);
         
         //TODO asociar los relojes
         
         engine.think();
      }
   }
   
   @Override
   public synchronized void playother()
   {
      if(state == States.OBSERVING)
      {
         engine.setOwnTurn(engine.getPosition().getTurn().opposite());
         
         //Para que se active la ponderación en caso de que este permitida.
         engine.think();
      }
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
   public synchronized void usermove(Movement move)
   {
      if(move != null)
      {
         if(state == States.WAITING || state == States.PONDERING)
            state = States.THINKING;
         engine.doMove(move);
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
      engine.forceMove();
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#name(java.lang.String)
    */
   @Override
   public synchronized void name(String name)
   {
      //Se ignora el comando
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
      //Se ignora el comando
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#ptell(java.lang.String)
    */
   @Override
   public synchronized void ptell(String text)
   {
      //Se ignaora el comando
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#quit()
    */
   @Override
   public synchronized void quit()
   {
      state = States.OBSERVING;
      
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
      //Se ignora el comando
   }
   
   @Override
   public synchronized void ics(String hostname)
   {
      //Se ignora el comando
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
      //Se ignora el comando
      System.err.println("[EngineAdpater]: Se recibe comando result: " + result + comment);
   }

   public synchronized void setboard(String fen)
   {
      engine.stop();
      
      Position position = engine.getPosition();
      boolean isValid = position.loadFen(fen);
      
      if(!isValid)
      {
         engineController.tellusererror("Illegal position");
      }
      else
      {
         if(state == States.THINKING || state == States.PONDERING || state == States.WAITING)
         {
            engine.think();
         }
      }
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#sd(java.lang.String)
    */
   @Override
   public synchronized void sd(String depth)
   {
      try
      {
         int depthNumber = Integer.parseInt(depth);
         engine.setDepthLimit(depthNumber);
      }
      catch(Exception ex)
      {
         System.err.println("[EngineAdapter.sd]: Imposible convertir cadena a entero: " + depth);
      }
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#st(java.lang.String)
    */
   @Override
   public synchronized void st(String time)
   {
      // TODO Implementar
      System.err.println("[EngineAdapter.st]: Comando no implementado");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#time(java.lang.String)
    */
   @Override
   public synchronized void time(String n)
   {
      // TODO Implementar
      System.err.println("[EngineAdapter.time]: Comando no implementado");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#undo()
    */
   @Override
   public synchronized void undo()
   {
      // TODO Implementar
      System.err.println("[EngineAdapter.undo]: Comando no implementado");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#variant(java.lang.String)
    */
   @Override
   public synchronized void variant(String name)
   {
      //No se soporta ninguna variante
      engineController.error("command not implemented", "variant");
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#xboard()
    */
   @Override
   public synchronized void xboard()
   {
      engine.stop();
      
      state = States.OBSERVING;
   }
   
   
   @Override
   public synchronized void protover(String version)
   {
      try
      {
         int versionNum = Integer.parseInt(version);
         
         if(versionNum >= 2)
         {
            String features = 
               "ping=0 " +
               "setboard=1 " +
               "playother=1 " +
               "san=0 " +
               "usermove=1 " +
               "time=1 " +
               "draw=1 " +
               "sigint=0 " +
               "sigterm=0 " +
               "reuse=1 " +
               "analyze=0 " +
               "myname=\"Gladiator (v0.0.1)\" " +
               "variants=\"normal\" " +
               "colors=0 " +
               "ics=0 " +
               "name=0 " +
               "pause=1 " +
               "done=1";
            
            engineController.feature(features);
         }
      }
      catch(Exception ex)
      {
         
      }
      
   }
   
   @Override
   public synchronized void accepted(String feature)
   {
      //TODO completar
   }
   
   @Override
   public synchronized void rejected(String feature)
   {
      //TODO completar
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
      
      if(state == States.THINKING)
      {
         if(ponderingActive)
            state = States.PONDERING;
         else
            state = States.WAITING;
      }
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