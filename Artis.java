package JavaSem2.ManajemenPlaylistMusik;

public class Artis {
    private String namaArtis;

    public Artis(String namaArtis) {
        this.namaArtis = namaArtis;
    }

    public String getNamaArtis() {
        return namaArtis;
    }

    @Override
    public String toString() {
        return "Artis: " + namaArtis;
    }
}
