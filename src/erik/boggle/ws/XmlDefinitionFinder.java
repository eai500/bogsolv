package erik.boggle.ws;

import android.util.Xml;
import erik.boggle.Constants;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author erik
 */

public class XmlDefinitionFinder implements DefinitionFinder {
    private static final String ns = null;

    @Override
    public String getDefinition(String word) {
        try {
            return parse(new WebServiceCaller().getInputStream(buildUrl(word)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private URL buildUrl(String word) {
        try {
            return new URL(Constants.DEFINITION_WS_BASE_URL + word + "?key=" + Constants.DEFINITION_WS_KEY);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private String parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private String readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        String definition = "no defition found...";

        parser.require(XmlPullParser.START_TAG, ns, "entry_list");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                return readEntry(parser);
            } else {
                skip(parser);
            }
        }
        return definition;
    }

    private String readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");
        String type = null;
        String definition = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("fl")) {
                type = readType(parser);
            } else if (name.equals("def")) {
                definition = readDefinitionEntry(parser);
                return "["+type + "] " + definition;
            } else {
                skip(parser);
            }
        }
        return "cannot find def.";
    }

    // Processes title tags in the feed.
    private String readType(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "fl");
        String type = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "fl");
        return type;
    }

    private String readDefinitionEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "def");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("dt")) {
                return readDefinition(parser);
            } else {
                skip(parser);
            }
        }
        return "cannot find def.";
    }

    // Processes summary tags in the feed.
    private String readDefinition(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "dt");
        String summary = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "dt");
        return summary;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public static void main(String[] arg) {
        Collection<String> strings = new ArrayList<String>(null);
    }
}
