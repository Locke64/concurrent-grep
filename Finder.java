import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// A Finder looks for the given pattern in the given string.
// Finder also provides static methods to initiate the process with one or more Finder objects.
public class Finder implements Callable<Found> {

	// size of the ExecutorService fixed thread pool
	private static final int NUM_THREADS = 3;

	// pattern to match
	protected Pattern pattern;
	
	// input to match the pattern against
	private String input;

	// find the given pattern in the given input by creating an executing a callable Finder
	public static void find( Pattern pattern, String input ) {
		ExecutorService es = Executors.newFixedThreadPool( NUM_THREADS );
		Collection<Finder> finders = new ArrayList<Finder>( 1 ); // only need one finder, but must wrap it in a collection for the find interface
		finders.add( new Finder( pattern, input ) );
		try {
			executeSearch( es, finders );
		} catch( InterruptedException e ) {
			e.printStackTrace();
		} catch( ExecutionException e ) {
			e.printStackTrace();
		}
	}

	// find the given pattern in the given files by creating and executing a callable Finder for each file
	public static void find( Pattern pattern, Collection<File> files ) {
		ExecutorService es = Executors.newFixedThreadPool( NUM_THREADS );
		Collection<Finder> finders = new ArrayList<Finder>( files.size() );
		for( File f : files )
			finders.add( new FileFinder( pattern, f ) );
		try {
			executeSearch( es, finders );
		} catch( InterruptedException e ) {
			e.printStackTrace();
		} catch( ExecutionException e ) {
			e.printStackTrace();
		}
	}
	
	// execute the search with the given finders
	private static void executeSearch( Executor executor, Collection<Finder> finders ) throws InterruptedException, ExecutionException {
		CompletionService<Found> ecs = new ExecutorCompletionService<Found>( executor );
		for( Finder f : finders )
			ecs.submit( f );
		((ExecutorService) executor).shutdown();
		int n = finders.size();
		for( int i = 0; i < n; ++i ) {
			Found fd = ecs.take().get();
			if( fd != null )
				report( fd );
		}
	}
	
	// report the results of a found match
	private static void report( Found found ) {
		String filename = found.getFileName();
		if ( filename == "" )
			System.out.println("Results for input not in file");
		else
			System.out.println(found.getFileName());
		List<String> matches = found.getMatches();
		if ( matches.size() == 0 )
			System.out.println("No matches found");
		else {
			for( int a = 0; a < matches.size(); a++ )
				System.out.println(matches.get(a));
			System.out.println("");
		}
	}
	
	// create a new string finder to look for the given Pattern in the given input
	public Finder( Pattern pattern, String input ) {
		this( pattern );
		this.input = input;
	}
	
	// create a new finder to look for the given pattern
	// protected because a pattern is not enough - should only be used in another constructor which also specifies a search target
	protected Finder( Pattern pattern ) {
		this.pattern = pattern;
	}
	
	// execute the match
	public Found call() {
		ArrayList<String> matches = new ArrayList<String>();
		Matcher m = pattern.matcher(input);
		if ( pattern.toString().startsWith("^") && pattern.toString().endsWith("$") ) {
			if ( m.matches() )
				matches.add(input);
		} else {
			if ( m.find() )
				matches.add(input);
		}
		Found found = new Found("", matches);
		return found;
	}
}