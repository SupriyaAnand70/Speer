


	import java.io.IOException;
	import java.util.HashSet;
	import java.util.Iterator;
	import java.util.List;
	import java.util.Scanner;
	import java.util.Set;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;

	import io.github.bonigarcia.wdm.WebDriverManager;
	import io.restassured.RestAssured;
	import io.restassured.response.Response;
	import io.restassured.specification.RequestSpecification;

	public class VisitWikiLinks {
		
		static String link="";
		static int n;
		
		public static void inputLink()
		{
			//Input Link
					System.out.println(" Enter a wikipedia link");
					Scanner s1= new Scanner(System.in);
					link= s1.next();
					//System.out.println("The entered link is   - " + link);
					
					// checking valid Wikipedia  link
					try {
						
					
						if (checkWikiLink(link))
						{
							System.out.println("valid Wiki page");
						}
						else
						{
							 System.out.println("Not a valid Wikipedia link!!! Enter again");
							inputLink();
							
						}
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					
					
					
					
					
		}
		
		public static boolean checkWikiLink(String l) throws IOException
		 {
			 //Rest assured send request and check response status 
			// if status response ok return true 
			
		
			int statusCode;
			if ( l.contains(".wikipedia.org/wiki/") && !l.contains("#"))
			{
			
					// Specify the base URL to the RESTful web service
					RestAssured.baseURI =l;
					// Get the RequestSpecification of the request to be sent to the server
					RequestSpecification httpRequest = RestAssured.given();
					Response response = httpRequest.get();

					// Get the status code of the request. 
					//If request is successful, status code will be 200
					 statusCode = response.getStatusCode();
				
					 
					 
					 if (statusCode==200)
					 {
						 //Print the valid Wikipedia page links
						 //System.out.println("Valid Wiki Link " + l);
						 return true;
					 }
					 else
						  return false;
					 
			  
	  
				}
			else
				return false;
			
			
		
			 
			 
		 }
		public static void main(String[] args) {
			
			//Function for accepting input
			inputLink();
			
		int count=0;
					
			//Input Number for iterations
			System.out.println("Enter  a number between 1-20 ");
			
			Scanner s=new Scanner(System.in);
			n=s.nextInt();
			System.out.println("The number entered is " + n);
			
					
			  WebDriverManager.chromedriver().setup();
			  
			 // Headless execution without opening browser instance
			 /**********************/
			  ChromeOptions options= new ChromeOptions();
			  options.setHeadless(true);
			  WebDriver driver= new  ChromeDriver(options);
			  
			  /**********************/
			  
				
			//  WebDriver driver= new  ChromeDriver();
			  
			  //Store all unique (non-repeating) links in FinalSet 
			  Set<String> FinalSet = new HashSet<String>();
			  
			//Web driver opens wiki page, collects all anchor tags (a) in list of WebElements
			  
			  driver.get(link );
			  List<WebElement> linkslist= driver.findElements(By.tagName("a"));
			  
			  //Iterate and count the number of links  using list Iterator
			
				  Iterator<WebElement> itr= linkslist.iterator();
				 
				
			  
					 try {
					  while(itr.hasNext())
					  {
						  String anchorTag;
						  anchorTag=itr.next().getAttribute("href");
						  
					//check and store valid Wikipedia page links in data structure/csv file		
						if (anchorTag!= null && checkWikiLink(anchorTag))
						{
						  
							//Store link in Main data structure-Set for non repeating unique items
							
							FinalSet.add(anchorTag);
						    count++;
						  
						}
						  
					  }
					 }
					 catch (IOException e)
						{
							e.printStackTrace();
						}
			  
			  System.out.println("Number of Wiki links   "+ count );
			  
			  
			  
			  for (String finalLink:FinalSet)
			  {
			  System.out.println(finalLink);
			  }
			  //Store Count in CSV file
			  
			  
		}
		 
	}

