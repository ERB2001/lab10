package it.unibo.oop.lab.streams;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    private static final String ANY_NON_WORD = "(\\s|\\p{Punct})+";

    private enum Command {
        /**
         * Commands.
         */
        IDENTITY("No modifications", Function.identity()),

        TO_LOWER("Converter to lowercase", s -> s.toLowerCase()),

        CHARS_NUMBER("Count the number of chars", (s -> Long.toString(s.chars().count()))),

        LINES_NUMBER("Count the number of lines", (s -> Long.toString(s.lines().count()))),

        ALPHABETICAL_ORDER("List all the words in alphabetical order",
                (s -> Arrays
                        .stream(s.split(ANY_NON_WORD))
                        .sorted()
                        .collect(Collectors.joining()))),

        WORDS_COUNT("Write the count for each word",
                (s -> Arrays.stream(s.split(ANY_NON_WORD))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream()
                        .map(e -> e.getKey() + e.getValue()).collect(Collectors.joining())));

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
            right.setText(((Command) combo.getSelectedItem()).translate(left.getText()));

            // TENTATIVI MIEI
            // List<String> list = new ArrayList<String>();

            // list.add(left.getText().toLowerCase()); BUONE

            // list.add(String.valueOf(left.getText().chars().filter(ch -> ch != '
            // ').filter(ch -> ch != '\n').count())); BUONE

            // list.add(String.valueOf(left.getText().lines().count())); BUONE

            // right.setText(list.stream().sorted().toString()).translate(left.getText().toString());

            // right.setText(List.of(left.getText()).stream().sorted().toString());

            // right.setText(List.of(left.getText()).stream().sorted().toString());
            // System.out.println(List.of(left.getText().toString()).sort(s -> s));
            // String cazzo = left.getText();
            // list.add(cazzo);
            // System.out.println(list);

            // list.add(list2.toString());

            // String output = left.getText().substring(0).toString();

            // List<IntStream> list2 = Stream.of(right.getText().chars().filter(ch -> ch !=
            // ' ' && ch != '\n')).sorted().toList();

            // List<String> listTMP = list.sort(String::compareTo);

            // list.add(List.of(left.getText()).stream().map(s ->
            // s.toLowerCase()).sorted().collect(Collectors.joining()));

            // list.add(left.getText().substring(0).compareTo(list.iterator().next().toString()));

            // listTMP = ;
            // List<String> list3 =

            // Long tmp = list.stream().count();

            // System.out.println(tmp);

            // CERCARE COME METTERE I ORDINE LE STRINGHE O GLI ARRAY DI CARATTERI
            // MALE MALE MALE MALE MALE
            // MANCANO UNLTIMI DUE METODI

            // char[] line = left.getText().toCharArray();

            // list.add((Arrays.sort(line)));

            // Arrays.sort(Integer.valueOf(list.add(left.getText().toCharArray().toString())));

            // list.add(List.of(left).stream().sorted().toString());

            // List<String> alphabetical =

            // List.of(left.getText()).stream().sorted().collect(Collectors.toList());
            // Funziona ma male -- NON ORDINA

            // list.add(((Collection<String>)
            // left).stream().sorted().collect(Collectors.joining()));

            // list.stream().sorted();

            // right.append(list.toString()); BUONO
            // right.append(list.stream().sorted().toString());

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
