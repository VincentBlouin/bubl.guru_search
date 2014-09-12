/*
 * Copyright Vincent Blouin under the Mozilla Public License 1.1
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;

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
