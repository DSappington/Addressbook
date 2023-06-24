package gui;

import pojo.AddressBook;
import pojo.Contact;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;

public class AddressBookPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel model;
    private AddressBook addressBook;
    private JTextField nameField;
    private JTextField streetField;
    private JTextField cityField;
    private JComboBox<String> stateField;
    private JTextField zipField;
    private JTextField phoneField;
    private JMenuItem updateItem;

    public AddressBookPanel(AddressBook addressBook) {
        this.addressBook = addressBook;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());


        model = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);

        model.addColumn("Name");
        model.addColumn("Street");
        model.addColumn("City");
        model.addColumn("State");
        model.addColumn("ZIP");
        model.addColumn("Phone");

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Actions");
        JMenuItem addItem = new JMenuItem("Add Contact");
        JMenuItem deleteItem = new JMenuItem("Delete Contact");
        updateItem = new JMenuItem("Update Contact");
        JMenuItem refreshItem = new JMenuItem("Refresh");

        menu.add(addItem);
        menu.add(deleteItem);
        menu.add(updateItem);
        menu.add(refreshItem);
        menuBar.add(menu);

        add(menuBar, BorderLayout.NORTH);

        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add Contact action
                showAddContactDialog();
            }
        });

        deleteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Delete Contact action
                deleteSelectedContact();
            }
        });

        updateItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Update Contact action
                showUpdateContactDialog();
            }
        });

        refreshItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Refresh action
                refreshContacts();
            }
        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        add(new JScrollPane(table), BorderLayout.CENTER);
        refreshContacts();
    }

    private void showAddContactDialog() {
        nameField = createValidatedTextField(20, "Enter name");
        streetField = createValidatedTextField(20, "Enter street");
        cityField = createValidatedTextField(20, "Enter city");
        stateField = new JComboBox<>(getStates());
        zipField = createValidatedTextField(20, "Enter ZIP code");
        phoneField = createValidatedTextField(20, "Enter phone number (xxx-xxx-xxxx)");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Street:"));
        panel.add(streetField);
        panel.add(new JLabel("City:"));
        panel.add(cityField);
        panel.add(new JLabel("State:"));
        panel.add(stateField);
        panel.add(new JLabel("ZIP:"));
        panel.add(zipField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        boolean validForm = false;
        do {
            int result = JOptionPane.showConfirmDialog(null, panel, "Add a Contact",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                validForm = validateFields();

                if (validateFields()) {
                    Contact contact = new Contact(
                            nameField.getText(),
                            streetField.getText(),
                            cityField.getText(),
                            (String) stateField.getSelectedItem(),
                            zipField.getText(),
                            phoneField.getText()
                    );
                    addressBook.addContact(contact);
                    refreshContacts();
                } else {
                    JOptionPane.showMessageDialog(AddressBookPanel.this, "Please fill in all the required fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Cancelled
                validForm = true;
            }
        } while (!validForm);
    }

    private void deleteSelectedContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            addressBook.deleteContact(addressBook.getContacts().get(selectedRow));
            refreshContacts();
        } else {
            JOptionPane.showMessageDialog(AddressBookPanel.this, "Please select a contact to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showUpdateContactDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Contact oldContact = addressBook.getContacts().get(selectedRow);

            nameField = createValidatedTextField(20, "Enter name");
            streetField = createValidatedTextField(20, "Enter street");
            cityField = createValidatedTextField(20, "Enter city");
            stateField = new JComboBox<>(getStates());
            zipField = createValidatedTextField(20, "Enter ZIP code");
            phoneField = createValidatedTextField(20, "Enter phone number (xxx-xxx-xxxx)");

            // Set fields with old contact data
            nameField.setText(oldContact.getName());
            streetField.setText(oldContact.getStreet());
            cityField.setText(oldContact.getCity());
            stateField.setSelectedItem(oldContact.getState());
            zipField.setText(oldContact.getZip());
            phoneField.setText(oldContact.getPhoneNumber());

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Street:"));
            panel.add(streetField);
            panel.add(new JLabel("City:"));
            panel.add(cityField);
            panel.add(new JLabel("State:"));
            panel.add(stateField);
            panel.add(new JLabel("ZIP:"));
            panel.add(zipField);
            panel.add(new JLabel("Phone:"));
            panel.add(phoneField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Update a Contact",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                if (validateFields()) {
                    Contact newContact = new Contact(
                            nameField.getText(),
                            streetField.getText(),
                            cityField.getText(),
                            (String) stateField.getSelectedItem(),
                            zipField.getText(),
                            phoneField.getText()
                    );
                    addressBook.updateContact(oldContact, newContact);
                    refreshContacts();
                } else {
                    JOptionPane.showMessageDialog(AddressBookPanel.this, "Please fill in all the required fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(AddressBookPanel.this, "Please select a contact to update.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshContacts() {
        model.setRowCount(0);
        for (Contact contact : addressBook.getContacts()) {
            model.addRow(new Object[]{
                    contact.getName(),
                    contact.getStreet(),
                    contact.getCity(),
                    contact.getState(),
                    contact.getZip(),
                    contact.getPhoneNumber()
            });
        }
    }

    private JTextField createValidatedTextField(int columns, String placeholder) {
        JTextField textField = new JTextField(columns);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField source = (JTextField) e.getSource();
                if (!source.getText().isEmpty()) {
                    if (!validateField(source)) {
                        setFieldError(source, "Invalid input");
                    } else {
                        setFieldError(source, null);
                    }
                    validateFields();
                }
            }
        });
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField source = (JTextField) e.getSource();
                if (source.getText().equals(placeholder)) {
                    source.setText("");
                    source.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField source = (JTextField) e.getSource();
                if (source.getText().isEmpty()) {
                    source.setForeground(Color.GRAY);
                    source.setText(placeholder);
                }
            }
        });
        return textField;

    }


    private String[] getStates() {
        String[] states = {
                "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
                "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY",
                "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA",
                "WV", "WI", "WY"
        };
        Arrays.sort(states);
        return states;
    }


    private boolean validateFields() {
        boolean isValid = true;

        isValid &= validateField(nameField);
        isValid &= validateField(streetField);
        isValid &= validateField(cityField);
        isValid &= validateField(zipField);
        isValid &= validateField(phoneField);

        return isValid;
    }

    private boolean validateField(JTextField field) {
        String text = field.getText();
        if (field == zipField) {
            return text.matches("\\d{5}");
        }
        if (field == phoneField) {
            return text.matches("\\d{3}-\\d{3}-\\d{4}");
        }
        return true;
    }

    private void setFieldError(JTextField field, String errorMessage) {
        field.setBorder(BorderFactory.createLineBorder(errorMessage != null ? Color.RED : UIManager.getColor("TextField.border")));
        field.setToolTipText(errorMessage);
    }
}
