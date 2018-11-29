import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Document {
    private String _content;
    private String _title;
    private List<String> _terms;
    private List<Double> _tfIdfs;

    public Document(String content, String title) {
        this._content = content;
        this._title = title;
        preprocessDocument();
    }

    public String getContent() {
        return _content;
    }

    public String getTitle() {
        return _title;
    }

    public List<String> getTerms() {
        return _terms;
    }

    public List<Double> getTfIdfs() {
        return _tfIdfs;
    }

    public void preprocessDocument() {
        String normalized = normalizeText(_content);
        List<String> tokens = tokenizeDocument(normalized);
        _terms = stemTokens(tokens);
    }

    public void calculateRepresentations(Dictionary dictionary) {
        Map<String, Double> bagOfWords = calculateBagOfWords(_terms, dictionary);
        Map<String, Double> tfs = calculateTfs(bagOfWords);
        _tfIdfs = calculateTfIds(tfs, dictionary);
    }

    private List<String> stemTokens(List<String> tokens) {
        return tokens.stream()
                .map(Stemmer::stemToken)
                .collect(Collectors.toList());
    }

    //DONE!
    private String normalizeText(String content) {
        String newContentString = content.replaceAll("/[^A-Za-z0-9 ]/", "");
        
    	return newContentString; //TODO remove non-alphanumeric signs, keep only letters, digits and spaces.
    }

    //DONE!
    private List<String> tokenizeDocument(String normalized) {
        
    	List<String> oNormalisedArrayString = new ArrayList<String>();
    	oNormalisedArrayString = Arrays.asList( normalized.split("\\s+") );
    	
    	return oNormalisedArrayString; //TODO: tokenize document - use simple division on white spaces.
    }
    
    //DONE! - to test!
    private Map<String, Double> calculateBagOfWords(List<String> termsListString, Dictionary dictionary) {
        
    	HashMap<String,Double> oBagOfWordHashMap = new HashMap<String,Double>();
    	int iTermExistCounter = 0;
    	
    	for( String termFromDictionaryString : dictionary.getTerms() )
    	{
    		for( String termFromListString : termsListString )
    		{
    			if( termFromListString.equals(termFromDictionaryString) )
    			{
    				iTermExistCounter++;	
    			}
    		}
    		
    		oBagOfWordHashMap.put(termFromDictionaryString, (double) iTermExistCounter);
    		iTermExistCounter = 0;
    	}
    	
    	return oBagOfWordHashMap;
        
        //TODO: calculate bag-of-words representation - count how many times each term from dictionary.getTerms
        // exists in document
    }

    private Map<String, Double> calculateTfs(Map<String, Double> bagOfWords) {
        
        HashMap<String,Double> oTermTfsResultsHashMap = new HashMap<String,Double>();
        
        //TODO
        
        return oTermTfsResultsHashMap;
        //TODO: calculate TF representation - divide elements from bag-of-words by maximal value from this vector
    }

    private List<Double> calculateTfIds(Map<String, Double> tfs, Dictionary dictionary) {
        //TODO: calculate TF-IDF representation - multiply elements from tf representation my matching IDFs (dictionary.getIfs())
        //return results as list of tf-IDF values for terms in the same order as dictionary.getTerms()
        return new ArrayList<>();
    }

    public double calculateSimilarity(Document query) {
        return 0; //TODO: calculate cosine similarity between current document and query document (use calculated TF_IDFs)
    }
}
