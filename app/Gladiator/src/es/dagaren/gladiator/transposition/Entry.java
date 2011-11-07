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

/**
 * @author dagaren
 *
 */
public class Entry
{
   public enum Type{
      EXACT,
      ALPHA,
      BETA
   };
   
   private long hash = 0;
   
   private int depth = 0;
   
   private int score = 0;
   
   private Type type = Type.EXACT;
   
   private int age = 0;
   
   private Movement bestMove = null;
   
   public Entry()
   {
      
   }
   
   public Entry(long hash, int depth, int score, 
         Type type, int age, Movement bestMove)
   {
      init(hash, depth, score, 
             type, age, bestMove);
   }
   
   public void init(long hash, int depth, int score, 
         Type type, int age, Movement bestMove)
   {
      this.hash = hash;
      this.depth = depth;
      this.score = score;
      this.type = type;
      this.age = age;
      this.bestMove = bestMove;
   }

   public long getHash()
   {
      return hash;
   }

   public void setHash(long hash)
   {
      this.hash = hash;
   }

   public int getDepth()
   {
      return depth;
   }

   public void setDepth(int depth)
   {
      this.depth = depth;
   }

   public int getScore()
   {
      return score;
   }

   public void setScore(int score)
   {
      this.score = score;
   }

   public Type getType()
   {
      return type;
   }

   public void setType(Type type)
   {
      this.type = type;
   }

   public int getAge()
   {
      return age;
   }

   public void setAge(int age)
   {
      this.age = age;
   }

   public Movement getBestMove()
   {
      return bestMove;
   }

   public void setBestMove(Movement bestMove)
   {
      this.bestMove = bestMove;
   }
   
   
   public Integer probe(int depth, int alpha, int beta)
   {
      if(this.depth >= depth)
      {
         if(this.type == Entry.Type.EXACT)
            return score;
         else if(this.type == Entry.Type.ALPHA && this.score <= alpha)
            return alpha;
         else if(this.type == Entry.Type.BETA && this.score >= beta)
            return beta;
      }
      return null;
   }
   
}
