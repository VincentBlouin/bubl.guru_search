/*
 * Copyright Vincent Blouin under the Mozilla Public License 1.1
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;
import org.triple_brain.module.model.graph.GraphElementType;
import org.triple_brain.module.model.graph.schema.SchemaPojo;

public class PropertySearchResult implements GraphElementSearchResult {

    private GraphElementPojo graphElement;
    private SchemaPojo schema;
    private GraphElementType type = GraphElementType.property;

    public static PropertySearchResult forPropertyAndSchema(
            GraphElementPojo property,
            SchemaPojo schema
    ) {
        return new PropertySearchResult(
                property,
                schema
        );
    }

    protected PropertySearchResult(
            GraphElementPojo property,
            SchemaPojo schema
    ){
        this.graphElement = property;
        this.schema = schema;
    }

    @Override
    public GraphElementSearchResultPojo getGraphElementSearchResult() {
        return new GraphElementSearchResultPojo(
                graphElement,
                type
        );
    }

    @Override
    public GraphElementPojo getGraphElement() {
        return graphElement;
    }

    @Override
    public GraphElementType getType() {
        return type;
    }

    public SchemaPojo getSchema(){
        return schema;
    }
}
