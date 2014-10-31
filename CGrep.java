
import java.awt.List;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class CGrep 
{
	public static void main( String[] args ) 
	{
		
		if ( args.length < 2 )
		{
			System.out.println("Please input the correct format.");
		}
		else
		{
			 String pattern = args[0];
			 if ( args.length == 2 && !args[1].endsWith(".txt") )
			 {
				 Finder.find(Pattern.compile(args[0]), args[1]);
			 }
		//TODO
		//List<String> entry = new ArrayList<String>() ;
		}
		
	}  
}
