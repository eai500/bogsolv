package erik.boggle.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author erik
 */
public class WebServiceCaller {


    public String call(URL url) {
        try {
            return readStream(getInputStream(url));
        } catch (IOException e) {
            return "No internet connection.";
        }
    }

    public InputStream getInputStream(URL url) throws IOException {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return con.getInputStream();
    }


    private String readStream(InputStream in) {
        StringBuilder response = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}


