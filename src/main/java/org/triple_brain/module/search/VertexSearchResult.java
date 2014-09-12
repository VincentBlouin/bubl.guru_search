/*
 * Copyright Vincent Blouin under the Mozilla Public License 1.1
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;

import java.util.ArrayList;
import java.util.List;

public class VertexSearchResult implements GraphElementSearchResult{
    private GraphElementPojo graphElement;
    private List<String> propertiesName;

    public VertexSearchResult(
            GraphElementPojo graphElement,
            List<String> propertiesName
    ){
        this.graphElement = graphElement;
        this.propertiesName = propertiesName;
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

    public List<String> getPropertiesName(){
        if(null == propertiesName){
            return new ArrayList<>();
        }
        return propertiesName;
    }

    public Boolean hasProperties(){
        return null != propertiesName;
    }
}
