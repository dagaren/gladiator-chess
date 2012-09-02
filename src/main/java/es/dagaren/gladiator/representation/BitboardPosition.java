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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import es.dagaren.gladiator.notation.Notation;


/**
 * @author dagaren
 *
 */
public class BitboardPosition extends AbstractPosition {
   
   private Logger logger = Logger.getLogger(BitboardPosition.class);
   
   public long tiempoRecuperarPeones = 0;
   public long tgiempoRecuperarMovements = 0;
   
   //Arrays de bitboards
   /**
    * Array de bitboards que representan la dirección +1 de cada casilla
    * (pone a uno las posiciones de las casillas que estan en la misma fila 
    * a su derecha)
    * 
    * Ej. El bitboad en la posición 27 del array (que representa el bitboard
    * que aparece a la izquierda), sería el que se muestra a la derecha:
    * 
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . 1 . . . .     . . . . 1 1 1 1
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * 
    */
   public static long bbSquareRight[] = new long[64];
   
   /**
    * Array de bitboards que representan la dirección +7 de cada casilla
    * (pone a uno las posiciones de las casillas que estan en su diagonal
    * superior izquierda)
    * 
    * Ej. El bitboad en la posición 27 del array (que representa el bitboard
    * que aparece a la izquierda), sería el que se muestra a la derecha:
    * 
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     1 . . . . . . .
    * . . . . . . . .     . 1 . . . . . .
    * . . . . . . . .     . . 1 . . . . .
    * . . . 1 . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * 
    */
   public static long bbSquareTopLeft[] = new long[64];
   
   /**
    * Array de bitboards que representan la dirección +8 de cada casilla
    * (pone a uno las posiciones de las casillas que estan en su misma columna
    * por la parte superior)
    * 
    * Ej. El bitboad en la posición 27 del array (que representa el bitboard
    * que aparece a la izquierda), sería el que se muestra a la derecha:
    * 
    * . . . . . . . .     . . . 1 . . . .
    * . . . . . . . .     . . . 1 . . . .
    * . . . . . . . .     . . . 1 . . . .
    * . . . . . . . .     . . . 1 . . . .
    * . . . 1 . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * 
    */
   public static long bbSquareTop[] = new long[64];
   
   /**
    * Array de bitboards que representan la dirección +9 de cada casilla
    * (pone a uno las posiciones de las casillas que estan en su diagonal
    * superior derecha)
    * 
    * Ej. El bitboad en la posición 27 del array (que representa el bitboard
    * que aparece a la izquierda), sería el que se muestra a la derecha:
    * 
    * . . . . . . . .     . . . . . . . 1
    * . . . . . . . .     . . . . . . 1 .
    * . . . . . . . .     . . . . . 1 . .
    * . . . . . . . .     . . . . 1 . . .
    * . . . 1 . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * 
    */
   public static long bbSquareTopRight[] = new long[64];
   
   /**
    * Array de bitboards que representan la dirección -1 de cada casilla
    * (pone a uno las posiciones de las casillas que estan en la misma fila 
    * a su izquierda)
    * 
    * Ej. El bitboad en la posición 27 del array (que representa el bitboard
    * que aparece a la izquierda), sería el que se muestra a la derecha:
    * 
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . 1 . . . .     1 1 1 . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * 
    */
   public static long bbSquareLeft[] = new long[64];
   
   /**
    * Array de bitboards que representan la dirección -7 de cada casilla
    * (pone a uno las posiciones de las casillas que estan en su diagonal
    * inferior derecha)
    * 
    * Ej. El bitboad en la posición 27 del array (que representa el bitboard
    * que aparece a la izquierda), sería el que se muestra a la derecha:
    * 
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . 1 . . . .     . . . . . . . .
    * . . . . . . . .     . . . . 1 . . .
    * . . . . . . . .     . . . . . 1 . .
    * . . . . . . . .     . . . . . . 1 .
    * 
    */
   public static long bbSquareBottomRight[] = new long[64];
   
   /**
    * Array de bitboards que representan la dirección -8 de cada casilla
    * (pone a uno las posiciones de las casillas que estan en su misma columna
    * por la parte inferior)
    * 
    * Ej. El bitboad en la posición 27 del array (que representa el bitboard
    * que aparece a la izquierda), sería el que se muestra a la derecha:
    * 
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . 1 . . . .     . . . . . . . .
    * . . . . . . . .     . . . 1 . . . .
    * . . . . . . . .     . . . 1 . . . .
    * . . . . . . . .     . . . 1 . . . .
    * 
    */
   public static long bbSquareBottom[] = new long[64];
   
   /**
    * Array de bitboards que representan la dirección --9 de cada casilla
    * (pone a uno las posiciones de las casillas que estan en su diagonal
    * inferior izquierda)
    * 
    * Ej. El bitboad en la posición 27 del array (que representa el bitboard
    * que aparece a la izquierda), sería el que se muestra a la derecha:
    * 
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . . . . . .     . . . . . . . .
    * . . . 1 . . . .     . . . . . . . .
    * . . . . . . . .     . . 1 . . . . .
    * . . . . . . . .     . 1 . . . . . .
    * . . . . . . . .     1 . . . . . . .
    * 
    */
   public static long bbSquareBottomLeft[] = new long[64];
   
   /**
    * Array de bitboards que representan las 8 filas del tablero
    * 
    * Ej. En la posición 0 del array, correspondiente a la primera fila
    * se tendrá el siguiente bitboard:
    * 
    * . . . . . . . .
    * . . . . . . . .
    * . . . . . . . .
    * . . . . . . . .
    * . . . . . . . .
    * . . . . . . . .
    * . . . . . . . .
    * 1 1 1 1 1 1 1 1
    * 
    */
   public static long bbRank[] = new long[8];
   
   /**
    * Array de bitboards que representan las 8 columnas del tablero
    * 
    * Ej. En la posición 0 del array, correspondiente a la primera columna
    * se tendrá el siguiente bitboard:
    * 
    * 1 . . . . . . .
    * 1 . . . . . . .
    * 1 . . . . . . .
    * 1 . . . . . . .
    * 1 . . . . . . .
    * 1 . . . . . . .
    * 1 . . . . . . .
    * 1 . . . . . . .
    * 
    */
   public static long bbFile[] = new long[8];
   
   /**
    * Array bidimensional de bitboards de casillas atacadas en fila
    * La primera dimensión indica la casilla y la segunda indica la ocupación
    * de la fila (para cada uno de los posible 256 (2^8) valores)
    * 
    * 
    * B. CASILLA             B. OCUPACION FILA      BITBOARD_FILA[CASILLA][OCUPACION]
    * . . . . . . . .        . . . . . . . .        . . . . . . . .
    * . . . . . . . .        . . . . . . . .        . . . . . . . .
    * . . . . . . . .        . . . . . . . .        . . . . . . . .
    * . . . . . . . .        . . . . . . . .        . . . . . . . .
    * . . . 1 . . . .        . 1 . 1 . . 1 1        . 1 1 . 1 1 1 .
    * . . . . . . . .        . . . . . . . .        . . . . . . . .
    * . . . . . . . .        . . . . . . . .        . . . . . . . .
    * . . . . . . . .        . . . . . . . .        . . . . . . . .
    */
   public static long bbRankAttack[][] = new long[64][256];
   
   /**
    * Array bidimensional de bitboards de casillas atacadas en columna
    * La primera dimensión indica la casilla y la segunda indica la ocupación
    * de la columna (para hallar esta ocupación hay que rotar la columna
    * para conseguir que se vea como fila y desplazarla para que la fila
    * quede en los ocho bits menos significativos). 
    * 
    * B. CASILLA             B. OCUPACION COLUMNA   BITBOARD_COLUMNA[CASILLA][OCUPACION]
    * . . . . . . . .        . . . . . . . .        . . . . . . . .
    * . . . . . . . .        . . . 1 . . . .        . . . 1 . . . .
    * . . . . . . . .        . . . . . . . .        . . . 1 . . . .
    * . . . . . . . .        . . . . . . . .        . . . 1 . . . .
    * . . . 1 . . . .        . . . 1 . . . .        . . . . . . . .
    * . . . . . . . .        . . . . . . . .        . . . 1 . . . .
    * . . . . . . . .        . . . 1 . . . .        . . . 1 . . . .
    * . . . . . . . .        . . . 1 . . . .        . . . . . . . .
    */
   public static long bbFileAttack[][] = new long[64][256];
   
   
   public static long bbPrincipalDiagonalAttack[][] = new long[64][256];
   public static long bbSecondaryDiagonalAttack[][] = new long[64][256];
   
   public static long bbKnightAttack[] = new long[64];
   public static long bbKingAttack[] = new long[64];
   public static long bbQueenAttack[] = new long[64];
   public static long bbWhitePawnAttack[] = new long[64];
   public static long bbBlackPawnAttack[] = new long[64];
   public static long bbWhitePawnReach[] = new long[64];
   public static long bbBlackPawnReach[] = new long[64];
   
   /**
    * Bitboard que representa las casillas negras del tablero:
    * 
    * . 1 . 1 . 1 . 1
    * 1 . 1 . 1 . 1 .
    * . 1 . 1 . 1 . 1
    * 1 . 1 . 1 . 1 .
    * . 1 . 1 . 1 . 1
    * 1 . 1 . 1 . 1 .
    * . 1 . 1 . 1 . 1
    * 1 . 1 . 1 . 1 .
    */
   public static final long bbBlackSquares = 0xAA55AA55AA55AA55L;
   
   /**
    * Bitboard que representa las casillas blancas del tablero:
    * 
    * 1 . 1 . 1 . 1 .
    * . 1 . 1 . 1 . 1
    * 1 . 1 . 1 . 1 .
    * . 1 . 1 . 1 . 1
    * 1 . 1 . 1 . 1 .
    * . 1 . 1 . 1 . 1
    * 1 . 1 . 1 . 1 .
    * . 1 . 1 . 1 . 1
    */
   public static final long bbWhiteSquares = 0x55AA55AA55AA55AAL;
   
   /**
    * Array de 64 posiciones (una por cada casilla del tablero)
    * de modo que el indice del array devuelve el Square correspondiente
    * (hace lo mismo que Square.fromIndex(int indice), pero agiliza 
    */
   public static Square square[] = new Square[64];
   
   /**
    * Array de 64 posiciones (una por cada casilla del tablero)
    * para cada casilla se indica la fila (0-7) a la que pertenece
    */
   public static int rankSquare[] = new int[64];
   
   /**
    * Array de 64 posiciones (una por cada casilla del tablero)
    * para cada casilla se indica la columna (0-7) a la que pertenece
    */
   public static int fileSquare[] = new int[64];
   
   /**
    * Array de 64 posiciones (una por cada casilla del tablero)
    * para cada casilla se indica la diagonal (0-14) a la que pertenece
    */
   public static int diagonalSquare[] = new int[64];
   
   /**
    * Array de 64 posiciones (una por cada casilla del tablero)
    * Para cada casilla indica el bitboard que representa la casilla 
    * en el tablero.
    */
   public static long bbSquare[] = new long[64];
   
   public static int squareRotated90DLeft[] = new int[64];
   public static int squareRotated90DRight[] = new int[64];
   
   public static int squareRotated45DLeft[] = new int[64];
   public static int squareRotated45DRight[] = new int[64];
   
   public static int inverseSquareRotated45DLeft[] = new int[64];
   public static int inverseSquareRotated45DRight[] = new int[64];
   
   public static long mainDiagonalMask[] = new long[64];
   public static long secondaryDiagonalMask[] = new long[64];
   
   /////
   public static long bbMainDiagonalFullConnection[][] = new long[64][64];
   public static long bbSecondaryDiagonalFullConnection[][] =  new long[64][64];
   public static long bbRankFullConnection[][] = new long[64][64];
   public static long bbFileFullConnection[][] = new long[64][64];
   public static long bbDiagonalFullConnection[][] = new long[64][64];
   public static long bbLinealFullConnection[][] = new long[64][64];
   public static long bbFullConnection[][] = new long[64][64];
   
   /////
   public static long bbMainDiagonalInclusiveConnection[][] = new long[64][64];
   public static long bbSecondaryDiagonalInclusiveConnection[][] =  new long[64][64];
   public static long bbRankInclusiveConnection[][] = new long[64][64];
   public static long bbFileInclusiveConnection[][] = new long[64][64];
   public static long bbDiagonalInclusiveConnection[][] = new long[64][64];
   public static long bbLinearInclusiveConnection[][] = new long[64][64];
   public static long bbInclusiveConnection[][] = new long[64][64];
   
   /////
   public static long bbMainDiagonalExclusiveConnection[][] = new long[64][64];
   public static long bbSecondaryDiagonalExclusiveConnection[][] =  new long[64][64];
   public static long bbRankExclusiveConnection[][] = new long[64][64];
   public static long bbFileExclusiveConnection[][] = new long[64][64];
   public static long bbDiagonalExclusiveConnection[][] = new long[64][64];
   public static long bbLinealExclusiveConnection[][] = new long[64][64];
   public static long bbExclusiveConnection[][] = new long[64][64];
   
   /** BITBOARDS DE CASILLAS DE ENROQUE **/
   public static long[] bbCastlingLongSquares = new long[2];
   public static Square[][] bbCastlingShortSquares = new Square[2][];
   public static long[] castlingLongKingSquares = new long[2];
   public static Square[][] castlingShortKingSquares = new Square[2][];
   /** FIN BITBOARDS DE CASILLAS DE ENROQUE **/
   
   //Squares de origen y destino de enroques
   public static Square[] castlingSourceSquares = new Square[2];
   public static Square[] castlingLongDestinationSquares = new Square[2];
   public static Square[] castlingShortDestinationSquares = new Square[2];
   
   //Casillas relacionadas con las capturas al paso
   
   
   //Array con las casillas de destino del atacante en un captura al paso,
   //teniendo como índice la casilla que se encuentra 'al paso'
   public static Square[] enPassantSquareDestination = new Square[64];
   //Array con las casillas 'que se encuentran al paso' en una captura al paso,
   //teniendo como índice la casilla destino en la que se coloca el atacante.
   public static Square[] enPassantSquareSource = new Square[64];
   
   //Cola en la que se almacenan los
   protected LinkedList<IrreversibleState> stateStack = new LinkedList<IrreversibleState>();
   
   
   
   /** BITBOARDS DE OCUPACIÓN DE PIEZAS **/
   
   /**
    * Array de mapeo de la pieza que se encuentra en cada casilla
    */
   Piece pieceInSquare[] = new Piece[64];
   
   /**
    * Array de bitboards de ocupación de piezas.
    * Cada uno de los bitboards del array representa la
    * ocupación de un tipo de pieza de un color
    */
   long bbPiecesOccupation[] = new long[12];
   
   /**
    * Bitboard de ocupación de las piezas blancas
    */
   protected long bbWhiteOccupation;
   
   /**
    * Birboard de ocupación de las piezas negras
    */
   protected long bbBlackOccupation;
   
   /**
    * Array de bitboards de ocupacion de piezas
    */
   long[] bbColourOccupation = new long[2];
   
   /**
    * Bitboard de ocupación de todas las piezas
    */
   protected long bbOccupation;
   
   /**
    * Bitboard de ocupación de todas las piezas ROTADO 90 grados
    * en sentido antihorario
    */
   protected long bbOccupationR90L;
   
   /**
    * Bitboard de ocupación de todas las piezas ROTADO 45 grados
    * en sentido antihorario
    */
   protected long bbOccupationR45L;
   
   /**
    * Bitboard de ocupación de todas las piezas ROTADO 45 grados
    * en sentido horario
    */
   protected long bbOccupationR45R;
   /** FIN DE BITBOARDS DE OCUPACIÓN DE PIEZAS **/
   
   protected long bbAttackedFromSquare[] = new long[64];
   long bbAttackerToSquare[] = new long[64];
   
   
   protected boolean movesGenerated = false;
   protected List<Movement> movesList = null;
   
   
   /** CAMPOS DE FUNCIONES INTERNAS */
   long bbPieceOccupation;
   long bbTurnOccupied;
   long bbOppositeOccupied;
   int numPieces;
   int numSquares;
   int squareIndex;
   int pieceSquareIndex;
   int checkSquareIndex;
   long bbAttacked;
   long bbAttackedBefore;
   long bbConnection;
   int kingSquareIndex;
   long bbKing;
   long bbAttacker;
   int attackerSquareIndex;
   int attackedSquareIndex;
   int numAttacked;
   long bbAdvances;
   long bbAttackers;
   long bbKingAttackers;
   int numAttackers;
   boolean isPinned = false;
   Piece piece;
   Piece capture;
   Movement mov;
   long advances1;
   long advances2;
   GenericPiece genericPiece;
   Piece squarePiece;
   Piece capturePiece;
   Square sourceSquare;
   Square destinationSquare;
   long bbEnPassantCapture;
   
   int uaNumPieces;
   long uaBbOccupiedTemp; 
   int uaSquareIndex;
   long uaBbAttacked;
   long uaBbAttackerSquare;
   
   int uatNumAtacked; 
   int uatSquareIndex;
   /** FIN DE CAMPOS DE FUNCIONES INTERNAS */
   
   /**
    * Constructor de BitBoard
    */
   public BitboardPosition()
   {
      clean();
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#limpiar()
    */
   public void clean()
   {
      //Se inicia a cero el vector de atacadas_desde
      for(int i = 0; i < 64; i++)
      {
         bbAttackedFromSquare[i] = 0;
      }
      //Se inicia a cero el vector de atacantes_de
      for(int i = 0; i < 64; i++)
      {
         bbAttackerToSquare[i] = 0;
      }
      
      //Se inician los vectores de ocupacion
      for(int i = 0; i < 64; i++)
      {
         pieceInSquare[i] = null;
      }
      
      for(int i = 0; i < 12; i++)
      {
         bbPiecesOccupation[i] = 0;
      }
      
      bbWhiteOccupation = 0;
      
      bbBlackOccupation = 0;
      
      bbColourOccupation[Colour.WHITE.index] = 0;
      bbColourOccupation[Colour.BLACK.index] = 0;
      
      bbOccupationR90L = 0;
      
      bbOccupationR45L = 0;
      
      bbOccupationR45R = 0;
   }
   
   /**
    * Método que inicia todos los Bitboards y variables auxiliares
    *
    */
   static
   {
      //Se inicializan los bitboards de filas y columnas
      long f1 = 0x00000000000000ffL;
      long c1 = 0x0101010101010101L;
      for(int i = 0; i < 8; i++)
      {
         bbRank[i] = f1;
         f1 = f1 << 8;
         bbFile[i] = c1;
         c1 = c1 << 1;
      }
      
      //Se inicia el array de mepeo de casillas por indice
      for(int i = 0; i < 64; i++)
      {
         square[i] = Square.fromIndex(i);
      }
      
      //Se inicia el array de mapeo entre casillas y filas y columnas;
      for(int i = 0; i < 64; i++)
      {
         rankSquare[i] = i / 8;
         fileSquare[i] = i % 8; 
      }
      
      //Se inicia el array de mapeo casilla-diagonal
      for(int i = 0; i < 64; i++)
      {
         diagonalSquare[i] = rankSquare[i] + fileSquare[i];
      }
      
      //Se inician los arrays de mascaras de diagonales a1h8 y h8a1
      for(int i = 0; i < 64; i++)
      {
         int rank = i / 8;
         int file = i % 8;
         long full = 0xFFL;
         
         if(file <= (7 - rank))
         {
            mainDiagonalMask[i] = (full >>> rank) << 8 * rank;
         }
         else
         {   
            mainDiagonalMask[i] = (full ^ (full >>> rank) ) << 8 * rank;
         }
         
         if(rank > file)
         {
            secondaryDiagonalMask[i] = ((full ^ (full << rank)) & full) << 8 * rank;
         }
         else
         {
            secondaryDiagonalMask[i] =  ((full << rank) & full) << 8 * rank;
         }
         
         //System.out.printf("Mascara de la casilla %s: %x%n",Square.toString(i),mascara_diagonalA8H1[i]);
      }
      
      //Se inician los arrays de mapeo de casillas con sus posiciones rotadas 90 grados izquierda y derecha, 
      //y 45 a izquierda y derecha
      for(int i = 0; i < 64; i++)
      {
         int rank = i / 8;
         int file = i % 8;
         
         squareRotated90DLeft[i] = (8 * file) + (7 - rank);
         squareRotated90DRight[i] = (8 * (7 - file)) + rank;
         
         squareRotated45DLeft[i] = (rank - file) >= 0 ? 8 * (rank - file) + file : 8 * (rank - file + 8) + file;
         squareRotated45DRight[i] = (rank + file) > 7 ? (file + rank - 8) * 8 + file : 8 * (rank + file) + file;
         
         inverseSquareRotated45DLeft[i] = ((1 + rank + file) % 8) * 8 + file;
         inverseSquareRotated45DRight[i] = ((rank + 7 - file) % 8) * 8 + file; 
      }
      
      
      //Se inicializan los bitboards de casillas;
      long bbSquareTemp = 1;
      for(int i = 0; i < 64; i++)
      {
         bbSquare[i] = bbSquareTemp;
         
         bbSquareTemp = bbSquareTemp << 1;
      }
      
      //Se inicia el bitboard de casillas atacadas por el caballo
      for(int i = 0; i < 64; i++)
      {
         bbSquareTemp = bbSquare[i];
         
         bbKnightAttack[i] =  (bbSquareTemp & ~(bbRank[7] | bbFile[0] | bbFile[1])) << 6 |
                        (bbSquareTemp & ~(bbRank[7] | bbFile[7] | bbFile[6])) << 10 |
                        (bbSquareTemp & ~(bbRank[7] | bbRank[6] | bbFile[0])) << 15 |
                        (bbSquareTemp & ~(bbRank[7] | bbRank[6] | bbFile[7])) <<17 |
                        (bbSquareTemp & ~(bbRank[0] | bbFile[0] | bbFile[1])) >>> 10 |
                        (bbSquareTemp & ~(bbRank[0] | bbFile[7] | bbFile[6])) >>> 6 |
                        (bbSquareTemp & ~(bbRank[0] | bbRank[1] | bbFile[0])) >>> 17 |
                        (bbSquareTemp & ~(bbRank[0] | bbRank[1] | bbFile[7])) >>> 15;
      }
      
      //Se inicia el bitboard de las casillas atacadas por el rey
      for(int i = 0; i < 64; i++)
      {
         bbSquareTemp = bbSquare[i];
         
         bbKingAttack[i] =   (bbSquareTemp & ~(bbFile[0] | bbRank[7])) << 7 |
                     (bbSquareTemp & ~bbRank[7]) << 8 |
                     (bbSquareTemp & ~(bbFile[7] | bbRank[7])) <<9 |
                     (bbSquareTemp & ~bbFile[7]) << 1 |
                     (bbSquareTemp & ~bbFile[0]) >>> 1 |
                     (bbSquareTemp & ~(bbFile[7] | bbRank[0])) >>> 7 |
                     (bbSquareTemp & ~bbRank[0]) >>> 8 |
                     (bbSquareTemp & ~(bbFile[0] | bbRank[0])) >>> 9;
      }
      
      //Se inicia el bitboard de las casillas atacadas por un peon
      for(int i = 0; i < 64; i++)
      {
         bbSquareTemp = bbSquare[i];
         
         if(i > 7)
         {
            bbWhitePawnAttack[i] = ((bbSquareTemp & ~bbFile[0])  << 7) | ((bbSquareTemp & ~bbFile[7]) << 9);
         }
         else
         {
            bbWhitePawnAttack[i] = 0;
         }
         
         if(i < 56)
         {
            bbBlackPawnAttack[i] = ((bbSquareTemp & ~bbFile[7])) >>>7 | ((bbSquareTemp & ~bbFile[0]) >>> 9);
         }
         else
         {
            bbBlackPawnAttack[i] = 0;
         }
      }
      
      //Se inicia el bitboard de las casillas alcanzables por un peon
      for(int i = 0; i < 64; i++)
      {
         bbSquareTemp = bbSquare[i];
         
         if(i > 7 && i < 56)
         {
            //Actualización del bitboard de casillas alcanzanzables por un peón blanco
            bbWhitePawnReach[i]  =  bbSquareTemp << 8;
            
            if(i > 7 && i < 16)
               bbWhitePawnReach[i] |= bbSquareTemp << 16;
      
            //Actualización del bitboarde de casillas alcanzables por un peón negro
            bbBlackPawnReach[i] = bbSquareTemp >> 8;
            
            if(i > 47 && i < 56)
               bbBlackPawnReach[i] |= bbSquareTemp >> 16;
            
         }
         else
         {
            bbWhitePawnAttack[i] = 0;
            bbBlackPawnAttack[i] = 0;
         }
      }
      
      //Se inicializan los bitboards de direcciones
      bbSquareTemp = 1;
      long bbTemp;
      for(int i = 0; i < 64; i++)
      {
         //Se incia la direccion +1
         //Lo que quiere decir que para cada casilla se le asigna un bitboard
         //que pone a uno las posiciones de las casillas que estan en la misma fila 
         //a su derecha
         bbSquareRight[i] = 0;
         bbTemp = bbSquareTemp;
         while((bbTemp = bbTemp << 1 & ~bbFile[0]) != 0)
         {
            bbSquareRight[i] ^= bbTemp;
         }
         
         //Se inicia la direccion +7
         //Lo que quiere decir que para cada casilla se le asigna un bitboard
         //que pone a uno las posiciones de las casillas que estan en su diagonal
         //superior izquierda
         bbSquareTopLeft[i] = 0;
         bbTemp = bbSquareTemp;
         while((bbTemp = bbTemp << 7 & ~bbFile[7]) != 0)
         {
            bbSquareTopLeft[i] ^= bbTemp;
         }
         
         //Se inicia la direccion +8
         //Lo que quiere decir que para cada casilla se le asigna un bitboard
         //que pone a uno las posiciones de las casillas que estan en su misma
         //columna por la parte superior
         bbSquareTop[i] = 0;
         bbTemp = bbSquareTemp;
         while((bbTemp = bbTemp << 8) != 0)
         {
            bbSquareTop[i] ^= bbTemp;
         }
         
         //Se inicia la direccion +9
         //Lo que quiere decir que para cada casilla se le asigna un bitboard
         //que pone a uno las posiciones de las casillas que estan en su diagonal
         //superior derecha
         bbSquareTopRight[i] = 0;
         bbTemp = bbSquareTemp;
         while((bbTemp = bbTemp << 9  & ~bbFile[0]) != 0)
         {
            bbSquareTopRight[i] ^= bbTemp;
         }
         
         //Se inicia la direccion -1
         //Lo que quiere decir que para cada casilla se le asigna un bitboard
         //que pone a uno las posiciones de las casillas que estan  en la misma fila
         //a su izquierda
         bbSquareLeft[i] = 0;
         bbTemp = bbSquareTemp;
         while((bbTemp = bbTemp >>> 1   &  ~bbFile[7]) != 0)
         {
            bbSquareLeft[i] ^= bbTemp;
         }
         
         //Se inicia la direccion -7
         //Lo que quiere decir que para cada casilla se le asigna un bitboard
         //que pone a uno las posiciones de las casillas que estan en su diagonal
         //inferior derecha
         bbSquareBottomRight[i] = 0;
         bbTemp = bbSquareTemp;
         while((((bbTemp = bbTemp >>> 7)   & ~bbFile[0]) != 0))
         {
            bbSquareBottomRight[i] ^= bbTemp;
         }
         
         //Se inicia la direccion -8
         //Lo que quiere decir que para cada casilla se le asigna un bitboard
         //que pone a uno las posiciones de las casillas que estan en su misma
         //columna por la parte inferior
         bbSquareBottom[i] = 0;
         bbTemp = bbSquareTemp;
         while((bbTemp = bbTemp >>> 8) != 0)
         {
            bbSquareBottom[i] ^= bbTemp;
         }
         
         //Se inicia la direccion -9
         //Lo que quiere decir que para cada casilla se le asigna un bitboard
         //que pone a uno las posiciones de las casillas que estan en su diagonal
         //inferior izquierda
         bbSquareBottomLeft[i] = 0;
         bbTemp = bbSquareTemp;
         while((bbTemp = bbTemp >>> 9  & ~bbFile[7]) != 0)
         {
            bbSquareBottomLeft[i] ^= bbTemp;
         }
         
         //Se actualiza el bitboard de posicion desplazandole a la casilla siguiente
         bbSquareTemp = bbSquareTemp << 1;
      }
      
      //Se inicia el bitboard de las casillas atacadas por una dama
      for(int i = 0; i < 64; i++)
      {
         bbQueenAttack[i] = bbSquareRight[i] | bbSquareTopLeft[i] | bbSquareTop[i] | bbSquareTopRight[i]
                              | bbSquareLeft[i] | bbSquareBottomRight[i] | bbSquareBottom[i] | bbSquareBottomLeft[i];
      }
      
      //Se incician los bitboards de conexion
      for(int i = 0; i < 64; i++)
      {
         for(int j = 0; j < 64; j++)
         {
            long bbI = bbSquare[i];
            long bbJ = bbSquare[j];
            
            long bbMainDiagonalI = bbSquareTopRight[i] | bbSquareBottomLeft[i] | bbI;
            long bbMainDiagonalJ = bbSquareTopRight[j] | bbSquareBottomLeft[j] | bbJ;
            bbMainDiagonalFullConnection[i][j] = bbMainDiagonalI & bbMainDiagonalJ;
            
            long bbSecondaryDiagonalI = bbSquareTopLeft[i] | bbSquareBottomRight[i] | bbI;
            long bbSecondaryDiagonalJ = bbSquareTopLeft[j] | bbSquareBottomRight[j] | bbJ;
            bbSecondaryDiagonalFullConnection[i][j] = bbSecondaryDiagonalI & bbSecondaryDiagonalJ;
            
            long bbRankI = bbSquareRight[i] | bbSquareLeft[i] | bbI;
            long bbRankJ = bbSquareRight[j] | bbSquareLeft[j] | bbJ;
            bbRankFullConnection[i][j] = bbRankI & bbRankJ;
            
            long bbFileI = bbSquareTop[i] | bbSquareBottom[i] | bbI;
            long bbFileJ = bbSquareTop[j] | bbSquareBottom[j] | bbJ;
            bbFileFullConnection[i][j] = bbFileI & bbFileJ;
            
            bbDiagonalFullConnection[i][j] = bbMainDiagonalFullConnection[i][j] |
                                           bbSecondaryDiagonalFullConnection[i][j];
            
            bbLinealFullConnection[i][j] = bbRankFullConnection[i][j] |
                                          bbFileFullConnection[i][j];
            
            bbFullConnection[i][j] = bbDiagonalFullConnection[i][j] |
                                     bbLinealFullConnection[i][j];
            
            
            bbMainDiagonalInclusiveConnection[i][j] =  (bbSquareTopRight[i] ^ bbSquareTopRight[j] | bbI | bbJ) & 
                                                   bbMainDiagonalFullConnection[i][j];
            bbMainDiagonalExclusiveConnection[i][j] = bbMainDiagonalInclusiveConnection[i][j]
                                                   & ~bbI &  ~bbJ;
            
            
            bbSecondaryDiagonalInclusiveConnection[i][j] = (bbSquareTopLeft[i] ^ bbSquareTopLeft[j] | bbI | bbJ) &
                                                   bbSecondaryDiagonalFullConnection[i][j];
            bbSecondaryDiagonalExclusiveConnection[i][j] = bbSecondaryDiagonalInclusiveConnection[i][j]
                                                   & ~bbI & ~bbJ;
            
            
            bbRankInclusiveConnection[i][j] = (bbSquareRight[i] ^ bbSquareRight[j] | bbI | bbJ) &
                                          bbRankFullConnection[i][j];
            bbRankExclusiveConnection[i][j] = bbRankInclusiveConnection[i][j]
                                         & ~bbI &  ~bbJ;
            
            
            
            bbFileInclusiveConnection[i][j] = (bbSquareTop[i] ^ bbSquareTop[j] | bbI | bbJ) &
                                          bbFileFullConnection[i][j];
            bbFileExclusiveConnection[i][j] = bbFileInclusiveConnection[i][j]
                                           & ~bbI &  ~bbJ;
            
            
            
            bbDiagonalExclusiveConnection[i][j] = bbMainDiagonalExclusiveConnection[i][j] |
                                       bbSecondaryDiagonalExclusiveConnection[i][j];
            bbDiagonalInclusiveConnection[i][j] = bbMainDiagonalInclusiveConnection[i][j] |
                                          bbSecondaryDiagonalInclusiveConnection[i][j];
            
            
            bbLinealExclusiveConnection[i][j] = bbRankExclusiveConnection[i][j] |
                                          bbFileExclusiveConnection[i][j];
            bbLinearInclusiveConnection[i][j]  = bbRankInclusiveConnection[i][j] |
                                          bbFileInclusiveConnection[i][j];
            
            
            bbExclusiveConnection[i][j] = bbDiagonalExclusiveConnection[i][j] |
                                      bbLinealExclusiveConnection[i][j];
            bbInclusiveConnection[i][j] = bbDiagonalInclusiveConnection[i][j] |
                                      bbLinearInclusiveConnection[i][j];
         }
      }
   
      bbSquareTemp = 1;
      //Se inician los bitboards de ataque en filas, columnas y diagonales
      for(int i = 0; i < 64; i++)
      {
         int rank = i / 8;
         for(long j = 0; j < 256; j++)
         {
            //Se calcula primero el ataque en fila
            bbTemp = j << (8 * rank); 
            
            if((bbSquareTemp & bbTemp) != 0)
            { 
               //Si el bitboard de la posicion y la ocupacion de fila no coinciden 
               //Se calculan las casillas atacadas por la derecha
               long bbAttack = bbSquareRight[i];
               long bbBlockingSquares = bbAttack & bbTemp & bbRank[rank];
               if(bbBlockingSquares != 0)
               {
                  //Se obtiene el bitboard que representa la posicion
                  //mas cercana bloqueada por la derecha 
                  int blockingIndex = Long.numberOfTrailingZeros(bbBlockingSquares);
                  bbAttack ^= bbSquareRight[blockingIndex];
               }
               
               //Se actualiza el bitboard de atacadas fila de la casilla i con la ocupacion de fila j (por la derecha)
               bbRankAttack[i][(int)j] = bbAttack;
               
               //Se calculan las casillas atacadas por la izquierda
               bbAttack = bbSquareLeft[i];
               bbBlockingSquares = bbAttack & bbTemp & bbRank[rank];
               if(bbBlockingSquares != 0)
               {
                  //Se obtiene el bitboard que representa la posicion
                  //mas cercana bloqueada por la izquierda
                  int blockingIndex = 63 - Long.numberOfLeadingZeros(bbBlockingSquares);
                  bbAttack ^= bbSquareLeft[blockingIndex];
               }
               
               //Se actualiza el bitboard de atacadas fila de la casilla i con la ocupacion de fila j (por la izquierda)
               bbRankAttack[i][(int)j] ^= bbAttack;
            }
            
            //A partir del ataque en fila se calcula el ataque en columna rotando el bitboard 90 grados
            //en sentido horario
            bbFileAttack[squareRotated90DRight[i]][(int) j] = BitboardUtils.rotate90DegreesRight(bbRankAttack[i][(int)j]);
            
            //A partir del ataque en fila se calcula el ataque en la diagonal a1h8
            //desrotando 45 grados horario
            bbPrincipalDiagonalAttack[squareRotated45DRight[i]][(int)j] = BitboardUtils.unrotate45DegreesRight(bbRankAttack[i][(int)j] & mainDiagonalMask[i]);
            
            //A partir del ataque en fila se calcula el ataque en la diagonal a8h1
            //desrotando 45 grados en sentido antihorario
            bbSecondaryDiagonalAttack[inverseSquareRotated45DRight[i]][(int)j] = BitboardUtils.unrotate45DegreesLeft(bbRankAttack[i][(int)j] & secondaryDiagonalMask[i]);
         }
         
         //Se desplaza el bitboard de la posicion para que apunte a la siguiente
         bbSquareTemp = bbSquareTemp << 1;
      }
      
      
      //Se inicializan los bitboards de casillas relacionadas con el enroque
      bbCastlingLongSquares[Colour.WHITE.index] = bbSquare[Square.b1.index] | bbSquare[Square.c1.index] | bbSquare[Square.d1.index];
      bbCastlingLongSquares[Colour.BLACK.index] = bbSquare[Square.b8.index] | bbSquare[Square.c8.index] | bbSquare[Square.d8.index];
      
      bbCastlingShortSquares[Colour.WHITE.index] = new Square[2];
      bbCastlingShortSquares[Colour.WHITE.index][0] = Square.c1;
      bbCastlingShortSquares[Colour.WHITE.index][1] = Square.d1;
      
      bbCastlingShortSquares[Colour.BLACK.index] = new Square[2];
      bbCastlingShortSquares[Colour.BLACK.index][0] = Square.c8;
      bbCastlingShortSquares[Colour.BLACK.index][1] = Square.d8;
      
      castlingLongKingSquares[Colour.WHITE.index] = bbSquare[Square.f1.index] | bbSquare[Square.g1.index];
      castlingLongKingSquares[Colour.BLACK.index] = bbSquare[Square.f8.index] | bbSquare[Square.g8.index];
      
      castlingShortKingSquares[Colour.WHITE.index] = new Square[2];
      castlingShortKingSquares[Colour.WHITE.index][0] = Square.f1;
      castlingShortKingSquares[Colour.WHITE.index][1] = Square.g1;
      
      castlingShortKingSquares[Colour.BLACK.index] = new Square[2];
      castlingShortKingSquares[Colour.BLACK.index][0] = Square.f8;
      castlingShortKingSquares[Colour.BLACK.index][1] = Square.g8;
      
      
      //Se inicializan los arrays de casillas relacionadas con las capturas al paso
      for(Square  s = Square.a1; s != null; s = s.getNext())
      {
         if(s.getRank() == Rank._3)
         {
            enPassantSquareSource[s.index] = s.getNextInFile();
            enPassantSquareDestination[s.index] = null;
         }
         else if(s.getRank() == Rank._6)
         {
            enPassantSquareSource[s.index] = s.getPreviousInFile();
            enPassantSquareDestination[s.index] = null;
         }
         else if(s.getRank() == Rank._4)
         {
            enPassantSquareDestination[s.index] = s.getPreviousInFile();
            enPassantSquareSource[s.index] = null;
         }
         else if(s.getRank() == Rank._5)
         {
            enPassantSquareDestination[s.index] = s.getNextInFile();
            enPassantSquareSource[s.index] = null;
         }
         else
         {
            enPassantSquareSource[s.index] = null;
            enPassantSquareDestination[s.index] = null;
         }    
      }
   } // Fin de iniciarlizar()
   
   /**
    * 
    *
    */
   protected void updateOcupied()
   {
      //long ti = System.currentTimeMillis();
      
      //Se calcula el bitboard de ocupación de piezas blancas
      bbWhiteOccupation = bbPiecesOccupation[Piece.WHITE_PAWN.index] |
                     bbPiecesOccupation[Piece.WHITE_ROOK.index] |
                     bbPiecesOccupation[Piece.WHITE_KNIGHT.index] |
                     bbPiecesOccupation[Piece.WHITE_BISHOP.index] |
                     bbPiecesOccupation[Piece.WHITE_QUEEN.index] |
                     bbPiecesOccupation[Piece.WHITE_KING.index];
         
      //Se calcula el bitboard de ocupación de piezas negras
      bbBlackOccupation =  bbPiecesOccupation[Piece.BLACK_PAWN.index] |
                     bbPiecesOccupation[Piece.BLACK_ROOK.index] |
                     bbPiecesOccupation[Piece.BLACK_KNIGHT.index] |
                     bbPiecesOccupation[Piece.BLACK_BISHOP.index] |
                     bbPiecesOccupation[Piece.BLACK_QUEEN.index] |
                     bbPiecesOccupation[Piece.BLACK_KING.index];
      
      bbColourOccupation[Colour.WHITE.index] = bbWhiteOccupation;
      bbColourOccupation[Colour.BLACK.index] = bbBlackOccupation;
      
      //Se calcula el bitboard de las casillas ocupadas
      bbOccupation = bbWhiteOccupation | bbBlackOccupation;
      
      //long to = System.currentTimeMillis();
      //System.out.println("Tiempo de actualizacion de ocupadas: "+(to-ti)+ " milisegundos");
      
   } //Fin de actualizarOcupadas()
   

   public void updateAttackedFromFull()
   {  
      for(int i = 0; i < 64; i++)
      {
         bbAttackedFromSquare[i] = 0;
         bbAttackerToSquare[i] = 0;
      }
      
      
      //Se generan las casillas atacadas por el rey blanco
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.WHITE_KING.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         uaBbAttacked = bbKingAttack[uaSquareIndex];
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare, uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por el rey negro
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.BLACK_KING.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         uaBbAttacked = bbKingAttack[uaSquareIndex];
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare, uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por los caballos blancos
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.WHITE_KNIGHT.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         uaBbAttacked = bbKnightAttack[uaSquareIndex];
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare, uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por los caballos negros
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.BLACK_KNIGHT.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         uaBbAttacked = bbKnightAttack[uaSquareIndex];
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare, uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por las torres blancas
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.WHITE_ROOK.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         //Se generan las casillas atacadas en filas y columnas
         uaBbAttacked = getAttackedInRank(uaSquareIndex);
         uaBbAttacked |= getAttackedInFile(uaSquareIndex);
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare, uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por las torres negras
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.BLACK_ROOK.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         //Se generan las casillas atacadas en filas y columnas
         uaBbAttacked = getAttackedInRank(uaSquareIndex);
         uaBbAttacked |= getAttackedInFile(uaSquareIndex);
         
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare, uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por las reinas blancas
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.WHITE_QUEEN.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         //Se generan las casillas atacadas en filas y columnas.
         uaBbAttacked = getAttackedInRank(uaSquareIndex);
         uaBbAttacked |= getAttackedInFile(uaSquareIndex);
         //Se generan las casillas atacadas en diagonal
         uaBbAttacked |= getAttackedInMainDiagonal(uaSquareIndex);
         uaBbAttacked |= getAttackedInSecondaryDiagonal(uaSquareIndex);
         
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare,uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por las reinas negras
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.BLACK_QUEEN.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         //Se generan las casillas atacadas en filas y columnas.
         uaBbAttacked = getAttackedInRank(uaSquareIndex);
         uaBbAttacked |= getAttackedInFile(uaSquareIndex);
         //Se generan las casillas atacadas en diagonal
         uaBbAttacked |= getAttackedInMainDiagonal(uaSquareIndex);
         uaBbAttacked |= getAttackedInSecondaryDiagonal(uaSquareIndex);
         
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare,uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por los alfiles blancos
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.WHITE_BISHOP.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         //Se generan las casillas atacadas en diagonal
         uaBbAttacked = getAttackedInMainDiagonal(uaSquareIndex);
         uaBbAttacked |= getAttackedInSecondaryDiagonal(uaSquareIndex);
         
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare,uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por los alfiles negros
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.BLACK_BISHOP.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         //Se generan las casillas atacadas en diagonal
         uaBbAttacked = getAttackedInMainDiagonal(uaSquareIndex);
         uaBbAttacked |= getAttackedInSecondaryDiagonal(uaSquareIndex);
         
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare,uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por los peones blancos (en diagonal)
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.WHITE_PAWN.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         //Se generan las casillas atacadas en diagonal
         uaBbAttacked = bbWhitePawnAttack[uaSquareIndex];
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare,uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
      
      //Se generan las casillas atacadas por los peones negros (en diagonal)
      uaBbOccupiedTemp = bbPiecesOccupation[Piece.BLACK_PAWN.index];
      uaNumPieces = Long.bitCount(uaBbOccupiedTemp);
      for(int i = 0; i < uaNumPieces; i++)
      {
         uaSquareIndex = Long.numberOfTrailingZeros(uaBbOccupiedTemp);
         uaBbAttackerSquare = bbSquare[uaSquareIndex];
         
         //Se generan las casillas atacadas en diagonal
         uaBbAttacked = bbBlackPawnAttack[uaSquareIndex];
         bbAttackedFromSquare[uaSquareIndex] |= uaBbAttacked;
         
         updateAttackingTo(uaBbAttackerSquare,uaBbAttacked);
         
         uaBbOccupiedTemp ^= uaBbAttackerSquare;
      }
   } //Fin de actualizarAtacadasDesdeAmpliado()
   
   /**
    * 
    * @param bbAttacker
    * @param bbAttacked
    */
   private void updateAttackingTo(long bbAttacker, long bbAttacked)
   {
      //Se actualiza los bitboards de atacantes_de de las casillas que ataca el bitboard
      uatNumAtacked = Long.bitCount(bbAttacked);
      for(int j = 0; j < uatNumAtacked; j++)
      {
         uatSquareIndex = Long.numberOfTrailingZeros(bbAttacked);
         
         bbAttackerToSquare[uatSquareIndex] |= bbAttacker; 
         
         bbAttacked ^= bbSquare[uatSquareIndex];
      }
   } //Fin de actualizarAtacantesDe(long bitboardAtacante, long atacadas)
   
   
   /**
    * 
    * @param squareIndex
    * @return
    */
   public long getAttackedInRank(int squareIndex)
   {
      //Se obtiene el desplazamiento para obtener la fila correspondiente a la casilla
      int offset = rankSquare[squareIndex] << 3;
      //Se obtiene la fila desplazando el bitboard de ocupadas
      int occupation = (int)((bbOccupation >>> offset) & 0xFF);
      //Se devuelve el bitboard de atacadas fila para la casilla y ocupacion
      return bbRankAttack[squareIndex][occupation];
   } //Fin de getAtacadasRank(int casilla)
   
   /**
    * 
    * @param squareIndex
    * @return
    */
   public long getAttackedInFile(int squareIndex)
   {
      //Se obtiene el desplazamiento para obtener la columna correspondiente a la casilla
      int offset = rankSquare[squareRotated90DLeft[squareIndex]] << 3;
      //Se obtiene la fila (columna rotada) desplazando el bitboard de ocupadasR90
      int occupation = (int)((bbOccupationR90L >>> offset) & 0xFF);
      //Se devuelve el bitboard de atacadas columna para la casilla y ocupacion
      return bbFileAttack[squareIndex][occupation];
   } //Fin de getAtacadasFile(int casilla)

   /**
    * 
    * @param squareIndex
    * @return
    */
   public long getAttackedInMainDiagonal(int squareIndex)
   {
      //Se obtiene el desplazamiento para obtener la diagonal principal correspondiente a la casilla
      int offset = rankSquare[squareRotated45DLeft[squareIndex]] <<3;
      //Se obtiene la fila (diagonal rotada) desplazando el bitboard de ocupadasR45R
      int occupation = (int)((bbOccupationR45R >>> offset) & 0xFF);
      //Se devuelve el bitboard de atacadas diagonal A1H8 para la casilla y ocupacion
      return bbPrincipalDiagonalAttack[squareIndex][occupation];
   } //Fin de getAtacadasDiagonalA1H8(int casilla)
   
   /**
    * 
    * @param squareIndex
    * @return
    */
   public long getAttackedInSecondaryDiagonal(int squareIndex)
   {
      //Se obtiene el desplazamiento para obtener la diagonal secndaria correspondiente a la casilla
      int offset = rankSquare[inverseSquareRotated45DLeft[squareIndex]] <<3;
      //Se obtiene la fila (diagonal rotada) desplazando el bitboard de ocupadasR45L
      int occupation = (int) ((bbOccupationR45L >>> offset) & 0xFF);
      //Se devuelve el bitboard de atacadas diagonal A8H1 para la casilla y ocupacion
      return bbSecondaryDiagonalAttack[squareIndex][occupation];
   } //Fin de getAtacadasDiagonalA8H1
   
   
   protected void resetMoves()
   {
      movesGenerated = false;
      movesList = null;
   }
   
   
   /************* MÉTODOS HEREDADOS DE POSICION ABSTRACTA ********************/

   public boolean loadFen(String posicionFen)
   {
      resetMoves();
      
      boolean result = super.loadFen(posicionFen);
      
      updateOcupied();
      updateAttackedFromFull();
      
      return result;
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#deshacerMovement()
    */
   public boolean undoMove(Movement mov)
   {
      resetMoves();
      
      //Se elimina la posición resultante en el hash de posiciones
      Integer keyValue = gamePositionsHash.get(zobristKey.getKey());
      if(keyValue != null)
      {
         keyValue = keyValue - 1;
         if(keyValue == 0)
            gamePositionsHash.remove(zobristKey.getKey());
         else
            gamePositionsHash.put(zobristKey.getKey(), keyValue);
      }
      
      Piece sp = mov.getSourcePiece();
      Piece dp = mov.getDestinationPiece();
      Square ss = mov.getSource();
      Square ds = mov.getDestination();
      
      removePiece(ds);
      if(dp != null)
         putPiece(dp,ds);
      putPiece(sp,ss);
      
      //Si es captura al paso se pone un peón en la casilla correspondiente 
      //TODO añadir comprobación de si es casilla al paso en el caso
      //de que sea un movimiento de peón en diagonal (origen.file != destino.file)
      //y en la casilla destino no exista pieza
      if(mov.isInPassant())
      {
         if(ds.getRank() == Rank._3)
         {
            putPiece(Piece.WHITE_PAWN, enPassantSquareSource[ds.index]);
         }
         else if(ds.getRank() == Rank._6)
         {
            putPiece(Piece.BLACK_PAWN, enPassantSquareSource[ds.index]);
         }
      }
      
      if(sp == Piece.WHITE_KING && ss == Square.e1)
      {
         if(ds == Square.g1)
         {
            //Enroque corto blanco
            removePiece(Square.f1);
            putPiece(Piece.WHITE_ROOK,Square.h1);
            
         }
         else if(ds == Square.c1)
         {
            //Enroque largo blanco
            removePiece(Square.d1);
            putPiece(Piece.WHITE_ROOK,Square.a1);
         }
      }
      else if(sp == Piece.BLACK_KING && ss == Square.e8)
      {
         if(ds == Square.g8)
         {
            //Enroque corto negro
            removePiece(Square.f8);
            putPiece(Piece.BLACK_ROOK,Square.h8);
         }
         else if(ds == Square.c8)
         {
            //Enroque largo negro
            removePiece(Square.d8);
            putPiece(Piece.BLACK_ROOK,Square.a8);
         }
      }
      
      //Actualizar turno
      this.setTurn(turn.opposite());
      
      //Se actualizan los bitboards para la posición resultante
      updateAttackedFromFull();
      
      //Se recoge el estado irreversible de la pila y se reinstaura
      IrreversibleState state = stateStack.removeLast();
      this.setEnPassantSquare(state.enPassantSquare);
      this.setFiftyMovesRuleIndex(state.fiftyMovesCounter);
      this.setCastlingLong(Colour.WHITE, state.longCastling[Colour.WHITE.index]);
      this.setCastlingLong(Colour.BLACK, state.longCastling[Colour.BLACK.index]);
      this.setCastlingShort(Colour.WHITE, state.shortCastling[Colour.WHITE.index]);
      this.setCastlingShort(Colour.BLACK, state.shortCastling[Colour.BLACK.index]);
      /////
      
      return true;
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#hacerMovement(es.dagaren.chesi.Movement)
    */
   public boolean doMove(Movement mov)
   {
      //Se recoge el estado irreversible actual y se mete en la pila
      IrreversibleState state = new IrreversibleState();
      state.enPassantSquare = this.enPassantSquare;
      state.fiftyMovesCounter = this.fiftyMovesRuleIndex;
      state.longCastling[Colour.WHITE.index] = this.whiteCastlingLong;
      state.longCastling[Colour.BLACK.index] = this.blackCastlingLong;
      state.shortCastling[Colour.WHITE.index] = this.whiteCastlingShort;
      state.shortCastling[Colour.BLACK.index] = this.blackCastlingShort;
      stateStack.addLast(state);
      /////
      
      resetMoves();
      
      Piece sp = mov.getSourcePiece();
      Piece dp = mov.getDestinationPiece();
      Piece pp = mov.getPromotionPiece();
      Square ss = mov.getSource();
      Square ds = mov.getDestination();
      
      removePiece(ss);
      removePiece(ds);
      
      //Si es captura al paso se quita el peón de su casilla
      //TODO añadir comprobación de si es casilla al paso en el caso
      //de que sea un movimiento de peón en diagonal (origen.file != destino.file)
      //y en la casilla destino no exista pieza
      if(mov.isInPassant())
      {
         removePiece(enPassantSquareSource[ds.index]);
      }
      
      if(pp != null)
      {
         putPiece(pp,ds);
      }
      else
      {
         putPiece(sp,ds);
      }
      
      //Se realiza el movimiento de torre en caso de que el movimiento sea un enroque
      if(sp == Piece.WHITE_KING && ss == Square.e1)
      {
         if(ds == Square.g1)
         {
            //Enroque corto blanco
            removePiece(Square.h1);
            putPiece(Piece.WHITE_ROOK,Square.f1);
         }
         else if(ds == Square.c1)
         {
            //Enroque largo blanco
            removePiece(Square.a1);
            putPiece(Piece.WHITE_ROOK,Square.d1);
         }
      }
      else if(sp == Piece.BLACK_KING && ss == Square.e8)
      {
         if(ds == Square.g8)
         {
            //Enroque corto negro
            removePiece(Square.h8);
            putPiece(Piece.BLACK_ROOK,Square.f8);
         }
         else if(ds == Square.c8)
         {
            //Enroque largo negro
            removePiece(Square.a8);
            putPiece(Piece.BLACK_ROOK,Square.d8);
         }
      }
      //Se actualizan los permisos de enroque
      if(sp.genericPiece == GenericPiece.KING)
      {
         if(sp == Piece.WHITE_KING)
         {
            this.setCastlingLong(Colour.WHITE, false);
            this.setCastlingShort(Colour.WHITE, false);
         }
         else if(sp == Piece.BLACK_KING)
         {
            this.setCastlingLong(Colour.BLACK, false);
            this.setCastlingShort(Colour.BLACK, false);
         }
      }
      else if(sp.genericPiece == GenericPiece.ROOK)
      {
         if(sp == Piece.WHITE_ROOK)
         {
            if(ss == Square.a1)
            {
               this.setCastlingLong(Colour.WHITE, false);
            }
            else if(ss == Square.h1)
            {
               this.setCastlingShort(Colour.WHITE, false);
            }
         }
         else if(sp == Piece.BLACK_ROOK)
         {
            if(ss == Square.a8)
            {
               this.setCastlingLong(Colour.BLACK, false);
            }
            else if(ss == Square.h8)
            {
               this.setCastlingShort(Colour.BLACK, false);
            }
         }
      }
      //Se comprueba si es una captura de una torre en casilla
      //origigen quitar permiso enroque.
      if(dp != null && dp.genericPiece == GenericPiece.ROOK)
      {
        if(ds == Square.a1 && this.whiteCastlingLong)
        {
           this.setCastlingLong(Colour.WHITE, false);
        }
        else if(ds == Square.a8 && this.blackCastlingLong)
        {
           this.setCastlingLong(Colour.BLACK, false);
        }
        else if(ds == Square.h1 && this.whiteCastlingShort)
        {
           this.setCastlingShort(Colour.WHITE, false);
        }
        else if(ds == Square.h8 && this.blackCastlingShort)
        {
           this.setCastlingShort(Colour.BLACK, false);
        }
      }
      
      
      //Todo ver si el movimiento hace que un peón se quede en disposición
      //de ser capturado al paso
      this.setEnPassantSquare(null);
      if(sp.genericPiece == GenericPiece.PAWN)
      {
         if(sp == Piece.WHITE_PAWN)
         {
            if(ss.getRank() == Rank._2 && ds.getRank() == Rank._4)
            {
               this.setEnPassantSquare(enPassantSquareDestination[ds.index]);
            }
         }
         else
         {
            if(ss.getRank() == Rank._7 && ds.getRank() == Rank._5)
            {
               this.setEnPassantSquare(enPassantSquareDestination[ds.index]);
            }
         }
      }
      
      //Actualizar regla 50 movimientos
      if(dp != null || sp.genericPiece == GenericPiece.PAWN || mov.isInPassant())
      {
         //Si es un movimiento de peón o captura se resetea el contador
         //de la regla de los 50 movimientos
         this.fiftyMovesRuleIndex = 0;
      }
      else
      {
         //Si no se incrementa el contador
         this.fiftyMovesRuleIndex++;
      }
      
      //TODO Actualizar número de movimiento
      
      
      //Actualizar turno
      setTurn(turn.opposite());
      
      //Se añade la posición resultante en el hash de posiciones
      Integer keyValue = gamePositionsHash.get(zobristKey.getKey());
      if(keyValue != null)
      {
         gamePositionsHash.put(zobristKey.getKey(), keyValue + 1);
      }
      else
      {
         gamePositionsHash.put(zobristKey.getKey(), 1);
      }
      
      //Se actualizan los bitboards para la posición resultante
      updateAttackedFromFull();
      
      return true;
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.representation.Position#getMoves(java.util.List)
    */
   @Override
   public void getMoves(List<Movement> movesList)
   {
      generarMovements(movesList);
   }
   
   public List<Movement> getMovements()
   {
      if(movesGenerated == false)
      {
         this.movesList = new ArrayList<Movement>();
         generarMovements(this.movesList);
      }
      
      return movesList;
   }
   
   public void generarMovements(List<Movement> movesList)
   {
      //long before = System.currentTimeMillis();
      movesList.clear();
      
      bbTurnOccupied = bbColourOccupation[turn.index];
      bbOppositeOccupied = bbColourOccupation[turn.opposite().index];
      
      //Se recupera la posición y bitboard del rey
      bbKing = bbPiecesOccupation[Piece.get(GenericPiece.KING, turn).index];
      kingSquareIndex = Long.numberOfTrailingZeros(bbKing);
      
      if(isInCheck(turn))
      {
         //Se generan los movimientos si el bando al que le toca jugar
         //se encuentra en jaque
         
         
         //Se generan los movimientos del rey a casillas que no están atacadas por las piezas
         //del oponente.
         /************** GENERACIÓN DE LOS MOVIMIENTOS DE REY *************/
         piece = Piece.get(GenericPiece.KING, turn);
         bbPieceOccupation = bbPiecesOccupation[piece.index];
         numPieces = Long.bitCount(bbPieceOccupation);
         for(int i = 0; i < numPieces; i++)
         {
            pieceSquareIndex = Long.numberOfTrailingZeros(bbPieceOccupation);
            
            //Se obtienen las casillas que ataca la pieza
            bbAttacked = bbAttackedFromSquare[pieceSquareIndex];
            bbAttacked &= ~bbTurnOccupied;
      
            //Se eliminan de las casillas en las que se mantiene el jaque por alguna pieza. 
            bbAttackers = bbAttackerToSquare[pieceSquareIndex] & bbOppositeOccupied;

            numAttackers = Long.bitCount(bbAttackers);

            for(int j = 0; j < numAttackers; j++)
            {
               attackerSquareIndex = Long.numberOfTrailingZeros(bbAttackers);

               if(pieceInSquare[attackerSquareIndex].genericPiece == GenericPiece.PAWN)
                 bbAttacked &= ~(bbInclusiveConnection[attackerSquareIndex][kingSquareIndex] & bbAttacked & ~bbSquare[attackerSquareIndex]);
               else
                 bbAttacked &= ~(bbFullConnection[attackerSquareIndex][kingSquareIndex] & bbAttacked & ~bbSquare[attackerSquareIndex]);
               
               bbAttackers ^= bbSquare[attackerSquareIndex];
            }

            numAttacked = Long.bitCount(bbAttacked);
            for(int j = 0; j < numAttacked; j++)
            {
               attackedSquareIndex = Long.numberOfTrailingZeros(bbAttacked);
               
               //Sólo se añade el movimiento si la casilla destino no está atacada por una pieza del contrario
               if((bbAttackerToSquare[attackedSquareIndex] & bbColourOccupation[turn.opposite().index]) == 0)
               {
                  capture = pieceInSquare[attackedSquareIndex];
                  mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], piece, capture);
                  
                  movesList.add(mov);
               }
               
               
               bbAttacked ^= bbSquare[attackedSquareIndex];
            }
            
         }
         /************** FIN DE GENERACIÓN DE LOS MOVIMIENTOS DE REY *************/
         
         
         
         //Se recupera el número de atacantes del rey
         bbKingAttackers = bbAttackerToSquare[kingSquareIndex] & bbOppositeOccupied;
         numAttackers = Long.bitCount(bbKingAttackers);
         
         //Si es más de uno quiere decir que sólo son válidos los movimientos de rey
         //y no hay que generar las capturas ni las intercepciones
         if(numAttackers == 1)
         {
            //Si sólo está dando jaque una pieza se generan las capturas de la pieza atacante y los movimientos de intercepción
            
            checkSquareIndex = Long.numberOfTrailingZeros(bbKingAttackers);
            /*** GENERACIÓN DE LAS CAPTURAS DE LA PIEZA ATACANTE ****/
            
            //Se recuperan las piezas que atacan a la pieza que ejerce el jaque
            bbAttackers = bbAttackerToSquare[checkSquareIndex];
            //Se quitan los atacantes del turno contrario y el propio rey
            bbAttackers &= ~(bbOppositeOccupied | bbKing) ; 
            
            numAttackers = Long.bitCount(bbAttackers);
            for(int i = 0; i < numAttackers; i++)
            {
               pieceSquareIndex = Long.numberOfTrailingZeros(bbAttackers);
               
               isPinned = false;
               
               bbAttacked = bbKingAttackers;
               
               //Se comprueba si hay conexión entre la casilla del rey y la casilla de la pieza
               //Si no la hay se generan los movimientos de forma normal
               bbConnection = bbInclusiveConnection[pieceSquareIndex][kingSquareIndex];
               if(bbConnection != 0)
               {
                  //Si hay conexión, se comprueba si hay alguna pieza en las casillas
                  //que unen la casilla del rey y la casilla de la pieza
                  //Si sí que hay alguna pieza se generan los movimientos de la pieza de forma normal
                  bbConnection = bbExclusiveConnection[pieceSquareIndex][kingSquareIndex];
                  if((bbConnection & bbOccupation) == 0)
                  {
                     //Si no hay casillas se comprueba si hay alguna pieza que ataque a la pieza en la misma
                     //linea que el rey y la pieza
                     //Si no hay ninguna se generan los movimientos de la pieza de forma normal
                     bbAttacker = bbFullConnection[pieceSquareIndex][kingSquareIndex] & 
                           bbAttackerToSquare[pieceSquareIndex] & bbOppositeOccupied;
                     if(bbAttacker != 0)
                     {
                        genericPiece = pieceInSquare[Long.numberOfTrailingZeros(bbAttacker)].genericPiece;
                        if(genericPiece != GenericPiece.PAWN && genericPiece != GenericPiece.KING)
                        {
                           //Si hay alguna pieza atacando en esa linea quiere decir que la pieugggza
                           //sólo se podrá mover a las casillas que conectan las casilla de la pieza
                           //y la casilla del atacante.
                           isPinned = true;
                        }
                     }
                  }
               }
               
               if(isPinned == false)
               {
                  squarePiece = pieceInSquare[pieceSquareIndex];
                  capturePiece = pieceInSquare[checkSquareIndex];
                  sourceSquare = square[pieceSquareIndex];
                  destinationSquare = square[checkSquareIndex];
                  
                  if(squarePiece.genericPiece != GenericPiece.PAWN)
                  {
                     mov = new Movement(sourceSquare, destinationSquare, squarePiece, capturePiece);
                     
                     movesList.add(mov);
                  }
                  else
                  {
                     if(checkSquareIndex < 56 && checkSquareIndex > 7)
                     {
                        mov = new Movement(sourceSquare, destinationSquare, squarePiece, capturePiece);
                     
                        movesList.add(mov);
                     }
                     else
                     {
                        mov = new Movement(sourceSquare, destinationSquare, squarePiece, capturePiece, Piece.get(GenericPiece.ROOK, turn));
                        
                        movesList.add(mov);
                        
                        mov = new Movement(sourceSquare, destinationSquare, squarePiece, capturePiece, Piece.get(GenericPiece.KNIGHT, turn));

                        movesList.add(mov);
                        
                        mov = new Movement(sourceSquare, destinationSquare, squarePiece, capturePiece, Piece.get(GenericPiece.BISHOP, turn));

                        movesList.add(mov);
                        
                        mov = new Movement(sourceSquare, destinationSquare, squarePiece, capturePiece, Piece.get(GenericPiece.QUEEN, turn));

                        movesList.add(mov);
                     }
                  }
               }
               
               bbAttackers ^= bbSquare[pieceSquareIndex];
            }
            
                   
            /* SI SE PUEDE CAPTURAR AL PASO */
            if(enPassantSquare != null && (bbSquare[enPassantSquareSource[enPassantSquare.index].index] & bbKingAttackers) != 0)
            {
               piece = Piece.get(GenericPiece.PAWN,turn);
               bbAttackers = bbAttackerToSquare[enPassantSquare.index] & bbPiecesOccupation[piece.index];
               
               numPieces = Long.bitCount(bbAttackers);
               for(int i = 0; i < numPieces; i++)
               {
                  isPinned = false;
                  
                  pieceSquareIndex = Long.numberOfTrailingZeros(bbAttackers);
                  bbEnPassantCapture = bbSquare[enPassantSquareSource[enPassantSquare.index].index];
                  
                  //Se comprueba si hay conexión entre la casilla del rey y la casilla de la pieza
                  //Si no la hay se generan los movimientos de forma normal
                  bbConnection = bbInclusiveConnection[pieceSquareIndex][kingSquareIndex];
                  if(bbConnection != 0)
                  {
                     //Si hay conexión, se comprueba si hay alguna pieza en las casillas
                     //que unen la casilla del rey y la casilla de la pieza
                     //Si sí que hay alguna pieza se generan los movimientos de la pieza de forma normal
                     bbConnection = bbExclusiveConnection[pieceSquareIndex][kingSquareIndex];
                     if((bbConnection & bbOccupation) == 0)
                     {
                        //Si no hay casillas se comprueba si hay alguna pieza de ataque de largo alcance
                        //que ataque a la pieza en la misma línea que el rey y la pieza
                        //Si no hay ninguna se generan los movimientos de la pieza de forma norma
                        bbAttacker = bbFullConnection[pieceSquareIndex][kingSquareIndex] & 
                              bbAttackerToSquare[pieceSquareIndex] & bbOppositeOccupied;
                        if(bbAttacker != 0)
                        {
                           genericPiece = pieceInSquare[Long.numberOfTrailingZeros(bbAttacker)].genericPiece;
                           if(genericPiece != GenericPiece.PAWN && genericPiece != GenericPiece.KING)
                           {
                              isPinned = true;
                           }
                        }
                     }
                  }
                  
                  if(!isPinned)
                  {
                     mov = new Movement(square[pieceSquareIndex], enPassantSquare, piece);
                     mov.setEnPassant(true);
                  
                     movesList.add(mov);
                  }
                  ///////////////////////
                  
               }
            }
            /* FIN SI SE PUEDE CAPTURAR AL PASO */
            
            /*** FIN DE GENERACIÓN DE LAS CAPTURAS DE LA PIEZA ATACANTE ****/
            
            
            
            
            
            
            /*** GENERACIÓN DE LOS MOVIMIENTOS DE INTERPOSICIÓN ****/
            
            //Se obtienen las casillas que se encuentran entre el rey y la pieza atacante y que son las piezas
            //en las que se puede interceptar el jaque
            bbAttacked = bbExclusiveConnection[checkSquareIndex][kingSquareIndex];
            
            //Se recorre cada una de las casillas de conexión y se generan los movimientos posibles de las piezas a esas casillas
            numSquares = Long.bitCount(bbAttacked);
            for(int i = 0; i < numSquares; i++)
            {
               squareIndex = Long.numberOfTrailingZeros(bbAttacked);
               
               //Para cada casilla se obtienen las piezas del mismo bando que pueden ir a esa casilla
               bbAttackers = bbAttackerToSquare[squareIndex]; 
               bbAttackers &= bbTurnOccupied & ~bbKing & ~bbPiecesOccupation[Piece.get(GenericPiece.PAWN,turn).index];
               
               //OperacionesBitboard.imprimirEnConsola(bitboardAtacantes);
               
               numAttackers = Long.bitCount(bbAttackers);
               for(int j = 0; j < numAttackers; j++)
               {
                  attackerSquareIndex = Long.numberOfTrailingZeros(bbAttackers);
                  
                  isPinned = false;
                  
                  //Se comprueba si hay conexión entre la casilla del rey y la casilla de la pieza
                  //Si no la hay se generan los movimientos de forma normal
                  bbConnection = bbInclusiveConnection[attackerSquareIndex][kingSquareIndex];
                  if(bbConnection != 0)
                  {
                     
                     //Si hay conexión, se comprueba si hay alguna pieza en las casillas
                     //que unen la casilla del rey y la casilla de la pieza
                     //Si sí que hay alguna pieza se generan los movimientos de la pieza de forma normal
                     bbConnection = bbExclusiveConnection[attackerSquareIndex][kingSquareIndex];
                     if((bbConnection & bbOccupation) == 0)
                     {
                        //Si no hay casillas se comprueba si hay alguna pieza que ataque a la pieza en la misma
                        //linea que el rey y la pieza
                        //Si no hay ninguna se generan los movimientos de la pieza de forma normal
                        bbAttacker = bbFullConnection[attackerSquareIndex][kingSquareIndex] & 
                              bbAttackerToSquare[attackerSquareIndex] & bbOppositeOccupied;
                        if(bbAttacker != 0)
                        {
                           genericPiece = pieceInSquare[Long.numberOfTrailingZeros(bbAttacker)].genericPiece;
                           if(genericPiece != GenericPiece.PAWN && genericPiece != GenericPiece.KING)
                           {
                              //Si hay alguna pieza atacando en esa linea quiere decir que la pieza
                              //sólo se podrá mover a las casillas que conectan las casilla de la pieza
                              //y la casilla del atacante.
                              isPinned = true;
                           }
                        }
                     }
                  }

                  if(isPinned == false)
                  {
                     mov = new Movement(square[attackerSquareIndex], square[squareIndex], pieceInSquare[attackerSquareIndex]);
       
                     movesList.add(mov);
                  }
                        
                        
                  bbAttackers ^= bbSquare[attackerSquareIndex];   
               }     
                        
               bbAttacked ^= bbSquare[squareIndex];
            }
            
            
            //Se recuperan el bitboard de las casillas entre la pieza que provoca el jaque y el rey
            bbAttacked = bbExclusiveConnection[checkSquareIndex][kingSquareIndex];
            //Se generan los movimientos de interposición de peones
            if(turn == Colour.WHITE)
            {
               //Se calculan las casillas de avance de los peones blancos
               advances1 = (bbPiecesOccupation[Piece.WHITE_PAWN.index] << 8 ) & ~bbOccupation;
               advances2 = ((bbRank[2] & advances1) << 8) & ~bbOccupation;
               
               //Se hace un AND con las casillas entre la pieza atacante y el rey
               advances1 &= bbAttacked;
               advances2 &= bbAttacked;
               
               
               numPieces = Long.bitCount(advances1);
               for(int i = 0; i < numPieces; i++)
               {
                  attackedSquareIndex = Long.numberOfTrailingZeros(advances1);
                  //bitboardSquareAtacada = bitboardSquare[indiceSquareAtacada];
                  //indiceSquarePiece = Long.numberOfTrailingZeros(bitboardSquareAtacada >>> 8);
                  pieceSquareIndex = attackedSquareIndex - 8;
                  
                  isPinned = false;
                  
                  //Se comprueba si hay conexión entre la casilla del rey y la casilla de la pieza
                  //Si no la hay se generan los movimientos de forma normal
                  bbConnection = bbInclusiveConnection[pieceSquareIndex][kingSquareIndex];
                  if(bbConnection != 0)
                  {   
                     //Si hay conexión, se comprueba si hay alguna pieza en las casillas
                     //que unen la casilla del rey y la casilla de la pieza
                     //Si sí que hay alguna pieza se generan los movimientos de la pieza de forma normal
                     bbConnection = bbExclusiveConnection[pieceSquareIndex][kingSquareIndex];
                     if((bbConnection & bbOccupation) == 0)
                     {
                        //Si no hay casillas se comprueba si hay alguna pieza que ataque a la pieza en la misma
                        //linea que el rey y la pieza
                        //Si no hay ninguna se generan los movimientos de la pieza de forma normal
                        bbAttacker = bbFullConnection[pieceSquareIndex][kingSquareIndex] & 
                              bbAttackerToSquare[pieceSquareIndex] & bbOppositeOccupied;
                        if(bbAttacker != 0)
                        {
                           genericPiece = pieceInSquare[Long.numberOfTrailingZeros(bbAttacker)].genericPiece;
                           if(genericPiece != GenericPiece.PAWN && genericPiece != GenericPiece.KING)
                           {
                              //Si hay alguna pieza atacando en esa linea quiere decir que la pieza
                              //sólo se podrá mover a las casillas que conectan las casilla de la pieza
                              //y la casilla del atacante.
                              isPinned = true;
                           }
                        }
                     }
                  }

                  if(isPinned == false)
                  {
                     if(attackedSquareIndex < 56 && attackedSquareIndex > 7)
                     {
                        mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.WHITE_PAWN);

                        movesList.add(mov);
                     }
                     else
                     {
                        mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.WHITE_PAWN, null, Piece.WHITE_ROOK);

                        movesList.add(mov);
                        
                        mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.WHITE_PAWN, null, Piece.WHITE_KNIGHT);

                        movesList.add(mov);
                        
                        mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.WHITE_PAWN, null, Piece.WHITE_BISHOP);

                        movesList.add(mov);
                        
                        mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.WHITE_PAWN, null, Piece.WHITE_QUEEN);

                        movesList.add(mov);
                     }
                  }
                  
                  advances1 ^= bbSquare[attackedSquareIndex];
               }
               
               numPieces = Long.bitCount(advances2);
               for(int i = 0; i < numPieces; i++)
               {
                  attackedSquareIndex = Long.numberOfTrailingZeros(advances2);
                  //bitboardSquareAtacada = bitboardSquare[indiceSquareAtacada];
                  //indiceSquarePiece = Long.numberOfTrailingZeros(bitboardSquareAtacada >>> 16);
                  pieceSquareIndex = attackedSquareIndex - 16;
                  
                  isPinned = false;
                  
                  //Se comprueba si hay conexión entre la casilla del rey y la casilla de la pieza
                  //Si no la hay se generan los movimientos de forma normal
                  bbConnection = bbInclusiveConnection[pieceSquareIndex][kingSquareIndex];
                  if(bbConnection != 0)
                  {      
                     //Si hay conexión, se comprueba si hay alguna pieza en las casillas
                     //que unen la casilla del rey y la casilla de la pieza
                     //Si sí que hay alguna pieza se generan los movimientos de la pieza de forma normal
                     bbConnection = bbExclusiveConnection[pieceSquareIndex][kingSquareIndex];
                     if((bbConnection & bbOccupation) == 0)
                     {
                        //Si no hay casillas se comprueba si hay alguna pieza que ataque a la pieza en la misma
                        //linea que el rey y la pieza
                        //Si no hay ninguna se generan los movimientos de la pieza de forma normal
                        bbAttacker = bbFullConnection[pieceSquareIndex][kingSquareIndex] & 
                              bbAttackerToSquare[pieceSquareIndex] & bbOppositeOccupied;
                        if(bbAttacker != 0)
                        {
                           genericPiece = pieceInSquare[Long.numberOfTrailingZeros(bbAttacker)].genericPiece;
                           if(genericPiece != GenericPiece.PAWN && genericPiece != GenericPiece.KING)
                           {
                              //Si hay alguna pieza atacando en esa linea quiere decir que la pieza
                              //sólo se podrá mover a las casillas que conectan las casilla de la pieza
                              //y la casilla del atacante.
                              isPinned = true;
                           }
                        }
                     }
                  }
                  
                  
                  if(isPinned == false)
                  {
                     mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.WHITE_PAWN);
                  
                     movesList.add(mov);
                  }
                  
                  advances2 ^= bbSquare[attackedSquareIndex];
               }
            }
            else
            {
               advances1 = (bbPiecesOccupation[Piece.BLACK_PAWN.index] >>> 8 ) & ~bbOccupation;
               advances2 = ((bbRank[5] & advances1) >>> 8) & ~bbOccupation;
               
               //Se hace un AND con las casillas entre la pieza atacante y el rey
               advances1 &= bbAttacked;
               advances2 &= bbAttacked;
               
               numPieces = Long.bitCount(advances1);
               for(int i = 0; i < numPieces; i++)
               {
                  attackedSquareIndex = Long.numberOfTrailingZeros(advances1);
                  //bitboardSquareAtacada = bitboardSquare[indiceSquareAtacada];
                  //indiceSquarePiece = Long.numberOfTrailingZeros(bitboardSquareAtacada << 8);
                  pieceSquareIndex = attackedSquareIndex + 8;
                  
                  isPinned = false;
                  
                  //Se comprueba si hay conexión entre la casilla del rey y la casilla de la pieza
                  //Si no la hay se generan los movimientos de forma normal
                  bbConnection = bbInclusiveConnection[pieceSquareIndex][kingSquareIndex];
                  if(bbConnection != 0)
                  {   
                     //Si hay conexión, se comprueba si hay alguna pieza en las casillas
                     //que unen la casilla del rey y la casilla de la pieza
                     //Si sí que hay alguna pieza se generan los movimientos de la pieza de forma normal
                     bbConnection = bbExclusiveConnection[pieceSquareIndex][kingSquareIndex];
                     if((bbConnection & bbOccupation) == 0)
                     {
                        //Si no hay casillas se comprueba si hay alguna pieza que ataque a la pieza en la misma
                        //linea que el rey y la pieza
                        //Si no hay ninguna se generan los movimientos de la pieza de forma normal
                        bbAttacker = bbFullConnection[pieceSquareIndex][kingSquareIndex] & 
                              bbAttackerToSquare[pieceSquareIndex] & bbOppositeOccupied;
                        if(bbAttacker != 0)
                        {
                           genericPiece = pieceInSquare[Long.numberOfTrailingZeros(bbAttacker)].genericPiece;
                           if(genericPiece != GenericPiece.PAWN && genericPiece != GenericPiece.KING)
                           {
                              //Si hay alguna pieza atacando en esa linea quiere decir que la pieza
                              //sólo se podrá mover a las casillas que conectan las casilla de la pieza
                              //y la casilla del atacante.
                              isPinned = true;
                           }
                        }
                     }
                  }
                  
                  if(isPinned == false)
                  {
                     if(attackedSquareIndex < 56 && attackedSquareIndex > 7)
                     {
                        mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.BLACK_PAWN);
                     
                        movesList.add(mov);
                     }
                     else
                     {
                        mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.BLACK_PAWN, null, Piece.BLACK_ROOK);

                        movesList.add(mov);
                        
                        mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.BLACK_PAWN, null, Piece.BLACK_KNIGHT);

                        movesList.add(mov);
                        
                        mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.BLACK_PAWN, null, Piece.BLACK_BISHOP);

                        movesList.add(mov);
                        
                        mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.BLACK_PAWN, null, Piece.BLACK_QUEEN);

                        movesList.add(mov);
                     }
                  }
                  
                  advances1 ^= bbSquare[attackedSquareIndex];
               }
               
               numPieces = Long.bitCount(advances2);
               for(int i = 0; i < numPieces; i++)
               {
                  attackedSquareIndex = Long.numberOfTrailingZeros(advances2);
                  //bitboardSquareAtacada = bitboardSquare[indiceSquareAtacada];
                  //indiceSquarePiece = Long.numberOfTrailingZeros(bitboardSquareAtacada << 16);
                  pieceSquareIndex = attackedSquareIndex + 16;
                  
                  isPinned = false;
                  
                  //Se comprueba si hay conexión entre la casilla del rey y la casilla de la pieza
                  //Si no la hay se generan los movimientos de forma normal
                  bbConnection = bbInclusiveConnection[pieceSquareIndex][kingSquareIndex];
                  if(bbConnection != 0)
                  {   
                     //Si hay conexión, se comprueba si hay alguna pieza en las casillas
                     //que unen la casilla del rey y la casilla de la pieza
                     //Si sí que hay alguna pieza se generan los movimientos de la pieza de forma normal
                     bbConnection = bbExclusiveConnection[pieceSquareIndex][kingSquareIndex];
                     if((bbConnection & bbOccupation) == 0)
                     {
                        //Si no hay casillas se comprueba si hay alguna pieza que ataque a la pieza en la misma
                        //linea que el rey y la pieza
                        //Si no hay ninguna se generan los movimientos de la pieza de forma normal
                        bbAttacker = bbFullConnection[pieceSquareIndex][kingSquareIndex] & 
                              bbAttackerToSquare[pieceSquareIndex] & bbOppositeOccupied;
                        if(bbAttacker != 0)
                        {
                           genericPiece = pieceInSquare[Long.numberOfTrailingZeros(bbAttacker)].genericPiece;
                           if(genericPiece != GenericPiece.PAWN && genericPiece != GenericPiece.KING)
                           {
                              //Si hay alguna pieza atacando en esa linea quiere decir que la pieza
                              //sólo se podrá mover a las casillas que conectan las casilla de la pieza
                              //y la casilla del atacante.
                              isPinned = true;
                           }
                        }
                     }
                  }
               
                  if(isPinned == false)
                  {
                     mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], Piece.BLACK_PAWN);
                  
                     movesList.add(mov);
                  }
                  
                  advances2 ^= bbSquare[attackedSquareIndex];
               }
            }
            
            /*** FIN DE GENERACIÓN DE LOS MOVIMIENTOS DE INTERPOSICIÓN ****/
            
         }
         
         
      }
      else
      {
         //Se generan los movimientos si no se encuentra en jaque
         
         /************** GENERACIÓN DE LOS MOVIMIENTOS DE DAMA *************/
         piece = Piece.get(GenericPiece.QUEEN,turn);
         
         this.generateMoves(movesList, piece);
         /************** FIN DE GENERACIÓN DE LOS MOVIMIENTOS DE DAMA *************/
         
         
         /************** GENERACIÓN DE LOS MOVIMIENTOS DE TORRE *************/
         piece = Piece.get(GenericPiece.ROOK,turn);
         
         this.generateMoves(movesList, piece);
         /************** FIN DE GENERACIÓN DE LOS MOVIMIENTOS DE TORRE *************/
         
         
         /************** GENERACIÓN DE LOS MOVIMIENTOS DE ÁLFIL *************/
         piece = Piece.get(GenericPiece.BISHOP,turn);
         
         this.generateMoves(movesList, piece);
         /************** FIN DE GENERACIÓN DE LOS MOVIMIENTOS DE ÁLFIL *************/
         
         
         
         
         /************** GENERACIÓN DE LOS MOVIMIENTOS DE CABALLO *************/
         piece = Piece.get(GenericPiece.KNIGHT,turn);
         
         this.generateMoves(movesList, piece);
         /************** FIN DE GENERACIÓN DE LOS MOVIMIENTOS DE CABALLO *************/
         
         
         
         /************** GENERACIÓN DE LOS MOVIMIENTOS DE REY *************/
         piece = Piece.get(GenericPiece.KING,turn);
         bbPieceOccupation = bbPiecesOccupation[piece.index];
         numPieces = Long.bitCount(bbPieceOccupation);
         for(int i = 0; i < numPieces; i++)
         {
            pieceSquareIndex = Long.numberOfTrailingZeros(bbPieceOccupation);
            
            //Se obtienen las casillas que ataca la pieza
            bbAttacked = bbAttackedFromSquare[pieceSquareIndex];
            bbAttacked &= ~bbTurnOccupied;
            
            numAttacked = Long.bitCount(bbAttacked);
            for(int j = 0; j < numAttacked; j++)
            {
               attackedSquareIndex = Long.numberOfTrailingZeros(bbAttacked);
               
               //Sólo se añade el movimiento si la casilla destino no está atacada por una pieza del contrario
               if((bbAttackerToSquare[attackedSquareIndex] & bbColourOccupation[turn.opposite().index]) == 0)
               {
                  capture = pieceInSquare[attackedSquareIndex];
                  mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], piece, capture);
                  
                  movesList.add(mov);
               }
               
               bbAttacked ^= bbSquare[attackedSquareIndex];
            }
            
         }
         
         //Se generan los movimientos de enroque
         if(this.getCastlingShort(turn))
         {
            //Se comprueba si están libres las casillas entre el rey y la torre en la fila h
            if((bbOccupation & castlingLongKingSquares[turn.index]) == 0)
            {
               //Se comprueba si están atacadas por el contrario las casillas por las que debe pasar el rey
               boolean attacked = false;
               for(Square square: castlingShortKingSquares[turn.index] )
               {
                  if((bbAttackerToSquare[square.index] & bbColourOccupation[turn.opposite().index]) != 0)
                  {
                     attacked = true;
                     break;
                  }
               }
               
               if(!attacked)
               {
                  Movement shortCastlingMove = Movement.getMovementEnroqueCorto(turn);
                  movesList.add(shortCastlingMove);
               }
            }
         }
         if(this.getCastlingLong(turn))
         {
            //Se comprueba si están libres las casillas entre el rey y la torre en la fila a
            if((bbOccupation & bbCastlingLongSquares[turn.index]) == 0)
            {
               //Se comprueba si están atacadas por el contrario las casillas por las que debe pasar el rey
               boolean attacked = false;
               for(Square square: bbCastlingShortSquares[turn.index] )
               {
                  if((bbAttackerToSquare[square.index] & bbColourOccupation[turn.opposite().index]) != 0)
                  {
                     attacked = true;
                     break;
                  }
               }
               
               if(!attacked)
               {
                  Movement longCastlingMove = Movement.getMovementEnroqueLargo(turn);
                  movesList.add(longCastlingMove);
               }
            }
         }
         /************** FIN DE GENERACIÓN DE LOS MOVIMIENTOS DE REY *************/
         
         
         /************** GENERACIÓN DE LOS MOVIMIENTOS DE PEÓN *************/
         piece = Piece.get(GenericPiece.PAWN,turn);
         bbPieceOccupation = bbPiecesOccupation[piece.index];
         numPieces = Long.bitCount(bbPieceOccupation);
         for(int i = 0; i < numPieces; i++)
         {
            pieceSquareIndex = Long.numberOfTrailingZeros(bbPieceOccupation);
               
            bbAttacked = bbAttackedFromSquare[pieceSquareIndex];
            bbAttacked &= bbOppositeOccupied;
            
            if(turn == Colour.WHITE)
            {
               bbAdvances = bbWhitePawnReach[pieceSquareIndex];
               bbAdvances = bbAdvances & ~(bbOccupation | ((bbOccupation ^ bbSquare[pieceSquareIndex]) <<8));
               
            }
            else
            {
               bbAdvances = bbBlackPawnReach[pieceSquareIndex];
               bbAdvances = bbAdvances & ~(bbOccupation | ((bbOccupation ^ bbSquare[pieceSquareIndex]) >>8));
            }
            
            bbAttacked |= bbAdvances;
            
            bbEnPassantCapture = 0;
            //Se comprueba si es posible la captura al paso
            if(enPassantSquare != null)
            {
               bbAttackedBefore = bbAttacked;
               bbAttacked |=    bbAttackedFromSquare[pieceSquareIndex] &
                                bbSquare[enPassantSquare.index];
               if(bbAttackedBefore != bbAttacked)
               {
                  bbEnPassantCapture = bbSquare[enPassantSquareSource[enPassantSquare.index].index];
               }
            }
            //////////////////////////////
            
            //Se comprueba si hay conexión entre la casilla del rey y la casilla de la pieza
            //Si no la hay se generan los movimientos de forma normal
            bbConnection = bbInclusiveConnection[pieceSquareIndex][kingSquareIndex];
            if(bbConnection != 0)
            {
               //Si hay conexión, se comprueba si hay alguna pieza en las casillas
               //que unen la casilla del rey y la casilla de la pieza
               //Si sí que hay alguna pieza se generan los movimientos de la pieza de forma normal
               bbConnection = bbExclusiveConnection[pieceSquareIndex][kingSquareIndex];
               if((bbConnection & bbOccupation) == 0)
               {
                  //Si no hay casillas se comprueba si hay alguna pieza de ataque de largo alcance
                  //que ataque a la pieza en la misma línea que el rey y la pieza
                  //Si no hay ninguna se generan los movimientos de la pieza de forma norma
                  bbAttacker = bbFullConnection[pieceSquareIndex][kingSquareIndex] & 
                        bbAttackerToSquare[pieceSquareIndex] & bbOppositeOccupied;
                  if(bbAttacker != 0)
                  {
                     genericPiece = pieceInSquare[Long.numberOfTrailingZeros(bbAttacker)].genericPiece;
                     if(genericPiece != GenericPiece.PAWN && genericPiece != GenericPiece.KING)
                     {
                        //Si hay alguna pieza atacando en esa línea quiere decir que la pieza
                        //sólo se podrá mover a las casillas entre la pieza clavada y el rey y las 
                        //que se encuentran entre la pieza clavada y la pieza que provoca la clavada
                        bbConnection = bbExclusiveConnection[pieceSquareIndex][Long.numberOfTrailingZeros(bbAttacker)] | bbAttacker;
                        bbConnection |= bbExclusiveConnection[pieceSquareIndex][kingSquareIndex];
                        
                        bbAttacked = bbAttacked & bbConnection;
                     }
                  }
               }

               if(bbEnPassantCapture != 0 && 
                     ((bbAttacked & bbSquare[enPassantSquare.index]) != 0) &&
                     (square[kingSquareIndex].getRank() == enPassantSquareSource[enPassantSquare.index].getRank()))
               {
                  //Se comprueba si hay que eliminar la captura al paso debido a
                  //que resultaría en una posición de jaque
                  if((bbExclusiveConnection[pieceSquareIndex][kingSquareIndex] & 
                     (bbOccupation ^
                      bbSquare[enPassantSquareSource[enPassantSquare.index].index])) == 0)
                  {
                     bbAttacker = bbFullConnection[pieceSquareIndex][kingSquareIndex] &
                        (bbAttackerToSquare[pieceSquareIndex] | 
                         bbAttackerToSquare[enPassantSquareSource[enPassantSquare.index].index] ) & 
                         bbOppositeOccupied;
                     if(bbAttacker != 0)
                     {
                        genericPiece = pieceInSquare[Long.numberOfTrailingZeros(bbAttacker)].genericPiece;
                        if(genericPiece != GenericPiece.PAWN && genericPiece != GenericPiece.KING)
                        {
                           //Eliminar el ovimiento de captura al paso
                           bbAttacked = bbAttacked ^ bbSquare[enPassantSquare.index];
                        }
                     }
                  } 
               }
            }            
            
            /////////////////////////////
            
            numAttacked = Long.bitCount(bbAttacked);
            for(int j = 0; j < numAttacked; j++)
            {
               attackedSquareIndex = Long.numberOfTrailingZeros(bbAttacked);
               
               if(attackedSquareIndex < 56 && attackedSquareIndex > 7)
               {
                  capture = pieceInSquare[attackedSquareIndex];
                  mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], piece, capture);
                  
                  //Se comprueba si es un movimiento al paso
                  if(enPassantSquare != null && 
                        attackedSquareIndex == enPassantSquare.index)
                  {
                     mov.setEnPassant(true);
                  }
               
                  movesList.add(mov);
               }
               else
               {
                  capture = pieceInSquare[attackedSquareIndex];
                  mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], piece, capture, Piece.get(GenericPiece.ROOK,turn));
                  
                  movesList.add(mov);
                  
                  mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], piece, capture, Piece.get(GenericPiece.KNIGHT,turn));

                  movesList.add(mov);
                  
                  mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], piece, capture, Piece.get(GenericPiece.BISHOP,turn));

                  movesList.add(mov);
                  
                  mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], piece, capture, Piece.get(GenericPiece.QUEEN,turn));

                  movesList.add(mov);
               }
               
               bbAttacked ^= bbSquare[attackedSquareIndex];
            }
            //TODO calcular movimiento al paso
            
            bbPieceOccupation ^= bbSquare[pieceSquareIndex];
         }
         /************** FIN DE GENERACIÓN DE LOS MOVIMIENTOS DE PEÓN *************/
         
      }
      
      movesGenerated = true;
      
      //long despues = System.currentTimeMillis();
      //tiempoRecuperarMovements += despues - before;
   }
   
   protected void generateMoves(List<Movement> movesList, Piece piece)
   {
      /************** GENERACIÓN DE LOS MOVIMIENTOS  *************/
      bbPieceOccupation = bbPiecesOccupation[piece.index];
      numPieces = Long.bitCount(bbPieceOccupation);
      for(int i = 0; i < numPieces; i++)
      {
         pieceSquareIndex = Long.numberOfTrailingZeros(bbPieceOccupation);
         
         //Se obtienen las casillas que ataca la pieza
         bbAttacked = bbAttackedFromSquare[pieceSquareIndex];
         bbAttacked &= ~bbTurnOccupied;
         
         //Se comprueba si hay conexión entre la casilla del rey y la casilla de la pieza
         //Si no la hay se generan los movimientos de forma normal
         bbConnection = bbInclusiveConnection[pieceSquareIndex][kingSquareIndex];
         if(bbConnection != 0)
         {
            //Si hay conexión, se comprueba si hay alguna pieza en las casillas
            //que unen la casilla del rey y la casilla de la pieza
            //Si sí que hay alguna pieza se generan los movimientos de la pieza de forma normal
            bbConnection = bbExclusiveConnection[pieceSquareIndex][kingSquareIndex];
            if((bbConnection & bbOccupation) == 0)
            {
               //Si no hay casillas se comprueba si hay alguna pieza de ataque de largo alcance
               //que ataque a la pieza en la misma línea que el rey y la pieza
               //Si no hay ninguna se generan los movimientos de la pieza de forma normal
               bbAttacker = bbFullConnection[pieceSquareIndex][kingSquareIndex] & 
                     bbAttackerToSquare[pieceSquareIndex] & bbOppositeOccupied;
               if(bbAttacker != 0)
               {
                  genericPiece = pieceInSquare[Long.numberOfTrailingZeros(bbAttacker)].genericPiece;
                  if(genericPiece != GenericPiece.PAWN && genericPiece != GenericPiece.KING)
                  {
                     //Si hay alguna pieza atacando en esa línea quiere decir que la pieza
                     //sólo se podrá mover a las casillas entre la pieza clavada y el rey y las 
                     //que se encuentran entre la pieza clavada y la pieza que provoca la clavada
                     bbConnection = bbExclusiveConnection[pieceSquareIndex][Long.numberOfTrailingZeros(bbAttacker)] | bbAttacker;
                     bbConnection |= bbExclusiveConnection[pieceSquareIndex][kingSquareIndex];
                     
                     bbAttacked = bbAttacked & bbConnection;
                  }
               }
            }
         }
         
         
         //En este momento en bitboardAtacadas se encuentran las casillas a las que legarmente puede ir
         //la dama
         numAttacked = Long.bitCount(bbAttacked);
         for(int j = 0; j < numAttacked; j++)
         {
            attackedSquareIndex = Long.numberOfTrailingZeros(bbAttacked);
            
            capture = pieceInSquare[attackedSquareIndex];
            mov = new Movement(square[pieceSquareIndex], square[attackedSquareIndex], piece, capture);
            
            movesList.add(mov);
            
            bbAttacked ^= bbSquare[attackedSquareIndex];
         }
         
         
         bbPieceOccupation ^= bbSquare[pieceSquareIndex];
      }
      /************** FIN DE GENERACIÓN DE LOS MOVIMIENTOS *************/
      
   }
   

   public Colour getPieceColour(Square cas)
   {
      // TODO Auto-generated method stub
      return null;
   }

   public GenericPiece getGenericPieceInSquare(Square cas)
   {
      // TODO Auto-generated method stub
      return null;
   }

   public int getNumPieces(GenericPiece fic, Colour col)
   {   
      return 0;
   }

   public boolean hasGenericPiece(GenericPiece fic, Colour col, Square cas)
   {
      // TODO Auto-generated method stub
      return false;
   }

   public boolean hasGenericPiece(GenericPiece fic, Colour col, Rank fil)
   {
      // TODO Auto-generated method stub
      return false;
   }

   public boolean hasPiece(GenericPiece fic, Colour col, File colu)
   {
      // TODO Auto-generated method stub
      return false;
   }

   public boolean hasPiece(Piece pie, File colu)
   {
      // TODO Auto-generated method stub
      return false;
   }
   
   public boolean isInCheck(Colour color)
   {
      Piece piezaRey = Piece.get(GenericPiece.KING, color);
      long ocupados = bbPiecesOccupation[piezaRey.index];
      int indiceSquareRey = Long.numberOfTrailingZeros(ocupados);
      
      if((bbAttackerToSquare[indiceSquareRey] & bbColourOccupation[color.opposite().index]) != 0)
      {
         //System.out.println("Square del rey en jaque: "+Square.fromIndex(indiceSquareRey).name());
         //System.out.println("Atacantes del rey: \n"+OperacionesBitboard.toString(atacantes_de[indiceSquareRey]));
         return true;
      }
      else
      {
         return false;
      }
   }

   public void putGenericPiece(GenericPiece ficha, Colour color, Square casilla)
   {
      resetMoves();
      
      Piece pieza = Piece.get(ficha,color);
      putPiece(pieza,casilla);
   }

   public void putPiece(Piece piece, Square square)
   {
      resetMoves();
      
      int squareIndex = square.index;
      
      Piece currentPiece = pieceInSquare[squareIndex];
      
      if(currentPiece == null)
      {
         //Se añade la pieza al bitboard correspondiente a la pieza
         bbPiecesOccupation[piece.index] |= bbSquare[squareIndex]; 
         
         //Se guarda la pieza que se encuentra en la casilla
         pieceInSquare[squareIndex] = piece;
         
         //Se actualizan los bitboards de ocupacion: normal, rotado 90 grados y rotados 45 grados a ambos lados
         bbOccupation |= bbSquare[squareIndex];
         bbOccupationR90L |= bbSquare[squareRotated90DLeft[squareIndex]];
         bbOccupationR45R |= bbSquare[squareRotated45DLeft[squareIndex]];
         bbOccupationR45L |= bbSquare[inverseSquareRotated45DLeft[squareIndex]];
   
         if(piece.colour == Colour.WHITE)
         {
            bbWhiteOccupation |= bbSquare[squareIndex];
            bbColourOccupation[Colour.WHITE.index] = bbWhiteOccupation;
         }
         else
         {
            bbBlackOccupation |= bbSquare[squareIndex];
            bbColourOccupation[Colour.BLACK.index] = bbBlackOccupation;
         }
         
         //Se actualiza la clave zobrist
         zobristKey.xorPiece(piece, square);
      }
      else
      {
         logger.error("Poniendo " + piece.name() + " en la casilla " + square.name() + " que no está vacía");
         System.exit(0);
      }
   }
   
   public void removePiece(Square square)
   {
      resetMoves();
      
      int squareIndex = square.index;
      
      Piece piece = pieceInSquare[squareIndex];
      
      if(piece != null)
      {
         int indicePiece = piece.index;
         
         //Se quita la pieza de la casilla
         pieceInSquare[squareIndex] = null;
         
         //Se elimina la pieza del bitboard correspondiente a la pieza
         bbPiecesOccupation[indicePiece] ^= bbSquare[squareIndex];
         
         //Se pone a 0 el bitboard de la posicion que ocupaba la pieza
         bbAttackedFromSquare[squareIndex] = 0;
         
         //Se actualizan los bitboards de ocupacion: normal, rotado 90 grados y rotados 45 grados a ambos lados
         bbOccupation ^= bbSquare[squareIndex];
         bbOccupationR90L ^= bbSquare[squareRotated90DLeft[squareIndex]];
         bbOccupationR45R ^= bbSquare[squareRotated45DLeft[squareIndex]];
         bbOccupationR45L ^= bbSquare[inverseSquareRotated45DLeft[squareIndex]];
         
         if(piece.colour == Colour.WHITE)
         {
            bbWhiteOccupation ^= bbSquare[squareIndex];
            bbColourOccupation[Colour.WHITE.index] = bbWhiteOccupation;
         }
         else
         {
            bbBlackOccupation ^= bbSquare[squareIndex];
            bbColourOccupation[Colour.BLACK.index] = bbBlackOccupation;
         }
         
         //Se actualiza la clave zobrist
         zobristKey.xorPiece(piece, square);
      }
      else
      {
         //throw new RuntimeException("ERROR: intentando quitar pieza de casilla sin nada");
      }
   }
   
   public Piece getPieceInSquare(Square casilla)
   {
      //int i = (int)casilla + 1;
      return pieceInSquare[casilla.index];
   }

   public int getNumPieces(Piece pieza)
   {
      return Long.bitCount(bbPiecesOccupation[pieza.index]);
   }

   public boolean hasPiece(Piece pie, Square cas)
   {
      // TODO Auto-generated method stub
      return false;
   }

   public boolean hasPiece(Piece pie, Rank fil)
   {
      // TODO Auto-generated method stub
      return false;
   }
   
   public Square[] getPiecesSquares(Colour colour)
   {
      
      long bbColour = bbColourOccupation[colour.index];
      int numSquares = Long.bitCount(bbColour);
      Square[] squares = new Square[numSquares];
      for(int j = 0; j < numSquares; j++)
      {
         int squareIndex = Long.numberOfTrailingZeros(bbColour);
         
         squares[j] = square[squareIndex];
         
         bbColour ^= bbSquare[squareIndex];
      }
      return squares;
   }
   
   public void setInitialPosition()
   {
      super.setInitialPosition();
      
      this.updateAttackedFromFull();
      
      resetMoves();
   }
   
   @Override
   public Position getCopy()
   {
      BitboardPosition posicion = new BitboardPosition();
      
      //Se clonan los bitboards
      posicion.pieceInSquare = this.pieceInSquare.clone();
      posicion.bbWhiteOccupation = this.bbWhiteOccupation;
      posicion.bbBlackOccupation = this.bbBlackOccupation;
      posicion.bbColourOccupation = this.bbColourOccupation.clone();
      posicion.bbPiecesOccupation = this.bbPiecesOccupation.clone();
      posicion.bbOccupation = this.bbOccupation;
      posicion.bbOccupationR90L = this.bbOccupationR90L;
      posicion.bbOccupationR45L = this.bbOccupationR45L;
      posicion.bbOccupationR45R = this.bbOccupationR45R;
      posicion.bbAttackedFromSquare = this.bbAttackedFromSquare.clone();
      posicion.bbAttackerToSquare = this.bbAttackerToSquare.clone();
      
      //Se clonan las propiedades
      posicion.turn = this.turn;
      posicion.whiteCastlingShort = this.whiteCastlingShort;
      posicion.blackCastlingShort = this.blackCastlingShort;
      posicion.whiteCastlingLong = this.whiteCastlingLong;
      posicion.blackCastlingLong = this.blackCastlingLong;
      posicion.enPassantSquare = this.enPassantSquare;
      posicion.moveIndex = this.moveIndex;
      posicion.fiftyMovesRuleIndex = this.fiftyMovesRuleIndex;
      
      posicion.movesGenerated = this.movesGenerated;
      posicion.movesList = this.movesList;
      
      posicion.setZobristKey(this.getZobristKey().getKey());
      posicion.setPositionsHash(new HashMap<Long, Integer>(this.getPositionsHash()));
      
      return posicion;
   }

   @Override
   public int getMobility(Colour color)
   {
      long bitboardMovilidad = 0;
      long ocupadasTemp = 0;
      
      if(color == Colour.WHITE)
      {
         ocupadasTemp = bbWhiteOccupation;
      }
      else if(color == Colour.BLACK)
      {
         ocupadasTemp = bbBlackOccupation;
      }
      
      int numPieces = Long.bitCount(ocupadasTemp);
      for(int i = 0; i < numPieces; i++)
      {
         int indicePiece = Long.numberOfTrailingZeros(ocupadasTemp);
         
         bitboardMovilidad |= bbAttackedFromSquare[indicePiece];
         
         ocupadasTemp ^= bbSquare[indicePiece];
      }
      
      return Long.bitCount(bitboardMovilidad);
   }
   
   /************* FIN DE MÉTODOS HEREDADOS DE POSICION ABSTRACTA ********************/

}