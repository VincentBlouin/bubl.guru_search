/*
 * Copyright Vincent Blouin under the GPL License version 3
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.User;

import java.net.URI;
import java.util.List;

public interface GraphSearch {
    List<VertexSearchResult> searchSchemasOwnVerticesAndPublicOnesForAutoCompletionByLabel(
            String searchTerm,
            User user
    );

    List<VertexSearchResult> searchOnlyForOwnVerticesOrSchemasForAutoCompletionByLabel(
            String label, User user
    );

    List<VertexSearchResult> searchOnlyForOwnVerticesForAutoCompletionByLabel(
            String searchTerm, User user
    );
    List<GraphElementSearchResult> searchRelationsPropertiesOrSchemasForAutoCompletionByLabel(
            String searchTerm,
            User user
    );
    GraphElementSearchResult getDetails(URI uri, User user);

    List<VertexSearchResult> searchPublicVerticesOnly(
            String searchTerm
    );

    GraphElementSearchResult getDetailsAnonymously(URI uri);

}
