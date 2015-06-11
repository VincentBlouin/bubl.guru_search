/*
 * Copyright Vincent Blouin under the GPL License version 3
 */

package guru.bubl.module.search;

import guru.bubl.module.model.graph.GraphElementPojo;
import guru.bubl.module.model.graph.GraphElementType;
import guru.bubl.module.model.graph.edge.EdgePojo;

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
