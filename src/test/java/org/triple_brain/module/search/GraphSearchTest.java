package org.triple_brain.module.search;

import org.junit.Test;
import org.triple_brain.module.model.graph.Vertex;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/*
* Copyright Mozilla Public License 1.1
*/
public class GraphSearchTest extends SearchRelatedTest{

    @Test
    public void can_search_vertices_for_auto_completion(){
        indexVertexABAndC();
        indexVertex(pineApple);
        GraphSearch graphSearch = GraphSearch.withCoreContainer(coreContainer);
        List<Vertex> vertices = graphSearch.searchVerticesForAutoCompletionByLabelAndUser("vert", user);
        assertThat(vertices.size(), is(3));
        vertices = graphSearch.searchVerticesForAutoCompletionByLabelAndUser("vertex B", user);
        assertThat(vertices.size(), is(3));
        Vertex firstVertex = vertices.get(0);
        assertThat(firstVertex.label(), is("vertex B"));
        vertices = graphSearch.searchVerticesForAutoCompletionByLabelAndUser("pine A", user);
        assertThat(vertices.size(), is(1));
    }

}