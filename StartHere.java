package suggestions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StartHere {
	public static void main(String [] args){
		System.out.println("Hello I'm working");
		
		//Open all files, this goes through each of your files
		File allFiles = new File("docs/");
		File[] listOfFiles = allFiles.listFiles();
		int tester = 0;
		TheMiddleWoman middleWoman = new TheMiddleWoman();
		for(File curFile: listOfFiles)
		{
			//System.out.println("hello?");
			try {
				Scanner scans = new Scanner(curFile);
				int firstSixWordSkip = 0;
				while(scans.hasNext())
				{
					if(firstSixWordSkip >2)
					{
						//System.out.println("not in skipped");
					int curID =0;
					String query = "";
					String date = "";
					String time = "";
					if(scans.hasNextInt())
					{
						curID = scans.nextInt();
						//System.out.println("ID:");
						//System.out.println(curID);
					}
					int i = 1;
					while(scans.hasNext())
					{
						String curentSpot = scans.next();
						//System.out.println(curentSpot);
						if(curentSpot.startsWith("2006-"))
						{	
							date = curentSpot;
							//System.out.println("Date:");
							//System.out.println(date);
							break;
						}
						else
						{
							if(i==1)
							{
								query += curentSpot;
							}
							else
							{
								query += " " + curentSpot;
							}
						}
						i++;
						//middleWoman.pleaseAddIntoTrie(query, date, time, curID);
						
					}
					
					//System.out.println("Query:");
					//System.out.println(query);
					time = scans.next();
					//System.out.println("Time:");
					//System.out.println(time);
					middleWoman.pleaseAddIntoTrie(query, date, time, curID);
				}
					else
					{
						//System.out.println(+"!!!!!!!!!!!!");
						scans.next();
						firstSixWordSkip++;
						//scans.next();
					}
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String answer = "";
		while(!answer.equals("-1"))
		{
			System.out.println("Hello! Please type in what you would like to search for");
			Scanner user = new Scanner(System.in);
			answer = user.nextLine();
			middleWoman.pleaseCheckforQueryInTrie(answer);
		}
		
		
		
		
		
		
		
		//TESTING TRIE
		String trieTest = "hello laura";
		String trieTest2 = "A bunny SAID hello!!";
		String trieTest3 = "today is a good day";
		String trieTest4 = "A happy owl is a good owl";
		String trieTest5 = "hello Laura";
		/*middleWoman.pleaseAddIntoTrie(trieTest, "a", "b", 3);
		middleWoman.pleaseCheckforQueryInTrie(trieTest);
		middleWoman.pleaseCheckforQueryInTrie(trieTest3);
		middleWoman.pleaseCheckforQueryInTrie(trieTest3);
		middleWoman.pleaseCheckforQueryInTrie(trieTest5);
		
		//System.out.println(middleWoman.pleaseAddIntoTrie(trieTest2, "a", "b", 3));
		//System.out.println(middleWoman.pleaseAddIntoTrie(trieTest3,"a", "b", 3));
		//System.out.println(middleWoman.pleaseAddIntoTrie(trieTest4,"a", "b", 3));
		middleWoman.pleaseAddIntoTrie(trieTest2,"a", "b", 3);
		middleWoman.pleaseAddIntoTrie(trieTest3,"a", "b", 3);
		middleWoman.pleaseAddIntoTrie(trieTest4,"a", "b", 3);*/
		String time = "12:33:15";
		String timeArray[] = time.split(":");
		String hour = timeArray[0];
		String minute = timeArray[1];
		String minute2 = "20";
		if(Integer.valueOf(minute)-Integer.valueOf(minute2)<=10)
		{
			//System.out.println("Within 10 minutes!");	
		}
		System.out.println(hour);
		System.out.println(minute);
		System.out.println("silver pure metal pellet");
		middleWoman.pleaseCheckforQueryInTrie("silver pure metal pellet");
		System.out.println("merit release appearance");
		middleWoman.pleaseCheckforQueryInTrie("merit release appearance");
		System.out.println("cam");
		middleWoman.pleaseCheckforQueryInTrie("cam");
		System.out.println("magic tilt trailer");
		middleWoman.pleaseCheckforQueryInTrie("magic tilt trailer");
		System.out.println("atomic bomb");
		middleWoman.pleaseCheckforQueryInTrie("atomic bomb");
		System.out.println("atomic bomb cloud");
		middleWoman.pleaseCheckforQueryInTrie("atomic bomb cloud");
		//middleWoman.pleaseCheckforQueryInTrie(query);
		System.out.println("taptap");
		middleWoman.pleaseCheckforQueryInTrie("taptap");
	}
}
