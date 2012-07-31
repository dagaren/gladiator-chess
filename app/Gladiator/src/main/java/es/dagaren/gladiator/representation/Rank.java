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

import java.util.HashMap;
import java.util.Map;

/**
 * @author dagaren
 *
 */
public class Rank
{
   public static final Rank _1 = new Rank(0, "_1");
   public static final Rank _2 = new Rank(1, "_2");
   public static final Rank _3 = new Rank(2, "_3");
   public static final Rank _4 = new Rank(3, "_4");
   public static final Rank _5 = new Rank(4, "_5");
   public static final Rank _6 = new Rank(5, "_6");
   public static final Rank _7 = new Rank(6, "_7");
   public static final Rank _8 = new Rank(7, "_8");
   
   public int index = 0;
   
   protected static Rank[] ranks;
   protected static String[] ranksNames;
   protected static Map<String, Rank> ranksMap;
   
   private Rank(int index, String name)
   {
      this.index = index;
      
      if(ranks == null)
      {
         ranks = new Rank[8];
         ranksNames = new String[8];
         ranksMap = new HashMap<String, Rank>();
      }
      
      ranks[index] = this;
      ranksNames[index] = name;
      ranksMap.put(name, this);
   }
   
   public static Rank fromIndex(int index)
   {
      return ranks[index];
   }
   
   public static Rank valueOf(String name)
   {
      if(ranksMap.containsKey(name))
         return ranksMap.get(name);
      else
         return null;
   }
   
   public String name()
   {
      return ranksNames[this.index];
   }
   
   public String toString()
   {
      return name();
   }
}