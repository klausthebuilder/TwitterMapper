package ui;

import query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * A UI panel for entering new queries.
 */
public class NewQueryPanel extends JPanel {
    private final JTextField newQuery = new JTextField(10);
    private final JPanel colorSetter;
    private final MainPanel app;
    private Random random;

    public NewQueryPanel(MainPanel app) {
        this.app = app;
        this.colorSetter = new JPanel();

        random = new Random();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        JLabel queryLabel = new JLabel("Enter Search: ");
        queryLabel.setLabelFor(newQuery);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        add(queryLabel, gridBagConstraints);
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        newQuery.setMaximumSize(new Dimension(200, 20));
        gridBagConstraints.gridx = 1;
        add(newQuery, gridBagConstraints);

        add(Box.createRigidArea(new Dimension(5, 5)));

        JLabel colorLabel = new JLabel("Select Color: ");
        colorSetter.setBackground(getRandomColor());

        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        add(colorLabel, gridBagConstraints);
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 1;
        colorSetter.setMaximumSize(new Dimension(200, 20));
        add(colorSetter, gridBagConstraints);

        add(Box.createRigidArea(new Dimension(5, 5)));

        JButton addQueryButton = new JButton("Add New Search");
        gridBagConstraints.gridx = GridBagConstraints.RELATIVE;       //aligned with button 2
        gridBagConstraints.gridwidth = 2;   //2 columns wide
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;       //third row
        add(addQueryButton, gridBagConstraints);

        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("New Search"),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        addQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newQuery.getText().equals("")) {
                    addQuery(newQuery.getText().toLowerCase());
                    newQuery.setText("");
                }
            }
        });

        // This makes the "Enter" key submit the query.
        app.getRootPane().setDefaultButton(addQueryButton);

        colorSetter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Color newColor = JColorChooser.showDialog(
                            app,
                            "Choose Background Color",
                            colorSetter.getBackground());
                    // newColor is "null" if user clicks "Cancel" button on JColorChooser popup.
                    if (newColor != null) {
                        colorSetter.setBackground(newColor);
                    }
                }
            }
        });
    }

    private void addQuery(String newQuery) {
        Query query = new Query(newQuery, colorSetter.getBackground(), app.map());
        app.addQuery(query);
        colorSetter.setBackground(getRandomColor());
    }

    public Color getRandomColor() {
        // Pleasant colors: https://stackoverflow.com/questions/4246351/creating-random-colour-in-java#4246418
        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        return Color.getHSBColor(hue, saturation, luminance);
    }
}
