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
package es.dagaren.gladiator.testing.notation;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import es.dagaren.gladiator.notation.ExtendedPositionDescription;
import es.dagaren.gladiator.representation.BitboardPosition;
import es.dagaren.gladiator.time.Stopwatch;

/**
 * @author dagaren
 *
 */
public class ExtendedPositionDescriptionTest
{
   @BeforeClass
   public static void init()
   {
   }
   
   @Test
   public void test()
   {
      String epdString = "1r2r1k1/5pp1/R2p3n/3P1P1p/2p2PP1/1qb2B1P/Q7/2BR1K2 b - - bm Bd2; id \"STS(v7.0) Simplification.009\"; c0 \"Bd2=10, Bb2=5, Bb4=5, hxg4=3\";";
      ExtendedPositionDescription epd =  ExtendedPositionDescription.load(epdString);
   }
}
