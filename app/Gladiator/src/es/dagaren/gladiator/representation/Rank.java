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
package es.dagaren.gladiator.representation;

/**
 * @author dagaren
 *
 */
public enum Rank
{
   _1(0),
   _2(1),
   _3(2),
   _4(3),
   _5(4),
   _6(5),
   _7(6),
   _8(7);
   
   
   public int index = 0;
   
   Rank(int index)
   {
      this.index = index;
   }
   
   public Rank fromIndex(int index)
   {
      return Rank.values()[index];
   }
}
