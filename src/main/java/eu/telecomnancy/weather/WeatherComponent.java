package eu.telecomnancy.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Call the OpenWeather API to get the weather for a given city
public class WeatherComponent {

    String APIKey;
    String uri;

    // Constructor
    public WeatherComponent() throws Exception {
        // read properties from a file
        InputStream input=getClass().getClassLoader().getResourceAsStream("application.properties");  
        Properties p=new Properties();  
        p.load(input);  
        this.APIKey = p.getProperty("OPENWEATHERMAP_API_KEY");
        this.uri = p.getProperty("OPENWEATHERMAP_WEATHERURL");
    }

    // Get the weather for a given city
    public String getWeather(String city) throws Exception {
        // make an http request to the OpenWeather API
        URL url = new URL(uri+"?q="+city+"&appid="+APIKey+"&units=metric");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		} else {
			response=new StringBuffer(""+responseCode);
		}
        // return the response
        return response.toString();
    }

    public static void main(String[] args) throws Exception {
        WeatherComponent client=new WeatherComponent();
        String result=client.getWeather("Nancy");
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        System.out.println(result);
        System.out.println(jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble());

    }
}
