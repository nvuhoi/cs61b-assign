package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

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


    /* TODO: fill in the rest of this class. */
    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        } else {
            GITLET_DIR.mkdir();
            BRANCH_DIR.mkdir();
            LOGS_DIR.mkdir();
            STAGED_DIR.mkdir();

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
        try {
            branch.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return branch;
    }

    /** TODO: If the current working version of the file is identical
     *to the version in the current commit, do not stage it to be added, and
     *remove it from the staging area if it is already there
     */
    public static void add(String filename) {
        File file = Utils.join(CWD, filename);
        String fileContentsSha = Utils.sha1(readContents(file));
        for (String i : plainFilenamesIn(STAGED_DIR)) {
            if (i == filename) {
                File fileOverwrite = Utils.join(STAGED_DIR, filename);
                Utils.writeContents(fileOverwrite, Utils.readContentsAsString(file));
                try {
                    fileOverwrite.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        }
        File fileCopy = Utils.join(STAGED_DIR, filename);
        Utils.writeContents(fileCopy, Utils.readContentsAsString(file));
        try {
            fileCopy.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
