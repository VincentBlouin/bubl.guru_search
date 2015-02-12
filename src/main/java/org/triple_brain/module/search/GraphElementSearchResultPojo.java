/*
 * Copyright Vincent Blouin under the Mozilla Public License 1.1
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;
import org.triple_brain.module.model.graph.GraphElementType;

public class GraphElementSearchResultPojo implements GraphElementSearchResult {
    private GraphElementPojo graphElement;
    private GraphElementType type;

    public GraphElementSearchResultPojo(
            GraphElementPojo graphElement,
            GraphElementType type
    ) {
        this.graphElement = graphElement;
        this.type = type;
    }

    @Override
    public GraphElementSearchResultPojo getGraphElementSearchResult() {
        return this;
    }

    @Override
    public GraphElementPojo getGraphElement() {
        return graphElement;
    }

    @Override
    public GraphElementType getType() {
        return type;
    }
}
