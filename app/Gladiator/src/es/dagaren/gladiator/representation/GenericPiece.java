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
public enum GenericPiece
{
   PAWN(0, 100),
   ROOK(1, 500),
   KNIGHT(2, 300),
   BISHOP(3, 300),
   QUEEN(4, 900),
   KING(5, 1000);
   
   public int index;
   public int value;
   
   private GenericPiece(int index, int value){
      this.index = index;
      this.value = value;
   }
}
