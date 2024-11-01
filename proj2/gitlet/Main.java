package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
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
            System.out.println("Please enter a command.");
            exitGitlet();
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
                validateNumArgs(args, 2);
                Repository.commit(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                exitGitlet();
        }
    }
    /** Exit gitlet. */
    private static void exitGitlet() {
        System.exit(0);
    }

    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            System.out.println("Incorrect operands.");
            exitGitlet();
        }
    }
}
