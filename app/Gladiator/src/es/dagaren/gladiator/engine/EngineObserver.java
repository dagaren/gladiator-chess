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
package es.dagaren.gladiator.engine;

import es.dagaren.gladiator.representation.Movement;
import es.dagaren.gladiator.search.SearchInfo;

/**
 * @author dagaren
 *
 */
public interface EngineObserver
{
   public void onPublishInfo(SearchInfo info);
   public void onMoveDone(Movement selectedMove);
   public void onGameFinished(String result, String reason);
   public void onIncorrectMove(Movement move);
}
