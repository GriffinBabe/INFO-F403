package util;

import java.io.*;

/**
 * A simple file writer.
 * Write the latex source with the {@link #write(String)} method.
 */
public class LatexWriter {

    /**
     * Path where to output the latex file.
     */
    private final String path;

    /**
     * Base constructor.
     * @param path, the path where to output the latex file.
     */
    public LatexWriter(String path) {
        this.path = path;
    }

    /**
     * Writes the given texSource to the file.
     * @param texSource, the tex source code to write.
     */
    public void write(String texSource) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(texSource);
            writer.close();
        }
        catch (IOException e) {
            System.err.println("Couldn't write LaTeX file: " + e.toString());
        }

    }
}
