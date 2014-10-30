import java.io.File;
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
		//TODO stub
		return null;
	}
}