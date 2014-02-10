package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;
import org.triple_brain.module.model.graph.edge.Edge;
import org.triple_brain.module.model.graph.edge.EdgePojo;

/*
* Copyright Mozilla Public License 1.1
*/
public class EdgeSearchResult implements GraphElementSearchResult {

    private EdgePojo edge;

    public EdgeSearchResult(
            EdgePojo edgePojo
    ){
        this.edge = edgePojo;
    }

    @Override
    public GraphElementSearchResultPojo getGraphElementSearchResult() {
        return new GraphElementSearchResultPojo(
                edge.getGraphElement()
        );
    }

    @Override
    public GraphElementPojo getGraphElement() {
        return edge.getGraphElement();
    }

    public Edge getEdge(){
        return edge;
    }
}
