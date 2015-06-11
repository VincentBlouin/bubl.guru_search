/*
 * Copyright Vincent Blouin under the GPL License version 3
 */

package guru.bubl.module.search;

import guru.bubl.module.model.graph.GraphElementPojo;
import guru.bubl.module.model.graph.GraphElementType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class VertexSearchResult implements GraphElementSearchResult{
    private GraphElementPojo graphElement;
    private Map<URI, GraphElementPojo> properties;
    private GraphElementType type;

    public VertexSearchResult(
            GraphElementPojo graphElement,
            GraphElementType type
    ){
        this(
                graphElement,
                type,
                null
        );
    }

    public VertexSearchResult(
            GraphElementPojo graphElement,
            GraphElementType type,
            Map<URI, GraphElementPojo> properties
    ){
        this.graphElement = graphElement;
        this.type = type;
        this.properties = properties;
    }

    @Override
    public GraphElementSearchResultPojo getGraphElementSearchResult() {
        return new GraphElementSearchResultPojo(
                graphElement,
                type
        );
    }

    @Override
    public GraphElementPojo getGraphElement(){
        return graphElement;
    }

    @Override
    public GraphElementType getType() {
        return null;
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
