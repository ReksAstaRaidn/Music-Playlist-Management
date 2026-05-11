import java.util.ArrayList;

public class Album {

    private String nama;
    private String artis;
    private int tahun;
    private ArrayList<Lagu> daftarLagu;

    public Album(String nama, String artis, int tahun) {
        this.nama      = nama;
        this.artis     = artis;
        this.tahun     = tahun;
        this.daftarLagu = new ArrayList<>();
    }

    public void tambahLagu(Lagu lagu) {
        daftarLagu.add(lagu);
    }

    // --- Getter ---
    public String getNama() { 
        return nama; 
    }
    
    public String getArtis() { 
        return artis; 
    }

    public int    getTahun() { 
        return tahun; 
    }

    public ArrayList<Lagu> getDaftarLagu() { 
        return daftarLagu; 
    }

    public void tampilkan() {
        System.out.println("Album  : " + nama);
        System.out.println("Artis  : " + artis);
        System.out.println("Tahun  : " + tahun);
        System.out.println("Jumlah : " + daftarLagu.size() + " lagu");
        garis();
        System.out.printf("| %-3s | %-33s | %-6s |%n", "No", "Judul", "Durasi");
        garis();
        for (int i = 0; i < daftarLagu.size(); i++) {
            Lagu l = daftarLagu.get(i);
            System.out.printf("| %-3d | %-33s | %-6s |%n", (i + 1), l.getJudul(), l.getDurasiFormat());
        }
        garis();
    }

    private void garis() {
        System.out.println("+-----+-----------------------------------+--------+");
    }

    @Override
    public String toString() {
        return nama + " - " + artis + " (" + tahun + ") [" + daftarLagu.size() + " lagu]";
    }
}