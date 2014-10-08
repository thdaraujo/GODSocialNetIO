import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Facebook
{
	private final static String USER_AGENT = "Mozilla/5.0";
	private final static String GRAPH_API = "https://graph.facebook.com/v2.1/";
	
	private String getById(final String id, final String accessToken)
	{
		final String url = GRAPH_API + id + "?" + accessToken;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString(); 
	}
	
	public String pages(final String page, final String accessToken)
	{
		final String url = GRAPH_API + "search?q=" + page.replace(" ", "+") + "&type=page&" + accessToken;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString(); 
	}
	
	public String pageDescription(final String id, final String accessToken)
	{
		return getById(id, accessToken);
	}
	
	public String users(final String user, final String accessToken)
	{
		final String url = GRAPH_API + "search?q=" + user.replace(" ", "+") + "&type=user&" + accessToken;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString(); 
	}
	
	public String groups(final String group, final String accessToken)
	{
		final String url = GRAPH_API + "search?q=" + group.replace(" ", "+") + "&type=group&" + accessToken;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString(); 
	}
	
	public String groupDescription(final String id, final String accessToken)
	{
		return getById(id, accessToken);
	}
	
	public String events(final String event, final String accessToken)
	{
		final String url = GRAPH_API + "search?q=" + event.replace(" ", "+") + "&type=event&" + accessToken;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString(); 
	}
	
	public String events(final String event, final String since, final String accessToken)
	{
		final String url = GRAPH_API + "search?q=" + event.replace(" ", "+") + "&since=" + since + 
				"&type=event&" + accessToken;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString(); 
	}
	
	public String events(final String event, final String since, final String until, final String accessToken)
	{
		final String url = GRAPH_API + "search?q=" + event.replace(" ", "+") + "&since=" + since + 
				"&until=" + until + "&type=event&" + accessToken;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString(); 
	}
	
	public String posts(final String user, final String accessToken)
	{
		final String url = GRAPH_API + user + "/posts?" + accessToken;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString();
	}
	
	public String posts(final String user, final String since, final String accessToken)
	{
		final String url = GRAPH_API + user + "/posts?" + "since=" + since + "&" + accessToken;
		
		StringBuffer response = new StringBuffer();
		sendGet(url, response);
		
		return response.toString();
	}
	
	public String posts(final String user, final String since, final String until, final String accessToken)
	{
		final String url = GRAPH_API + user + "/posts?" + "since=" + since + "&until=" + until + "&" + accessToken;
		
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
		
		// Get the test users from app		
		url = GRAPH_API + id + "/accounts/test-users?" + response.toString();
		
		response = new StringBuffer();
		sendGet(url, response);
		
		// Get the access_token of the first user (Use JSON parser in smalltalk for this)
		String accessToken = response.toString();
		accessToken = accessToken.substring(accessToken.indexOf("access_token") + 15);
		
		return "access_token=" + accessToken.substring(0, accessToken.indexOf("\"")); 
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
