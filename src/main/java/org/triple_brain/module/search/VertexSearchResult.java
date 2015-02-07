/*
 * Copyright Vincent Blouin under the Mozilla Public License 1.1
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class VertexSearchResult implements GraphElementSearchResult{
    private GraphElementPojo graphElement;
    private Map<URI, GraphElementPojo> properties;

    public VertexSearchResult(
            GraphElementPojo graphElement
    ){
        this(
                graphElement,
                null
        );
    }

    public VertexSearchResult(
            GraphElementPojo graphElement,
            Map<URI, GraphElementPojo> properties
    ){
        this.graphElement = graphElement;
        this.properties = properties;
    }

    @Override
    public GraphElementSearchResultPojo getGraphElementSearchResult() {
        return new GraphElementSearchResultPojo(
                graphElement
        );
    }

    @Override
    public GraphElementPojo getGraphElement(){
        return graphElement;
    }

    public Map<URI, GraphElementPojo> getProperties(){
        if(!hasProperties()){
            properties = new HashMap<>();
        }
        return properties;
    }

    public Boolean hasProperties(){
        return null != properties && !properties.isEmpty();
    }
}
