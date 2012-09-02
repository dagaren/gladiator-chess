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
package es.dagaren.gladiator.notation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import es.dagaren.gladiator.representation.BitboardPosition;
import es.dagaren.gladiator.representation.Colour;
import es.dagaren.gladiator.representation.GenericPiece;
import es.dagaren.gladiator.representation.Position;
import es.dagaren.gladiator.representation.Square;

/**
 * @author dagaren
 *
 */
public class ExtendedPositionDescription
{
   private static Logger logger = Logger.getLogger(ExtendedPositionDescription.class);
   
   protected Position position;

   protected Map<String, List<String>> operationsMap = new HashMap<String, List<String>>();
   
   
   public Position getPosition()
   {
      return position;
   }

   public void setPosition(Position position)
   {
      this.position = position;
   }
   
   public Map<String, List<String>> getOperationsMap()
   {
      return operationsMap;
   }

   public void setOperationsMap(Map<String, List<String>> operationsMap)
   {
      this.operationsMap = operationsMap;
   }

   public String toString()
   {
      String epdString = "";
      epdString += this.position.toString();
      epdString += "\n\nOperaciones:\n";
      
      Set<String> opcodes = this.operationsMap.keySet();
      for(String opcode: opcodes)
      {
         epdString += "=>" + opcode + ":";
         
         List<String> operands = this.operationsMap.get(opcode);
         for(String operand : operands)
         {
            epdString += " " + operand;
         }
         epdString += "\n";
      }
      
      return epdString;
   }
   
   public static ExtendedPositionDescription load(String epdString)
   {
      ExtendedPositionDescription epd = new ExtendedPositionDescription();
      
      String opcodeRegex = "([a-zA-Z][a-zA-Z_0-9]*)";
      String operandRegex = "([^\\s\";']+|\"[^\"]*\")";
      String operationRegex = opcodeRegex  + "(( " + operandRegex + ")*);";
      
      String boardRegex = "^" +
                          "([1-8pnbrqkPNBRQK]{1,8}/" +
                          "[1-8pnbrqkPNBRQK]{1,8}/" +
                          "[1-8pnbrqkPNBRQK]{1,8}/" +
                          "[1-8pnbrqkPNBRQK]{1,8}/" +
                          "[1-8pnbrqkPNBRQK]{1,8}/" +
                          "[1-8pnbrqkPNBRQK]{1,8}/" +
                          "[1-8pnbrqkPNBRQK]{1,8}/" +
                          "[1-8pnbrqkPNBRQK]{1,8})" +
                          " " +
                          "([bw])" +
                          " " +
                          "([kqKQ]+|-)" +
                          " " +
                          "([a-h][36]|-)" +
                          "((?: " + operationRegex + ")*)" +
                          "$";
      
      Pattern regex = Pattern.compile(boardRegex);
      Matcher regexMatcher = regex.matcher(epdString); 
      
      if(regexMatcher.find())
      {
         String boardString =      regexMatcher.group(1);
         String turnString =       regexMatcher.group(2);
         String castlingString =   regexMatcher.group(3);
         String enPassantString =  regexMatcher.group(4);
         String operationsString = regexMatcher.group(5);
         
         regex = Pattern.compile(operationRegex);
         regexMatcher = regex.matcher(operationsString);
         
         List<String> operations = new ArrayList<String>();
         while(regexMatcher.find())
         {
            operations.add(regexMatcher.group());
         }
         
         List<String> opcodes = new ArrayList<String>();
         List<String> operands = new ArrayList<String>();
         Map<String,List<String>> operationsMap =  new HashMap<String,List<String>>();
         
         for(String s: operations)
         {
            regexMatcher = regex.matcher(s);
            if(regexMatcher.matches())
            {
               opcodes.add(regexMatcher.group(1));
               operands.add(regexMatcher.group(2));
            }
         }
         
         for(int i = 0; i < opcodes.size(); i++)
         {
            String op = operands.get(i);
            
            regex = Pattern.compile("([^\\s\";']+)|\"([^\"]*)\"");
            regexMatcher = regex.matcher(op);
            
            List<String> operandList = new ArrayList<String>();
            while(regexMatcher.find())
            {
               if(regexMatcher.group(1) != null)
               {
                  operandList.add(regexMatcher.group(1));
               }
               else if(regexMatcher.group(2) != null)
               {
                  operandList.add(regexMatcher.group(2));
               }
            }
            
            operationsMap.put(opcodes.get(i), operandList);
         }
         
         //Se crea la posición
         Position position = new BitboardPosition();
         
         //Se ve si se puede recuperar los movimientos hasta
         //la regla de los 50 movimientos
         String fiftyMovesString = "0";
         if(operationsMap.containsKey("hmvc"))
         {
            fiftyMovesString = operationsMap.get("hmvc").get(0);
         }
         
         //Se ve si se puede recuperar el movimiento
         String moveString = "1";
         if(operationsMap.containsKey("fmcn"))
         {
            moveString = operationsMap.get("hmvc").get(0);
         }
         
         
         position.loadFen(boardString      + " " + 
                          turnString       + " " +
                          castlingString   + " " + 
                          enPassantString  + " " + 
                          fiftyMovesString + " " +
                          moveString
                          );
         
         epd.setPosition(position);
         epd.setOperationsMap(operationsMap);
         
         return epd;
      }
      else
      {
         logger.error("La cadena '" + epdString + "' no es un epd correcto");
         
         return null;
      }
   }
}
