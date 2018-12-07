import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Document{
    private String _content;
    private String _title;
    private List<String> _terms;
    private List<Double> _tfIdfs;

    public Document(String content, String title){
        this._content = content;
        this._title = title;
        preprocessDocument();
    }

    public String getContent(){
        return _content;
    }

    public String getTitle(){
        return _title;
    }

    public List<String> getTerms(){
        return _terms;
    }

    public List<Double> getTfIdfs(){
        return _tfIdfs;
    }

    public void preprocessDocument(){
        String normalized = normalizeText(_content);
        List<String> tokens = tokenizeDocument(normalized);
        _terms = stemTokens(tokens);
    }

    public void calculateRepresentations(Dictionary dictionary){
        Map<String, Double> bagOfWords = calculateBagOfWords(_terms, dictionary);
        Map<String, Double> tfs = calculateTfs(bagOfWords);
        _tfIdfs = calculateTfIds(tfs, dictionary);
    }

    private List<String> stemTokens(List<String> tokens){
        return tokens.stream()
                .map(Stemmer::stemToken)
                .collect(Collectors.toList());
    }

    private String normalizeText( String oContentString ){
    	//TODO remove non-alphanumeric signs, keep only letters, digits and spaces.
    	//DONE!
    	
    	String oNewContentString = oContentString.replaceAll( "[^A-Za-z0-9 ]", "" );
    	oNewContentString = oNewContentString.toLowerCase();
    	
    	return oNewContentString;
    }

    private List<String> tokenizeDocument( String oNormalizedString ){
    	//TODO: tokenize document - use simple division on white spaces.
    	//DONE!
    	
    	List<String> oNormalisedArrayString = new ArrayList<String>();
    	oNormalisedArrayString = Arrays.asList( oNormalizedString.split("\\s+") );
    	
    	return oNormalisedArrayString;
    }
    
    private Map<String, Double> calculateBagOfWords( List<String> oTermsListString, Dictionary oDictionary ){
        //TODO: calculate bag-of-words representation - count how many times each term from dictionary.getTerms
        // exists in document
    	//DONE!
    	
    	HashMap<String,Double> oBagOfWordHashMap = new HashMap<String,Double>();
    	int iTermExistCounter = 0;
    	
    	for( String oTermFromDictionaryString : oDictionary.getTerms() )
    	{
    		iTermExistCounter = 0;
    		
    		for( String oTermFromListString : oTermsListString )
    		{
    			if( oTermFromListString.equals( oTermFromDictionaryString ) )
    			{
    				iTermExistCounter++;
    			}
    		}
    		
    		oBagOfWordHashMap.put( oTermFromDictionaryString, (double) iTermExistCounter );
    	}
    	
    	return oBagOfWordHashMap;
    }

    private Map<String, Double> calculateTfs( Map<String, Double> oBagOfWordsMap ){
    	//TODO: calculate TF representation - divide elements from bag-of-words by maximal value from this vector
    	//DONE!
    	
        HashMap<String,Double> oTermTfsResultsHashMap = new HashMap<String,Double>(); 
        double dMaxValueFromBagOfWords = FindMaxValueFromMap( oBagOfWordsMap );
        double dTfsValueResult = 0.0;
        
        for( Map.Entry<String, Double> oBagOfWordsMapEntry : oBagOfWordsMap.entrySet() )
        {
        	if( dMaxValueFromBagOfWords != 0 )
        	{
        		dTfsValueResult = oBagOfWordsMapEntry.getValue() / dMaxValueFromBagOfWords;
        	}
        	
        	oTermTfsResultsHashMap.put( oBagOfWordsMapEntry.getKey(), dTfsValueResult );	
        }
        
        return oTermTfsResultsHashMap;
    }

    private double FindMaxValueFromMap( Map<String, Double> bagOfWordsMap ){

    	double dMaxValueFromMap = 0.0;
    	double dTempValueFromMap = 0.0;
    	
    	for( Map.Entry<String, Double> oMapEntry : bagOfWordsMap.entrySet() )
    	{
    		dTempValueFromMap = oMapEntry.getValue();
    		
    	    if( dTempValueFromMap > dMaxValueFromMap)
    	    {
    	    	dMaxValueFromMap = dTempValueFromMap;
    	    }
    	}

		return dMaxValueFromMap;
	}

	private List<Double> calculateTfIds( Map<String, Double> oTfsMap, Dictionary oDictionary ) {
        //TODO: calculate TF-IDF representation - multiply elements from tf representation my matching IDFs (dictionary.getIfs())
        //return results as list of tf-IDF values for terms in the same order as dictionary.getTerms()
		//DONE!
		
		List<Double> oCalculateTfIdsMeasures = new ArrayList<Double>();
		double dTfIdfResult = 0.0;
		double dValueFromIdfsMap = 0.0;
		double dValueFromTfsMap = 0.0;
		
		for( String oTermString : oDictionary.getTerms() )
		{
			if( oTermString != null )
			{
				dValueFromIdfsMap = GetIdfsElementValueByKey( oDictionary, oTermString );
				dValueFromTfsMap = GetTfsElementValueByKey( oTfsMap, oTermString );
				dTfIdfResult = dValueFromIdfsMap * dValueFromTfsMap;
				
				if( Double.isNaN( dTfIdfResult ) )
				{
					dTfIdfResult = 0.0;
				}	
			}
			
			oCalculateTfIdsMeasures.add(dTfIdfResult);
		}
		
		return oCalculateTfIdsMeasures;
    }

    private double GetTfsElementValueByKey( Map<String, Double> oTfsMap, String oTermString ) {
    	
    	double dTfsElementValue = 0.0;
    	
    	for( Map.Entry<String,Double> oTfsElementMapEntry : oTfsMap.entrySet() )
		{
			if( oTfsElementMapEntry.getKey().equals( oTermString ) )
			{
				dTfsElementValue = oTfsElementMapEntry.getValue();
				break;
			}
		}
    	
    	return dTfsElementValue;
	}

	private double GetIdfsElementValueByKey( Dictionary dictionary, String oTermString ) {
		
    	double dIdfsElementValue = 0.0;
    	
    	for( Map.Entry<String,Double> oIdfsElementMapEntry : dictionary.getIdfs().entrySet() )
		{
			if( oIdfsElementMapEntry.getKey().equals( oTermString ) )
			{
				dIdfsElementValue = oIdfsElementMapEntry.getValue();
				break;
			}
		}
    	
    	return dIdfsElementValue;
	}

	public double calculateSimilarity( Document oQueryDocument ) {
		//TODO: calculate cosine similarity between current document and query document (use calculated TF_IDFs)
		//DONE!
		
		double dCosineSimilarity = 0.0;
		double dCounterPart = 0.0;
		double dDenominatorPart1 = 0.0;
		double dDenominatorPart2 = 0.0;
		double dDenominatorPart = 0.0;
		
		for( int iCounter = 0; iCounter < _tfIdfs.size(); iCounter++ )
		{
			dCounterPart += _tfIdfs.get( iCounter ) * oQueryDocument.getTfIdfs().get( iCounter );
			dDenominatorPart1 += Math.pow( _tfIdfs.get( iCounter ), 2.0 );
			dDenominatorPart2 += Math.pow( oQueryDocument.getTfIdfs().get( iCounter ), 2.0 );
		}
		
		dDenominatorPart = Math.sqrt( dDenominatorPart1 * dDenominatorPart2 );
		
		if( dDenominatorPart != 0 
			&& !Double.isNaN( dDenominatorPart ) )
		{
			dCosineSimilarity = dCounterPart / dDenominatorPart;
		}
		
		return dCosineSimilarity;
    }
}
