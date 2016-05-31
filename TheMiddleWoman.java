package suggestions;

import java.sql.Time;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TheMiddleWoman {

	public QueryTrie theTrie = new QueryTrie();
	public int prevID = 0;
	public String prevQuery = "";
	public String prevDate = "";
	public String prevTime = "";
	
	
	public void pleaseAddIntoTrie(String query, String date, String time, int custID)
	{
		//System.out.println("please add");
		int modified = 0;
		String updatedQuery = stopAndStem(query);
		//System.out.println("1. Prev ID:"+ prevID);
		//System.out.println("2. Cur ID:"+custID);
		if(prevID == custID)
		{
			
			//System.out.println("1. Prev Date:"+ prevDate);
			//System.out.println("2. Cur Date:"+date);
			if(prevDate.equals(date))
			{
				//System.out.println("Same Day!");
				String timeArray[] = time.split(":");
				String hour = timeArray[0];
				String minute = timeArray[1];
				String timeArrayPrev[] = prevTime.split(":");
				String hourPrev = timeArrayPrev[0];
				String minutePrev = timeArrayPrev[1];
				if(hourPrev.equals(hour))
				{
					if(Integer.valueOf(minute)-Integer.valueOf(minutePrev)<=10)
					{
						//System.out.println("Within 10 minutes!");
						//Compare Query
						if(query.startsWith(prevQuery))
						{
							modified = 1;
						}
					}
				}
			}
		}
		//System.out.println(updatedQuery+ "!!!!!!!!!!!!!");
		if(updatedQuery != "")
		{
			theTrie.checkTheQuery(updatedQuery, 0, modified);
		}
		prevID = custID;
		prevQuery = query;
		prevDate = date;
		prevTime = time;
	}
	
	public void pleaseCheckforQueryInTrie(String query)
	{
		String updatedQuery = stopAndStem(query);
		theTrie.checkTheQuery(updatedQuery, 1, 0);//zero for modified because we are just looking at words not adding to
	}
	
	public String stopAndStem(String query)
	{
		String newQuery = "";
		Scanner scans = new Scanner(query);
		String curLine = scans.nextLine();
		StringTokenizer token = new StringTokenizer(curLine," ,\n\t\r.,?!;:\")(*&^%$#@-_'][");
		int i = 1;
		int j = 1;//checks whether it is the first word or not so you know whether to add a space.
		while(token.hasMoreTokens())
		{
			String curWord = token.nextToken();
			String curWordLower = curWord.toLowerCase();
			if(i == 1)
			{
				StopWords checkStopWords = new StopWords();
				if(!checkStopWords.contains(curWordLower))
				{
					newQuery += curWordLower;
					j++;
				}
				i++;
			}
			else
			{
				if(j==1)
				{
					newQuery += curWordLower;
					j++;
				}
				else
				{
					newQuery += " " + curWordLower;
				}
			}
		}
		//System.out.println(newQuery);
		return newQuery;
	}
	
}
