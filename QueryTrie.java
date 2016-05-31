package suggestions;

public class QueryTrie {
	QueryNode root = new QueryNode();
	int maxFreq = 0;
	int maxMode = 0;
	
	public void checkTheQuery(String query, int search, int modified)
	{
		FreqAndMode theFreqAndMode = new FreqAndMode();
		root.fullSentance = query;
		root.maxFreq = maxFreq;
		root.maxMode = maxMode;
		theFreqAndMode = root.addOnLetter(query, search, modified);
		
		if(theFreqAndMode.getFrequency()>maxFreq)
		{
			maxFreq = theFreqAndMode.getFrequency();
		}
		if(theFreqAndMode.getModify()>maxMode)
		{
			maxMode = theFreqAndMode.getModify();
		}
	}
	
}
