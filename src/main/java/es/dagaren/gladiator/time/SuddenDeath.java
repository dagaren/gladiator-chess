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
public class SuddenDeath implements TimeControl
{
   private Logger logger = Logger.getLogger(SuddenDeath.class);
   
   private long time = 0;
   private long increment = 0;
   private IncrementTypes incrementType = null;
   
   public SuddenDeath(long time, long increment, IncrementTypes incrementType)
   {
      this.time = time;
      this.increment = increment;
      this.incrementType = incrementType;
      
      logger.debug("Creando sudden death:" +
            ", tiempo: " + this.time +
            ", incremento: " + this.increment +
            ", tipo: " + this.incrementType.name());
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.time.TimeControl#startTurn(long[], int, es.dagaren.gladiator.representation.Colour)
    */
   @Override
   public void startTurn(long[] times, int numMove, Colour turn)
   {
      if(numMove == 1 && turn == Colour.WHITE)
      {
         times[Colour.WHITE.index] = time;
         times[Colour.BLACK.index] = time;
      }
         
      if(incrementType == IncrementTypes.FISCHER)
      {
         times[turn.index] += increment * 1000;
      }
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.time.TimeControl#endTurn(long[], int, es.dagaren.gladiator.representation.Colour)
    */
   @Override
   public void finishTurn(long[] initTimes, long[] times, int numMove, Colour turn)
   {
      if(incrementType == IncrementTypes.BRONSTEIN)
      {
         long timespan = initTimes[turn.index] - times[turn.index];
         if(timespan >= increment)
            times[turn.index] += increment * 1000;
         else
            times[turn.index] += timespan * 1000;
      }
   }

  
   @Override
   public long getStartTime(Colour turn)
   {
      return time;
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.time.TimeControl#getNumMoves()
    */
   @Override
   public int getNumMoves()
   {
      return 0;
   }
}
