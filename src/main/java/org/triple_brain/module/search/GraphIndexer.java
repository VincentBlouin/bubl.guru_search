package org.triple_brain.module.search;

/*
* Copyright Mozilla Public License 1.1
*/

import org.triple_brain.module.model.graph.Edge;
import org.triple_brain.module.model.graph.GraphElement;
import org.triple_brain.module.model.graph.Vertex;

public interface GraphIndexer {
    public void indexWholeGraph();
    public void indexVertex(Vertex vertex);
    public void indexRelation(Edge edge);
    public void deleteGraphElement(GraphElement graphElement);
    public void handleEdgeLabelUpdated(Edge edge);
    public void commit();
}
