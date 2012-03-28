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

/**
 * @author dagaren
 *
 */
public class Stopwatch
{
   private long time = 0;
   private long startTime = 0;
   private States state = States.STOPPED;
   
   public synchronized void setTime(long time)
   {
      this.time = time;
      
      if(state == States.RUNNING)
         this.startTime = System.currentTimeMillis();
   }
   
   public synchronized long getTime()
   {
      if(state == States.STOPPED)
      {
         return this.time;
      }
      else if(state == States.RUNNING)
      {
         return time - (System.currentTimeMillis() - startTime);
      }
      
      return -1;
   }
   
   public synchronized void start()
   {
      if(state == States.STOPPED)
      {
         this.startTime = System.currentTimeMillis();
         state = States.RUNNING;
      }
   }
   
   public synchronized void stop()
   {
      this.time = this.getTime();
      
      state = States.STOPPED;
   }
}
