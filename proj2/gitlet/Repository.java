package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
    /** Prepare commit file director. */
    public static final File PREPAREDCOMMIT_DIR = join(BLOB_DIR, "prepareCommit");
    /** Prepare remove fileSet. */
    public static final File REMOVE = join(STAGED_DIR, "prepareRemove");

    public static void init() {
        if (GITLET_DIR.exists()) {
            throw error("A Gitlet version-control system already exists in the current directory.");
        } else {
            GITLET_DIR.mkdir();
            BRANCH_DIR.mkdir();
            LOGS_DIR.mkdir();
            STAGED_DIR.mkdir();
            BLOB_DIR.mkdir();
            PREPAREDCOMMIT_DIR.mkdir();

            createFile(BLOBS);
            createFile(STAGING);
            createFile(COMMIT);
            createFile(REMOVE);

            HashMap<String, File> blogsMap = new HashMap<>();
            writeObject(BLOBS, blogsMap);

            HashMap<String, String> fileMap = new HashMap<>();
            writeObject(STAGING, fileMap);

            HashMap<String, File> commitHashMap = new HashMap<>();
            writeObject(COMMIT, commitHashMap);

            HashSet<String> removeSet = new HashSet<>();
            writeObject(REMOVE, removeSet);

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
            throw error("Not in an initialized Gitlet directory.");
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


    public static void add(String filename) {
        checkInit();

        File file = Utils.join(CWD, filename);
        if (!file.exists()) {
            throw error("File does not exist.");
        }

        checkTheCommitVersion(filename, fileSha(filename, file));

        File fileSendToBlobs = join(PREPAREDCOMMIT_DIR, filename);
        copyfile(fileSendToBlobs, file);
        createFile(fileSendToBlobs);

        putKeyValueInStagedMap(fileNameSha(filename), fileSha(filename, file));
        exitGitlet();
    }

    /** Put key and value in stagedMap */
    private static void putKeyValueInStagedMap(String fileNameSha, String fileSha) {
        HashMap<String, String> fileMap = getStagedMap();
        fileMap.put(fileNameSha, fileSha);
        writeObject(STAGING, fileMap);
    }

    /** Check the file version in HEAD commit. If equals to add file, refuse adding,
     *  then check stagedMap version of the file.
     */
    private static void checkTheCommitVersion(String filename, String fileSha) {
        HashMap<String, String> headBlobsMap = getHeadCommitBlobsMap();
        if (headBlobsMap.containsValue(fileSha)) {
            removeStagedMapKey(fileNameSha(filename));
            join(PREPAREDCOMMIT_DIR, filename).delete();
            exitGitlet();
        }
    }

    /** Remove key from stagedMap. */
    private static void removeStagedMapKey(String key) {
        HashMap<String, String> stagedMap = getStagedMap();
        stagedMap.remove(key);
        writeObject(STAGING, stagedMap);
    }

    /** Return fileMap in STAGING. */
    private static HashMap<String, String> getStagedMap() {
        return readObject(STAGING, HashMap.class);
    }

    /** Return Sha-1 of filename. */
    private static String fileNameSha(String filename) {
        return sha1(filename);
    }

    /** Return Sha-1 of filename and contents. */
    private static String fileSha(String filename, File file) {
        return sha1(filename, readContents(file));
    }

    public static void commit(String commitMessage) {
        checkInit();
        if (StagedMapIsClear()) {
            throw Utils.error("No changes added to the commit.");
        }

        HashMap<String, String> newBlobsMap = createNewCommitBlobsMap();
        Commit newCommit = new Commit (getHeadCommitSha(), commitMessage, newBlobsMap, getHeadCommitBranch());
        String newCommitSha = newCommit.saveCommit();

        changePrepareCommit_DirFilesPathToBlobs_Dir();

        changeBranchPoint(HEAD, newCommitSha);
        changeBranchPoint(join(BRANCH_DIR, getHeadCommitBranch()), newCommitSha);
        cleanStagedMap();
        exitGitlet();
    }
    /** Change PrepareCommit_Dir Files' Path To Blobs_Dir and update blobsMap. */
    private static void changePrepareCommit_DirFilesPathToBlobs_Dir() {
        HashMap<String, File> blobsMap = getBlobsMap();
        for (String i : plainFilenamesIn(PREPAREDCOMMIT_DIR)) {

            File file = join(PREPAREDCOMMIT_DIR, i);
            String filesha = fileSha(i, join(PREPAREDCOMMIT_DIR, i));

            file.renameTo(join(BLOB_DIR, filesha));
            blobsMap.put(filesha, join(BLOB_DIR, filesha));

        }
        writeObject(BLOBS, blobsMap);
    }
    /** add HashMap to TargetMap, if targetMap have same key, replace that value. And return the new HashMap. */
    private static HashMap<String, String> mergeTwoMapToTarget(HashMap<String, String> targetMap, HashMap<String, String> hashMap) {
        for (String i : hashMap.keySet()) {
            targetMap.put(i, hashMap.get(i));
        }
        return targetMap;
    }
    /** Create new commit blobsMap */
    private static HashMap<String, String> createNewCommitBlobsMap() {
        HashMap<String, String> newBlobsMap = mergeTwoMapToTarget(getHeadCommitBlobsMap(), getStagedMap());
        if (! (getRemoveSet().isEmpty())) {
            for (String i : getRemoveSet()) {
                newBlobsMap.remove(i);
            }
        }
        clearRemoveSet();
        return newBlobsMap;
    }

    /** get Sha-1 commit. */
    private static Commit getCommit (String sha) {
        HashMap<String, File> commitHashMap = readObject(COMMIT, HashMap.class);
        return readObject(commitHashMap.get(sha), Commit.class);
    }
    /** get HEAD commit's Sha-1. */
    private static String getHeadCommitSha() {
        return readContentsAsString(HEAD);
    }
    /** get head commit. */
    private static Commit getHeadCommit() {
        return getCommit(getHeadCommitSha());
    }
    /** get head commit blobsMap. */
    private static HashMap<String, String> getHeadCommitBlobsMap() {
        Commit headCommit = getHeadCommit();
        return headCommit.getBlobs();
    }
    /** get blobsMap. */
    private static HashMap<String, File> getBlobsMap() {
        return readObject(BLOBS, HashMap.class);
    }
    /** Clean staging area */
    private static void cleanStagedMap() {
        HashMap<String, String> Map = getStagedMap();
        Map.clear();
        writeObject(STAGING, Map);
    }
    /** Check whether stagedMap is clear */
    private static boolean StagedMapIsClear() {
        return getStagedMap().isEmpty();
    }

    /** Get prepareRemoveSet. */
    private static HashSet<String> getRemoveSet() {
        return readObject(REMOVE, HashSet.class);
    }
    /** Get branch of HEAD commit. */
    private static String getHeadCommitBranch() {
        Commit headCommit = getHeadCommit();
        return headCommit.getBranch();
    }

    /** Add fileNameSha of filename in REMOVE. */
    private static void addFileInRemove(String filename) {
        HashSet<String> removeSet = getRemoveSet();
        removeSet.add(fileNameSha(filename));
        writeObject(REMOVE, removeSet);
    }

    /** Clear REMOVE */
    private static void clearRemoveSet() {
        HashSet<String> set = getRemoveSet();
        set.clear();
        writeObject(REMOVE, set);
    }
    public static void rm(String filename) {
        checkInit();
        if (getHeadCommitBlobsMap().containsKey(fileNameSha(filename))) {
            File file1 = join(CWD, filename);
            restrictedDelete(file1);
        } else if (getStagedMap().containsKey(fileNameSha(filename))) {
            ;
        } else {
            throw error("No reason to remove the file.");
        }

        addFileInRemove(filename);
        removeStagedMapKey(fileNameSha(filename));

        File file = join(PREPAREDCOMMIT_DIR, filename);
        file.delete();

        exitGitlet();
    }

    public static void log() {
        checkInit();
        printLogHistoryFirstParent();
        exitGitlet();
    }

    /** Print log history of parent1. */
    private static void printLogHistoryFirstParent() {

        Commit commit = getHeadCommit();
        String commitId = getHeadCommitSha();

        while (commit.getParent1() != null) {
            System.out.println("===");

            String string1 = "commit " + commitId;
            System.out.println(string1);

            Date currentDate = commit.getDate();
            Formatter formatter = new Formatter(Locale.US);
            formatter.format("%ta %tb %te %tT %tY %tz", currentDate, currentDate, currentDate, currentDate, currentDate, currentDate);
            String string2 = "Date: " + formatter.toString();
            System.out.println(string2);

            System.out.println(commit.getMessage());
            System.out.println();

            commitId = commit.getParent1();
            commit = getCommit(commit.getParent1());
        }
        printFirstCommit(commit, commitId);
    }

    private static void printFirstCommit(Commit firstCommit, String firstCommitId) {
        System.out.println("===");

        String string1 = "commit " + firstCommitId;
        System.out.println(string1);

        Date currentDate = firstCommit.getDate();
        Formatter formatter = new Formatter(Locale.US);
        formatter.format("%ta %tb %te %tT %tY %tz", currentDate, currentDate, currentDate, currentDate, currentDate, currentDate);
        String string2 = "Date: " + formatter.toString();
        System.out.println(string2);

        System.out.println(firstCommit.getMessage());
        System.out.println();
    }
}
