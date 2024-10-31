package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.HashSet;

import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

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

    /* TODO: fill in the rest of this class. */

    /** first commit */
    public Commit() {
        this.parent1 = null;
        this.parent2 = null;
        this.message = "initial commit";
        this.blobs = null;
        this.date = new Date(0);
    }

    /** Serialize commit as file. */
    public void saveCommit(String sha) {
        File commitFile = Utils.join(LOGS_DIR, sha);
        try {
            commitFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeObject(commitFile, this);
    }
}
