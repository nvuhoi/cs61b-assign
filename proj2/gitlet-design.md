# Gitlet Design Document

**Name**:Muse

## Classes and Data Structures

### Class Main

呼叫 gitlet 命令的地方，主要透過呼叫 Repository 的 method 來完成命令。

### Class Repository

處理處藏庫邏輯的地方，會創建各個資料夾處存訊息。

#### Fields

##### 1.public static final File CWD = new File(System.getProperty("user.dir"));

/** The current working directory. */

##### 2.public static final File GITLET_DIR = join(CWD, ".gitlet");

/** The .gitlet directory. */

##### 3.public static final File BRANCH_DIR = join(GITLET_DIR, "branches");

/** The branch directory to save branch. */

##### 4.public static final File LOGS_DIR = join(GITLET_DIR, "logs");

/** The logs directory to save commits. */

##### 5.public static void init()

初始化資料夾。

##### 6.private static void changeBranchPoint(File pointer, String sha)

改變分支指向。

##### 7.private static File createBranch(String branchName)

創造分支並處存在 Branches 資料夾，分支是包含 commitSha 的文件。

### Class Commit

有關commit事項的實現細節。

#### Fields

##### 1.public static final File CWD = new File(System.getProperty("user.dir"));

/** The current working directory. */

##### 2.public static final File GITLET_DIR = join(CWD, ".gitlet");

/** The .gitlet directory. */

##### 3.public static final File LOGS_DIR = join(GITLET_DIR, "logs");

/** The logs directory to save commits. */

##### 4.instance variables
1. private String message;
2. private String parent1;
3. private String parent2;
4. private HashSet<String> blobs;
5. private Date date;

##### 5.public void saveCommit(String sha)

/** Serialize commit as file. */


## Algorithms

### init

會創建 .gitlet 資料夾處存跟 gitlet 有關的事項，初始化過程包含了創建
branches 資料夾處存分支，也創建了 logs 資料夾處存 commit 資料。

另外創建了指針 HEAD 、 分支 master 指向了 firstCommit。

firstCommit 包含 Date = 00:00:00 UTC, Thursday, 1 January 1970
. message = "initial commit".

## Persistence

