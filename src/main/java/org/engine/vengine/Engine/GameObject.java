
/*
 * V-Engine
 * Copyright (C) 2025
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.engine.vengine.Engine;

import org.engine.vengine.Scripting.Script;
import org.engine.vengine.Utils.Transform;

import java.util.List;

public interface GameObject {
    Transform getTransform();
    Transform setTransform(Transform transform); // Must return new Transform

    boolean isActive();
    boolean setActive(Boolean status);

    List<Script> getScripts();
    void addScript(Script script);
    Script getScriptById(int scriptId);

    Transform setTransform(Transform transform);
}
