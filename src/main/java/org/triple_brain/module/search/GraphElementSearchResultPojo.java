package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;

/*
* Copyright Mozilla Public License 1.1
*/
public class GraphElementSearchResultPojo {
    private GraphElementPojo graphElement;

    public GraphElementSearchResultPojo(
            GraphElementPojo graphElement
    ){
        this.graphElement = graphElement;
    }
    public GraphElementPojo getGraphElement(){
        return graphElement;
    }
}
