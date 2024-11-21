package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Muse
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        /** TODO:If a user inputs a command with the wrong number
         *  or format of operands, print the message Incorrect operands. and exit.
         */
        if (args.length == 0) {
            throw Utils.error("Please enter a command.");
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                validateNumArgs(args, 1);
                Repository.init();
                break;
            case "add":
                validateNumArgs(args, 2);
                Repository.add(args[1]);
                break;
            case "commit":
                if (args.length < 2) {
                    throw Utils.error("Please enter a commit message.");
                }
                validateNumArgs(args, 2);
                Repository.commit(args[1]);
                break;
            case "rm":
                validateNumArgs(args, 2);
                Repository.rm(args[1]);
                break;
            case "log":
                validateNumArgs(args, 1);
                Repository.log();
                break;
            case "global-log":
                validateNumArgs(args,1);
                Repository.global_log();
                break;
            case "find":
                validateNumArgs(args, 2);
                Repository.find(args[1]);
                break;
            case "status":
                validateNumArgs(args, 1);
                Repository.status();
                break;
            case "checkout":
                if (args.length == 2) {
                    Repository.checkoutBranch(args[1]);
                } else if (args.length == 3) {
                    if (args[1].equals("--")) {
                        Repository.checkoutFile(args[2]);
                    } else { throw Utils.error("Incorrect operands."); }
                } else if (args.length == 4) {
                    if (args[2].equals("--")) {
                        Repository.checkoutCommitFile(args[1], args[3]);
                    } else { throw Utils.error("Incorrect operands."); }
                } else {
                    throw Utils.error("Incorrect operands.");
                }
                break;
            case "branch":
                validateNumArgs(args, 2);
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                validateNumArgs(args, 2);
                Repository.rmBranch(args[1]);
                break;
            case "reset":
                validateNumArgs(args, 2);
                Repository.reset(args[1]);
                break;
            case "uuu":
                Repository.printcommitblobs(args[1]);
                break;
            default:
                throw Utils.error("No command with that name exists.");
        }
    }

    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            throw Utils.error("Incorrect operands.");
        }
    }
}
