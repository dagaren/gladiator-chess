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
package es.dagaren.gladiator.time;

import org.apache.log4j.Logger;

import es.dagaren.gladiator.representation.Colour;

/**
 * @author dagaren
 *
 */
public class Clock
{
   private Logger logger = Logger.getLogger(Clock.class);
   
   private TimeControl timeControl = null;
   
   private Stopwatch whiteStopwatch = new Stopwatch();
   private Stopwatch blackStopwatch = new Stopwatch();
   
   private int    initialMove = 1;
   private Colour initialTurn = Colour.WHITE;
   
   private int move    = initialMove;
   private Colour turn = initialTurn;
   
   private long[] times      = new long[2];
   private long[] startTimes = new long[2];
   
   States state = States.STOPPED;
   
   public synchronized void setTimeControl(TimeControl timeControl)
   {
      logger.debug("Cambiando control de tiempo");
      
      this.timeControl = timeControl; 
     
      if(this.state == States.STOPPED)
      {
         reset();
      }
   }
   
   public synchronized void setWhiteTime(long whiteTime)
   {
      logger.debug("Cambiando el tiempo de blancas a " + whiteTime);
      
      this.whiteStopwatch.setTime(whiteTime);
   }
   
   public synchronized long getWhiteTime()
   {
      return this.whiteStopwatch.getTime();
   }
   
   public synchronized void setBlackTime(long blackTime)
   {
      logger.debug("Cambiando el tiempo de las negras a " + blackTime);
      
      this.blackStopwatch.setTime(blackTime);
   }
   
   public synchronized long getBlackTime()
   {
      return this.blackStopwatch.getTime();
   }
   
   public synchronized void setMove(int move)
   {
      this.move = move;
   }
   
   public synchronized int getMove()
   {
      return this.move;
   }
   
   public synchronized void setTurn(Colour turn)
   {
      this.turn = turn;
   }
   
   public synchronized Colour getTurn()
   {
      return this.turn;
   }
   
   private void startTurn()
   {
      if(timeControl == null)
         throw new RuntimeException("Clock time control is null");
      
      times[Colour.WHITE.index] = whiteStopwatch.getTime();
      times[Colour.BLACK.index] = blackStopwatch.getTime();
      
      startTimes[Colour.WHITE.index] = times[Colour.WHITE.index];
      startTimes[Colour.BLACK.index] = times[Colour.BLACK.index];
      
      timeControl.startTurn(times, move, turn);
      
      whiteStopwatch.setTime(times[Colour.WHITE.index]);
      blackStopwatch.setTime(times[Colour.BLACK.index]);
      
      logger.debug("Iniciando turno. Tiempo de blancas: " + whiteStopwatch.getTime() +
            ", Tiempo de negras: " + blackStopwatch.getTime());
      
      if(turn == Colour.WHITE)
      {
         whiteStopwatch.start();
         blackStopwatch.stop();
      }
      else
      {
         blackStopwatch.start();
         whiteStopwatch.stop();
      }
   }
   
   private void endTurn()
   {
      if(timeControl == null)
         throw new RuntimeException("Clock time control is null");
      
      if(turn == Colour.WHITE)
         whiteStopwatch.stop();
      else
         blackStopwatch.stop();
      
      times[Colour.WHITE.index] = whiteStopwatch.getTime();
      times[Colour.BLACK.index] = blackStopwatch.getTime();
      
      timeControl.finishTurn(startTimes, times, move, turn);
      
      whiteStopwatch.setTime(times[Colour.WHITE.index]);
      blackStopwatch.setTime(times[Colour.BLACK.index]);
   }
   
   private void reset()
   {
      if(timeControl == null)
         throw new RuntimeException("Clock time control is null");
      
      whiteStopwatch.stop();
      blackStopwatch.stop();
      
      whiteStopwatch.setTime(timeControl.getStartTime(Colour.WHITE));
      blackStopwatch.setTime(timeControl.getStartTime(Colour.BLACK));
      
      times[Colour.WHITE.index] = whiteStopwatch.getTime();
      times[Colour.BLACK.index] = blackStopwatch.getTime();
      
      startTimes[Colour.WHITE.index] = times[Colour.WHITE.index];
      startTimes[Colour.BLACK.index] = times[Colour.BLACK.index];
      
      move = initialMove;
      turn = initialTurn;
      
      state = States.STOPPED;
   }
   
   
   public synchronized void init()
   {
      logger.debug("Inicializando reloj");
      
      reset();
   }
   
   public synchronized void start()
   {
      logger.debug("Poniendo en marcha reloj");
      
      if(state == States.STOPPED)
      {
         state = States.RUNNING;
         
         startTurn();
      }
   }
   
   
   public synchronized void pause()
   {
      logger.debug("Pausando reloj");
      
      if(state == States.RUNNING)
      {
         state = States.PAUSED;
         
         whiteStopwatch.stop();
         blackStopwatch.stop();
      }
   }
   
   public synchronized void resume()
   {
      logger.debug("Reanudando reloj");
      
      if(state == States.PAUSED)
      {
         state = States.RUNNING;
         
         if(turn == Colour.WHITE)
            whiteStopwatch.start();
         else
            blackStopwatch.start();
      }
   }
   
   
   public synchronized void switchClocks()
   {
      
      if(state == States.RUNNING || state == States.STOPPED)
      {
         this.endTurn();
         
         turn = turn.opposite();
         
         logger.debug("Pulsando reloj, iniciando el turno de " + turn.toString());
         
         if(turn == Colour.WHITE)
            move++;
         
         this.startTurn();
      }
   }
}
