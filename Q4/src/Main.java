class Main {
    public static void main(String[] args) {
        String[] commands = FileIO.readFile(args[0], true, true);
        CommandManager.setOutputPath(args[1]); // Set output file path
        CommandManager.executeCommands(CommandManager.createCommandQueue(commands)); // Start commands
    }
}