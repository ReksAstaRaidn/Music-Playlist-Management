public class Lagu implements Dapat_Diputar, Comparable<Lagu> {
    
    private String judul;
    private String artis;
    private String namaAlbum;
    private String genre;
    private int durasi; // dalam detik

   public Lagu(String judul, String artis, String namaAlbum, String genre, int durasi) {
        this.judul = judul;
        this.artis = artis;
        this.namaAlbum = namaAlbum;
        this.genre = genre;
        this.durasi = durasi;
    }

    public String getJudul() {
        return judul;
    }

    public String getArtis() {
        return artis;
    }

    public String getNamaAlbum() {
        return namaAlbum;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurasi() {
        return durasi;
    }

    public String getDurasiFormat() {
        int menit = durasi / 60;
        int detik = durasi % 60;
        return String.format("%d:%02d", menit, detik);
    }

    @Override
    public void play() {
        System.out.println("[LAGU]  " + judul + "-" + artis 
                            + " diputar." + "  [" + getDurasiFormat() + "]");
    }

    @Override
    public void pause() {
        System.out.println("[PAUSE] " + judul + " - " + artis);
    }
    
    @Override
    public void stop() {
        System.out.println("[STOP] " + judul + " - " + artis);
    }

    //comparable
    @Override
    public int compareTo(Lagu other) {
        return this.judul.compareToIgnoreCase(other.judul);
    }

    // equals & hashCode untuk membandingkan lagu berdasarkan judul dan artis
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Lagu)) return false;
        Lagu other = (Lagu) obj;
        return judul.equalsIgnoreCase(other.judul)
            && artis.equalsIgnoreCase(other.artis);
    }
 
    @Override
    public int hashCode() {
        return judul.toLowerCase().hashCode() * 31
             + artis.toLowerCase().hashCode();
    }
 
    @Override
    public String toString() {
        return String.format("%-33s | %-16s | %-22s | %-12s | %s",
                judul, artis, namaAlbum, genre, getDurasiFormat());
    }

}
