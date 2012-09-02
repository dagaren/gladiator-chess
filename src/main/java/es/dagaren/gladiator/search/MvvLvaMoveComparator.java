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

import java.util.Comparator;

import es.dagaren.gladiator.notation.Notation;
import es.dagaren.gladiator.representation.Movement;

/**
 * @author dagaren
 *
 */
public class MvvLvaMoveComparator implements Comparator<Movement>
{

   /* (non-Javadoc)
    * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
    */
   @Override
   public int compare(Movement m1, Movement m2)
   {
      int m1val = 0;
      if(!m1.isInPassant())
      {
         m1val = m1.getDestinationPiece().genericPiece.value 
         - m1.getSourcePiece().genericPiece.value;
      }
         
      int m2val = 0 ;
      if(!m2.isInPassant())
      {
         m2val = m2.getDestinationPiece().genericPiece.value 
         - m2.getSourcePiece().genericPiece.value;
      }
      
      return m2val - m1val;
   }
   
}
