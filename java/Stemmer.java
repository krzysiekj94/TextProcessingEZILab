public class Stemmer {
	
	static PorterStemmer oStemmer = null;
	
    public static String stemToken( String oTokenString ) {
        //TODO: use PorterStemmer to stem document
        // you can see exemplary use of stemmer in method main() of PorterStemmer class
        //DONE!
    	
    	if( oStemmer == null )
    	{
    		oStemmer = new PorterStemmer();
    	}
    	
    	if( oStemmer != null )
    	{
            oStemmer.add( oTokenString.toCharArray(), oTokenString.length() );
            oStemmer.stem();
    	}
    	
        return oTokenString;
    }
}