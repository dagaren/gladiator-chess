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

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author dagaren
 *
 */
public class ConsoleDebugCommandController extends ConsoleCommandController
{
   @Override
   public void sendCommand(String command) 
   {
      debug(command + " >");
      
      super.sendCommand(command);
   }
   
   @Override
   public void recieveCommand(String command) 
   {
      debug("< " + command);
      
      super.recieveCommand(command);
   }
   
   private void debug(String command)
   {
      try
      {
         //FileWriter fstream = new FileWriter("/tmp/gladiator_commands.txt", true);
         //BufferedWriter out = new BufferedWriter(fstream);
         //out.write(command + "\n");
         //out.close();
         System.err.println(command);
      }
      catch (Exception e)
      {
         System.err.println(command);
      }
      
   }
}
