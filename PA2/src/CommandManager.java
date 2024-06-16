public class CommandManager {
    private String[] commands;
    private VoyageManager vm;
    private String outputFile;

    public CommandManager(String[] commands, String outputPath) {
        this.commands = commands;
        this.outputFile = outputPath;
        this.vm = new VoyageManager(this.outputFile);
    }

    /**
     * It tests the applicability of the commands in the given input file and
     * applies the applicable ones.
     */
    public void executeCommands() {
        String lastCommand = ""; // For checking whether the last used command is Z-REPORT or not
        for (String command : this.commands) {
            FileIO.writeFile(this.outputFile, "COMMAND: " + command, true, true);
            String[] commandParameters = command.split("\t");
            switch (command.split("\t")[0]) {
                case "INIT_VOYAGE":
                    this.vm.initVoyage(commandParameters);
                    lastCommand = "INIT_VOYAGE";
                    break;

                case "SELL_TICKET":
                    this.vm.sellTicket(commandParameters);
                    lastCommand = "SELL_TICKET";
                    break;

                case "REFUND_TICKET":
                    this.vm.refundTicket(commandParameters);
                    lastCommand = "REFUND_TICKET";
                    break;

                case "PRINT_VOYAGE":
                    this.vm.printVoyage(commandParameters);
                    lastCommand = "PRINT_VOYAGE";
                    break;

                case "CANCEL_VOYAGE":
                    this.vm.cancelVoyage(commandParameters);
                    lastCommand = "CANCEL_VOYAGE";
                    break;

                case "Z_REPORT":
                    this.vm.zReport(commandParameters);
                    lastCommand = "Z_REPORT";
                    break;

                default:
                    FileIO.writeFile(this.outputFile,
                            String.format("ERROR: There is no command namely %s!", command.split("\t")[0]), true, true);
                    lastCommand = "DEFAULT";
                    break;
            }
        }
        if (!lastCommand.equals("Z_REPORT"))
            this.vm.zReport(new String[] { "Z_REPORT" });

        deleteLastEmptyLine(this.outputFile);
    }

    /**
     * Checks the correctness conditions of arguments received from the terminal
     * 
     * @param args argumans taken from terminal
     * @return "true" if the continuation conditions of the program are met,
     *         otherwise "false"
     */
    public static boolean checkArgs(String args[]) {
        if (args.length != 2) {
            System.out.println(
                    "ERROR: This program works exactly with two command line arguments, the first one is the path to the input file whereas the second one is the path to the output file. Sample usage can be as follows: \"java8 BookingSystem input.txt output.txt\". Program is going to terminate!");
            return false;
        } else {
            FileIO.readFile(args[0], false, false);
            FileIO.writeFile(args[1], "", false, false);
        }
        return true;
    }

    /**
     * Deletes the empty line at the end of the file
     * 
     * @param path path extension of output file
     */
    public void deleteLastEmptyLine(String path) {
        String[] tempData = FileIO.readFile(path, true, false);
        FileIO.writeFile(path, "", false, false);
        for (int i = 0; i < tempData.length; i++) {
            if (i != (tempData.length - 1))
                FileIO.writeFile(path, tempData[i], true, true);
            else
                FileIO.writeFile(path, tempData[i], true, false);
        }
    }
}
