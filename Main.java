package prog02;

/**
 * A program to query and modify the phone directory stored in csc220.txt.
 *
 * @author vjm
 */
public class Main {

    /**
     * Processes user's commands on a phone directory.
     *
     * @param fn The file containing the phone directory.
     * @param ui The UserInterface object to use
     *           to talk to the user.
     * @param pd The PhoneDirectory object to use
     *           to process the phone directory.
     */
    public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
        pd.loadData(fn);
        boolean changed = false;

        String[] commands = {"Add/Change Entry", "Look Up Entry", "Remove Entry", "Save Directory", "Exit"};

        String name, number, oldNumber;

        while (true) {
            int c = ui.getCommand(commands);
            switch (c) {
                case -1:
                    ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
                    break;
                //Add or Change
                case 0:
                    name = ui.getInfo("Enter the name");
                    if (name == null) {
                        break;
                    }
                    if (name.length() == 0) {
                        ui.sendMessage("Blank names aren't allowed");
                        break;
                    }
                    number = ui.getInfo("Enter the number");
                    if (number ==  null) {
                        break;
                    }
                    if (number.length()  == 0) {
                        ui.sendMessage("Blank numbers aren't allowed");
                        break;
                    }
                    oldNumber = pd.addOrChangeEntry(name, number);
                    if (oldNumber == null) {
                        ui.sendMessage("A new name, "+ name + ", and number, " + number + ", have been added");
                    } else {
                        ui.sendMessage(name + "'s number has been changed from " + oldNumber + " to " + number);
                    }
                    changed = true;
                    break;
                //Lookup Entry
                case 1:
                    name = ui.getInfo("Enter the name ");
                    if (name == null) {
                        break;
                    }
                    if (name.length() == 0) {
                        ui.sendMessage("Blank names aren't allowed");
                        break;
                    }
                    number = pd.lookupEntry(name);
                    if (number == null) {
                        ui.sendMessage(name + " is not in the directory");
                        break;
                    } else {
                        ui.sendMessage(name + " has the number " + number);
                        break;
                    }
                    //Remove Entry
                case 2:
                    name = ui.getInfo("Enter the name");
                    if (name == null) {
                        break;
                    }
                    if (name.length() == 0) {
                        ui.sendMessage("Blank names aren't allowed");
                        break;
                    }
                    number = pd.removeEntry(name);
                    if (number == null) {
                        ui.sendMessage("The entry does not exist");
                    } else {
                        ui.sendMessage(name + " and his number, " + number + ", have been removed");
                        changed = true;
                    }
                    break;
                //Save Directory
                case 3:
                    pd.save();
                    changed = false;
                    break;
                //Exit
                case 4:
                    if (changed) {
                        String choice = ui.getInfo("Do you really want to exit without saving? Enter YES or NO");
                        if (choice == null) {
                            break;
                        } else if (choice.equalsIgnoreCase("yes")) {
                            return;
                    } else if (choice.equalsIgnoreCase("no")){
                            break;
                        } else {
                            ui.sendMessage("Please enter YES or NO");
                            break;
                        }
            }
                    return;
        }
    }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fn = "csc220.txt";
        // PhoneDirectory pd = new ArrayBasedPD();
        PhoneDirectory pd = new SortedPD();
        //UserInterface ui = new ConsoleUI();

        UserInterface ui = new GUI("Phone Directory");
        //UserInterface ui = new TestUI("Phone Directory");
        processCommands(fn, ui, pd);
    }
}
