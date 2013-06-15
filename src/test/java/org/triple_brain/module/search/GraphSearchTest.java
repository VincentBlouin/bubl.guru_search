package org.triple_brain.module.search;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.triple_brain.module.common_utils.JsonUtils;
import org.triple_brain.module.search.json.SearchJsonConverter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.triple_brain.module.model.json.graph.VertexJsonFields.LABEL;
/*
* Copyright Mozilla Public License 1.1
*/
public class GraphSearchTest extends SearchRelatedTest {

    @Test
    public void can_search_vertices_for_auto_completion() throws Exception{
        indexVertexABAndC();
        indexVertex(pineApple);
        GraphSearch graphSearch = GraphSearch.withCoreContainer(coreContainer);
        JSONArray vertices;
        vertices = graphSearch.searchVerticesForAutoCompletionByLabelAndUser("vert", user);
        assertThat(vertices.length(), is(3));
        vertices = graphSearch.searchVerticesForAutoCompletionByLabelAndUser("vertex Cad", user);
        assertThat(vertices.length(), is(1));
        JSONObject firstVertex = vertices.getJSONObject(0);
        assertThat(firstVertex.getString(LABEL), is("vertex Cadeau"));
        vertices = graphSearch.searchVerticesForAutoCompletionByLabelAndUser("pine A", user);
        assertThat(vertices.length(), is(1));
    }

    @Test
    public void cant_search_in_vertices_of_another_user() throws Exception{
        indexVertexABAndC();
        indexVertex(pineApple);
        GraphSearch graphSearch = GraphSearch.withCoreContainer(coreContainer);
        JSONArray vertices = graphSearch.searchVerticesForAutoCompletionByLabelAndUser(
                "vert",
                user
        );
        assertTrue(vertices.length() > 0);
        vertices = graphSearch.searchVerticesForAutoCompletionByLabelAndUser(
                "vert",
                user2
        );
        assertFalse(vertices.length() > 0);
    }

    @Test
    public void vertex_note_can_be_retrieved_from_search()throws Exception{
        vertexA.note("A description");
        indexVertexABAndC();
        GraphSearch graphSearch = GraphSearch.withCoreContainer(coreContainer);
        JSONArray searchResults = graphSearch.searchVerticesForAutoCompletionByLabelAndUser(
                vertexA.label(),
                user
        );
        String note = searchResults.getJSONObject(0).getString("note");
        assertThat(note, is("A description"));
    }

    @Test
    public void vertex_relations_name_can_be_retrieved()throws Exception{
        indexVertexABAndC();
        GraphSearch graphSearch = GraphSearch.withCoreContainer(coreContainer);
        JSONArray searchResults = graphSearch.searchVerticesForAutoCompletionByLabelAndUser(
                vertexA.label(),
                user
        );
        JSONObject result = searchResults.getJSONObject(0);
        JSONArray relationsName = result.getJSONArray(
                SearchJsonConverter.RELATIONS_NAME
        );
        assertTrue(JsonUtils.containsString(
                relationsName,
                "between vertex A and vertex B"
        ));
    }

    @Test
    public void incoming_and_outgoing_vertex_relations_name_can_be_retrieved()throws Exception{
        indexVertexABAndC();
        GraphSearch graphSearch = GraphSearch.withCoreContainer(coreContainer);
        JSONArray searchResults = graphSearch.searchVerticesForAutoCompletionByLabelAndUser(
                vertexB.label(),
                user
        );
        JSONObject result = searchResults.getJSONObject(0);
        JSONArray relationsName = result.getJSONArray(
                SearchJsonConverter.RELATIONS_NAME
        );
        assertTrue(JsonUtils.containsString(
                relationsName,
                "between vertex A and vertex B"
        ));
        assertTrue(JsonUtils.containsString(
                relationsName,
                "between vertex B and vertex C"
        ));
    }

}