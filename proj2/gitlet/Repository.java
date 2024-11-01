package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static gitlet.Utils.*;


/** Represents a gitlet repository.
 *
 *  does at a high level.
 *
 *  @author Muse
 */
public class Repository {

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The branch directory to save branch. */
    public static final File BRANCH_DIR = join(GITLET_DIR, "branches");
    /** The logs directory to save commits. */
    public static final File LOGS_DIR = join(GITLET_DIR, "logs");
    /** The staged directory to save stagedFile */
    public static final File STAGED_DIR = join(GITLET_DIR, "staged");
    /** The blobs directory to save files. */
    public static final File BLOB_DIR = join(GITLET_DIR, "blobs");


    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            exitGitlet();
        } else {
            GITLET_DIR.mkdir();
            BRANCH_DIR.mkdir();
            LOGS_DIR.mkdir();
            STAGED_DIR.mkdir();
            BLOB_DIR.mkdir();

            Commit firstCommit = new Commit();
            String firstCommitSha = Utils.sha1("firstCommit");
            firstCommit.saveCommit(firstCommitSha);
            File master = createBranch("master");
            changeBranchPoint(master, firstCommitSha);
            File HEAD = createBranch("HEAD");
            changeBranchPoint(HEAD, firstCommitSha);
        }
    }

    private static void changeBranchPoint(File pointer, String sha) {
        Utils.writeContents(pointer, sha);
    }

    private static File createBranch(String branchName) {
        File branch = Utils.join(BRANCH_DIR, branchName);
        createFile(branch);
        return branch;
    }

    /** Exit gitlet. */
    private static void exitGitlet() {
        System.exit(0);
    }

    /** Check whether init */
    private static void checkInit() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            exitGitlet();
        }
    }

    private static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** TODO: If the current working version of the file is identical
     *to the version in the current commit, do not stage it to be added, and
     *remove it from the staging area if it is already there
     */
    public static void add(String filename) {
        checkInit();

        File file = Utils.join(CWD, filename);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            exitGitlet();
        }

        File stagedList = join(STAGED_DIR, "stagedList");
        if (stagedList.exists()) {
            HashMap<String, String> fileMap = readObject(stagedList, HashMap.class);
            fileMap.put(fileNameSha(filename), fileSha(filename, file));
            writeObject(stagedList, HashMap.class);
            exitGitlet();
        } else {
            HashMap<String, String> fileMap = new HashMap<>();
            createFile(stagedList);
            fileMap.put(fileNameSha(filename), fileSha(filename, file));
            writeObject(stagedList, HashMap.class);
            exitGitlet();
        }
    }

    /** Return Sha-1 of filename. */
    public static String fileNameSha(String filename) {
        return sha1(filename);
    }

    /** Return Sha-1 of filename and contents. */
    public static String fileSha(String filename, File file) {
        return sha1(filename, readContents(file));
    }
}
