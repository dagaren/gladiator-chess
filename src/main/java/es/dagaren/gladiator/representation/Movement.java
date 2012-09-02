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
public class Movement
{
   protected int value = 0;
   
   private Square sourceSquare;
   private Square destinationSquare;
   
   private Piece sourcePiece;
   private Piece destinationPiece;
   
   private Piece promotionPiece;
   
   private boolean inPassant;
   
   public Movement()
   {
      
   }
   
   public Movement(Square sourceSquare, Square destinationSquare)
   {
      this.sourceSquare = sourceSquare;
      this.destinationSquare = destinationSquare;
   }
   
   public Movement(Square sourceSquare, Square destinationSquare, Piece sourcePiece)
   {
      this.sourceSquare = sourceSquare;
      this.destinationSquare = destinationSquare;
      this.sourcePiece = sourcePiece;
   }
   
   public Movement(Square sourceSquare, Square destinationSquare, Piece sourcePiece, Piece destinationPiece)
   {
      this.sourceSquare = sourceSquare;
      this.destinationSquare = destinationSquare;
      this.sourcePiece = sourcePiece;
      this.destinationPiece = destinationPiece;
   }
   
   public Movement(Square sourceSquare, Square destinationSquare, Piece sourcePiece, Piece destinationPiece, Piece promotionPiece)
   {
      this.sourceSquare = sourceSquare;
      this.destinationSquare = destinationSquare;
      this.sourcePiece = sourcePiece;
      this.destinationPiece = destinationPiece;
      this.promotionPiece = promotionPiece;
   }
   
   
   public Movement(String movString)
   {
      int sourceFile = movString.charAt(0) - 'a';
      int sourceRank = movString.charAt(1) - '1';
      int destinationFile = movString.charAt(2) - 'a';
      int destinationRank = movString.charAt(3) - '1';
      
      this.setSource(Square.fromRankAndFile(sourceRank,sourceFile));
      this.setDestination(Square.fromRankAndFile(destinationRank, destinationFile));
   }
   
   /**
    * @return the alPaso
    */
   public boolean isInPassant()
   {
      return inPassant;
   }

   /**
    * @param inPassant the alPaso to set
    */
   public void setEnPassant(boolean inPassant)
   {
      this.inPassant = inPassant;
   }

   /**
    * @return the destino
    */
   public Square getDestination()
   {
      return destinationSquare;
   }

   /**
    * @param destination the destino to set
    */
   public void setDestination(Square destination)
   {
      this.destinationSquare = destination;
   }

   /**
    * @return the origen
    */
   public Square getSource()
   {
      return sourceSquare;
   }

   /**
    * @param source the origen to set
    */
   public void setSource(Square source)
   {
      this.sourceSquare = source;
   }

   /**
    * @return the piezaCoronacion
    */
   public Piece getPromotionPiece()
   {
      return promotionPiece;
   }

   /**
    * @param promotionPiece the piezaCoronacion to set
    */
   public void setPromotionPiece(Piece promotionPiece)
   {
      this.promotionPiece = promotionPiece;
   }

   /**
    * @return the piezaDestino
    */
   public Piece getDestinationPiece()
   {
      return destinationPiece;
   }

   /**
    * @param destinationPiece the piezaDestino to set
    */
   public void setDestinationPiece(Piece destinationPiece)
   {
      this.destinationPiece = destinationPiece;
   }

   /**
    * @return the piezaOrigen
    */
   public Piece getSourcePiece() 
   {
      return sourcePiece;
   }

   /**
    * @param sourcePiece the piezaOrigen to set
    */
   public void setSourcePiece(Piece sourcePiece)
   {
      this.sourcePiece = sourcePiece;
   }
   
   /**
    * @return the value
    */
   public int getValue()
   {
      return value;
   }

   /**
    * @param value the move value
    */
   public void setValue(int value)
   {
      this.value = value;
   }
   
   public boolean equals(Object m)
   {
      if(m == null) return false;
      
      Movement mov = (Movement)m;
      
      if(this.sourceSquare != mov.sourceSquare)
         return false;
      if(this.destinationSquare != mov.destinationSquare)
         return false;
     
      if(this.getPromotionGenericPiece() != mov.getPromotionGenericPiece())
      {
         return false;
      }
      
      return true;
   }

   /**
    * @return the fichaCoronacion
    */
   public GenericPiece getPromotionGenericPiece()
   {
      return this.promotionPiece != null ? this.promotionPiece.genericPiece : null;
   }
   
   /**
    * @param fichaCoronacion the fichaCoronacion to set
    */
   public void setFichaCoronacion(GenericPiece fichaCoronacion)
   {
      this.promotionPiece = Piece.get(fichaCoronacion, Colour.WHITE);
   }
   
   public static Movement getMovementEnroqueLargo(Colour turno)
   {
      Movement mov = new Movement();
      
      if(turno == Colour.WHITE)
      {
         mov.setSourcePiece(Piece.WHITE_KING);
         mov.setSource(Square.e1);
         mov.setDestination(Square.c1);
      }
      else
      {
         mov.setSourcePiece(Piece.BLACK_KING);
         mov.setSource(Square.e8);
         mov.setDestination(Square.c8);
      }
      
      return mov;
   }
   
   public static Movement getMovementEnroqueCorto(Colour turno)
   {
      Movement mov = new Movement();

      if(turno == Colour.WHITE)
      {
         mov.setSourcePiece(Piece.WHITE_KING);
         mov.setSource(Square.e1);
         mov.setDestination(Square.g1);
      }
      else
      {
         mov.setSourcePiece(Piece.BLACK_KING);
         mov.setSource(Square.e8);
         mov.setDestination(Square.g8);
      }
      
      return mov;
   }
}