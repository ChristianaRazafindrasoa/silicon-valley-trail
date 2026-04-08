package trail.model;

import java.io.IOException;
import java.util.List;

public interface MapboxApi {
    record MapboxApiSearchEntry(String name, double lat, double lon) { }
    record MapboxApiSearchResponse(List<MapboxApiSearchEntry> entries) { }
    record MapboxApiSearchRequest(String query, double lat, double lon) { }

    MapboxApiSearchResponse search(MapboxApiSearchRequest request)
            throws IOException, InterruptedException;
}
