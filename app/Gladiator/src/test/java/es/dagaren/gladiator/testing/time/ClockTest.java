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
package es.dagaren.gladiator.testing.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import es.dagaren.gladiator.time.Clock;
import es.dagaren.gladiator.time.IncrementTypes;
import es.dagaren.gladiator.time.Quota;
import es.dagaren.gladiator.time.Stopwatch;
import es.dagaren.gladiator.time.TimeControl;

/**
 * @author dagaren
 *
 */
@Ignore
public class ClockTest
{
   public Clock clock;
   
   @BeforeClass
   public static void init()
   {
      
   }
   
   @Test
   public void test()
   {
	  
      clock = new Clock();
      TimeControl tc = new Quota(30, 1800, 5, IncrementTypes.FISCHER);
      
      clock.setTimeControl(tc);
      clock.init();
      
      Thread t = new Thread(new Runnable(){
         public void run()
         {
            while(true)
            {
               System.out.println("Blancas: " + formatTime(clock.getWhiteTime()) + ", Negras: " + formatTime(clock.getBlackTime()));
               try{
                  Thread.sleep(700);
               }catch(Exception ex){
                  
               }
            }
         }
      });
      t.start();
      
      System.out.println("Se inicia el reloj");
      clock.start();
      
      try{
         Thread.sleep(3000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se hace switch del reloj");
      clock.switchClocks();
      
      try{
         Thread.sleep(3000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se hace otra vez switch del reloj");
      
      clock.switchClocks();
      
      try{
         Thread.sleep(3000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se pausa el reloj");
      
      clock.pause();
      
      
      try{
         Thread.sleep(10000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se reanuda el reloj");
      
      clock.resume();
      
      try{
         Thread.sleep(10000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se pause el reloj");
      
      clock.pause();
      
      t.stop();
      
   }
   
   @Test
   public void test2()
   {
      clock = new Clock();
      TimeControl tc = new Quota(2, 1800, 0, IncrementTypes.FISCHER);
      
      clock.setTimeControl(tc);
      clock.init();
      
      Thread t = new Thread(new Runnable(){
         public void run()
         {
            while(true)
            {
               System.out.println("Turno: " + clock.getTurn().toString() + 
                                  ", Movimiento: " + clock.getMove() +
                                  ", Blancas: " + formatTime(clock.getWhiteTime()) + 
                                  ", Negras: " + formatTime(clock.getBlackTime()));
               try{
                  Thread.sleep(700);
               }catch(Exception ex){
                  
               }
            }
         }
      });
      t.start();
      
      System.out.println("Se inicia el reloj");
      clock.start();
      
      try{
         Thread.sleep(3000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se hace switch del reloj");
      clock.switchClocks();
      
      try{
         Thread.sleep(3000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se hace otra vez switch del reloj");
      
      clock.switchClocks();
      
      try{
         Thread.sleep(3000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se pausa el reloj");
      
      clock.pause();
      
      
      try{
         Thread.sleep(10000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se reanuda el reloj");
      
      clock.resume();
      
      try{
         Thread.sleep(10000);
      }catch(Exception ex){
         
      }
      
     System.out.println("Se hace otra vez switch del reloj");
      
      clock.switchClocks();
      
      try{
         Thread.sleep(10000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se hace otra vez switch del reloj");
      
      clock.switchClocks();
      
      try{
         Thread.sleep(10000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se hace otra vez switch del reloj");
      
      clock.switchClocks();
      
      try{
         Thread.sleep(10000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se hace otra vez switch del reloj");
      
      clock.switchClocks();
      
      try{
         Thread.sleep(10000);
      }catch(Exception ex){
         
      }
      
      System.out.println("Se pause el reloj");
      
      clock.pause();
      
      t.stop();
      
   }
   
   public String formatTime(long time)
   {
      time = time / 1000;
      long seconds = time % 60;
      time = time / 60;
      long minutes = time % 60;
      long hours = time / 60;     
      String timeFormatted = String.format("%03d:%02d:%02d", hours, minutes, seconds);
      
      return timeFormatted;
   }
   
}
