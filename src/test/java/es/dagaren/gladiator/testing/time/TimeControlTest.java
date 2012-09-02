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

import es.dagaren.gladiator.representation.Colour;
import es.dagaren.gladiator.time.IncrementTypes;
import es.dagaren.gladiator.time.SuddenDeath;
import es.dagaren.gladiator.time.TimeControl;

/**
 * @author dagaren
 *
 */
@Ignore
public class TimeControlTest
{
   @BeforeClass
   public static void init()
   {
      
   }
   
   @Test
   public void testSuddenDeath1()
   {
      long time = 30000;
      long increment = 2000;
      Colour turn = Colour.WHITE;
      int move = 1;
      IncrementTypes incrementType = IncrementTypes.FISCHER;
      
      TimeControl tc = new SuddenDeath(time, increment, incrementType);
      
      long[] times =  { time, time };
      long[] startTimes = { time, time };
      
      tc.startTurn(times, move, turn);
      
      assertEquals("Los tiempos del turno no coinciden.", time + increment, times[turn.index]);
      assertEquals("Los tiempos del oponente no coinciden.", time, times[turn.opposite().index]);
      
      tc.finishTurn(startTimes, times, move, turn);
      
      assertEquals("Los tiempos del turno (después) no coinciden.", time + increment, times[turn.index]);
      assertEquals("Los tiempos del oponente (después) no coinciden.", time, times[turn.opposite().index]);
   }
   
   @Test
   public void testSuddenDeath2()
   {
      long time = 30000;
      long increment = 2000;
      Colour turn = Colour.BLACK;
      int move = 1;
      IncrementTypes incrementType = IncrementTypes.FISCHER;
      
      TimeControl tc = new SuddenDeath(time, increment, incrementType);
      
      long[] times =  { time, time };
      long[] startTimes = { time, time };
      
      tc.startTurn(times, move, turn);
      
      assertEquals("Los tiempos del turno (antes) no coinciden.", time + increment, times[turn.index]);
      assertEquals("Los tiempos del oponente (antes) no coinciden.", time, times[turn.opposite().index]);
      
      tc.finishTurn(startTimes, times, move, turn);
      
      assertEquals("Los tiempos del turno (después) no coinciden.", time + increment, times[turn.index]);
      assertEquals("Los tiempos del oponente (después) no coinciden.", time, times[turn.opposite().index]);
      
   }
   
   @Test
   public void testSuddenDeath3()
   {
      long time = 30000;
      long increment = 2000;
      Colour turn = Colour.WHITE;
      int move = 1;
      IncrementTypes incrementType = IncrementTypes.BRONSTEIN;
      
      TimeControl tc = new SuddenDeath(time, increment, incrementType);
      
      long[] times =  { time, time };
      long[] startTimes = { time, time };
      
      tc.startTurn(times, move, turn);
      
      assertEquals("Los tiempos del turno no coinciden.", time, times[turn.index]);
      assertEquals("Los tiempos del oponente no coinciden.", time, times[turn.opposite().index]);
      
      tc.finishTurn(startTimes, times, move, turn);
      
      assertEquals("Los tiempos del turno (después) no coinciden.", time, times[turn.index]);
      assertEquals("Los tiempos del oponente (después) no coinciden.", time, times[turn.opposite().index]);
   }
   
   @Test
   public void testSuddenDeath4()
   {
      long time = 30000;
      long increment = 2000;
      Colour turn = Colour.BLACK;
      int move = 1;
      IncrementTypes incrementType = IncrementTypes.BRONSTEIN;
      
      TimeControl tc = new SuddenDeath(time, increment, incrementType);
      
      long[] times =  { time, time };
      long[] startTimes = { time, time };
      
      tc.startTurn(times, move, turn);
      
      assertEquals("Los tiempos del turno (antes) no coinciden.", time, times[turn.index]);
      assertEquals("Los tiempos del oponente (antes) no coinciden.", time, times[turn.opposite().index]);
      
      tc.finishTurn(startTimes, times, move, turn);
      
      assertEquals("Los tiempos del turno (después) no coinciden.", time, times[turn.index]);
      assertEquals("Los tiempos del oponente (después) no coinciden.", time, times[turn.opposite().index]);
      
   }
   
   @Test
   public void testSuddenDeath5()
   {
      long time = 30000;
      long increment = 2000;
      Colour turn = Colour.WHITE;
      int move = 1;
      IncrementTypes incrementType = IncrementTypes.BRONSTEIN;
      
      TimeControl tc = new SuddenDeath(time, increment, incrementType);
      
      long[] times =  { time - increment, time };
      long[] startTimes = { time, time };
      
      tc.finishTurn(startTimes, times, move, turn);
      
      assertEquals("Los tiempos del turno (después) no coinciden.", time, times[turn.index]);
      assertEquals("Los tiempos del oponente (después) no coinciden.", time, times[turn.opposite().index]);
      
   }
   
   @Test
   public void testSuddenDeath6()
   {
      long time = 30000;
      long increment = 2000;
      Colour turn = Colour.BLACK;
      int move = 1;
      IncrementTypes incrementType = IncrementTypes.BRONSTEIN;
      
      TimeControl tc = new SuddenDeath(time, increment, incrementType);
      
      long[] times =  { time, time - increment };
      long[] startTimes = { time, time };
      
      tc.finishTurn(startTimes, times, move, turn);
      
      assertEquals("Los tiempos del turno (después) no coinciden.", time, times[turn.index]);
      assertEquals("Los tiempos del oponente (después) no coinciden.", time, times[turn.opposite().index]);
      
   }
   
   @Test
   public void testSuddenDeath7()
   {
      long time = 30000;
      long increment = 2000;
      Colour turn = Colour.BLACK;
      int move = 1;
      IncrementTypes incrementType = IncrementTypes.BRONSTEIN;
      
      TimeControl tc = new SuddenDeath(time, increment, incrementType);
      
      long[] times =  { time, time - (increment + 1000) };
      long[] startTimes = { time, time };
      
      tc.finishTurn(startTimes, times, move, turn);
      
      assertEquals("Los tiempos del turno (después) no coinciden.", startTimes[turn.index] - 1000, times[turn.index]);
      assertEquals("Los tiempos del oponente (después) no coinciden.", time, times[turn.opposite().index]);
      
   }
   
   @Test
   public void testSuddenDeath8()
   {
      long time = 30000;
      long increment = 2000;
      Colour turn = Colour.WHITE;
      int move = 1;
      IncrementTypes incrementType = IncrementTypes.BRONSTEIN;
      
      TimeControl tc = new SuddenDeath(time, increment, incrementType);
      
      long[] times =  { time - (increment + 1000), time };
      long[] startTimes = { time, time };
      
      tc.finishTurn(startTimes, times, move, turn);
      
      assertEquals("Los tiempos del turno (después) no coinciden.", startTimes[turn.index] - 1000, times[turn.index]);
      assertEquals("Los tiempos del oponente (después) no coinciden.", time, times[turn.opposite().index]);
      
   }
   
   @Test
   public void testSuddenDeath9()
   {
      long time = 30000;
      long increment = 2000;
      Colour turn = Colour.WHITE;
      int move = 1;
      IncrementTypes incrementType = IncrementTypes.BRONSTEIN;
      
      TimeControl tc = new SuddenDeath(time, increment, incrementType);
      
      long[] times =  { time - (increment - 1000), time };
      long[] startTimes = { time, time };
      
      tc.finishTurn(startTimes, times, move, turn);
      
      assertEquals("Los tiempos del turno (después) no coinciden.", startTimes[turn.index] , times[turn.index]);
      assertEquals("Los tiempos del oponente (después) no coinciden.", time, times[turn.opposite().index]);
      
   }
   
   @Test
   public void testSuddenDeath10()
   {
      long time = 30000;
      long increment = 2000;
      Colour turn = Colour.BLACK;
      int move = 1;
      IncrementTypes incrementType = IncrementTypes.BRONSTEIN;
      
      TimeControl tc = new SuddenDeath(time, increment, incrementType);
      
      long[] times =  { time, time - (increment - 1000) };
      long[] startTimes = { time, time };
      
      tc.finishTurn(startTimes, times, move, turn);
      
      assertEquals("Los tiempos del turno (después) no coinciden.", startTimes[turn.index] , times[turn.index]);
      assertEquals("Los tiempos del oponente (después) no coinciden.", time, times[turn.opposite().index]);
      
   }
}
