public class Stemmer {
	
	static PorterStemmer stemmer = null;
	
	//DONE!
    public static String stemToken(String token) {
        //TODO: use PorterStemmer to stem document
        // you can see exemplary use of stemmer in method main() of PorterStemmer class
        
    	if(stemmer == null)
    	{
    		stemmer = new PorterStemmer();
    	}
    	
    	if(stemmer != null)
    	{
            stemmer.add(token.toCharArray(), token.length());
            stemmer.stem();
            System.out.println(stemmer.toString());	
    	}
    	
        return token;
    }
}
