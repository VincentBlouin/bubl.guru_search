package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;

import java.util.List;

/*
* Copyright Mozilla Public License 1.1
*/
public class VertexSearchResult implements GraphElementSearchResult{
    private GraphElementPojo graphElement;
    private List<String> relationsName;

    public VertexSearchResult(
            GraphElementPojo graphElement,
            List<String> relationsName
    ){
        this.graphElement = graphElement;
        this.relationsName = relationsName;
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

    public List<String> getRelationsName(){
        return relationsName;
    }

    public Boolean hasRelations(){
        return null != relationsName;
    }
}
