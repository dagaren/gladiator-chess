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

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import es.dagaren.gladiator.time.Stopwatch;

/**
 * @author dagaren
 *
 */
@Ignore
public class StopwatchTest
{

   @BeforeClass
   public static void init()
   {
      
   }
   
   @Test
   public void test()
   {
      Stopwatch sw = new Stopwatch();
      
      long startTime = 1000;
      
      sw.setTime(startTime);
      
      assertEquals("El tiempo recuperado no coincide con el tiempo inicial", startTime, sw.getTime());
   }
   
   @Test
   public void test2()
   {
      Stopwatch sw = new Stopwatch();
      
      long startTime = 10000;
      long sleepTime = 10000;
      
      sw.setTime(startTime);
      sw.start();
      
      try
      {
         Thread.sleep(sleepTime);
      }
      catch(Exception ex)
      {
         System.err.println("Ocurre error mientras el hilo permanecía dormido: " + ex.getMessage());
         return;
      }
      
      long endTime = sw.getTime();
      
      assertTrue("El tiempo devuelto no es cercano al esperado", endTime > (startTime - sleepTime) - 1000);
      assertTrue("El tiempo devuelto no es cercano al esperado", endTime < (startTime - sleepTime) + 1000);
   }
   
   @Test
   public void test3()
   {
      Stopwatch sw = new Stopwatch();
      
      long startTime = 10000;
      long sleepTime = 6000;
      
      sw.setTime(startTime);
      sw.start();
      
      try
      {
         Thread.sleep(sleepTime);
      }
      catch(Exception ex)
      {
         System.err.println("Ocurre error mientras el hilo permanecía dormido: " + ex.getMessage());
         return;
      }
      
      long endTime = sw.getTime();
      
      assertTrue("El tiempo devuelto no es cercano al esperado", endTime > (startTime - sleepTime) - 1000);
      assertTrue("El tiempo devuelto no es cercano al esperado", endTime < (startTime - sleepTime) + 1000);
   }
   
   @Test
   public void test4()
   {
      Stopwatch sw = new Stopwatch();
      
      long startTime = 10000;
      long sleepTime = 6000;
      
      sw.setTime(startTime);
      sw.start();
      
      try
      {
         Thread.sleep(sleepTime);
      }
      catch(Exception ex)
      {
         System.err.println("Ocurre error mientras el hilo permanecía dormido: " + ex.getMessage());
         return;
      }
      
      sw.stop();
      
      long endTime = sw.getTime();
      
      assertTrue("El tiempo devuelto no es cercano al esperado", endTime > (startTime - sleepTime) - 1000);
      assertTrue("El tiempo devuelto no es cercano al esperado", endTime < (startTime - sleepTime) + 1000);
   }
   
   @Test
   public void test5()
   {
      Stopwatch sw = new Stopwatch();
      
      long startTime = 10000;
      long sleepTime = 6000;
      
      sw.setTime(startTime);
      sw.start();
      
      try
      {
         Thread.sleep(sleepTime);
      }
      catch(Exception ex)
      {
         System.err.println("Ocurre error mientras el hilo permanecía dormido: " + ex.getMessage());
         return;
      }
      
      sw.stop();
      
      try
      {
         Thread.sleep(sleepTime);
      }
      catch(Exception ex)
      {
         System.err.println("Ocurre error mientras el hilo permanecía dormido: " + ex.getMessage());
         return;
      }
      
      long endTime = sw.getTime();
      
      assertTrue("El tiempo devuelto no es cercano al esperado", endTime > (startTime - sleepTime) - 1000);
      assertTrue("El tiempo devuelto no es cercano al esperado", endTime < (startTime - sleepTime) + 1000);
   }
}
