import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;


public class CGrep {
	public static void main( String[] args ) {
		
		if ( args.length < 2 )
			System.out.println("Please input the correct format.");
		else {
			 if ( args.length == 2 && !args[1].endsWith(".txt") )
				 Finder.find(Pattern.compile(args[0]), args[1]);
			 else {
				 Collection<File> files = new ArrayList<File>();
				 for( int a = 1; a < args.length; a++ )
					 files.add(new File(args[a]));
				 Finder.find(Pattern.compile(args[0]), files);
			 }
		}
		
	}  
}
