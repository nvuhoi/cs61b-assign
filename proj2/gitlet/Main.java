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
            default:
                throw Utils.error("No command with that name exists.");
        }
    }
    /** Exit gitlet. */
    private static void exitGitlet() {
        System.exit(0);
    }

    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            throw Utils.error("Incorrect operands.");
        }
    }
}
