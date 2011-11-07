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

import java.util.ArrayList;
import java.util.List;

/**
 * @author dagaren
 *
 */
public abstract class CommandController 
{
   protected List<CommandReceiver> receivers = new ArrayList<CommandReceiver>();
   
   
   public void addCommandReceiver(CommandReceiver receiver)
   {
      receivers.add(receiver);
   }
   
   public void removeCommandRecevier(CommandReceiver receiver)
   {
      if(receivers.contains(receiver))
         receivers.remove(receiver);
   }
   
   public abstract void sendCommand(String command);
   
   public abstract void recieveCommand(String command);
   
   public abstract void start();
   
   public abstract void stop();
}
