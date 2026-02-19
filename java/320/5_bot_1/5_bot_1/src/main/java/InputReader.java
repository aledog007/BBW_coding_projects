import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Class InputReader reads typed text input from the standard text terminal. The text
 * typed by a user is then chopped into words, and a set of words is provided.
 * 
 * @author     Michael Kolling and David J. Barnes
 * @version    1.0  (31.Oct.2001)
 */
public class InputReader
{
    private BufferedReader reader;

    /**
     * Create a new InputReader that reads text from the text terminal.
     */
    public InputReader()
    {
        reader = new BufferedReader(new InputStreamReader(System.in));;
    }

    /**
     * Read a line of text from standard input (the text terminal), and return it
     * as a set of words.
     *
     * @return  A set of Strings, where each String is one of the words typed by the user
     */
    public String getInput() 
    {
        System.out.print("> ");                // print prompt
        String inputLine = readInputLine();

        return inputLine;
    }

    /**
     * Read one line of input and return it as a String. 
     *
     * @return  A Strings representing the input, or an empty String if an error occurs.
     */
    private String readInputLine()
    {
        String line = "";

        try {
            line = reader.readLine();
        }
        catch(java.io.IOException exc) {
            System.out.println ("There was an error during reading: " + exc.getMessage());
        }
        return line;
    }
}
