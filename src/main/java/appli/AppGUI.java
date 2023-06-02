package appli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import game.Game;

public class AppGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextArea outputTextArea;
    private JScrollPane scrollPane;
    private JButton startButton;

    public AppGUI() {
        super("6 Qui Prend!");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set the background image
        ImageIcon backgroundImage = new ImageIcon("/background.png");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        mainPanel.add(backgroundLabel, BorderLayout.CENTER);

        // Create the output text area and scroll pane
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setPreferredSize(new Dimension(580, 320));
        backgroundLabel.add(scrollPane);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                outputTextArea.setText("");
                try {
                    startGame();
                } catch (FileNotFoundException ex) {
                    outputTextArea.append("Error: File not found.");
                }
            }
        });
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void startGame() throws FileNotFoundException {
        Game g = new Game();
        boolean posable = true;
        int k = 0;

        outputTextArea.append("Les 2 joueurs sont Chloe et Cassandre. Merci de jouer à 6 qui prend !\n");
        outputTextArea.append("A Chloe de jouer.\n");
        outputTextArea.append(g.toString() + "\n");

        for (int i = 0; i < g.getNBTOUR(); i++) {
            k = 0;
            g.choixJoueur();
            while (k < g.getNbJoueurs()) {
                posable = g.poser(k);
                if (!posable)
                    break;
                ++k;
            }
            outputTextArea.append(g.toString() + "\n");
            outputTextArea.append("Cartes posées: ");
            g.affichagePose(posable);
            outputTextArea.append("\n");

            if (!posable) {
                while (k < g.getNbJoueurs()) {
                    posable = g.poser(k);
                    if (!posable) {
                        g.choixSerie(k);
                        posable = true;
                    }
                    ++k;
                }
                outputTextArea.append("Cartes posées: ");
                g.affichagePose(posable);
                outputTextArea.append("\n");
            }

            outputTextArea.append(g.toString() + "\n");
            g.affichagePenalite();
            outputTextArea.append("\n");
        }

        outputTextArea.append("** Score final\n");
        g.cloture();

        startButton.setEnabled(true);
    }

    public static void main(String[] args) {
        AppGUI app = new AppGUI();
        app.setVisible(true);
    }
}
