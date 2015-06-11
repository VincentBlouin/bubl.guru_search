/*
 * Copyright Vincent Blouin under the GPL License version 3
 */

package guru.bubl.module.search;

import guru.bubl.module.model.graph.GraphElementPojo;
import guru.bubl.module.model.graph.GraphElementType;

public interface GraphElementSearchResult {
    GraphElementSearchResultPojo getGraphElementSearchResult();
    GraphElementPojo getGraphElement();
    GraphElementType getType();
}
