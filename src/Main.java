import gui.AddressBookGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddressBookGUI());
    }
}