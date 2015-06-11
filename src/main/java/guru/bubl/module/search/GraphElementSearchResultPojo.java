/*
 * Copyright Vincent Blouin under the GPL License version 3
 */

package guru.bubl.module.search;

import guru.bubl.module.model.graph.GraphElementPojo;
import guru.bubl.module.model.graph.GraphElementType;

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
