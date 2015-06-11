/*
 * Copyright Vincent Blouin under the GPL License version 3
 */

package guru.bubl.module.search;

import guru.bubl.module.model.graph.GraphElementPojo;
import guru.bubl.module.model.graph.GraphElementType;
import guru.bubl.module.model.graph.schema.SchemaPojo;

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
