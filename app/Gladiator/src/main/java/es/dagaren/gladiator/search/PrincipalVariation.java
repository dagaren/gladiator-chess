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
package es.dagaren.gladiator.search;

import java.util.Arrays;

import org.apache.log4j.Logger;

import es.dagaren.gladiator.notation.Notation;
import es.dagaren.gladiator.representation.Movement;

/**
 * @author dagaren
 *
 */
public class PrincipalVariation
{
   private Logger logger = Logger.getLogger(PrincipalVariation.class);
   
   private Movement[][] pv;
   private int[] pvl;
   private Movement[] temp;
   private int size = 60;
   
   public PrincipalVariation()
   {
      pv = new Movement[size + 1][size + 1];
      
      pvl = new int[size + 1];
      
      for(int i = 0; i <= size; i++)
      {
         pvl[i] = 0;
      }
   }
   
   public void initDepth(int depth)
   {
      pvl[depth] = 0;
      pvl[depth + 1] = 0;
   }
   
   public void saveInDepth(Movement move, int depth)
   {
      if(pvl[depth + 1] > 0)
      {
         temp = pv[depth + 1];
         pv[depth + 1] = pv[depth];
         pv[depth] = temp;
         
      }
      pvl[depth] = pvl[depth + 1] + 1;
      pv[depth][depth] = move;
   }
   
   public Movement[] getPrincipalVariation()
   {  
      if(pvl[0] > 0)
      {
        Movement[] result =  Arrays.copyOf(pv[0], pvl[0]);
        
        return result;
      }
      else
      {
        logger.error("Retornando array vacio");
        
        return new Movement[0];
      }
   }

}
