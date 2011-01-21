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
package es.dagaren.gladiator.main;

import es.dagaren.gladiator.communication.CommandController;
import es.dagaren.gladiator.communication.ConsoleCommandController;
import es.dagaren.gladiator.communication.xboard.EngineAdapter;
import es.dagaren.gladiator.communication.xboard.EngineController;
import es.dagaren.gladiator.engine.Engine;
import es.dagaren.gladiator.representation.BitboardPosition;

/**
 * @author dagaren
 *
 */
public class Main
{

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      //TODO Se analizan los argumentos
      
      
      
      //Se inicializan los bitboards de posicion
      BitboardPosition.init();
      
      //Se crea el motor
      Engine gladiatorEngine = new Engine();
      
      //Se crea el controlador de comandos por consola
      CommandController commandController = new ConsoleCommandController();
      
      //Se crea el controlador xboard
      EngineController xboardController = new EngineController();
      //Se establece el controlador de comandos del controlador xboard
      xboardController.setCommandController(commandController);
      
      
      //Se crea el adaptador xboard
      EngineAdapter xboardAdapter = new EngineAdapter();
      //Se asigna el motor al adaptador
      xboardAdapter.setEngine(gladiatorEngine);
      //Se asigna el controlador xboard al adaptador
      xboardAdapter.setEngineController(xboardController);
      //Se establece el el adaptador como receptor de comandos del controlador
      xboardController.setReceiver(xboardAdapter);
      
      
      //Se arranca el controlador de comandos
      commandController.start();
      
      /** 
       * A PARTIR DE ESTE MOMENTO SE COMIENZAN A
       * RECIBIR Y PROCESAR LOS COMANDOS 
       **/
      
      //Se espera a que el motor finalice
      gladiatorEngine.waitForFinish();
      
      //Se para el controlador de comandos
      commandController.stop();
      
      
      System.err.println("TERMINACIÓN NORMAL DE PROGRAMA");
      
      //System.exit(0);
   }
}
