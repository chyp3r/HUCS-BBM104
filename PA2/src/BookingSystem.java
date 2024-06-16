import java.util.Locale;

class BookingSystem {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // For change "," to "." in floating points
        if (CommandManager.checkArgs(args)) {
            String[] commands = FileIO.readFile(args[0], true, true);
            CommandManager cm = new CommandManager(commands, args[1]);
            cm.executeCommands();
        }
    }
}
