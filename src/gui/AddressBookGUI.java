package gui;


import pojo.AddressbookHandler;

import javax.swing.*;

public class AddressBookGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private AddressbookHandler addressBook;
    private AddressBookPanel addressBookPanel;

    public AddressBookGUI() {
        addressBook = new AddressbookHandler();
        addressBookPanel = new AddressBookPanel(addressBook);

        // add panel to the gui
        add(addressBookPanel);

        pack();
        setLocationRelativeTo(null); // Set the frame to be centered on the screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        addShutdownHook();
    }

    // save contacts when app is closed.
    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            addressBook.saveContactsToFile("contacts.con");
        }));
    }
}
