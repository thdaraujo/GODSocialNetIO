import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Facebook
{
	private final static String USER_AGENT = "Mozilla/5.0";
	private final static String GRAPH_API = "https://graph.facebook.com/";
	
	public String posts(final String user, final String accessToken)
	{
		final String url = GRAPH_API + user + "/posts?" + accessToken;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString();
	}
	
	public String accessToken(final String id, final String secret)
	{
		final String grantType = "client_credentials";
		
		String url = GRAPH_API + "oauth/access_token?";
		url += "client_id=" + id + "&client_secret=" + secret;
		url += "&grant_type=" + grantType;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString();
	}
	
	private int sendGet(final String url, StringBuffer response)
	{
		int responseCode = 0;
		try
		{
			HttpURLConnection connection = 
				(HttpURLConnection) (new URL(url)).openConnection();
			
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", USER_AGENT);
			
			responseCode = connection.getResponseCode();
			System.out.println("Sending 'GET' request to URL: " + url);
			System.out.println("Response Code: " + responseCode);
			
			BufferedReader in =
				new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				response.append(inputLine);
			in.close();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
				
		return responseCode;
	}
}
