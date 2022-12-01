package it.unibo.oop.lab.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public final class MusicGroupImpl implements MusicGroup {

    private final Map<String, Integer> albums = new HashMap<>();
    private final Set<Song> songs = new HashSet<>();

    @Override
    public void addAlbum(final String albumName, final int year) {
        this.albums.put(albumName, year);
    }

    @Override
    public void addSong(final String songName, final Optional<String> albumName, final double duration) {
        if (albumName.isPresent() && !this.albums.containsKey(albumName.get())) {
            throw new IllegalArgumentException("invalid album name");
        }
        this.songs.add(new MusicGroupImpl.Song(songName, albumName, duration));
    }

    @Override
    public Stream<String> orderedSongNames() {
        Stream<String> result = songs
                .stream()
                .map(Song::getSongName)
                .sorted();
        return result;
    }

    @Override
    public Stream<String> albumNames() {
        Stream<String> result = albums
                .keySet()
                .stream();
        return result;
    }

    @Override
    public Stream<String> albumInYear(final int year) {
        Stream<String> result = albums
                .keySet()
                .stream()
                .filter(s -> albums.get(s) == year);
        return result;
    }

    @Override
    public int countSongs(final String albumName) {
        long tmp = songs
                .stream()
                .filter(s -> s.getAlbumName().isPresent())
                .filter(s -> s.getAlbumName().get().equals(albumName))
                .count();
        int count = ((int) tmp);
        return count;
    }

    @Override
    public int countSongsInNoAlbum() {
        long tmp = songs
                .stream()
                .filter(s -> s.getAlbumName().isEmpty()) // o .filter(s -> !s.getAlbumName().isPresent())
                .count();
        int count = ((int) tmp);
        return count;
    }

    @Override
    public OptionalDouble averageDurationOfSongs(final String albumName) {
        // ottengo la somma tra la durata delle varie canzoni
        Optional<Double> averageOfSongsDuration = songs
                .stream()
                .filter(s -> s.getAlbumName().isPresent())
                .filter(s -> s.getAlbumName().get().equals(albumName))
                .map(Song::getDuration)
                .reduce((a, b) -> (a + b));
        OptionalDouble result = OptionalDouble.of(averageOfSongsDuration.get() / this.countSongs(albumName));
        return result;
    }
    /*
     * versione 2: averageOfSongsDuration = songs
     * .stream()
     * .filter(s -> s.getAlbumName().isPresent())
     * .filter(s -> s.getAlbumName().get().equals(albumName))
     * .mapToDouble(Song::getDuration)
     * .average();
     * OptionalDouble result = OptionalDouble.of(averageOfSongsDuration.get() /
     * this.countSongs(albumName));
     * return result;
     */

    @Override
    public Optional<String> longestSong() {
        Optional<String> maxOfSongsDuration = songs
                .stream()
                .max((a, b) -> a.getDuration() > b.getDuration() ? 1 : -1)
                .map(Song::getSongName);

        return maxOfSongsDuration;
    }

    @Override
    public Optional<String> longestAlbum() {
        return null;
    }

    private static final class Song {

        private final String songName;
        private final Optional<String> albumName;
        private final double duration;
        private int hash;

        Song(final String name, final Optional<String> album, final double len) {
            super();
            this.songName = name;
            this.albumName = album;
            this.duration = len;
        }

        public String getSongName() {
            return songName;
        }

        public Optional<String> getAlbumName() {
            return albumName;
        }

        public double getDuration() {
            return duration;
        }

        @Override
        public int hashCode() {
            if (hash == 0) {
                hash = songName.hashCode() ^ albumName.hashCode() ^ Double.hashCode(duration);
            }
            return hash;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Song) {
                final Song other = (Song) obj;
                return albumName.equals(other.albumName) && songName.equals(other.songName)
                        && duration == other.duration;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Song [songName=" + songName + ", albumName=" + albumName + ", duration=" + duration + "]";
        }

    }

}
