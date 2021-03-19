package view;

import model.User;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdministratorView extends JFrame {
    private JLabel lbWelcome;
    private JButton btnCreate;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnReportGenerator;
    private JButton backButton;
    private JScrollPane tableScrollPane;
    private JComboBox<Long> cbUserSelection;
    private JTable userViewTable;
    private DefaultTableModel userTableModel;
    private JLabel lbStartDate;
    private JDatePickerImpl dpStartDate;
    private JLabel lbEndDate;
    private JDatePickerImpl dpEndDate;

    public  AdministratorView() throws HeadlessException {
        setSize(400, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lbWelcome);
        add(tableScrollPane);
        tableScrollPane.setViewportView(userViewTable);
        add(btnCreate);
        add(cbUserSelection);
        add(btnUpdate);
        add(btnDelete);
        add(lbStartDate);
        add(dpStartDate);
        add(lbEndDate);
        add(dpEndDate);
        add(btnReportGenerator);
        add(backButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        cbUserSelection = new JComboBox<>();
        cbUserSelection.addItem((long) -1);
        tableScrollPane = new JScrollPane();
        lbWelcome = new JLabel("Welcome Administrator");
        btnCreate = new JButton("Create");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnReportGenerator = new JButton("Generate Report");
        backButton = new JButton("Back");
        userTableModel = new DefaultTableModel();
        userViewTable = new JTable(userTableModel);

        UtilDateModel startModel = new UtilDateModel();
        UtilDateModel endModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel, p);
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, p);
        dpStartDate = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
        dpEndDate = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());

        lbStartDate = new JLabel("Start Date");
        lbEndDate = new JLabel("End Date");

        initializeTableColumns();
        userViewTable.setVisible(true);
    }

    private void initializeTableColumns() {
        userTableModel.addColumn("Id");
        userTableModel.addColumn("Username");
        userTableModel.addColumn("Password");
        userTableModel.addColumn("Role");
    }

    private void deleteAllTableRows() {
        if (userTableModel.getRowCount() > 0) {
            for (int i = userTableModel.getRowCount() - 1; i > -1; i--) {
                userTableModel.removeRow(i);
            }
        }
    }

    private void deleteAllCbRows() {
        cbUserSelection.removeAllItems();
        cbUserSelection.addItem((long) -1);
    }

    public void updateAllDataInView(List<User> users, List<Long> ids) {
        deleteAllTableRows();
        deleteAllCbRows();
        for(User u : users) {
            userTableModel.addRow(new String[]{u.getId().toString(),
                    u.getUsername(),
                    u.getPassword(),
                    u.getRole().getRole(),
                    });
        }
        for(Long id: ids) {
            cbUserSelection.addItem(id);
        }
    }

    public Long getCbSelectionItem() {
        return cbUserSelection.getItemAt(cbUserSelection.getSelectedIndex());
    }

    public LocalDate getStartDate() {
        Date startDate = (Date) dpStartDate.getModel().getValue();
        if(startDate == null) {
            return null;
        }
        return startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }

    public LocalDate getEndDate() {
        Date endDate = (Date) dpEndDate.getModel().getValue();
        if(endDate == null) {
            return null;
        }
        return endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setCreateButtonListener(ActionListener createButtonListener) {
        btnCreate.addActionListener(createButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        btnUpdate.addActionListener(updateButtonListener);
    }

    public void setDeleteButtonListener(ActionListener deleteButtonListener) {
        btnDelete.addActionListener(deleteButtonListener);
    }

    public void setReportGeneratorButtonListener(ActionListener reportGeneratorButtonListener) {
        btnReportGenerator.addActionListener(reportGeneratorButtonListener);
    }

    public void setBackButtonListener(ActionListener backButtonListener) {
        backButton.addActionListener(backButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }

    public void setNotVisible() {
        this.setVisible(false);
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}
