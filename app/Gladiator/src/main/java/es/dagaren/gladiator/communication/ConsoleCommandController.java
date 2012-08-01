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
package es.dagaren.gladiator.communication;

import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * @author dagaren
 *
 */
public class ConsoleCommandController extends CommandController implements Runnable
{
   private Logger logger = Logger.getLogger(ConsoleCommandController.class);
   
   boolean stopped = true;
   
   Thread t;
   
   @Override
   public void sendCommand(String command) 
   {
      logger.debug("Comando enviado > " + command);
      
      System.out.println(command);
   }
   
   @Override
   public void recieveCommand(String command) 
   {
      logger.debug("Comando recibido < " + command);
      
      for(CommandReceiver receiver : receivers)
      {
         receiver.onCommandReceived(command);
      }
   }

   @Override
   public synchronized void start()
   {
      if(stopped)
      {
         t = new Thread(this);
         t.setDaemon(true); //NECESARIO PARA QUE SE PUEDA INTERRUMPIR EL HILO
                            //QUE LEE DE LA ENTRADA ESTÁNDAR
         t.start();
      } 
   }

   @Override
   public synchronized void stop() 
   {
      stopped = true;
      
      if(t != null)
      {
         t.interrupt();
      }
   }
   
   public void run()
   {
      stopped = false;
      Scanner input = new Scanner(System.in);
      
      while(!stopped)
      {
         String command = input.nextLine();
         
         synchronized(this)
         {  
            recieveCommand(command);
         }
      }
   }

}