import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RandProductMaker extends JFrame {
    JTextField nameField, descriptionField, idField, costField, recordCountField;
    RandomAccessFile randomAccessFile;
    private int recordCount = 0;

    public RandProductMaker() {
        nameField = new JTextField(35);
        descriptionField = new JTextField(75);
        idField = new JTextField(6);
        costField = new JTextField();
        recordCountField = new JTextField();
        recordCountField.setEditable(false);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addRecord());

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Cost:"));
        panel.add(costField);
        panel.add(new JLabel("Record Count:"));
        panel.add(recordCountField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        try {
            randomAccessFile = new RandomAccessFile(System.getProperty("user.dir")+"\\src\\products.bin","rw");
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("Random Product Maker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addRecord() {
        try {
            if (nameField.getText().length() > 35 || descriptionField.getText().length() > 75 || idField.getText().length() > 6) {
                JOptionPane.showMessageDialog(this, "Invalid field lengths", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String paddedName = String.format("%-35s", nameField.getText());
            String paddedDescription = String.format("%-75s", descriptionField.getText());
            String paddedId = String.format("%-6s", idField.getText());
            double cost = Double.parseDouble(costField.getText());

            int pos = recordCount*124;

            randomAccessFile.seek(pos);
            randomAccessFile.write(paddedName.getBytes(StandardCharsets.UTF_8));
            randomAccessFile.write(paddedDescription.getBytes(StandardCharsets.UTF_8));
            randomAccessFile.write(paddedId.getBytes(StandardCharsets.UTF_8));
            randomAccessFile.writeDouble(cost);

            recordCount++;
            recordCountField.setText(String.valueOf(recordCount));

            nameField.setText("");
            descriptionField.setText("");
            idField.setText("");
            costField.setText("");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new RandProductMaker();
    }
}