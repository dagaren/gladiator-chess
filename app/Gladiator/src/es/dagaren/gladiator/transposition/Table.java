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
package es.dagaren.gladiator.transposition;

import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.transposition.Entry.Type;

/**
 * @author dagaren
 *
 */
public class Table
{
   
   private int size ;
   private Entry[] entries;
   
   private long hits = 0;
   private long misses = 0;
   
   public Table(int size)
   {
      this.size = size;
      
      this.entries = new Entry[this.size];
   }
   
   public Entry get(long hash)
   {  
      Entry entry = entries[(int)Math.abs(hash % this.size)];
      
      if(entry != null && entry.getHash() == hash)
      {
         hits++;
         return entry;
      }
      else
      {
         misses++;
         return null;
      }
   }
   
   public boolean save(long hash, int depth, int score, 
                         Type type, int age, Movement bestMove)
   {
      //Estrategia por defecto reemplazar siempre
      
      Entry currentEntry = entries[(int)Math.abs(hash % this.size)];
      
      if(currentEntry == null)
      {
        Entry newEntry = new Entry(hash, depth, score, 
                         type, age, bestMove);
        entries[(int)Math.abs(hash % this.size)] = newEntry;
      }
      else
      {
         currentEntry.init(hash, depth, score, type, age, bestMove);
      }
      
      return true;
   }

   public long getHits()
   {
      return hits;
   }

   public void setHits(long hits)
   {
      this.hits = hits;
   }

   public long getMisses()
   {
      return misses;
   }

   public void setMisses(long misses)
   {
      this.misses = misses;
   }
}
