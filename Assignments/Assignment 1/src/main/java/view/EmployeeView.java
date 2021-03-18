package view;

import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {
    private JLabel lbWelcome;
    private JButton btnCreate;
    private JButton btnUTA;
    private JButton btnDelete;
    private JButton backButton;
    private JScrollPane tableScrollPane;
    private JComboBox<Long> cbClientSelection;
    private JTable clientViewTable;
    private DefaultTableModel clientTableModel;
    private Long userid = (long) -1;

    public  EmployeeView() throws HeadlessException {
        setSize(400, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lbWelcome);
        add(tableScrollPane);
        tableScrollPane.setViewportView(clientViewTable);
        add(btnCreate);
        add(cbClientSelection);
        add(btnUTA);
        add(btnDelete);
        add(backButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        cbClientSelection = new JComboBox<>();
        cbClientSelection.addItem((long) -1);
        tableScrollPane = new JScrollPane();
        lbWelcome = new JLabel("Welcome Employee");
        btnCreate = new JButton("Create");
        btnUTA = new JButton("U / T / Ac");
        btnDelete = new JButton("Delete");
        backButton = new JButton("Back");
        clientTableModel = new DefaultTableModel();
        clientViewTable = new JTable(clientTableModel);
        initializeTableColumns();
        clientViewTable.setVisible(true);
    }

    private void initializeTableColumns() {
        clientTableModel.addColumn("Id");
        clientTableModel.addColumn("Surname");
        clientTableModel.addColumn("Firstname");
        clientTableModel.addColumn("IDNumber");
        clientTableModel.addColumn("CNP");
    }

    private void deleteAllTableRows() {
        if (clientTableModel.getRowCount() > 0) {
            for (int i = clientTableModel.getRowCount() - 1; i > -1; i--) {
                clientTableModel.removeRow(i);
            }
        }
    }

    private void deleteAllCbRows() {
        cbClientSelection.removeAllItems();
        cbClientSelection.addItem((long) -1);
    }

    public void updateAllDataInView(List<Client> clients, List<Long> ids) {
        deleteAllTableRows();
        deleteAllCbRows();
        for(Client c : clients) {
            clientTableModel.addRow(new String[]{c.getId().toString(),
                                                c.getSurname(),
                                                c.getFirstname(),
                                                c.getIDCardNumber(),
                                                c.getCnp()});
        }
        for(Long id: ids) {
            cbClientSelection.addItem(id);
        }
    }

    public Long getCbSelectionItem() {
        return cbClientSelection.getItemAt(cbClientSelection.getSelectedIndex());
    }

    public void setCreateButtonListener(ActionListener createButtonListener) {
        btnCreate.addActionListener(createButtonListener);
    }

    public void setUTAButtonListener(ActionListener utaButtonListener) {
        btnUTA.addActionListener(utaButtonListener);
    }

    public void setDeleteButtonListener(ActionListener deleteButtonListener) {
        btnDelete.addActionListener(deleteButtonListener);
    }

    public void setBackButtonListener(ActionListener backButtonListener) {
        backButton.addActionListener(backButtonListener);
    }

    public void setUserId(Long userId) {
        this.userid = userId;
    }

    public Long getUserId() {
        return this.userid;
    }

    public void setVisible() {
        this.setVisible(true);
    }

    public void setNotVisible() {
        this.setVisible(false);
    }
}
