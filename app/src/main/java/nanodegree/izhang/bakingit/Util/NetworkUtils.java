package nanodegree.izhang.bakingit.Util;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *  Created by ivanzhang on 12/2/17.
 *
 * These utilities will be used to communicate with the baking recipe servers.
 *
 */

public final class NetworkUtils {

    private static final String RECIPE_URL = "ENDPOINT_FOR_BAKING_DATA";

    /**
     * Builds the URL based on the passed in sort the user wants.
     *
     * @return The URL to use to query the service.
     */
    public static URL buildUrl() {

        Uri builtUri;

        builtUri = Uri.parse(RECIPE_URL);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
