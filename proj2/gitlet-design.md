# Gitlet Design Document

**Name**:Muse

## Classes and Data Structures

### Class Main

> 呼叫 gitlet 命令的地方，主要透過呼叫 Repository 的 method 來完成命令。

### Class Repository
> 處理處藏庫邏輯的地方，會創建各個資料夾處存訊息。

#### Fields
##### 1. public static final File CWD = new File(System.getProperty("user.dir"));
> /** The current working directory. */
##### 2. public static final File GITLET_DIR = join(CWD, ".gitlet");
> /** The .gitlet directory. */
##### 3. public static final File BRANCH_DIR = join(GITLET_DIR, "branches");
> /** The branch directory to save branch. */
##### 4. public static final File LOGS_DIR = join(GITLET_DIR, "logs");
> /** The logs directory to save commits. */
##### 5. public static void init()
> 初始化資料夾。
##### 6. private static void changeBranchPoint(File pointer, String sha)
> 改變分支指向。
##### 7. private static File createBranch(String branchName)
> 創造分支並處存在 Branches 資料夾，分支是包含 commitSha 的文件。
##### 8. private static void checkInit()
> 查看是否初始化過了。
##### 9. private static void createFile(File file)
> 創建真實文件 file
##### 10. private static void copyfile(File target, File file)
> 複製文件 file 的內容以字串形式寫入 target 。
##### 11. private static void exitGitlet()
> 離開 gitlet ，強制停止。
##### 12. public static void add(String filename)
>　執行 add 命令，將 filename 複製一份到 Stage 資料夾。
##### 13. public static final File BLOB_DIR = join(GITLET_DIR, "blobs");
> /** The blobs directory to save files. */

### Class Commit
> 有關commit事項的實現細節。
#### Fields

##### 1. public static final File CWD = new File(System.getProperty("user.dir"));
> /** The current working directory. */
##### 2. public static final File GITLET_DIR = join(CWD, ".gitlet");
> /** The .gitlet directory. */
##### 3. public static final File LOGS_DIR = join(GITLET_DIR, "logs");
> /** The logs directory to save commits. */
##### 4. instance variables
1. private String message;
2. private String parent1;
3. private String parent2;
4. private HashMap<String, String> blobs;
5. private Date date;
##### 5. public void saveCommit(String sha)
> /** Serialize commit as file. */
##### 6. private static void createFile(File file)
> 創建真實文件 file


## Algorithms

### init
會創建 .gitlet 資料夾處存跟 gitlet 有關的事項。

.gitlet 包含:

1. branches 資料夾處存分支
2. logs 資料夾處存 commit 資料
3. Staged 資料夾處存 staged 區域的文件
4. blobs 資料夾處存 commit file

branches 初始化後包含:
1. HEAD 指向 firstCommit
2. 分支 master 指向 firstCommit

logs 初始化後包含:
1. firstCommit

firstCommit 包含: 
1. Date = 00:00:00 UTC, Thursday, 1 January 1970 
2. message = "initial commit".

### add
先檢查是否初始化過了，再檢查文件是否存在，
迭代 Staged 資料夾內的文件名稱查看是否覆蓋，
若 staged 區不存在則複製一份 file 到 staged 區。
## Persistence

