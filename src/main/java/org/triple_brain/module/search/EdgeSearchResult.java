/*
 * Copyright Vincent Blouin under the GPL License version 3
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;
import org.triple_brain.module.model.graph.GraphElementType;
import org.triple_brain.module.model.graph.edge.Edge;
import org.triple_brain.module.model.graph.edge.EdgePojo;

public class EdgeSearchResult implements GraphElementSearchResult {

    private EdgePojo edge;
    private GraphElementType type = GraphElementType.edge;
    public EdgeSearchResult(
            EdgePojo edgePojo
    ){
        this.edge = edgePojo;
    }

    @Override
    public GraphElementSearchResultPojo getGraphElementSearchResult() {
        return new GraphElementSearchResultPojo(
                edge.getGraphElement(),
                type
        );
    }

    @Override
    public GraphElementPojo getGraphElement() {
        return edge.getGraphElement();
    }

    @Override
    public GraphElementType getType() {
        return type;
    }

    public EdgePojo getEdge(){
        return edge;
    }
}
