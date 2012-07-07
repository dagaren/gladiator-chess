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

import java.util.Map;

/**
 * @author dagaren
 *
 */
public interface EngineToUser
{
   
   /**
    * 
    * @param features
    */
   public void feature(Map<String, String> features);
   
   /**
    * 
    * @param move
    * @param reason
    */
   public void illegalMove(String move, String reason);
   
   /**
    * 
    * @param errorType
    * @param command
    */
   public void error(String errorType, String command);
   
   /**
    * 
    * @param move
    */
   public void move(String move);
   
   
   /**
    * 
    * @param result
    * @param comment
    */
   public void result(String result, String comment);
   

   /**
    * 
    * @param n
    */
   public void pong(String n);
   
   
   /**
    * 
    *
    */
   public void resign();
   
   /**
    * 
    *
    */
   public void offerDraw();
   
   /**
    * 
    * @param message
    */
   public void tellopponent(String message);
   
   /**
    * 
    * @param message
    */
   public void tellothers(String message);
   
   /**
    * 
    * @param message
    */
   public void tellall(String message);
   
   /**
    * 
    * @param message
    */
   public void telluser(String message);
   
   /**
    * 
    * @param message
    */
   public void tellusererror(String message);
   
   /**
    * 
    * @param reptag
    * @param message
    */
   public void askuser(String reptag, String message);
   
   /**
    * 
    * @param message
    */
   public void tellics(String message);
   
   /**
    * 
    * @param message
    */
   public void tellicsnoalias(String message);
   
   /**
    * 
    * @param deep
    * @param rating
    * @param time
    * @param nodes
    * @param pv
    */
   public void thinkingOutput(String deep, String rating, String time, String nodes, String pv);
   
}