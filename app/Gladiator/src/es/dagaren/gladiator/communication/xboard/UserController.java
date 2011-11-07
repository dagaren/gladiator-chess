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

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import es.dagaren.gladiator.communication.ProtocolController;
import es.dagaren.gladiator.notation.Notation;
import es.dagaren.gladiator.representation.Movement;

/**
 * @author dagaren
 *
 */
public class UserController extends ProtocolController implements UserToEngine
{
   EngineToUser receiver;
   
   
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
   
   @Override
   public void onCommandReceived(String command)
   {
      Scanner commandScanner = new Scanner(command);
      commandScanner.useDelimiter("\\s+");
      
      if(receiver != null)
      {
         if("offer draw".equals(command))
         {
            receiver.offerDraw();
         }
         else if("resign".equals(command))
         {
            receiver.resign();
         }
         else if(command.startsWith("feature"))
         {
            Map<String, String> features = new HashMap<String, String>();
            
            command = command.substring("feature".length()).trim();
            
            while(command.length() > 0)
            {  
               int separatorIndex = command.indexOf("=");
               String feature = command.substring(0, separatorIndex);

               String value;
               
               command = command.substring(separatorIndex + 1);

               if(command.startsWith("\""))
               {
                 command = command.substring(1);
                 value = command.substring(0, command.indexOf("\""));
                 command = command.substring(value.length() + 1);
               }
               else
               {
                  int endCommandIndex = command.indexOf(" ");
                  if(endCommandIndex == -1)
                    endCommandIndex = command.length();
                  value = command.substring(0, endCommandIndex);
                  command = command.substring(value.length());
               }
               
               
               features.put(feature, value);
               
               command = command.trim();
            }
            
            receiver.feature(features);
            
         }
         else if(command.startsWith("Illegal move:"))
         {
            receiver.illegalMove(command.substring("Illegal move:".length()).trim(), "");
         }
         else if(command.startsWith("Illegal move ("))
         {
            int startIndex = command.indexOf("(");
            int endIndex = command.indexOf(")");
            
            String reason = "";
            if(startIndex != -1 && endIndex != -1)
               reason = command.substring(startIndex + 1, endIndex).trim();
            
            startIndex = command.indexOf(":");
            
            String move = "";
            if(startIndex != -1)
               move = command.substring(startIndex).trim();
            
            receiver.illegalMove(move, reason);
         }
         else if(command.startsWith("Error"))
         {
            int startIndex = command.indexOf("(");
            int endIndex = command.indexOf(")");
            
            String errorType = "";
            if(startIndex != -1 && endIndex != -1)
               errorType = command.substring(startIndex + 1, endIndex).trim();
            
            startIndex = command.indexOf(":");
            
            String message = "";
            if(startIndex != -1)
               message = command.substring(startIndex).trim();
            
            receiver.error(errorType, message);
         }
         else if(command.startsWith("move"))
         {
            String move = command.substring("move".length()).trim();
            
            receiver.move(move);
         }
         else if(command.startsWith("tellopponent"))
         {
            receiver.tellopponent(command.substring("tellopponent".length()).trim());
         }
         else if(command.startsWith("tellothers"))
         {
            receiver.tellothers(command.substring("tellothers".length()).trim());
         }
         else if(command.startsWith("tellall"))
         {
            receiver.tellall(command.substring("tellall".length()).trim());
         }
         else if(command.startsWith("telluser"))
         {
            receiver.telluser(command.substring("telluser".length()).trim());
         }
         else if(command.startsWith("tellusererror"))
         {
            receiver.tellusererror(command.substring("tellusererror".length()).trim());
         }
         else if(command.startsWith("askuser"))
         {
            //TODO Implementar
         }
         else if(command.startsWith("tellics"))
         {
            receiver.tellics(command.substring("tellics".length()).trim());
         }
         else if(command.startsWith("tellicsnoalias"))
         {
            receiver.tellicsnoalias(command.substring("tellicsnoalias".length()).trim());
         }
         else if(command.startsWith("1-0"))
         {
            String result = "1-0";
            
            int startIndex = command.indexOf("{");
            int endIndex = command.indexOf("}");
            
            String comment = "";
            if(startIndex != -1 && endIndex != -1)
               comment = command.substring(startIndex + 1, endIndex);
            
            receiver.result(result, comment);
         }
         else if(command.startsWith("0-1"))
         {
            String result = "0-1";
            
            int startIndex = command.indexOf("{");
            int endIndex = command.indexOf("}");
            
            String comment = "";
            if(startIndex != -1 && endIndex != -1)
               comment = command.substring(startIndex + 1, endIndex);
            
            receiver.result(result, comment);
         }
         else if(command.startsWith("1/2-1/2"))
         {
            String result = "1/2-1/2";
            
            int startIndex = command.indexOf("{");
            int endIndex = command.indexOf("}");
            
            String comment = "";
            if(startIndex != -1 && endIndex != -1)
               comment = command.substring(startIndex + 1, endIndex);
            
            receiver.result(result, comment);
         }
         
      }
   }
   
   @Override
   public synchronized void protover(String version)
   {
      commandController.sendCommand("protover " + version);
   }
   
   @Override
   public synchronized void rejected(String feature)
   {
      commandController.sendCommand("rejected");
   }
   
   @Override
   public synchronized void accepted(String feature)
   {
      commandController.sendCommand("accepted");
   }
   
   @Override
   public synchronized void New()
   {
      commandController.sendCommand("new");
   }

   @Override
   public synchronized void analyze()
   {
      commandController.sendCommand("analyze");
   }

   @Override
   public synchronized void bk()
   {
      commandController.sendCommand("bk");
   }

   @Override
   public synchronized void black()
   {
      commandController.sendCommand("black");
   }

   @Override
   public synchronized void computer()
   {
      commandController.sendCommand("computer");
   }

   @Override
   public synchronized void draw()
   {
      commandController.sendCommand("draw");
   }

   @Override
   public synchronized void easy()
   {
      commandController.sendCommand("easy");
   }

   @Override
   public synchronized void edit()
   {
      commandController.sendCommand("edit");
   }

   @Override
   public synchronized void force()
   {
      commandController.sendCommand("force");
   }

   @Override
   public synchronized void go()
   {
      commandController.sendCommand("go");
   }
   
   @Override
   public synchronized void playother()
   {
      commandController.sendCommand("playother");
   }

   @Override
   public synchronized void hard()
   {
      commandController.sendCommand("hard");
   }

   @Override
   public synchronized void hint()
   {
      commandController.sendCommand("hint");
   }

   @Override
   public synchronized void holding(String white, String black, String colour, String piece)
   {
      commandController.sendCommand("holding " + white + " " + black);
   }

   @Override
   public synchronized void level(String numMoves, String time, String increment)
   {
      commandController.sendCommand("level " + numMoves + " " + time + " " + increment);
   }

   @Override
   public synchronized void usermove(Movement move)
   {
      commandController.sendCommand("usermove " + Notation.toString(move));
   }

   @Override
   public synchronized void moveNow()
   {
      commandController.sendCommand("?");
   }

   @Override
   public synchronized void name(String name)
   {
      commandController.sendCommand("name " + name);
   }

   @Override
   public synchronized void nopost()
   {
      commandController.sendCommand("nopost");
   }

   @Override
   public synchronized void otim(String n)
   {
      commandController.sendCommand("otim " + n);
   }

   @Override
   public synchronized void partner(String player)
   {
      commandController.sendCommand("partner " + player);
   }

   @Override
   public synchronized void partner()
   {
      commandController.sendCommand("partner");
   }

   @Override
   public synchronized void post() 
   {
      commandController.sendCommand("post");
   }

   @Override
   public synchronized void ptell(String text)
   {
      commandController.sendCommand("ptell " + text);
   }

   @Override
   public synchronized void quit()
   {
      commandController.sendCommand("quit");
   }

   @Override
   public synchronized void random()
   {
      commandController.sendCommand("random");
   }

   @Override
   public synchronized void rating()
   {
      commandController.sendCommand("rating");
   }
   
   @Override
   public synchronized void ics(String hostname)
   {
      commandController.sendCommand("hostname " + hostname);
   }
   
   @Override
   public synchronized void pause()
   {
      commandController.sendCommand("pause");
   }
   
   @Override
   public synchronized void resume()
   {
      commandController.sendCommand("resume");
   }
   
   @Override
   public synchronized void remove()
   {
      commandController.sendCommand("remove");
   }

   @Override
   public synchronized void result(String result, String comment)
   {
      String command = "result " + result;
      if(!comment.trim().isEmpty())
         command += " {" + comment + "}";
      commandController.sendCommand(command);
   }
   
   @Override
   public synchronized void setboard(String fen)
   {
      commandController.sendCommand("setboard " + fen);
   }

   @Override
   public synchronized void sd(String deep)
   {
      commandController.sendCommand("sd " + deep);
   }

   @Override
   public synchronized void st(String time)
   {
      commandController.sendCommand("st " + time);
   }

   @Override
   public synchronized void time(String n)
   {
      commandController.sendCommand("time " + n);
   }

   @Override
   public synchronized void undo()
   {
      commandController.sendCommand("undo");
   }

   @Override
   public synchronized void variant(String name)
   {
      commandController.sendCommand("variant " + name);
   }

   @Override
   public synchronized void white()
   {
      commandController.sendCommand("white");
   }

   @Override
   public synchronized void xboard()
   {
      commandController.sendCommand("xboard");
   }

   @Override
   public synchronized void ping(String n)
   {
      commandController.sendCommand("ping " + n);
   }

}
