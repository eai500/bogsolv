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
        return readStream(getInputStream(url));
    }

    public InputStream getInputStream(URL url) {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return con.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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


