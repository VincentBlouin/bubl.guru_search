package org.triple_brain.module.search;

/*
* Copyright Mozilla Public License 1.1
*/

import org.triple_brain.module.model.graph.GraphElement;
import org.triple_brain.module.model.graph.edge.Edge;
import org.triple_brain.module.model.graph.edge.EdgeOperator;
import org.triple_brain.module.model.graph.vertex.VertexOperator;

public interface GraphIndexer {
    public void indexWholeGraph();
    public void indexVertex(VertexOperator vertex);
    public void indexRelation(Edge edge);
    public void deleteGraphElement(GraphElement graphElement);
    public void handleEdgeLabelUpdated(EdgeOperator edge);
    public void commit();
}
