public class Main
{
	public static void main(String[] args)
	{
		// Facebook id e senha da app cadastrada
		final String facebookId = "743980072335668";
		final String facebookSecret = "0d3878c173d1f15ad5d158c0ef545107";
		
		Facebook facebook = new Facebook();
		
		// Pega o token necessário para efetuar qualquer consulta ao servidor
		final String accessTokenFacebook = 
			facebook.accessToken(facebookId, facebookSecret);
		System.out.println("Acess Token: " + accessTokenFacebook);
	
		// Pega os posts do usuário nike
		final String postsFacebook = facebook.posts("nike", accessTokenFacebook);
		System.out.println(postsFacebook);
		
		// Twitter id e senha da app cadastrada
		final String twitterId = "w8SKM5qJVaIgiNN3sVh4fZlha";
		final String twitterSecret =
			"RljHR2GPp3iPZZQ3kqE4EqowUPBHkWR0wtVv25DD5IFzp6nCGQ";
		
		Twitter twitter = new Twitter();
		
		// Pega o token necessário para efetuar qualquer consulta ao servidor
		String accessTokenTwitter =
			twitter.accessToken(twitterId, twitterSecret);
		System.out.println(accessTokenTwitter);
		
		// No caso do Twitter é necessário remover o texto extra e ficar apenas
		// com o token, para o smalltalk é mais fácil usar o parser de JSON dele
		accessTokenTwitter =
			accessTokenTwitter.substring(0, accessTokenTwitter.lastIndexOf('"'));
		accessTokenTwitter =
			accessTokenTwitter.substring(accessTokenTwitter.lastIndexOf('"') + 1);
		System.out.println(accessTokenTwitter);
		
		// Pega os posts do usuário usponline
		final String postsTwitter = twitter.posts("usponline", accessTokenTwitter);
		System.out.println(postsTwitter);
	}
}
