import gui.AddressBookGUI;

import javax.swing.*;

public class Addressbook {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddressBookGUI());
    }
}