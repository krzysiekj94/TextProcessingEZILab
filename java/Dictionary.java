import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Dictionary{
    
	private Map<String, Double> _idfs;
    private List<String> _terms;

    public Dictionary(List<String> keywords){
        _terms = keywords
                .stream()
                .map(Stemmer::stemToken)
                .distinct()
                .collect(Collectors.toList());
        _idfs = new HashMap<String,Double>();
    }

    public Map<String, Double> getIdfs(){
        return _idfs;
    }

    public List<String> getTerms(){
        return _terms;
    }

    public void calculateIdfs( List<Document> documents ){
        //TODO: calculate idfs for each term - log(N/m) - N - documents count, m - number of documents containing given term
        //assign computed values to _idfs map (key: term, value: IDF)
    	//DONE!
    	
    	double dIDFValue = 0.0;
    	int iNumberOfDocumentsContainingTerm = 0;
    	int iDocumentsCount = documents.size();
    	
    	for( String term : _terms )
    	{
    		dIDFValue = 0.0;
    		iNumberOfDocumentsContainingTerm = 0;
    		
    		for( Document document : documents )
        	{
    			if( IsExistTermInDocument( document, term ) )
    			{
    				iNumberOfDocumentsContainingTerm++;
    			}
        	}
    		
    		if( iNumberOfDocumentsContainingTerm > 0 )
    		{
    			dIDFValue =  Math.log( (double) iDocumentsCount / iNumberOfDocumentsContainingTerm );
    		}
    		
    		_idfs.put( term, dIDFValue );
    	}
    }

	private boolean IsExistTermInDocument( Document oDocument, String oTermString ){
		return oDocument.getTerms().contains( oTermString );
	}
}
