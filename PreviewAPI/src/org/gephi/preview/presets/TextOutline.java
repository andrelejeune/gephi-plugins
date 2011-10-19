/*
Copyright 2008-2010 Gephi
Authors : Sebastien Heymann <sebastien.heymann@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.preview.presets;

import org.gephi.preview.api.PreviewPreset;
import org.gephi.preview.api.PreviewProperty;
import org.openide.util.NbBundle;

public class TextOutline extends PreviewPreset {

    public TextOutline() {
        super(NbBundle.getMessage(TextOutline.class, "TextOutline.name"));

        //Default
        DefaultPreset defaultPreset = new DefaultPreset();
        properties.putAll(defaultPreset.getProperties());

        //Custom values
        properties.put(PreviewProperty.SHOW_EDGE_LABELS, Boolean.TRUE);
        properties.put(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
        properties.put(PreviewProperty.NODE_LABEL_OUTLINE_SIZE, 4f);
        properties.put(PreviewProperty.EDGE_LABEL_OUTLINE_SIZE, 2f);
    }
}
