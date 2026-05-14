import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Media_Player implements Dapat_Diputar {

    private Stack<Lagu> riwayatPutar;  // Stack: riwayat lagu sebelumnya
    private Stack<Lagu> riwayatMaju;   // Stack: untuk navigasi maju
    private Queue<Lagu> antrianPutar;  // Queue: antrian lagu berikutnya
    private Lagu   laguSaatIni;
    private boolean sedangBerjalan;
    private boolean dijeda;

    public Media_Player() {
        riwayatPutar   = new Stack<>();
        riwayatMaju    = new Stack<>();
        antrianPutar   = new LinkedList<>();
        sedangBerjalan = false;
        dijeda         = false;
    }

    // --- Implementasi DapatDiputar ---

    @Override
    public void play() {
        if (laguSaatIni != null && dijeda) {
            sedangBerjalan = true;
            dijeda         = false;
            System.out.println("[LANJUT] " + laguSaatIni.getJudul()
                    + " - " + laguSaatIni.getArtis());
        } else if (!antrianPutar.isEmpty()) {
            mainkanBerikutnya();
        } else {
            System.out.println("Tidak ada lagu untuk dimainkan.");
        }
    }

    @Override
    public void pause() {
        if (sedangBerjalan && laguSaatIni != null) {
            dijeda         = true;
            sedangBerjalan = false;
            laguSaatIni.pause();
        } else {
            System.out.println("Tidak ada lagu yang sedang diputar.");
        }
    }

    @Override
    public void stop() {
        if (laguSaatIni != null) {
            sedangBerjalan = false;
            dijeda         = false;
            laguSaatIni.stop();
        } else {
            System.out.println("Tidak ada lagu yang sedang diputar.");
        }
    }

    // Mainkan lagu tertentu langsung
    public void mainkanLagu(Lagu lagu) {
        if (laguSaatIni != null) {
            riwayatPutar.push(laguSaatIni); // simpan ke riwayat (Stack)
            riwayatMaju.clear();            // hapus forward history
        }
        laguSaatIni    = lagu;
        sedangBerjalan = true;
        dijeda         = false;
        lagu.play();
    }

    // Mainkan lagu berikutnya dari antrian (Queue.poll)
    public void mainkanBerikutnya() {
        if (!antrianPutar.isEmpty()) {
            if (laguSaatIni != null) {
                riwayatPutar.push(laguSaatIni);
                riwayatMaju.clear();
            }
            laguSaatIni    = antrianPutar.poll(); // ambil dari depan antrian
            sedangBerjalan = true;
            dijeda         = false;
            laguSaatIni.play();
        } else {
            System.out.println("Antrian putar kosong.");
        }
    }

    // Kembali ke lagu sebelumnya (Stack.pop dari riwayatPutar)
    public void kembali() {
        if (!riwayatPutar.isEmpty()) {
            if (laguSaatIni != null) {
                riwayatMaju.push(laguSaatIni); // simpan ke riwayat maju
            }
            laguSaatIni    = riwayatPutar.pop();
            sedangBerjalan = true;
            dijeda         = false;
            System.out.print("[KEMBALI] ");
            laguSaatIni.play();
        } else {
            System.out.println("Tidak ada riwayat sebelumnya.");
        }
    }

    // Maju ke lagu berikutnya dalam riwayat (Stack.pop dari riwayatMaju)
    public void maju() {
        if (!riwayatMaju.isEmpty()) {
            if (laguSaatIni != null) {
                riwayatPutar.push(laguSaatIni);
            }
            laguSaatIni    = riwayatMaju.pop();
            sedangBerjalan = true;
            dijeda         = false;
            System.out.print("[MAJU] ");
            laguSaatIni.play();
        } else {
            System.out.println("Tidak ada lagu berikutnya dalam riwayat.");
        }
    }

    // Tambah satu lagu ke antrian (Queue.add)
    public void tambahKeAntrian(Lagu lagu) {
        antrianPutar.add(lagu);
        System.out.println("Ditambahkan ke antrian: "
                + lagu.getJudul() + " - " + lagu.getArtis());
    }

    // Tambah seluruh playlist ke antrian
    public void tambahPlaylistKeAntrian(Playlist playlist) {
        ArrayList<Lagu> daftar = playlist.toDaftarLagu();
        for (Lagu l : daftar) {
            antrianPutar.add(l);
        }
        System.out.println(daftar.size() + " lagu dari playlist "
                + playlist.getNama() + " ditambahkan ke antrian.");
    }

    public void tampilkanStatus() {
        System.out.println("       STATUS MEDIA PLAYER      ");
        if (laguSaatIni != null) {
            System.out.println("Sedang diputar : " + laguSaatIni.getJudul());
            System.out.println("Artis          : " + laguSaatIni.getArtis());
            System.out.println("Album          : " + laguSaatIni.getNamaAlbum());
            System.out.println("Genre          : " + laguSaatIni.getGenre());
            System.out.println("Durasi         : " + laguSaatIni.getDurasiFormat());
            String status;
            if (sedangBerjalan) {
                status = "Sedang Bermain";
            } else if (dijeda) {
                status = "Dijeda";
            } else {
                status = "Berhenti";
            }
            System.out.println("Status         : " + status);
        } else {
            System.out.println("  Tidak ada lagu yang sedang diputar.");
        }
        System.out.println("Riwayat        : " + riwayatPutar.size() + " lagu");
        System.out.println("Antrian        : " + antrianPutar.size() + " lagu");
        garis("============================");
    }

    public void tampilkanRiwayat() {
        if (riwayatPutar.isEmpty()) {
            System.out.println("Riwayat kosong.");
            return;
        }
        System.out.println("=== Riwayat Putar (" + riwayatPutar.size() + " lagu) ===");
        // Salin stack untuk ditampilkan tanpa merusak stack asli
        Stack<Lagu> salin = new Stack<>();
        salin.addAll(riwayatPutar);
        // Balik supaya urutan dari awal ke terbaru
        Stack<Lagu> dibalik = new Stack<>();
        while (!salin.isEmpty()) {
            dibalik.push(salin.pop());
        }
        int no = 1;
        while (!dibalik.isEmpty()) {
            Lagu l = dibalik.pop();
            System.out.printf("  %2d. %s - %s%n", no++, l.getJudul(), l.getArtis());
        }
    }

    public void tampilkanAntrian() {
        if (antrianPutar.isEmpty()) {
            System.out.println("Antrian putar kosong.");
            return;
        }
        System.out.println("=== Antrian Putar (" + antrianPutar.size() + " lagu) ===");
        int no = 1;
        for (Lagu l : antrianPutar) {
            System.out.printf("  %2d. %s - %s%n", no++, l.getJudul(), l.getArtis());
        }
    }

    public Lagu    getLaguSaatIni(){ 
        return laguSaatIni; 
    }

    public boolean isSedangBerjalan(){ 
        return sedangBerjalan; 
    }

    public boolean isDijeda() { 
        return dijeda; 
    }

    public int     ukuranAntrian() { 
        return antrianPutar.size(); 
    }

    public int     ukuranRiwayat() { 
        return riwayatPutar.size(); 
    }

    private void garis(String s) {
        System.out.println(s);
    }
}