class CommandManager {
    private static String outputPath;

    public static String getOutputPath() {
        return outputPath;
    }

    public static void setOutputPath(String path) {
        outputPath = path;
    }

    /**
     * Create a queue for commands
     * 
     * @param <E>   Type of lines
     * @param lines Lines from input file
     * @return created queue
     */
    public static <E> Queue<Command> createCommandQueue(E[] lines) {
        Queue<Command> commandQueue = new Queue<Command>(); // Create command queue
        for (E line : lines) {
            String[] command = line.toString().split("\t"); // Split lines to command parts
            switch (command[0]) {
                case "ADD":
                    commandQueue.addCommandToQueue(new AddCommand(command));
                    break;
                case "REMOVE":
                    commandQueue.addCommandToQueue(new RemoveCommand(command));
                    break;
                case "SEARCHBYBARCODE":
                    commandQueue.addCommandToQueue(new SearchByBarcodeCommand(command));
                    break;
                case "SEARCHBYNAME":
                    commandQueue.addCommandToQueue(new SearchByNameCommand(command));
                    break;
                case "DISPLAY":
                    commandQueue.addCommandToQueue(new DisplayCommand(command));
                    break;
            }
        }
        return commandQueue;
    }

    /**
     * Start commands acording to queue
     * @param commandQueue commands for executing
     */
    public static void executeCommands(Queue<Command> commandQueue) {
        while (!commandQueue.isQueueEmpty()) {// Execute all commands 
            try {
                commandQueue.getFrontofCommandQueue().executeCommand(); // Start command
                commandQueue.removeCommandFromQueue(); // Remove it from command queue
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
