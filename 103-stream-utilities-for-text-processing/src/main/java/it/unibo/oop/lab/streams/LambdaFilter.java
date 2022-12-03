package it.unibo.oop.lab.streams;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Modify this small program adding new filters.
 * Realize this exercise using as much as possible the Stream library.
 *
 * 1) Convert to lowercase --> converti in minuscolo
 *
 * 2) Count the number of chars --> conta il numero di caratteri
 *
 * 3) Count the number of lines --> conta il numero di righe
 *
 * 4) List all the words in alphabetical order --> elencare le parole inserite
 * in ordine alfabetico
 * 
 * 5) Write the count for each word, e.g. "word word pippo" should output "pippo
 * -> 1 word -> 2"
 *
 */
public final class LambdaFilter extends JFrame {

    private static final long serialVersionUID = 1760990730218643730L;

    // private final static String NEWLINE = "\n";

    private enum Command {
        /**
         * Commands.
         */
        IDENTITY("No modifications", Function.identity());

        private final String commandName;
        private final Function<String, String> fun;

        Command(final String name, final Function<String, String> process) {
            commandName = name;
            fun = process;
        }

        @Override
        public String toString() {
            return commandName;
        }

        public String translate(final String s) {
            return fun.apply(s);
        }

        /*
         * public Stream<String> toLowerCase(final String s) {
         * List<String> lowerCase =
         * list.stream().map(String::toLowerCase).collect(Collectors.toList());
         * return null;
         * }
         */
    }

    private LambdaFilter() {
        super("Lambda filter GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel panel1 = new JPanel();
        final LayoutManager layout = new BorderLayout();
        panel1.setLayout(layout);
        final JComboBox<Command> combo = new JComboBox<>(Command.values());
        panel1.add(combo, BorderLayout.NORTH);
        final JPanel centralPanel = new JPanel(new GridLayout(1, 2));
        final JTextArea left = new JTextArea();
        left.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        final JTextArea right = new JTextArea();
        right.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        right.setEditable(false);
        centralPanel.add(left);
        centralPanel.add(right);
        panel1.add(centralPanel, BorderLayout.CENTER);
        final JButton apply = new JButton("Apply");
        // apply.addActionListener(ev -> right.setText(((Command)
        // combo.getSelectedItem()).translate(left.getText())));
        // new Thread(() -> right.setText)
        apply.addActionListener(ev -> {
            // right.setText(((Command) combo.getSelectedItem()).translate(left.getText()));
            List<String> list = new ArrayList<String>();

            list.add(left.getText().toLowerCase());

            list.add(String.valueOf(left.getText().chars().filter(ch -> ch != ' ').filter(ch -> ch != '\n').count()));

            list.add(String.valueOf(left.getText().lines().count()));

            right.append(list.toString());

            // right.setText(String.valueOf(left.getText().chars().filter(ch -> ch != '
            // ').count()));

            // right.setText(String.valueOf(left.getText().lines().count()));
            // right.setText(String.valueOf(left.getText().toLowerCase().chars().filter(ch
            // -> ch != ' ').count()));

        });

        // final JButton converter = new JButton("Converter");
        // converter.addActionListener(ev ->
        // right.setText(left.getText().toLowerCase()));
        // apply.add(converter);
        panel1.add(apply, BorderLayout.SOUTH);
        setContentPane(panel1);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        setSize(sw / 4, sh / 4);
        setLocationByPlatform(true);
    }

    /**
     * @param a unused
     */
    public static void main(final String... a) {
        final LambdaFilter gui = new LambdaFilter();
        gui.setVisible(true);
    }
}
