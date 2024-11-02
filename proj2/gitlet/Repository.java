package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

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
    /** The HEAD pointer file */
    public static final File HEAD = join(BRANCH_DIR, "HEAD");
    /** The commit record map */
    public static final File COMMIT = join(LOGS_DIR, "commitMap");
    /** The blobs file save blogMap */
    public static final File BLOBS = join(BLOB_DIR, "blobsMap");
    /** The stagedMap */
    public static final File STAGING = join(STAGED_DIR, "stagedMap");


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
            createFile(BLOBS);
            createFile(STAGING);
            createFile(COMMIT);

            HashMap<String, File> blogsMap = new HashMap<>();
            writeObject(BLOBS, blogsMap);

            HashMap<String, String> fileMap = new HashMap<>();
            writeObject(STAGING, fileMap);

            HashMap<String, File> commitHashMap = new HashMap<>();
            writeObject(COMMIT, commitHashMap);

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

    /** Copy contents of file as String to target. */
    private static void copyfile(File target, File file) {
        writeContents(target, readContentsAsString(file));
    }

    /** TODO: If the current working version of the file is identical
     *  to the version in the current commit, do not stage it to be added, and
     *  remove it from the staging area if it is already there
     */
    public static void add(String filename) {
        checkInit();

        File file = Utils.join(CWD, filename);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            exitGitlet();
        }

        File fileSendToBlobs = join(BLOB_DIR, filename);
        copyfile(fileSendToBlobs, file);
        createFile(fileSendToBlobs);

        HashMap<String, String> fileMap = getStagedMap();
        fileMap.put(fileNameSha(filename), fileSha(filename, file));
        writeObject(STAGING, fileMap);
        exitGitlet();
    }

    /** return fileMap in STAGING */
    public static HashMap<String, String> getStagedMap() {
        return readObject(STAGING, HashMap.class);
    }

    /** Return Sha-1 of filename. */
    public static String fileNameSha(String filename) {
        return sha1(filename);
    }

    /** Return Sha-1 of filename and contents. */
    public static String fileSha(String filename, File file) {
        return sha1(filename, readContents(file));
    }

    public static void commit(String commitMessage) {
        checkInit();

        HashMap<String, String> headCommitBlobs = getHeadCommitBlobsMap();


    }

    /** get Sha-1 commit. */
    public static Commit getCommit (String sha) {
        HashMap<String, File> commitHashMap = readObject(COMMIT, HashMap.class);
        return readObject(commitHashMap.get(sha), Commit.class);
    }
    /** get head commit. */
    public static Commit getHeadCommit() {
        String headSha = readContentsAsString(HEAD);
        return getCommit(headSha);
    }
    /** get head commit blobsMap */
    public static HashMap<String, String> getHeadCommitBlobsMap() {
        Commit headCommit = getHeadCommit();
        return headCommit.getBlobs();
    }
}
