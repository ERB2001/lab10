package it.unibo.oop.lab.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prova {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("CIAO");
        list.add("A");
        list.add("TUTTI");
        list.add("A");
        list.add("TUTTI");
        list.add("A");

        // 1) Convert to lowercase -> converti in minuscolo --> Funziona
        List<String> lowerCase = list.stream().map(String::toLowerCase).collect(Collectors.toList());

        String s = list.stream().toString();

        // 4) List all the words in alphabetical order -> Elenca tutte le parole in
        // ordine alfabetico --> Funziona
        List<String> alphabetical = list.stream().sorted().collect(Collectors.toList());

        // 5) Write the count for each word, e.g. "word word pippo" should output
        // "pippo-> 1 word -> 2"

        // List<String> wordCount = list.stream().collect(Collectors.toMap(null, null,
        // null));
        // list.stream().collect(Collectors.groupingBy(s -> s.contains("word"),
        // Collectors.counting()));

        // ArrayList.stream().collect(Collectors.groupingBy(s -> s)).forEach((k,v) ->
        // System.out.println(k + v.size()));

        // List.stream().collect(Collectors.groupingBy(s -> s)).forEach((k, v) ->
        // System.out.println(k + v.size()));

        // 2) Count the number of chars -> conta il numero di caratteri --> da rivedere
        // per bene
        String stringa = "Ciao a tutti";
        long count = stringa.chars().filter(c -> c != ' ').count();

        long count2 = list.stream().toString().chars().filter(c -> c != ' ').count();

        // long count3 = list.stream().map(String::chars).filter(c -> c != ' ').count();

        System.out.println(list);
        System.out.println(lowerCase);
        System.out.println(count);
        System.out.println(alphabetical);
        System.out.println(s);

    }
}
