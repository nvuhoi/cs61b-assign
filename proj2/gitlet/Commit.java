package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

/** Represents a gitlet commit object.
 *
 *  does at a high level.
 *
 *  @author Muse
 */
public class Commit implements Serializable {

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The logs directory to save commits. */
    public static final File LOGS_DIR = join(GITLET_DIR, "logs");
    /** The message of this Commit. */
    private String message;
    /** commit time. */
    private Date date;
    /** parent reference. */
    private String parent1;
    /** second parent reference for merges */
    private String parent2;
    /**  a mapping of file names to blob references. */
    private HashMap<String, String> blobs;

    /** first commit */
    public Commit() {
        this.parent1 = null;
        this.parent2 = null;
        this.message = "initial commit";
        this.blobs = null;
        this.date = new Date(0);
    }

    public Commit(String parent1, String message, HashMap<String, String> blobsMap) {
        this(parent1, null, message, blobsMap);
    }

    public Commit(String parent1, String parent2, String message, HashMap<String, String> blobsMap) {
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.message = message;
        this.blobs = blobsMap;
        this.date = new Date();
    }

    /** Serialize commit as file. */
    public void saveCommit(String sha) {
        File commitFile = Utils.join(LOGS_DIR, sha);
        createFile(commitFile);
        writeObject(commitFile, this);
    }

    private static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
