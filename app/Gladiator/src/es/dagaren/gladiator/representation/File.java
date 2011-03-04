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

import java.util.HashMap;
import java.util.Map;

/**
 * @author dagaren
 *
 */
public class File
{
   public static final File a = new File(0, "a");
   public static final File b = new File(1, "b");
   public static final File c = new File(2, "c");
   public static final File d = new File(3, "d");
   public static final File e = new File(4, "e");
   public static final File f = new File(5, "f");
   public static final File g = new File(6, "g");
   public static final File h = new File(7, "h");
   
   
   public int index;
   
   protected static File[] files;
   protected static String[] filesNames;
   protected static Map<String, File> filesMap;
   
   private File(int index , String name)
   {
      this.index = index;
      
      if(files == null)
      {
         files = new File[8];
         filesNames = new String[8];
         filesMap = new HashMap<String, File>();
      }
      
      files[index] = this;
      filesNames[index] = name;
      filesMap.put(name, this);
   }
   
   public static File fromIndex(int index)
   {
      return files[index];
   }
   
   public static File valueOf(String name)
   {
      if(filesMap.containsKey(name))
         return filesMap.get(name);
      else
         return null;
   }
   
   public String name()
   {
      return filesNames[this.index];
   }
   
   public String toString()
   {
      return name();
   }
}
