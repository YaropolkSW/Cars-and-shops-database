import ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        final UserInterface userInterface = new UserInterface();
        boolean isContinue = true;

        while (isContinue) {
            isContinue = userInterface.showUserInterface();
        }
    }
}
