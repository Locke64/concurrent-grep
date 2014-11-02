import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// A FileFinder looks for the given pattern in the given file.
public class FileFinder extends Finder {

	// file to match the pattern against
	private File file;
	
	// create a new file finder to look for the given pattern in the given file
	public FileFinder( Pattern pattern, File file ) {
		super( pattern );
		this.file = file;
	}

	// execute the match
	public Found call() {
		ArrayList<String> matches = new ArrayList<String>();
		BufferedReader reader = null;
		int line = 0;
		try {
			reader = new BufferedReader(new FileReader(file));
			String input;
			while ( (input = reader.readLine()) != null ) {
				Matcher m = pattern.matcher(input);
				if ( pattern.toString().startsWith("^") && pattern.toString().endsWith("$") ) {
					if ( m.matches() )
						matches.add(line + " " + input);
				} else {
					if ( m.find() )
						matches.add(line + " " + input);
				}
				++line;
			}
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    try {
		        if (reader != null)
		            reader.close();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
		Found found = new Found(file.toString(), matches);
		return found;
	}
}