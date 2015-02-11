/*
 * Copyright Vincent Blouin under the Mozilla Public License 1.1
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElementPojo;
import org.triple_brain.module.model.graph.schema.SchemaPojo;

public class PropertySearchResult implements GraphElementSearchResult {

    private GraphElementPojo property;
    private SchemaPojo schema;

    public static PropertySearchResult forPropertyAndSchemaName(
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
        this.property = property;
        this.schema = schema;
    }

    @Override
    public GraphElementSearchResultPojo getGraphElementSearchResult() {
        return new GraphElementSearchResultPojo(
                property
        );
    }

    @Override
    public GraphElementPojo getGraphElement() {
        return property;
    }

    public SchemaPojo getSchema(){
        return schema;
    }
}
