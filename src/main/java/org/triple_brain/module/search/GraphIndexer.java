/*
 * Copyright Vincent Blouin under the Mozilla Public License 1.1
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.graph.GraphElement;
import org.triple_brain.module.model.graph.edge.Edge;
import org.triple_brain.module.model.graph.edge.EdgeOperator;
import org.triple_brain.module.model.graph.schema.Schema;
import org.triple_brain.module.model.graph.schema.SchemaPojo;
import org.triple_brain.module.model.graph.vertex.VertexOperator;

public interface GraphIndexer {
    void indexWholeGraph();
    void indexVertex(VertexOperator vertex);
    void indexRelation(Edge edge);
    void indexSchema(SchemaPojo schema);
    void deleteGraphElement(GraphElement graphElement);
    void commit();
}
