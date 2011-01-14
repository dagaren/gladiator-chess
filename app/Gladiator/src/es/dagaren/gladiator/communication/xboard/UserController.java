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
package es.dagaren.gladiator.communication.xboard;

import java.util.Scanner;
import es.dagaren.gladiator.communication.ProtocolController;
import es.dagaren.gladiator.representation.Movement;

/**
 * @author dagaren
 *
 */
public class UserController extends ProtocolController implements UserToEngine
{
   EngineToUser receiver;
   
   @Override
   public void onCommandReceived(String command)
   {
      Scanner commandScanner = new Scanner(command);
      commandScanner.useDelimiter("\\s+");
      
      if(receiver != null)
      {
      }
   }
   
   
   /**
    * @param receiver the receiver to set
    */
   void setReceiver(EngineToUser receiver)
   {
      this.receiver = receiver;
   }

   /**
    * @return the receiver
    */
   EngineToUser getReceiver()
   {
      return receiver;
   }
   
   
   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#New()
    */
   @Override
   public void New()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#analyze()
    */
   @Override
   public void analyze()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#bk()
    */
   @Override
   public void bk()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#black()
    */
   @Override
   public void black()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#computer()
    */
   @Override
   public void computer()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#draw()
    */
   @Override
   public void draw()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#easy()
    */
   @Override
   public void easy()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#edit()
    */
   @Override
   public void edit()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#force()
    */
   @Override
   public void force()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#go()
    */
   @Override
   public void go()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#hard()
    */
   @Override
   public void hard()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#hint()
    */
   @Override
   public void hint()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#holding(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
    */
   @Override
   public void holding(String white, String black, String colour, String piece)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#level(java.lang.String, java.lang.String, java.lang.String)
    */
   @Override
   public void level(String numMoves, String time, String increment)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#move(es.dagaren.gladiator.representation.Movement)
    */
   @Override
   public void move(Movement move)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#moveNow()
    */
   @Override
   public void moveNow()
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#name(java.lang.String)
    */
   @Override
   public void name(String name)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#nopost()
    */
   @Override
   public void nopost()
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#otim(java.lang.String)
    */
   @Override
   public void otim(String n)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#partner(java.lang.String)
    */
   @Override
   public void partner(String player)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#partner()
    */
   @Override
   public void partner()
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#post()
    */
   @Override
   public void post() {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#ptell(java.lang.String)
    */
   @Override
   public void ptell(String text)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#quit()
    */
   @Override
   public void quit()
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#random()
    */
   @Override
   public void random()
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#rating()
    */
   @Override
   public void rating()
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#remove()
    */
   @Override
   public void remove()
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#result(java.lang.String, java.lang.String)
    */
   @Override
   public void result(String result, String comment)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#sd(java.lang.String)
    */
   @Override
   public void sd(String deep)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#st(java.lang.String)
    */
   @Override
   public void st(String time)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#time(java.lang.String)
    */
   @Override
   public void time(String n)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#undo()
    */
   @Override
   public void undo()
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#variant(java.lang.String)
    */
   @Override
   public void variant(String name)
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#white()
    */
   @Override
   public void white()
   {
      // TODO Auto-generated method stub
   }

   /* (non-Javadoc)
    * @see es.dagaren.gladiator.protocols.xboard.UserToEngine#xboard()
    */
   @Override
   public void xboard()
   {
      // TODO Auto-generated method stub
   }

}
