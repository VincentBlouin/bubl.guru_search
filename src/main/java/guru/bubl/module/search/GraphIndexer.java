/*
 * Copyright Vincent Blouin under the GPL License version 3
 */

package guru.bubl.module.search;

import guru.bubl.module.model.graph.GraphElement;
import guru.bubl.module.model.graph.edge.Edge;
import guru.bubl.module.model.graph.schema.SchemaPojo;
import guru.bubl.module.model.graph.vertex.VertexOperator;

public interface GraphIndexer {
    void indexWholeGraph();
    void indexVertex(VertexOperator vertex);
    void indexRelation(Edge edge);
    void indexSchema(SchemaPojo schema);
    void deleteGraphElement(GraphElement graphElement);
    void commit();
}
