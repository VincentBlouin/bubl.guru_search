package org.triple_brain.module.search;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.triple_brain.module.model.User;

import java.net.URI;

/*
* Copyright Mozilla Public License 1.1
*/
public interface GraphSearch {
    public JSONArray searchOwnVerticesAndPublicOnesForAutoCompletionByLabel(
            String label,
            User user
    );
    public JSONArray searchOnlyForOwnVerticesForAutoCompletionByLabel(
            String label, User user
    );
    public JSONArray searchRelationsForAutoCompletionByLabel(
            String label,
            User user
    );
    public JSONObject getByUri(URI uri, User user);
}
