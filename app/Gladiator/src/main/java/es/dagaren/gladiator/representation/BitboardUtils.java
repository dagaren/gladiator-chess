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
 * 
 * Clase que contiene un conjunto de métodos estáticos
 * con funciones sencillas sobre bitboards
 * @author dagaren
 *
 */
public class BitboardUtils {
   
   public static long flipVertical(long bitboard){
      return Long.reverseBytes(bitboard);
   }
   
   public static long flipDiagonalA1H8(long bitboard){
      long a;
      long b = 0x5500550055005500L;
      long c = 0x3333000033330000L;
      long d = 0x0F0F0F0F00000000L;
      
      a = d  & (bitboard ^ (bitboard << 28));
      bitboard ^= a ^ (a >>> 28);
      a = c & (bitboard ^ (bitboard << 14));
      bitboard ^= a ^ (a >>> 14);
      a = b  & (bitboard ^ (bitboard << 7));
      bitboard ^= a ^ (a >>> 7);
      
      return bitboard;
   }
   
   /**
    * Vuncion que devuelve un bitboard rotado 90 grados
    * en sentido antihorario, tal como se muestra en la imagen:
    * 
    * . 1 1 1 1 . . .     . . . . . . . .
    * . 1 . . . 1 . .     . . . . . . . .
    * . 1 . . . 1 . .     . 1 1 . . . . 1
    * . 1 . . 1 . . .     1 . . 1 . . 1 .
    * . 1 1 1 . . . .     1 . . . 1 1 . .
    * . 1 . 1 . . . .     1 . . . 1 . . .
    * . 1 . . 1 . . .     1 1 1 1 1 1 1 1
    * . 1 . . . 1 . .     . . . . . . . .
    * 
    * Para realizar la rotación se realiza primero un volteo vertical
    * del bitboard, y al resultado se le vuelve a realizar un volteo
    * alrededor de la diagonal principal (a1a8)
    *  
    * @param bitboard
    * @return
    */
   public static long rotate90DegreesLeft(long bitboard){
      return flipDiagonalA1H8(flipVertical(bitboard));
   }
   
   /**
    * Función que devuelve un bitboard rotado 90 grados
    * en sentido horario, tal como se muestra en la imagen:
    * 
    * . 1 1 1 1 . . .     . . . . . . . .
    * . 1 . . . 1 . .     1 1 1 1 1 1 1 1
    * . 1 . . . 1 . .     . . . 1 . . . 1
    * . 1 . . 1 . . .     . . 1 1 . . . 1
    * . 1 1 1 . . . .     . 1 . . 1 . . 1
    * . 1 . 1 . . . .     1 . . . . 1 1 .
    * . 1 . . 1 . . .     . . . . . . . .
    * . 1 . . . 1 . .     . . . . . . . .
    * 
    * Para realizar la rotación se realiza primero un volteo alrededor
    * de la diagonal principal (a1a8) y al resultado se le vuelve
    * a realizar un volteo vertical
    *  
    * @param bitboard El bitboard que se desea rotar
    * @return
    */
   public static long rotate90DegreesRight(long bitboard){
      return flipVertical(flipDiagonalA1H8(bitboard));
      
   }
   
   /**
    * Función que realiza una pseudorotación de 45 grados en sentido
    * horario. El concepto de rotación de 45 grados es el que se muestra
    * en la siguiente imagen, en la que se númeran los bits de la diagonal
    * principal del 0 a F:
    * 
    * 9 A B C D E F 0     9 1 1 1 1 1 1 1
    * A B C D E F 0 1     A A 2 2 2 2 2 2
    * B C D E F 0 1 2     B B B 3 3 3 3 3
    * C D E F 0 1 2 3     C C C C 4 4 4 4
    * D E F 0 1 2 3 4     D D D D D 5 5 5
    * E F 0 1 2 3 4 5     E E E E E E 6 6
    * F 0 1 2 3 4 5 6     F F F F F F F 7
    * 0 1 2 3 4 5 6 7     0 0 0 0 0 0 0 0
    * 
    * @param bitboard
    * @return
    */
   public static long rotate45DegreesRight(long bitboard){
      long k1 = 0xAAAAAAAAAAAAAAAAL;
      long k2 = 0xCCCCCCCCCCCCCCCCL;
      long k4 = 0xF0F0F0F0F0F0F0F0L;
      bitboard ^= k1 & (bitboard ^ Long.rotateRight(bitboard, 8));
      bitboard ^= k2 & (bitboard ^ Long.rotateRight(bitboard, 16));
      bitboard ^= k4 & (bitboard ^ Long.rotateRight(bitboard, 32));
      return bitboard;
   }
   
   public static long unrotate45DegreesRight(long bitboard){
      long k1 = 0xAAAAAAAAAAAAAAAAL;
      long k2 = 0xCCCCCCCCCCCCCCCCL;
      long k4 = 0xF0F0F0F0F0F0F0F0L;
      
      bitboard ^= k4 & (bitboard ^ Long.rotateLeft(bitboard,32));
      bitboard ^= k2 & (bitboard ^ Long.rotateLeft(bitboard,16));
      bitboard ^= k1 & (bitboard ^ Long.rotateLeft(bitboard,8));
      
      return bitboard;
   }
   
   /**
    * Función que realiza una pseudorotación de 45 grados en sentido
    * antihorario. El concepto de rotación de 45 grados en sentido antihorario
    * es el que se muestra en la siguiente imagen, en la que se númeran 
    * los bits de las diagonales secundaria del 0 a F:
    * 
    * 0 F E D C B A 9     1 1 1 1 1 1 1 9
    * 1 0 F E D C B A     2 2 2 2 2 2 A A
    * 2 1 0 F E D C B     3 3 3 3 3 B B B
    * 3 2 1 0 F E D C     4 4 4 4 C C C C
    * 4 3 2 1 0 F E D     5 5 5 D D D D D
    * 5 4 3 2 1 0 F E     6 6 E E E E E E
    * 6 5 4 3 2 1 0 F     7 F F F F F F F
    * 7 6 5 4 3 2 1 0     0 0 0 0 0 0 0 0
    * 
    * @param bitboard
    * @return
    */
   public static long rotate45DegreesLeft(long bitboard){
      long k1 = 0x5555555555555555L;
      long k2 = 0x3333333333333333L;
      long k4 = 0x0F0F0F0F0F0F0F0FL;
      bitboard ^= k1 & (bitboard ^ Long.rotateRight(bitboard, 8));
      bitboard ^= k2 & (bitboard ^ Long.rotateRight(bitboard, 16));
      bitboard ^= k4 & (bitboard ^ Long.rotateRight(bitboard, 32));
      return bitboard;
   }

   public static long unrotate45DegreesLeft(long bitboard){
      long k1 = 0x5555555555555555L;
      long k2 = 0x3333333333333333L;
      long k4 = 0x0F0F0F0F0F0F0F0FL;
      
      bitboard ^= k4 & (bitboard ^ Long.rotateLeft(bitboard, 32));
      bitboard ^= k2 & (bitboard ^ Long.rotateLeft(bitboard, 16));
      bitboard ^= k1 & (bitboard ^ Long.rotateLeft(bitboard, 8));
      return bitboard;
   }
   
   public static void printInConsole(long bitboard){
      System.out.println(BitboardUtils.toString(bitboard));
   }
   
   public static String toString(long bitboard){
      long posicion = 1;
      
      String[][] tablero = new String[8][8];
      
      for(int i=0;i<8;i++){
         for(int j=0;j<8;j++){
            long resultado = posicion & bitboard;
            
            
            if(resultado == 0){
               tablero[i][j] = " - ";
            }else{
               tablero[i][j] = " X ";
            }
         
            posicion = posicion << 1;
         }
      }  
      StringBuilder sb = new StringBuilder("");
      sb.append(" --------------------------\n");
      for(int i=7;i>=0;i--){
         sb.append(i+1);
         sb.append("|");
         
         for(int j=0;j<8; j++){
            sb.append(tablero[i][j]);
         }
         
         sb.append("|\n");
      }
      sb.append(" --------------------------\n");
      sb.append("   a  b  c  d  e  f  g  h\n"); 
      
      return sb.toString();
   }
}
