import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class RandProductSearch extends JFrame {
    JTextField partialNameField;
    JTextArea resultArea;
    RandomAccessFile randomAccessFile;

    public RandProductSearch() {
        partialNameField = new JTextField();
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchProducts());

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Partial Name:"));
        panel.add(partialNameField);
        panel.add(searchButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        try {
            randomAccessFile = new RandomAccessFile(System.getProperty("user.dir")+"\\src\\products.bin","rw");
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("Random Product Search");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void searchProducts() {
        try {
            String partialName = partialNameField.getText();
            resultArea.setText("");
            byte[] nameBytes = new byte[35];
            byte[] descBytes = new byte[75];
            byte[] idBytes = new byte[6];

            // Loop through the RandomAccessFile and display matching products
            randomAccessFile.seek(0);
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                randomAccessFile.read(nameBytes);
                String name = new String(nameBytes, StandardCharsets.UTF_8);
                randomAccessFile.read(descBytes);
                randomAccessFile.read(idBytes);
                double cost = randomAccessFile.readDouble();

                if (name.toLowerCase().contains(partialName.toLowerCase())) {
                    resultArea.append("Name: " + name + "\n");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }



    public static void main(String[] args) {
        JFrame frame = new RandProductSearch();
    }
}