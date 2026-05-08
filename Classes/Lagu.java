package JavaSem2.ManajemenPlaylistMusik;

public class Lagu {
    
    private String judul;
    private int durasi; // dalam detik

    public Lagu(String judul, int durasi) {
        this.judul = judul;
        this.durasi = durasi;
    }

    public String getJudul() {
        return judul;
    }

    public int getDurasi() {
        return durasi;
    }

    @Override
    public String toString() {
        return judul + " - " + " (" + durasi + " detik)";
    }

}
