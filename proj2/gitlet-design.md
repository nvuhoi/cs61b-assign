# Gitlet Design Document

**Name**:Muse

## Classes and Data Structures

### Class Main

> 呼叫 gitlet 命令的地方，主要透過呼叫 Repository 的 method 來完成命令。

### Class Repository
> 處理處藏庫邏輯的地方，會創建各個資料夾儲存訊息。

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
> 創造分支並儲存在 Branches 資料夾，分支是包含 commitSha 的文件。
##### 8. private static void checkInit()
> 查看是否初始化過了。
##### 9. private static void createFile(File file)
> 創建真實文件 file
##### 10. private static void copyfile(File target, File file)
> 複製文件 file 的內容以字串形式寫入 target 。
##### 11. private static void exitGitlet()
> 離開 gitlet ，強制停止。
##### 12. public static void add(String filename)
> add 命令。
##### 13. public static final File BLOB_DIR = join(GITLET_DIR, "blobs");
> /** The blobs directory to save files. */
##### 14. public static final File STAGED_DIR = join(GITLET_DIR, "staged");
> /** The staged directory to save stagedFile */
##### 15. public static final File HEAD = join(BRANCH_DIR, "HEAD");
> /** The HEAD pointer file */
##### 16. public static final File COMMIT = join(LOGS_DIR, "commitMap");
> /** The commit record map */
##### 17. public static final File BLOBS = join(BLOB_DIR, "blobsMap");
> /** The blobs file save blogMap */
##### 18. public static final File STAGING = join(STAGED_DIR, "stagedMap");
> /** The stagedMap */
##### 19. public static final File PREPAREDCOMMIT_DIR = join(BLOB_DIR, "prepareCommit");
> /** Prepare commit file director. */
##### 20. public static final File REMOVE = join(STAGED_DIR, "prepareRemove");
> /** Prepare remove fileSet. */
##### 21. private static void putKeyValueInStagedMap(String fileName, String fileSha)
> 把一對 key 和 value 放進 StagedMap。
##### 22.private static void checkTheCommitVersion(String filename, String fileSha)
> 確認儲存在 HEAD commit 裡的文件版本。若與即將 add 的版本相同，則拒絕 add 的行動，
> 同時把 StagedMap 中相同文件名的 key 刪除。
##### 23. private static void removeStagedMapKey(String key)
> 刪除 StagedMap 中的 key 。
##### 24. private static HashMap<String, String> getStagedMap()
> 傳回儲存在 STAGING 中的 StagedMap 。
##### 25.private static boolean checkBranchExist(String branchName)
> 查看分支是否存在。
##### 26. private static String fileSha(String filename, File file)
> /** Return Sha-1 of filename and contents. */
##### 27. public static void commit(String commitMessage)
> commit 命令。
##### 28. private static void changePrepareCommit_DirFilesPathToBlobs_Dir()
> /** Change PrepareCommit_Dir Files' Path To Blobs_Dir and update blobsMap. */
##### 29. private static HashMap<String, String> mergeTwoMapToTarget(HashMap<String, String> targetMap, HashMap<String, String> hashMap)
> 把 hashMap 中的鍵值複製一份到 targetMap ，若有相同 key 則覆蓋掉 target 原本的鍵值，
> 最後回傳新的 targetMap 。
##### 30. private static HashMap<String, String> createNewCommitBlobsMap()
> 建立新的 commit 中需要的 blobsMap 並回傳。
##### 31.private static Commit getCommit (String sha)
> 回傳 sha 指向的 commit 。
##### 32. private static String getHeadCommitSha()
> 回傳 HEAD 指向的 commit 的 sha-1 。
##### 33. private static Commit getHeadCommit()
> 回傳 HEAD 指向的 commit 。
##### 34. private static HashMap<String, String> getHeadCommitBlobsMap()
> 回傳 HEAD 指向的 commit 的 blobsMap 。
##### 35. private static HashMap<String, File> getBlobsMap()
> 回傳儲存在 BLOBS 中的 blobsMap 。
##### 36. private static void cleanStagedMap()
> 清空 StagedMap 。
##### 37. private static boolean StagedMapIsClear()
> 查看 StagedMap 是否是空的。
##### 38. private static HashSet<String> getRemoveSet()
> 回傳儲存在 REMOVE 中的 removeSet 。
##### 39. private static String getHeadCommitBranch()
> 回傳 HEAD 指向的分支。
##### 40. private static void addFileInRemove(String filename)
> 將 filename 的 sha-1 值加進 removeSet 。
##### 41. private static void clearRemoveSet()
> 清空 removeSet 。
##### 42. public static void rm(String filename)
> rm 命令。
##### 43. public static void log()
> log 命令。
##### 44. private static void printLogHistoryFirstParent()
> 輸出 parent log 歷史紀錄。
##### 45. private static void printFirstCommit(Commit firstCommit, String firstCommitId)
> 輔助 printLogHistoryFirstParent 輸出第一次的 commit 紀錄。
##### 46. private static void printCommitInfo(Commit commit, String commitId)
> 輸出給定的 commit 的資訊。
##### 47. public static void global_log()
> global-log 命令。
##### 48. public static void find(String commitMessage)
> find 命令。
##### 49. public static final File REMOVE_DIR = join(GITLET_DIR, "remove");
> 儲存 rm file 的地方。
##### 50. public static final File HEADPOINTBRANCH = join(GITLET_DIR, "HeadPoint.txt");
> /** HEAD point branch */
##### 51. private static void setHeadPointBranch(String newBranch)
> 改變 HEAD 指向的分支。
##### 52. public static void status()
> status 命令。
##### 53. private static void printBranchesZone()
> /** Print branches zone. */
##### 54. private static void printStagedZone()
> /** Print staged zone. */
##### 55. private static void prinfRemovedZone()
> /** Print removed zone. */
##### 56. private static void printModificationZone()
> /** Print Modifications Not Staged For Commit zone. */
##### 57. private static void printUntrackedZone()
> /** Print Untracked files zone. */
##### 58. public static void checkoutFile(String filename)
> checkout 命令 - checkout HEAD commit file 。
##### 59. public static void checkoutCommitFile(String commitID, String filename)
> checkout 命令 - checkout commitID file 。
##### 60. private static boolean checkCommitHaveFile(Commit commit, String filename)
> 回傳 commit 是否有 filename 。
##### 61. private static void checkoutCommitBlobs(Commit commit, String filename)
> 將 commit 的 file 備份到 CWD 裡，若原檔案存在則覆蓋之。
##### 62. private static void copyFileFromCommitToCWD(Commit commit, String filename)
> 執行 checkCommitHaveFile 若存在即執行 checkoutCommitBlobs ，
> 若不存在則提出 error 。
##### 63. public static void checkoutBranch(String branchName)
> checkout 命令 - checkout branch。
##### 64. private static Commit getBranchPointCommit(String branchName)
> 回傳分支指向的 commit 。
##### 65. private static void checkoutBranchFailureCases(String branchName)
> 檢查 checkout branch 的所有失敗情況。
##### 66. private static void checkoutWholeCommitToCWD(String commitID)
> checkout 所有在 commit 中的文件到 CWD 。
##### 67. public static void branch(String branchName)
> branch 命令。
##### 68. public static void rmBranch(String branchName)
> rm-Branch 命令。
##### 69. private static void checkFilesNotBeTrackedWouldNotBeReplace(Commit commit)
> /** Check no files not tracked in current commit would be replaced by new commit files. */
##### 70. private static void deleteFilesNotTrackedInCommit(Commit commit)
> 檢查 CWD 中的文件，若在 commit 中不存在則刪除它。
##### 71. private static void cleanAllStagingArea()
> 刪除全部的 staging 區域，包括準備 commit 和 removed 。
##### 72. public static void reset(String commitID)
> reset 命令。

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
會創建 .gitlet 資料夾儲存跟 gitlet 有關的事項。

.gitlet 包含:

1. branches 資料夾儲存分支
2. logs 資料夾儲存 commit 資料
3. Staged 資料夾儲存 staged 區域的文件
4. blobs 資料夾儲存 commit file

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

另外在 Blogs 資料夾儲存同名稱最新的副本(若未 commit 就 add 同文件名
會覆蓋掉，直到 commit 才會改名成 Sha-1 的文件名。)
## Persistence

