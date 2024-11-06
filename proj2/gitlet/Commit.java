package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import static gitlet.Utils.*;

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
    /** The commit record map */
    public static final File COMMIT = join(LOGS_DIR, "commitMap");
    /** The message of this Commit. */
    private String message;
    /** commit time. */
    private Date date;
    /** parent reference. */
    private String parent1;
    /** Second parent reference for merges. */
    private String parent2;
    /** A mapping of file names to blob references. */
    private HashMap<String, String> blobs;
    /** Commit's branch. */
    private String branch;

    /** first commit */
    public Commit() {
        this.parent1 = null;
        this.parent2 = null;
        this.message = "initial commit";
        this.blobs = new HashMap<>();
        this.date = new Date(0);
        this.branch = "master";
    }

    public String getParent1() {
        return parent1;
    }

    public String getParent2() {
        return parent2;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public HashMap<String, String> getBlobs() {
        return blobs;
    }

    public String getBranch() {
        return branch;
    }
    public Commit(String parent1, String message, HashMap<String, String> blobsMap, String branch) {
        this(parent1, null, message, blobsMap, branch);
    }

    public Commit(String parent1, String parent2, String message, HashMap<String, String> blobsMap, String branch) {
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.message = message;
        this.blobs = blobsMap;
        this.date = new Date();
        this.branch = branch;
    }

    /** Serialize commit as file. */
    public void saveCommit(String sha) {

        File commitFile = join(LOGS_DIR, sha);
        writeObject(commitFile, this);
        createFile(commitFile);

        HashMap<String, File> commitHashMap = readObject(COMMIT, HashMap.class);

        commitHashMap.put(sha, commitFile);
        writeObject(COMMIT, commitHashMap);
    }

    public String saveCommit() {
        String string = "";
        for (String i  : this.getBlobs().values()) {
            string += i;
        }
        if (parent2 != null) {
            string += parent2;
        }
        string += parent1;
        string += message;
        string += date.toString();
        string += branch;
        saveCommit(sha1(string));
        return sha1(string);
    }

    private static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
