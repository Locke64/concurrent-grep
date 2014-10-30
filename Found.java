import java.util.List;

// Found objects contain a String with the name of the file that was searched and a List<String> with one entry in the list for each matching line.
public class Found {

	private String filename;
	private List<String> matches;
	
	/*Constructor
	*
	*contains a string with name of file searched
	*contains List<String> with one entry in list for each matching line
	*/
	public Found(String filename, List<String> matches)
	{
		this.filename = filename;
		this.matches = matches;
	}
	
	public String getFileName()
	{
		return this.filename;
	}
	
	public List<String> getMatches(){
		return this.matches;
	}

}