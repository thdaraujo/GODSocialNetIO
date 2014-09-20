import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.commons.codec.binary.Base64;

public class Twitter
{
	private final static String USER_AGENT = "teste_app_poo";
	private final static String API = "https://api.twitter.com/";
	
	public String posts(final String user, final String accessToken)
	{
		String url = API + "1.1/statuses/user_timeline.json?";
		url += "screen_name=" + user;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, accessToken, response);
		
		return response.toString();
	}
	
	public String accessToken(final String id, final String secret)
	{
		final String grantType = "client_credentials";
		final String url = API + "oauth2/token?" + "&grant_type=" + grantType; 
		
		StringBuffer response = new StringBuffer();
		try
		{
			HttpURLConnection connection = 
				(HttpURLConnection) (new URL(url)).openConnection();
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Host", "api.twitter.com");
			connection.setRequestProperty("User-Agent", USER_AGENT);
			connection.setRequestProperty("Authorization",
										  "Basic " + encode(id, secret));
						
			final int responseCode = connection.getResponseCode();
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
		
		return response.toString();
	}
	
	private String encode(final String id, final String secret)
	{
		try
		{
			final String encodedId = URLEncoder.encode(id, "UTF-8");
			final String encodedSecret = URLEncoder.encode(secret, "UTF-8");
			
			return new String(Base64.encodeBase64((encodedId + ":" + encodedSecret)
					.getBytes()));
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return new String();
		}
	}
	
	private int sendGet(final String url, final String accessToken,
						StringBuffer response)
	{
		int responseCode = 0;
		try
		{
			HttpURLConnection connection = 
				(HttpURLConnection) (new URL(url)).openConnection();

			connection.setRequestMethod("GET");
			connection.setRequestProperty("Host", "api.twitter.com");
			connection.setRequestProperty("User-Agent", USER_AGENT);
			connection.setRequestProperty("Authorization", "Bearer " + accessToken);
			
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
