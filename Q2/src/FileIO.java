import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileIO {
    /**
     * Reads the file at the given path and returns contents of it in a string
     * array.
     *
     * @param path              Path to the file that is going to be read.
     * @param discardEmptyLines If true, discards empty lines with respect to trim;
     *                          else, it takes all the lines from the file.
     * @param trim              Trim status; if true, trims (strip in Python) each
     *                          line; else, it leaves each line as-is.
     * @return Contents of the file as a string array, returns null if there is not
     *         such a file or this program does not have sufficient permissions to
     *         read that file.
     */
    public static String[] readFile(String path, boolean discardEmptyLines, boolean trim) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path)); // Gets the content of file to the list.
            if (discardEmptyLines) { // Removes the lines that are empty with respect to trim.
                lines.removeIf(line -> line.trim().equals(""));
            }
            if (trim) { // Trims each line.
                lines.replaceAll(String::trim);
            }
            return lines.toArray(new String[0]);
        } catch (IOException e) { // Returns null if there is no such a file.
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads the file at the given path and returns contents of it in a string
     * array.
     *
     * @param path              Path to the file that is going to be read.
     * @param discardEmptyLines If true, discards empty lines with respect to trim;
     *                          else, it takes all the lines from the file.
     * @param trim              Trim status; if true, trims (strip in Python) each
     *                          line; else, it leaves each line as-is.
     * @return Contents of the file as a string array, returns null if there is not
     *         such a file or this program does not have sufficient permissions to
     *         read that file.
     */
    public static void writeFile(String path, String content, boolean append, boolean newLine) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(path, append));
            ps.print(content + (newLine ? "\n" : ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) { // Flushes all the content and closes the stream if it has been successfully
                              // created.
                ps.flush();
                ps.close();
            }
        }
    }
}
