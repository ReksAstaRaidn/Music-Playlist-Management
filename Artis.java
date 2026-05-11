import java.util.ArrayList;

public class Artis {

    private String nama;
    private ArrayList<Album> daftarAlbum;

    public Artis(String nama) {
        this.nama        = nama;
        this.daftarAlbum = new ArrayList<>();
    }

    public void tambahAlbum(Album album) {
        daftarAlbum.add(album);
    }

    // --- Getter ---
    public String getNama() {
         return nama;
    }
    public ArrayList<Album> getDaftarAlbum() {
        return daftarAlbum;
    }

    public ArrayList<Lagu> semuaLagu() {
        ArrayList<Lagu> semua = new ArrayList<>();
        for (Album album : daftarAlbum) {
            semua.addAll(album.getDaftarLagu());
        }
        return semua;
    }

    public void tampilkan() {
        System.out.println("Artis  : " + nama);
        System.out.println("Album  : " + daftarAlbum.size() + " album");
        for (int i = 0; i < daftarAlbum.size(); i++) {
            System.out.printf("  %d. %s%n", (i + 1), daftarAlbum.get(i));
        }
    }

    @Override
    public String toString() {
        return nama + " [" + daftarAlbum.size() + " album]";
    }
}