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

import es.dagaren.gladiator.representation.Colour;

/**
 * @author dagaren
 *
 */
public class Clock
{
   private TimeControl timeControl = null
   ;
   private Stopwatch whiteStopwatch = new Stopwatch();
   private Stopwatch blackStopwatch = new Stopwatch();
   
   private int initialMove    = 1;
   private Colour initialTurn = Colour.WHITE;
   
   private int move    = initialMove;
   private Colour turn = initialTurn;
   
   private long[] times      = new long[2];
   private long[] startTimes = new long[2];
   
   States state = States.STOPPED;
   
   public synchronized void setTimeControl(TimeControl timeControl)
   {
      this.timeControl = timeControl; 
     
      if(this.state == States.STOPPED)
      {
         reset();
      }
   }
   
   public synchronized void setWhiteTime(long whiteTime)
   {
      this.whiteStopwatch.setTime(whiteTime);
   }
   
   public synchronized long getWhiteTime()
   {
      return this.whiteStopwatch.getTime();
   }
   
   public synchronized void setBlackTime(long blackTime)
   {
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
      reset();
   }
   
   public synchronized void start()
   {
      if(state == States.STOPPED)
      {
         state = States.RUNNING;
         
         startTurn();
      }
   }
   
   
   public synchronized void pause()
   {
      if(state == States.RUNNING)
      {
         state = States.PAUSED;
         
         whiteStopwatch.stop();
         blackStopwatch.stop();
      }
   }
   
   public synchronized void resume()
   {
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
         
         if(turn == Colour.WHITE)
            move++;
         
         this.startTurn();
      }
   }
}
