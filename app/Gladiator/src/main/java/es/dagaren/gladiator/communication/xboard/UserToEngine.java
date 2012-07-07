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

import es.dagaren.gladiator.representation.Movement;

/**
 * @author dagaren
 *
 */
public interface UserToEngine
{
   /**
    * 
    */
   public void xboard();
   
   /**
    * 
    */
   public void protover(String version);
   
   /**
    * 
    */
   public void accepted(String feature);
   
   /**
    * 
    */
   public void rejected(String feature);
   
   /**
    * 
    */
   public void New();
   
   /**
    * 
    * @param nombreVariante
    */
   public void variant(String name);
   
   /**
    * 
    *
    */
   public void quit();
   
   
   /**
    * 
    *
    */
   public void random();
   
   
   /**
    * 
    *
    */
   public void force();
   
   /**
    * 
    *
    */
   public void go();
   
   /**
    * 
    */
   public void playother();
   
   
   /**
    * 
    *
    */
   public void white();
   
   
   /**
    * 
    *
    */
   public void black();
   
   /**
    * 
    * @param numMoves
    * @param time
    * @param increment
    */
   public void level(String numMoves, String time, String increment);
   
   /**
    * 
    * @param time
    */
   public void st(String time);
   
   /**
    * 
    * @param deep
    */
   public void sd(String deep);
   
   
   /**
    * 
    * @param n
    */
   public void time(String n);
   
   /**
    * 
    * @param n
    */
   public void otim(String n);
   
   /**
    * 
    *
    */
   public void usermove(Movement move);
   
   /**
    * 
    *
    */
   public void moveNow();
   
   /**
    * 
    * @param n
    */
   public void ping(String n);
   
   /**
    * 
    *
    */
   public void draw();
   
   /**
    * 
    * @param result
    * @param comment
    */
   public void result(String result, String comment);
   

   /**
    * 
    * @param fen
    */
   public void setboard(String fen);
   
   /**
    * 
    *
    */
   public void edit();
   
   /**
    * 
    *
    */
   public void hint();
   
   /**
    * 
    *
    */
   public void bk();
   
   
   /**
    * 
    *
    */
   public void  undo();
   
   /**
    * 
    *
    */
   public void remove();
   
   /**
    * 
    *
    */
   public void hard();
   
   /**
    * 
    *
    */
   public void easy();
   
   
   /**
    * 
    *
    */
   public void post();
   
   /**
    * 
    *
    */
   public void nopost();
   
   /**
    * 
    *
    */
   public void analyze();
   
   /**
    * 
    * @param name
    */
   public void name(String name);
   
   /**
    * 
    *
    */
   public void rating();
   
   /**
    * 
    */
   public void ics(String hostname);
   
   /**
    * 
    *
    */
   public void computer();
   
   /**
    * 
    */
   public void pause();
   
   /**
    * 
    */
   public void resume();
   
   
   /**
    * 
    * @param player
    */
   public void partner(String player);
   
   /**
    * 
    *
    */
   public void partner();
   
   /**
    * 
    * @param text
    */
   public void ptell(String text);
   
   /**
    * 
    *
    */
   public void holding(String white, String black, String colour, String piece);
}