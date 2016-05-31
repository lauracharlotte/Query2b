package suggestions;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class QueryNode {

	int freq = 0;
	int mod = 0;
	int addedALetter = 0;
	int containsAnActualLetter = 0;
	String fullSentance = "";
	int maxFreq = 0;
	int maxMode = 0;
	String wholeSQQuery = "";
	ArrayList<RankAndString> allRanks = new ArrayList<RankAndString>();
	ArrayList<QueryNode> nextNode = new ArrayList<QueryNode>(27);
	
	public QueryNode()
	{

	}
	
	public FreqAndMode addOnLetter(String theSentance, int search, int modified)
	{
		FreqAndMode curFreqAndMode = new FreqAndMode();
		int result = 0;
		containsAnActualLetter = 1;
		if(addedALetter == 0)
		{
			for(int i = 0; i<27; i++)
			{
				nextNode.add(i, new QueryNode());
			}
			addedALetter =1;
		}
		//System.out.println(theSentance);
		int spot = 0;
		if(theSentance.length() != 0)
		{
			char curLetter = theSentance.charAt(0);
			//int spot = 0;
			if(curLetter == ' ')
			{
				spot = 26;
			}
			else
			{	
				spot = curLetter - 'a';
			}
		}
		////String newSentance = theSentance.substring(1);
		if(theSentance.length() == 0)////!!!!)
		{
			if(search == 1) //they were searching for a word not adding
			{
				if(freq != 0)
				{
					//ADD TO
					findTheOptions();
				}
				else
				{
					//System.out.println("the words were not here!!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
			}
			else
			{
				freq++;
				if(modified == 1)
				{
					mod++;
				}
				curFreqAndMode.setFrequency(freq);
				curFreqAndMode.setModify(mod);
			}
		}
		else
		{
			String newSentance = theSentance.substring(1);
			nextNode.get(spot).fullSentance = fullSentance;
			nextNode.get(spot).maxFreq = maxFreq;
			nextNode.get(spot).maxMode = maxMode;
			curFreqAndMode = nextNode.get(spot).addOnLetter(newSentance, search, modified);
			
		}

		return curFreqAndMode;
	}
	
	public void testBetweenTheOptions()
	{
		//nextNode.get
	}
	
	public void findTheOptions()
	{
		if(nextNode.get(26).containsAnActualLetter == 1)
		{
			String word2 = "";
			String word1 = fullSentance.substring(fullSentance.lastIndexOf(" ")+1);
			fullSentance = fullSentance.substring(fullSentance.lastIndexOf(" ")+1);
			nextNode.get(26).fullSentance = word1;
			allRanks = nextNode.get(26).scaryRecursiveStepToFindAllOptions(word2, 0);
			ArrayList<RankAndString> topEight = new ArrayList<RankAndString>();
			for(int i = 0; i< allRanks.size(); i++)
			{
				if(topEight.size()<9)
				{
					topEight.add(allRanks.get(i));
				}
				else
				{
					int smallest = 9;
					RankAndString smallestOne = new RankAndString();
					for(int j = 0; j<topEight.size(); j++)
					{
						if(j == 0)
						{
							smallest = j;
							smallestOne = topEight.get(j);
						}
						else if(topEight.get(j).getRank()<smallestOne.getRank())
						{
							smallest = j;
							smallestOne= topEight.get(j);
						}
					}
					if(allRanks.get(i).getRank() > smallestOne.getRank())
					{
						topEight.add(smallest, allRanks.get(i));
					}
				}
			}
			for(int m = 0; m<topEight.size(); m++)
			{
				System.out.println("The SQ: " + topEight.get(m).getTheSQ());
				System.out.println("TheRank:" + topEight.get(m).getRank());
			}
		}
	}
	
	public ArrayList<RankAndString> scaryRecursiveStepToFindAllOptions(String word2, int spaceHappened)
	{
		if(mod > 0)
		{
			//DO ALL THE CALCULATIONS AND SOMEHOW RETURN
			RankAndString curRank = new RankAndString();
			curRank.setRank(makeTheRankCalculanke(fullSentance,word2));
			curRank.setTheSQ(wholeSQQuery);
			allRanks.add(curRank);
			
		}
		else
		{
			for(int i = 0; i< nextNode.size(); i++)
			{
				int wentPastASpace = 0;
				if(nextNode.get(i).containsAnActualLetter == 1)
				{
					if(i != 26 && spaceHappened !=1)
					{
						int b = i + 'a';
						////word2 += Character.toString((char)b);
						//System.out.println(word2);
						nextNode.get(i).fullSentance = fullSentance;
						nextNode.get(i).wholeSQQuery = wholeSQQuery + Character.toString((char)b);
						ArrayList<RankAndString> oldRanks = nextNode.get(i).scaryRecursiveStepToFindAllOptions(word2+Character.toString((char)b), spaceHappened);
						for(int p = 0; p<oldRanks.size(); p++)
						{
							allRanks.add(oldRanks.get(p));
						}
					}
					else
					{
						int b = i + 'a';
						spaceHappened = 1;
						nextNode.get(i).fullSentance = fullSentance;
						if(i== 26)
						{
							nextNode.get(i).wholeSQQuery = wholeSQQuery + " ";
						}
						else
						{
							nextNode.get(i).wholeSQQuery = wholeSQQuery + Character.toString((char)b);
						}
						ArrayList<RankAndString> oldRanks = nextNode.get(i).scaryRecursiveStepToFindAllOptions(word2, spaceHappened);
						for(int p = 0; p<oldRanks.size(); p++)
						{
							allRanks.add(oldRanks.get(p));
						}
					}
				}
			}
		}
		return allRanks;
	}
	
	public double makeTheRankCalculanke(String word1, String word2)
	{
		double wcfRank = theWCFCalculator(word1, word2)/0.01;
		double normalizedFreq = (double)freq/(double)maxFreq;
		double normalizedMod = (double)mod/(double)maxMode;
		double divideAllBy = 0;
		if(normalizedFreq <= normalizedMod)
		{
			if(normalizedFreq<=wcfRank)
			{
				divideAllBy = normalizedFreq;
			}
			else
			{
				divideAllBy = wcfRank;
			}
		}
		else
		{
			if(normalizedMod<=wcfRank)
			{
				divideAllBy = normalizedMod;
			}
			else
			{
				divideAllBy = wcfRank;
			}
		}
		
		double theRANK = (normalizedFreq + normalizedMod + wcfRank)/(1-divideAllBy);
		return theRANK;
	}
	
	public double theWCFCalculator(String word1, String word2)
	{
		PorterStemmer theStemmer = new PorterStemmer();
		Double val = 0.0;
		try{
			String w1 = word1; //stemmed version of a word
			String w2 = word2; //stemmed version of a word
			
			if(word1.length()>2)
			{
				w1 = theStemmer.stem(word1); //stemmed version of a word
			}
			if(word2.length()>2)
			{
				w2 = theStemmer.stem(word2); //stemmed version of a word
			}

			String myURL = "http://peacock.cs.byu.edu/CS453Proj2/?word1="+w1+"&word2="+w2;

			//System.out.println("Fetching content: "+myURL);

			Document pageDoc = Jsoup.connect(myURL).get();
			String htmlContent = pageDoc.html();		
			Document contentDoc = Jsoup.parse(htmlContent);
			String contentVal = contentDoc.body().text();
			
			//System.out.println(contentVal);

			val= Double.parseDouble(contentVal);
			
			//System.out.println(val);
			
			if(val == -1.0)
			{
				val = 0.0;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	public int getMod() {
		return mod;
	}
	public void setMod(int mod) {
		this.mod = mod;
	}
	
	
}
