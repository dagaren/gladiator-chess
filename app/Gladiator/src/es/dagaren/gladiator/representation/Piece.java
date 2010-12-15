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
public enum Piece
{
   WHITE_PAWN(0),
   BLACK_PAWN(1),
   WHITE_ROOK(2),
   BLACK_ROOK(3),
   WHITE_KNIGHT(4),
   BLACK_KNIGHT(5),
   WHITE_BISHOP(6),
   BLACK_BISHOP(7),
   WHITE_QUEEN(8),
   BLACK_QUEEN(9),
   WHITE_KING(10),
   BLACK_KING(11);
   
   public int index;
   
   Piece(int index)
   {
      this.index = index;
   }
}
