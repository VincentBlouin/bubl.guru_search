/*
 * Copyright Vincent Blouin under the Mozilla Public License 1.1
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;
import org.triple_brain.module.model.graph.GraphElementType;

public interface GraphElementSearchResult {
    GraphElementSearchResultPojo getGraphElementSearchResult();
    GraphElementPojo getGraphElement();
    GraphElementType getType();
}
