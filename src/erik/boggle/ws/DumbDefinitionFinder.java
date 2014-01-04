package erik.boggle.ws;

import erik.boggle.Constants;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author erik
 */
public class DumbDefinitionFinder implements DefinitionFinder {

    @Override
    public String getDefinition(String word) {
        String response;
        try {
            response = new WebServiceCaller().call(
                    new URL(Constants.DEFINITION_WS_BASE_URL + word + "?key=" + Constants.DEFINITION_WS_KEY));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return parseDefinition(response);
    }

    private String parseDefinition(String response) {
        int start = response.indexOf("<def>");
        int end = response.indexOf("</def>");
        return start > 0 && end > 0 ? clean(response, start, end) : "No definition found.";
    }

    private String clean(String response, int start, int end) {
        String cleaned = removeTags(response, start, end);
        return cleaned.startsWith(":") ? cleaned.substring(1, cleaned.length()) : cleaned;
    }

    private String removeTags(String response, int start, int end) {
        String def = response.substring(start, end);
        def = def.replaceAll("<date>.*?</date>", "");
        return def.replaceAll("<.*?>", "");
    }
}
