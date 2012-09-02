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
import java.util.List;
import java.util.Map;

/**
 * @author dagaren
 *
 */
public abstract class AbstractPosition implements Position
{
   /**
    * Establece el bando al que le toca jugar
    */
   protected Colour turn = Colour.WHITE;
   
   /**
    * Establece la posibilidad de enroque
    */
   protected boolean whiteCastlingShort = false;
   protected boolean blackCastlingShort = false;
   protected boolean whiteCastlingLong = false;
   protected boolean blackCastlingLong = false;
   
   /**
    * Establece la casilla de peón al paso
    */
   protected Square enPassantSquare = null;
   
   /**
    * Establece el número de jugada
    */
   protected int moveIndex;
   
   /**
    * Establece el número de movimientos para la regla de los
    * 50 movimientos
    */
   protected int fiftyMovesRuleIndex;
   
   /**
    * Almacena la clave zobrist de la posición
    */
   protected ZobristKey zobristKey = new ZobristKey();
   
   /**
    * Hash que almacena 
    */
   protected Map<Long, Integer> gamePositionsHash = new HashMap<Long, Integer>();
   
   public void setInitialPosition()
   {
      //Se colocan los peones blancos en la segunda fila
      putGenericPiece(GenericPiece.PAWN, Colour.WHITE, Square.a2);
      putGenericPiece(GenericPiece.PAWN, Colour.WHITE, Square.b2);
      putGenericPiece(GenericPiece.PAWN, Colour.WHITE, Square.c2);
      putGenericPiece(GenericPiece.PAWN, Colour.WHITE, Square.d2);
      putGenericPiece(GenericPiece.PAWN, Colour.WHITE, Square.e2);
      putGenericPiece(GenericPiece.PAWN, Colour.WHITE, Square.f2);
      putGenericPiece(GenericPiece.PAWN, Colour.WHITE, Square.g2);
      putGenericPiece(GenericPiece.PAWN, Colour.WHITE, Square.h2);
      //Se colocan los peones negros en la séptima fila
      putGenericPiece(GenericPiece.PAWN, Colour.BLACK, Square.a7);
      putGenericPiece(GenericPiece.PAWN, Colour.BLACK, Square.b7);
      putGenericPiece(GenericPiece.PAWN, Colour.BLACK, Square.c7);
      putGenericPiece(GenericPiece.PAWN, Colour.BLACK, Square.d7);
      putGenericPiece(GenericPiece.PAWN, Colour.BLACK, Square.e7);
      putGenericPiece(GenericPiece.PAWN, Colour.BLACK, Square.f7);
      putGenericPiece(GenericPiece.PAWN, Colour.BLACK, Square.g7);
      putGenericPiece(GenericPiece.PAWN, Colour.BLACK, Square.h7);
      //Se colocan las torres blancas en a1 y h1
      putGenericPiece(GenericPiece.ROOK,Colour.WHITE,Square.a1);
      putGenericPiece(GenericPiece.ROOK,Colour.WHITE,Square.h1);
      //Se colocan las torres negras en a8 y h9
      putGenericPiece(GenericPiece.ROOK,Colour.BLACK,Square.a8);
      putGenericPiece(GenericPiece.ROOK,Colour.BLACK,Square.h8);
      //Se colocan los caballos blancos en b1 y g1
      putGenericPiece(GenericPiece.KNIGHT,Colour.WHITE,Square.b1);
      putGenericPiece(GenericPiece.KNIGHT,Colour.WHITE,Square.g1);
      //Se colocan los caballos negros en b8 y g8
      putGenericPiece(GenericPiece.KNIGHT,Colour.BLACK,Square.b8);
      putGenericPiece(GenericPiece.KNIGHT,Colour.BLACK,Square.g8);
      //Se colocan los alfiles blancos en c1 y f1
      putGenericPiece(GenericPiece.BISHOP,Colour.WHITE,Square.c1);
      putGenericPiece(GenericPiece.BISHOP,Colour.WHITE,Square.f1);
      //Se colocan los alfiles negros en c8 y f8
      putGenericPiece(GenericPiece.BISHOP,Colour.BLACK,Square.c8);
      putGenericPiece(GenericPiece.BISHOP,Colour.BLACK,Square.f8);
      //Se coloca la dama blanca en d1
      putGenericPiece(GenericPiece.QUEEN,Colour.WHITE,Square.d1);
      //Se coloca la dama negra en d8
      putGenericPiece(GenericPiece.QUEEN,Colour.BLACK,Square.d8);
      //Se coloca el rey blanco en e1
      putGenericPiece(GenericPiece.KING,Colour.WHITE,Square.e1);
      //Se coloca el rey negro en e8
      putGenericPiece(GenericPiece.KING,Colour.BLACK,Square.e8);
      
      //Se permiten los enroques para ambos jugadores
      setCastlingShort(Colour.WHITE,true);
      setCastlingShort(Colour.BLACK,true);
      setCastlingLong(Colour.WHITE,true);
      setCastlingLong(Colour.BLACK,true);
      
      //Se da el turno a las blancas
      setTurn(Colour.WHITE);
      
      gamePositionsHash.clear();
      gamePositionsHash.put(zobristKey.getKey(), 1);
   }
   
   public boolean isValidMove(Movement mov)
   {
      if(isValidPosition())
      {
         List<Movement> possibleMoves = this.getMovements();
         
         if(possibleMoves.contains(mov))
            return true;
      }
      
      return false;
   }
   
   public boolean isValidPosition()
   {
      //Si el jugador que no tiene el turno se encuentra
      //en jaque la posición no es valida
      if(getTurn() == Colour.WHITE)
      {
         if(isInCheck(Colour.BLACK))
            return false;
      }
      else
      {
         if(isInCheck(Colour.WHITE))
            return false;
      }
      
      //No puede encontrarse ningún peon en la primera ni
      //en la octava fila
      if(hasGenericPiece(GenericPiece.PAWN, Colour.WHITE,Rank._1))
         return false;
      if(hasGenericPiece(GenericPiece.PAWN,Colour.BLACK,Rank._1))
         return false;
      if(hasGenericPiece(GenericPiece.PAWN,Colour.WHITE,Rank._8))
         return false;
      if(hasGenericPiece(GenericPiece.PAWN,Colour.BLACK,Rank._8))
         return false;
      
      //Tiene que haber un rey por bando
      //System.out.println("=>" + getNumeroPiezas(GenericPiece.REY, Colour.WHITE));
      //if(getNumeroPiezas(GenericPiece.REY, Colour.WHITE) != 1)
      // return false;
      //if(getNumeroPiezas(GenericPiece.REY,Colour.BLACK) != 1)
       //      return false;
      
      
      //TODO hay que controlar el nuemro de piezas posibles;
      
      //TODO hay que controlar la casilla al paso
      
      //Si no ocurre ninguna de las condiciones anteriores la posición
      //es válida
      return true;
   }
   
   public String toFen()
   {
      String fen = "";
      
      for(int i = 7; i >=0; i--)
      {
         String rank = "";
         
         int emptySquares = 0;
         for(int j=0; j<8; j++)
         {   
            Piece piece = this.getPieceInSquare(Square.fromRankAndFile(i, j));
            if(piece == null)
               emptySquares++;
            else
            {
               if(emptySquares > 0)
               {
                  rank += Integer.toString(emptySquares);
               }
               rank += piece.toFenString();
               emptySquares = 0;
            }
         }
         if(emptySquares > 0)
         {
            rank += Integer.toString(emptySquares);
         }
         
         fen += rank;
         if(i>0)
            fen+= "/";
      }
      
      String turn = this.getTurn() == Colour.WHITE ? "w" : "b"; 
      
      fen += " " + turn;
      
      String castlingRights = "";
      if(this.getCastlingShort(Colour.WHITE))
         castlingRights += "K";
      if(this.getCastlingLong(Colour.WHITE))
         castlingRights += "Q";
      if(this.getCastlingShort(Colour.BLACK))
         castlingRights += "k";
      if(this.getCastlingLong(Colour.BLACK))
         castlingRights += "q";
      
      fen += castlingRights == "" ? " -" : " " + castlingRights;
   
      String enPassant = "";
      Square enPassantSquare = this.getEnPassantSquare();
      if(enPassantSquare == null)
      {
         enPassant = "-";
      }
      else
      {
         enPassant = enPassantSquare.toString();
      }
      
      fen += " " + enPassant;
      
      String halfmoveClock = Integer.toString(this.getFiftyMovesRuleIndex());
      
      fen += " " + halfmoveClock;

      String fullmoveNumber = Integer.toString(this.getMoveIndex());
      
      fen += " " + fullmoveNumber;
      
      return fen;
   }
   
   public boolean loadFen(String fenPosition)
   {
      String[] parts = fenPosition.split("\\s+");
      
      if(parts.length != 6)
         throw new RuntimeException("Posición fen incorrecta: "+ fenPosition);
      
      String boardString = parts[0];
      String turnString = parts[1];
      String castlingString = parts[2];
      String enPassantString = parts[3];
      String fiftyMovesString = parts[4];
      String moveString = parts[5];
      
      String[] ranksStrings = boardString.split("/");
      
      //TODO limpiar el tablero lo primero
      clean();
      
      if(ranksStrings.length != 8)
         throw new RuntimeException("Posición fen incorrecta: "+ fenPosition);
      
      for(int i=0;i<8;i++)
      {
         String rankString = ranksStrings[i];
         
         int file = 0;
         
         for(int j=0;j<rankString.length();j++)
         {
            char character = rankString.charAt(j);
            
            if(file > 8)
               throw new RuntimeException("Posición fen incorrecta: " + fenPosition);
            
            switch(character)
            {
                case 'p':
                  putGenericPiece(GenericPiece.PAWN,Colour.BLACK,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'r':
                    putGenericPiece(GenericPiece.ROOK,Colour.BLACK,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'n':
                    putGenericPiece(GenericPiece.KNIGHT,Colour.BLACK,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'b':
                    putGenericPiece(GenericPiece.BISHOP,Colour.BLACK,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'q':
                    putGenericPiece(GenericPiece.QUEEN,Colour.BLACK,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'k':
                    putGenericPiece(GenericPiece.KING,Colour.BLACK,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'P':
                    putGenericPiece(GenericPiece.PAWN,Colour.WHITE,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'R':
                    putGenericPiece(GenericPiece.ROOK,Colour.WHITE,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'N':
                    putGenericPiece(GenericPiece.KNIGHT,Colour.WHITE,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'B':
                    putGenericPiece(GenericPiece.BISHOP,Colour.WHITE,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'Q':
                    putGenericPiece(GenericPiece.QUEEN,Colour.WHITE,Square.fromRankAndFile(7-i, file++));
                    break;
                case 'K':
                    putGenericPiece(GenericPiece.KING,Colour.WHITE,Square.fromRankAndFile(7-i, file++));
                    break;
                default:
                    try
                    {
                        int number = Integer.parseInt(rankString.substring(j,j+1));

                        if(number < 1 || number > 8)
                           throw new RuntimeException("Posición fen incorrecta: " + fenPosition);
                        else
                        {
                            file += number;
                        }
                    }
                    catch(Exception ex)
                    {
                       throw new RuntimeException("Posición fen incorrecta: " + fenPosition);
                    }
            } //switch
         } //for j
      } //for i
   
      //Se recoge el turno de juego
      if(turnString.equals("w"))
         setTurn(Colour.WHITE);
      else if(turnString.equals("b"))
         setTurn(Colour.BLACK);
      else
         throw new RuntimeException("Posición fen incorrecta: " + fenPosition);
      
      //Se recogen las posibilidades del enroque
      
      //Enroque corto de las negras
      if(castlingString.indexOf("k") != -1)
         setCastlingShort(Colour.BLACK,true);
      else
         setCastlingShort(Colour.BLACK,false);
      
      //Enroque corto de las blancas
      if(castlingString.indexOf("K") != -1)
         setCastlingShort(Colour.WHITE,true);
      else
         setCastlingShort(Colour.WHITE,false);
      
      //Enroque largo de las negras
      if(castlingString.indexOf("q") != -1)
         setCastlingLong(Colour.BLACK,true);
      else
         setCastlingLong(Colour.BLACK,false);
      
      //Enroque largo de las blancas
      if(castlingString.indexOf("Q") != -1)
         setCastlingLong(Colour.WHITE,true);
      else
         setCastlingLong(Colour.WHITE,false);
      
      
      //Se recoge la casilla al paso
      if(enPassantString.equals("-"))
      {
         setEnPassantSquare(null);
      }
      else
      {
         if (enPassantString.length() != 2)
            throw new RuntimeException("Posición fen incorrecta: "
                  + fenPosition);

         char rank = enPassantString.charAt(0);
         if (rank != 'a' && rank != 'b' && rank != 'c' && rank != 'd'
               && rank != 'e' && rank != 'f' && rank != 'g' && rank != 'h')
            throw new RuntimeException("Posición fen incorrecta: "
                  + fenPosition);

         char file = enPassantString.charAt(1);
         if (file != '3' && file != '6')
            throw new RuntimeException("Posición fen incorrecta: "
                  + fenPosition);

         
         // TODO construir la casilla y pasarla
         setEnPassantSquare(Square.valueOf(enPassantString));
      }
      
      //Se recoge el número de movimientos para la regla de los 50 movimientos
      try
      {
         int fiftyMovesRuleIndex = Integer.parseInt(fiftyMovesString);
         setFiftyMovesRuleIndex(fiftyMovesRuleIndex);
      }
      catch(Exception ex)
      {
         throw new RuntimeException("Posición fen incorrecta: " + fenPosition);
      }
      
      //Se recoge el número de movimiento
      try
      {
         int moveIndex = Integer.parseInt(moveString);
         setMoveIndex(moveIndex);
      }
      catch(Exception ex)
      {
         throw new RuntimeException("Posición fen incorrecta: " + fenPosition);
      }
      
      gamePositionsHash.clear();
      gamePositionsHash.put(zobristKey.getKey(), 1);
      
      return isValidPosition();
   }
   
   public String toString(){
      String[][] board = new String[8][8];
      
      for(int i = 0; i < 8; i++)
      {
         for(int j = 0; j < 8; j++)
         {
            Piece piece = getPieceInSquare(Square.fromRankAndFile(i,j));
            
            if(piece == Piece.WHITE_PAWN)
            {
               board[i][j] = " \u2659 ";
            }
            else if(piece == Piece.BLACK_PAWN)
            {
               board[i][j] = " \u265F ";
            }
            else if(piece == Piece.WHITE_ROOK)
            {
               board[i][j] = " \u2656 ";
            }
            else if(piece == Piece.BLACK_ROOK)
            {
               board[i][j] = " \u265C ";
            }
            else if(piece == Piece.WHITE_KNIGHT)
            {
               board[i][j] = " \u2658 ";
            }
            else if(piece == Piece.BLACK_KNIGHT)
            {
               board[i][j] = " \u265E ";
            }
            else if(piece == Piece.WHITE_BISHOP)
            {
               board[i][j] = " \u2657 ";
            }
            else if(piece == Piece.BLACK_BISHOP)
            {
               board[i][j] = " \u265D ";
            }
            else if(piece == Piece.WHITE_QUEEN)
            {
               board[i][j] = " \u2655 ";
            }
            else if(piece == Piece.BLACK_QUEEN)
            {
               board[i][j] = " \u265B ";
            }
            else if(piece == Piece.WHITE_KING)
            {
               board[i][j] = " \u2654 ";
            }
            else if(piece == Piece.BLACK_KING)
            {
               board[i][j] = " \u265A ";
            }
            else
            {
               if((i+j)%2 == 0)
               {
                  board[i][j] = " - ";
                  //tablero[i][j] = " \u25A0 ";
                  //tablero[i][j] = " \u25FE ";
               }
               else{
                  board[i][j] = " - ";
                  //tablero[i][j] = " \u25E6 ";
                  //tablero[i][j] = " \u25A1 ";
                  //tablero[i][j] = " \u25FD ";
               }
               
               //tablero[i][j] = " - ";
            }
         }
      }  
      StringBuilder sb = new StringBuilder("");
      sb.append(" --------------------------\n");
      for(int i = 7; i >= 0; i--)
      {
         sb.append(i+1);
         sb.append("|");
         
         for(int j = 0; j < 8; j++){
            sb.append(board[i][j]);
         }
         
         sb.append("|\n");
      }
      sb.append(" --------------------------\n");
      sb.append("   a  b  c  d  e  f  g  h\n"); 
      
      sb.append("FEN: " + this.toFen() + "\n");
      
      return sb.toString();
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#setTurno(es.dagaren.chesi.Colour)
    */
   public void setTurn(Colour colour)
   {
      if(colour != turn)
      {
         //Se actualiza la clave zobrist
         zobristKey.xorTurn(Colour.BLACK);
      }
      this.turn = colour;
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#getTurno()
    */
   public Colour getTurn()
   {
      return turn;
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#setEnroqueCorto(es.dagaren.chesi.Colour, boolean)
    */
   public void setCastlingShort(Colour colour, boolean enabled)
   {
      if(colour == Colour.WHITE)
      {
         if(whiteCastlingShort != enabled)
         {
            //Se actualiza la clave zobrist
            zobristKey.xorCastlingShort(colour);
         }
         whiteCastlingShort = enabled;
      }
      else
      {
         if(blackCastlingShort != enabled)
         {
            //Se actualiza la clave zobrist
            zobristKey.xorCastlingShort(colour);
         }
         blackCastlingShort = enabled;
      }
   }

   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#setEnroqueLargo(es.dagaren.chesi.Colour, boolean)
    */
   public void setCastlingLong(Colour colour, boolean enabled)
   {
      if(colour == Colour.WHITE)
      {
         if(whiteCastlingLong != enabled)
         {
            //Se actualiza la clave zobrist
            zobristKey.xorCastlingLong(colour);
         }
         whiteCastlingLong = enabled;
      }
      else
      {
         if(blackCastlingLong != enabled)
         {
            //Se actualiza la clave zobrist
            zobristKey.xorCastlingLong(colour);
         }
         blackCastlingLong = enabled;
      }
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#getEnroqueCorto(es.dagaren.chesi.Colour)
    */
   public boolean getCastlingShort(Colour colour)
   {
      if(colour == Colour.WHITE)
      {
         return whiteCastlingShort;
      }
      else
      {
         return blackCastlingShort;
      }
   }

   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#getEnroqueLargo(es.dagaren.chesi.Colour)
    */
   public boolean getCastlingLong(Colour colour)
   {
      if(colour == Colour.WHITE)
      {
         return whiteCastlingLong;
      }
      else
      {
         return blackCastlingLong;
      }
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#setSquareAlPaso(es.dagaren.chesi.Square)
    */
   public void setEnPassantSquare(Square square)
   {
      if(square != this.enPassantSquare)
      {
         //Se actualiza la clave zobrist
         zobristKey.xorEnPassant(enPassantSquare);
         zobristKey.xorEnPassant(square);
      }
      this.enPassantSquare = square;
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#getSquareAlPaso()
    */
   public Square getEnPassantSquare()
   {
      return enPassantSquare;
   }
   
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#setNumMovement(int)
    */
   public void setMoveIndex(int moveIndex)
   {
      this.moveIndex = moveIndex;
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#getNumMovement()
    */
   public int getMoveIndex()
   {
      return moveIndex;
   }
   
   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#getNumJugada50()
    */
   public int getFiftyMovesRuleIndex()
   {
      return fiftyMovesRuleIndex;
   }

   /* (non-Javadoc)
    * @see es.dagaren.chesi.Posicion#setNumJugada50(int)
    */
   public void setFiftyMovesRuleIndex(int fiftyMovesRuleIndex)
   {
      this.fiftyMovesRuleIndex = fiftyMovesRuleIndex;
   }
   
   
   /**
    * 
    */
   public boolean isCheckmate()
   {
      Colour turn = getTurn();
      
      //Para ver si es jaque mate primero se comprueba 
      //si es jaque. Si no lo es se devuelve false
      //inmediatamente
      if(isInCheck(turn))
      {
         List<Movement> movesList = getMovements();
      
         for(Movement mov : movesList)
         {
            //Se realiza el movimiento
            doMove(mov);
            //Si tras realizar un movimiento no se encuentra en jaque
            //quiere decir que al menos hay una jugada legal.
            //Se deshace el movimiento y se devuelve false;
            if(!isInCheck(turn))
            {
               undoMove(mov);
               
               return false;
            }
            
            undoMove(mov);
         }
         
         //Si tras probar todos los movimientos no se ha retornado
         //quiere decir que no a habido ningún movimiento legal
         //por lo que es jaque mate y se devuelve true
         return true;
      }
      else
      {
         return false;
      }
   }
   
   /**
    * 
    */
   
   public boolean isStalemate()
   {
      Colour turn = getTurn();
      
      //Para comprobar que es posición de ahogado primero se comprueba si
      //está en jaque el jugador con el turno. Si lo está no puede ser
      //posición de ahogado
      if(!isInCheck(turn))
      {
         List<Movement> movesList = getMovements();
         for(Movement mov : movesList)
         {
            //Se realiza el movimiento
            doMove(mov);
            
            //Si tras realizar un movimiento no se encuentra en jaque
            //quiere decir que al menos hay una jugada legal.
            //Se deshace el movimiento y se devuelve false;
            if(!isInCheck(turn))
            {
               undoMove(mov);
               
               return false;
            }
            
            undoMove(mov);
         }
         
         //Si tras probar todos los movimientos no se ha retornado
         //quiere decir que no a habido ningún movimiento legal
         //por lo que es ahogado y se devuelve true
         return true;
      }
      else
      {
         return false;
      }
   }
   
   public ZobristKey getZobristKey()
   {
      return this.zobristKey;
   }
   
   protected void setZobristKey(long key)
   {
      this.zobristKey.setKey(key);
   }
   
   public Map<Long,Integer> getPositionsHash()
   {
      return this.gamePositionsHash;
   }
   
   public void setPositionsHash(Map<Long,Integer> gamePositionsHash)
   {
      this.gamePositionsHash = gamePositionsHash;
   }
   
   /** 
      public String toString(){
      String[][] tablero = new String[8][8];
      
      for(int i=0;i<8;i++){
         for(int j=0;j<8;j++){
            Pieza pieza = piezaEn(Square.fromRankAndFile(i,j));
            
            if(pieza == Pieza.PAWN_WHITE){
               tablero[i][j] = " P ";
            }else if(pieza == Pieza.PAWN_BLACK){
               tablero[i][j] = " p ";
            }else if(pieza == Pieza.ROOK_BLANCA){
               tablero[i][j] = " T ";
            }else if(pieza == Pieza.ROOK_NEGRA){
               tablero[i][j] = " t ";
            }else if(pieza == Pieza.KNIGHT_WHITE){
               tablero[i][j] = " C ";
            }else if(pieza == Pieza.KNIGHT_BLACK){
               tablero[i][j] = " c ";
            }else if(pieza == Pieza.BISHOP_WHITE){
               tablero[i][j] = " A ";
            }else if(pieza == Pieza.BISHOP_BLACK){
               tablero[i][j] = " a ";
            }else if(pieza == Pieza.QUEEN_BLANCA){
               tablero[i][j] = " D ";
            }else if(pieza == Pieza.QUEEN_NEGRA){
               tablero[i][j] = " d ";
            }else if(pieza == Pieza.KING_WHITE){
               //tablero[i][j] = " R ";
               tablero[i][j] = "\u2654";
            }else if(pieza == Pieza.KING_BLACK){
               tablero[i][j] = " r ";
            }else{
               tablero[i][j] = " - ";
            }
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
    */
}