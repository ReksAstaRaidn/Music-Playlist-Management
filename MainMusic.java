import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class MainMusic {

    private static ArrayList<Lagu>    semuaLagu     = new ArrayList<>();
    private static ArrayList<Album>   semuaAlbum    = new ArrayList<>();
    private static ArrayList<Artis>   semuaArtis    = new ArrayList<>();
    private static ArrayList<Playlist> daftarPlaylist = new ArrayList<>();

    // Bab 11: Map genre -> daftar lagu
    private static Map<String, ArrayList<Lagu>> laguPerGenre = new HashMap<>();

    // Bab 11: Set lagu favorit
    private static Set<Lagu> lagiFavorit = new HashSet<>();

    private static Media_Player mediaPlayer = new Media_Player();
    private static Scanner     scanner     = new Scanner(System.in);

    
    public static void main(String[] args) {
        inisialisasiData();

        System.out.println("============================================");
        System.out.println("   SELAMAT DATANG DI APLIKASI MUSIK JAVA   ");
        System.out.println("============================================");

        boolean aktif = true;
        while (aktif) {
            tampilkanMenuUtama();
            int pilihan = bacaInt();
            switch (pilihan) {
                case 1:  menuLihatLagu();            
                    break;
                case 2:  menuBuatPlaylist();          
                    break;
                case 3:  menuTambahLaguKePlaylist();  
                    break;
                case 4:  menuPutarPlaylist();         
                    break;
                case 5:  menuRiwayatDanAntrian();     
                    break;
                case 6:  menuCariLagu();              
                    break;
                case 7:  menuUrutkanLagu();           
                    break;
                case 8:  menuRekomendasiLagu();       
                    break;
                case 9:  menuKelolaPlaylist();        
                    break;
                case 10: menuMediaPlayer();           
                    break;
                case 0:
                    aktif = false;
                    System.out.println("Sampai jumpa!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        }
        scanner.close();
    }

    //data untuk artis, album, dan lagu
    private static void inisialisasiData() {

        Artis joji = new Artis("Joji");
        Album nectar = new Album("Nectar",         "Joji", 2020);
        nectar.tambahLagu(new Lagu("Ew",          "Joji", "Nectar", "Alternative R&B", 202));
        nectar.tambahLagu(new Lagu("MODUS",       "Joji", "Nectar", "Alternative R&B", 218));
        nectar.tambahLagu(new Lagu("Tick Tock",   "Joji", "Nectar", "Alternative R&B", 200));
        nectar.tambahLagu(new Lagu("Upgrade",     "Joji", "Nectar", "Alternative R&B", 218));
        nectar.tambahLagu(new Lagu("Like You Do", "Joji", "Nectar", "Alternative R&B", 174));
        joji.tambahAlbum(nectar);

        Artis wknd = new Artis("The Weeknd");
        Album afterHours = new Album("After Hours", "The Weeknd", 2020);
        afterHours.tambahLagu(new Lagu("Alone Again",     "The Weeknd", "After Hours", "R&B", 266));
        afterHours.tambahLagu(new Lagu("Too Late",        "The Weeknd", "After Hours", "R&B", 239));
        afterHours.tambahLagu(new Lagu("Hardest to Love", "The Weeknd", "After Hours", "R&B", 219));
        afterHours.tambahLagu(new Lagu("Scared to Live",  "The Weeknd", "After Hours", "R&B", 213));
        afterHours.tambahLagu(new Lagu("Snowchild",       "The Weeknd", "After Hours", "R&B", 278));
        wknd.tambahAlbum(afterHours);

        Artis ed = new Artis("Ed Sheeran");
        Album divide = new Album("Divide", "Ed Sheeran", 2017);
        divide.tambahLagu(new Lagu("Eraser",            "Ed Sheeran", "Divide", "Pop", 229));
        divide.tambahLagu(new Lagu("Castle on the Hill","Ed Sheeran", "Divide", "Pop", 261));
        divide.tambahLagu(new Lagu("Dive",              "Ed Sheeran", "Divide", "Pop", 238));
        divide.tambahLagu(new Lagu("Shape of You",      "Ed Sheeran", "Divide", "Pop", 233));
        divide.tambahLagu(new Lagu("Perfect",           "Ed Sheeran", "Divide", "Pop", 263));
        ed.tambahAlbum(divide);
 
        Artis coldplay = new Artis("Coldplay");
        Album headFull = new Album("A Head Full of Dreams", "Coldplay", 2015);
        headFull.tambahLagu(new Lagu("A Head Full of Dreams", "Coldplay", "A Head Full of Dreams", "Rock", 253));
        headFull.tambahLagu(new Lagu("Birds",                 "Coldplay", "A Head Full of Dreams", "Rock", 228));
        headFull.tambahLagu(new Lagu("Hymn for the Weekend",  "Coldplay", "A Head Full of Dreams", "Rock", 257));
        headFull.tambahLagu(new Lagu("Everglow",              "Coldplay", "A Head Full of Dreams", "Rock", 300));
        headFull.tambahLagu(new Lagu("Fun",                   "Coldplay", "A Head Full of Dreams", "Rock", 167));
        coldplay.tambahAlbum(headFull);

        // Daftarkan ke koleksi global
        semuaArtis.add(joji);
        semuaArtis.add(wknd);
        semuaArtis.add(ed);
        semuaArtis.add(coldplay);

        semuaAlbum.add(nectar);
        semuaAlbum.add(afterHours);
        semuaAlbum.add(divide);
        semuaAlbum.add(headFull);

        // Kumpulkan semua lagu ke semuaLagu dan laguPerGenre (Map - Bab 11)
        for (Album album : semuaAlbum) {
            for (Lagu lagu : album.getDaftarLagu()) {
                semuaLagu.add(lagu);
                tambahKeGenreMap(lagu);
            }
        }

        System.out.println("Data berhasil dimuat: "
                + semuaLagu.size() + " lagu dari "
                + semuaAlbum.size() + " album, "
                + semuaArtis.size() + " artis.");
    }

    // Masukkan lagu ke Map berdasarkan genre (Bab 11)
    private static void tambahKeGenreMap(Lagu lagu) {
        String genre = lagu.getGenre();
        if (!laguPerGenre.containsKey(genre)) {
            laguPerGenre.put(genre, new ArrayList<>());
        }
        laguPerGenre.get(genre).add(lagu);
    }

    //Menu
    private static void tampilkanMenuUtama() {
        System.out.println();
        System.out.println("============================================");
        System.out.println("          MENU UTAMA APLIKASI MUSIK         ");
        System.out.println("============================================");
        System.out.println(" 1. Lihat Semua Lagu");
        System.out.println(" 2. Buat Playlist Baru");
        System.out.println(" 3. Tambah Lagu ke Playlist");
        System.out.println(" 4. Putar Playlist (antrian)");
        System.out.println(" 5. Riwayat & Antrian Putar");
        System.out.println(" 6. Cari Lagu (Binary Search)");
        System.out.println(" 7. Urutkan Lagu");
        System.out.println(" 8. Rekomendasi Lagu (Genre)");
        System.out.println(" 9. Kelola Playlist");
        System.out.println("10. Media Player");
        System.out.println(" 0. Keluar");
        System.out.println("============================================");
        System.out.print("Pilih menu: ");
    }

    //lihat lagu
    private static void menuLihatLagu() {
        System.out.println("\n--- LIHAT LAGU ---");
        System.out.println("1. Semua Lagu");
        System.out.println("2. Per Album");
        System.out.println("3. Per Genre");
        System.out.println("4. Per Artis");
        System.out.println("5. Lagu Favorit");
        System.out.print("Pilih: ");
        int sub = bacaInt();

        switch (sub) {
            case 1:
                tampilkanDaftarLagu(semuaLagu, "Semua Lagu");
                break;

            case 2:
                System.out.println("\nDaftar Album:");
                for (int i = 0; i < semuaAlbum.size(); i++) {
                    System.out.printf("  %d. %s%n", (i + 1), semuaAlbum.get(i));
                }
                System.out.print("Pilih album (nomor): ");
                int noAlbum = bacaInt() - 1;
                if (noAlbum >= 0 && noAlbum < semuaAlbum.size()) {
                    System.out.println();
                    semuaAlbum.get(noAlbum).tampilkan();
                } else {
                    System.out.println("Nomor tidak valid.");
                }
                break;

            case 3:
                System.out.println("\nGenre tersedia: " + laguPerGenre.keySet());
                System.out.print("Masukkan genre: ");
                String genre = scanner.nextLine().trim();
                ArrayList<Lagu> byGenre = laguPerGenre.get(genre);
                if (byGenre != null) {
                    tampilkanDaftarLagu(byGenre, "Genre: " + genre);
                } else {
                    System.out.println("Genre tidak ditemukan.");
                }
                break;

            case 4:
                System.out.println("\nDaftar Artis:");
                for (int i = 0; i < semuaArtis.size(); i++) {
                    System.out.printf("  %d. %s%n", (i + 1), semuaArtis.get(i));
                }
                System.out.print("Pilih artis (nomor): ");
                int noArtis = bacaInt() - 1;
                if (noArtis >= 0 && noArtis < semuaArtis.size()) {
                    System.out.println();
                    semuaArtis.get(noArtis).tampilkan();
                    tampilkanDaftarLagu(semuaArtis.get(noArtis).semuaLagu(),
                            "Lagu dari: " + semuaArtis.get(noArtis).getNama());
                } else {
                    System.out.println("Nomor tidak valid.");
                }
                break;

            case 5:
                if (lagiFavorit.isEmpty()) {
                    System.out.println("Belum ada lagu favorit.");
                } else {
                    ArrayList<Lagu> favList = new ArrayList<>(lagiFavorit);
                    tampilkanDaftarLagu(favList, "Lagu Favorit");
                }
                break;

            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    // =========================================================
    //  MENU 2: BUAT PLAYLIST BARU
    // =========================================================
    private static void menuBuatPlaylist() {
        System.out.println("\n--- BUAT PLAYLIST BARU ---");
        System.out.print("Masukkan nama playlist: ");
        String nama = scanner.nextLine().trim();
        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong.");
            return;
        }
        Playlist pl = new Playlist(nama);
        daftarPlaylist.add(pl);
        System.out.println("Playlist " + pl + " berhasil dibuat!");
    }

    // =========================================================
    //  MENU 3: TAMBAH LAGU KE PLAYLIST
    // =========================================================
    private static void menuTambahLaguKePlaylist() {
        System.out.println("\n--- TAMBAH LAGU KE PLAYLIST ---");
        if (daftarPlaylist.isEmpty()) {
            System.out.println("Belum ada playlist. Buat playlist terlebih dahulu.");
            return;
        }

        // Pilih playlist
        System.out.println("Pilih playlist:");
        for (int i = 0; i < daftarPlaylist.size(); i++) {
            System.out.printf("  %d. %s%n", (i + 1), daftarPlaylist.get(i));
        }
        System.out.print("Nomor playlist: ");
        int noPl = bacaInt() - 1;
        if (noPl < 0 || noPl >= daftarPlaylist.size()) {
            System.out.println("Nomor tidak valid.");
            return;
        }
        Playlist playlist = daftarPlaylist.get(noPl);

        // Tampilkan semua lagu
        tampilkanDaftarLagu(semuaLagu, "Semua Lagu");
        System.out.print("Masukkan nomor lagu yang akan ditambahkan (0 = selesai): ");

        while (true) {
            int noLagu = bacaInt();
            if (noLagu == 0) break;
            if (noLagu >= 1 && noLagu <= semuaLagu.size()) {
                Lagu lagu = semuaLagu.get(noLagu - 1);
                playlist.tambahLagu(lagu);
                System.out.println("'" + lagu.getJudul() + "' ditambahkan ke playlist.");

                // Tawarkan tambah ke favorit (Set - Bab 11)
                System.out.print("Tambah ke favorit? (y/n): ");
                String jawab = scanner.nextLine().trim().toLowerCase();
                if (jawab.equals("y")) {
                    lagiFavorit.add(lagu);
                    System.out.println("'" + lagu.getJudul() + "' ditambahkan ke favorit.");
                }

                System.out.print("Tambah lagu lagi? (0 = selesai, nomor lagu lainnya): ");
            } else {
                System.out.print("Nomor tidak valid. Coba lagi (0 = selesai): ");
            }
        }
        System.out.println("Playlist " + playlist + " selesai diperbarui.");
    }

    // =========================================================
    //  MENU 4: PUTAR PLAYLIST
    // =========================================================
    private static void menuPutarPlaylist() {
        System.out.println("\n--- PUTAR PLAYLIST ---");
        if (daftarPlaylist.isEmpty()) {
            System.out.println("Belum ada playlist.");
            return;
        }

        System.out.println("Pilih playlist:");
        for (int i = 0; i < daftarPlaylist.size(); i++) {
            System.out.printf("  %d. %s%n", (i + 1), daftarPlaylist.get(i));
        }
        System.out.print("Nomor playlist: ");
        int noPl = bacaInt() - 1;
        if (noPl < 0 || noPl >= daftarPlaylist.size()) {
            System.out.println("Nomor tidak valid.");
            return;
        }
        Playlist playlist = daftarPlaylist.get(noPl);

        if (playlist.kosong()) {
            System.out.println("Playlist ini kosong.");
            return;
        }

        System.out.println("Mode putar:");
        System.out.println("  1. Urutan normal");
        System.out.println("  2. Acak (Rekursi Shuffle)");
        System.out.print("Pilih: ");
        int mode = bacaInt();

        ArrayList<Lagu> daftar = playlist.toDaftarLagu();

        if (mode == 2) {
            // Bab 12: Rekursi untuk shuffle
            ArrayList<Lagu> salinan = new ArrayList<>(daftar);
            shuffleRekursif(salinan, salinan.size(), new Random());
            System.out.println("Playlist diacak secara rekursif. Memasukkan ke antrian...");
            for (Lagu l : salinan) {
                mediaPlayer.tambahKeAntrian(l);
            }
        } else {
            System.out.println("Memasukkan playlist ke antrian...");
            mediaPlayer.tambahPlaylistKeAntrian(playlist);
        }

        System.out.println("Memulai pemutaran...");
        mediaPlayer.mainkanBerikutnya();
    }

    // =========================================================
    //  MENU 5: RIWAYAT DAN ANTRIAN
    // =========================================================
    private static void menuRiwayatDanAntrian() {
        System.out.println("\n--- RIWAYAT & ANTRIAN PUTAR ---");
        System.out.println("1. Lihat Riwayat Putar");
        System.out.println("2. Lihat Antrian Putar");
        System.out.println("3. Kembali ke lagu sebelumnya");
        System.out.println("4. Maju ke lagu berikutnya (riwayat)");
        System.out.println("5. Putar lagu berikutnya dari antrian");
        System.out.print("Pilih: ");
        int sub = bacaInt();

        switch (sub) {
            case 1: mediaPlayer.tampilkanRiwayat();  break;
            case 2: mediaPlayer.tampilkanAntrian();  break;
            case 3: mediaPlayer.kembali();           break;
            case 4: mediaPlayer.maju();              break;
            case 5: mediaPlayer.mainkanBerikutnya(); break;
            default: System.out.println("Pilihan tidak valid.");
        }
    }

    // =========================================================
    //  MENU 6: CARI LAGU (BINARY SEARCH - Bab 13)
    // =========================================================
    private static void menuCariLagu() {
        System.out.println("\n--- CARI LAGU (Binary Search) ---");
        System.out.println("Catatan: pencarian dilakukan berdasarkan judul yang sudah diurutkan.");
        System.out.print("Masukkan judul lagu yang dicari: ");
        String judul = scanner.nextLine().trim();

        if (judul.isEmpty()) {
            System.out.println("Judul tidak boleh kosong.");
            return;
        }

        // Buat salinan dan urutkan berdasarkan judul (Bab 13: sorting)
        ArrayList<Lagu> terurut = new ArrayList<>(semuaLagu);
        Collections.sort(terurut); // menggunakan compareTo pada Lagu (sort by judul)

        // Binary search (Bab 13)
        int indeks = binarySearchJudul(terurut, judul);

        if (indeks >= 0) {
            Lagu ditemukan = terurut.get(indeks);
            System.out.println("\nLagu ditemukan (indeks " + indeks + " pada daftar terurut):");
            cetakHeaderLagu();
            System.out.println(formatBarisSong(1, ditemukan));
            garisPemisah();

            System.out.print("\nTambahkan ke antrian putar? (y/n): ");
            String jawab = scanner.nextLine().trim().toLowerCase();
            if (jawab.equals("y")) {
                mediaPlayer.tambahKeAntrian(ditemukan);
            }
        } else {
            System.out.println("Lagu '" + judul + "' tidak ditemukan.");
            System.out.println("Pastikan judul diketik persis sama (case-insensitive).");
        }
    }

    // Binary Search rekursif tidak perlu - cukup iteratif sesuai Bab 13
    private static int binarySearchJudul(ArrayList<Lagu> terurut, String judul) {
        int low  = 0;
        int high = terurut.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = terurut.get(mid).getJudul().compareToIgnoreCase(judul);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // =========================================================
    //  MENU 7: URUTKAN LAGU (Bab 13: Sorting + Comparator)
    // =========================================================
    private static void menuUrutkanLagu() {
        System.out.println("\n--- URUTKAN LAGU ---");
        System.out.println("Urutkan berdasarkan:");
        System.out.println("  1. Judul (A-Z)");
        System.out.println("  2. Artis (A-Z)");
        System.out.println("  3. Durasi (terpendek ke terpanjang)");
        System.out.println("  4. Durasi (terpanjang ke terpendek)");
        System.out.print("Pilih: ");
        int sub = bacaInt();

        ArrayList<Lagu> salinan = new ArrayList<>(semuaLagu);

        switch (sub) {
            case 1:
                // Gunakan Comparable (compareTo by judul)
                Collections.sort(salinan);
                tampilkanDaftarLagu(salinan, "Diurutkan Berdasarkan Judul (A-Z)");
                break;

            case 2:
                // Comparator lambda (Bab 13 + 19)
                Collections.sort(salinan,
                    (a, b) -> a.getArtis().compareToIgnoreCase(b.getArtis()));
                tampilkanDaftarLagu(salinan, "Diurutkan Berdasarkan Artis (A-Z)");
                break;

            case 3:
                Collections.sort(salinan,
                    (a, b) -> Integer.compare(a.getDurasi(), b.getDurasi()));
                tampilkanDaftarLagu(salinan, "Diurutkan Berdasarkan Durasi (Terpendek)");
                break;

            case 4:
                Collections.sort(salinan,
                    (a, b) -> Integer.compare(b.getDurasi(), a.getDurasi()));
                tampilkanDaftarLagu(salinan, "Diurutkan Berdasarkan Durasi (Terpanjang)");
                break;

            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    // =========================================================
    //  MENU 8: REKOMENDASI LAGU (Bab 19: Lambda & Stream)
    // =========================================================
    private static void menuRekomendasiLagu() {
        System.out.println("\n--- REKOMENDASI LAGU ---");
        System.out.println("Genre tersedia: " + laguPerGenre.keySet());
        System.out.print("Masukkan genre yang diinginkan: ");
        String genreInput = scanner.nextLine().trim();

        if (genreInput.isEmpty()) {
            System.out.println("Genre tidak boleh kosong.");
            return;
        }

        // Bab 19: Stream + Lambda untuk filter dan sort
        List<Lagu> rekomendasi = semuaLagu.stream()
            .filter(l -> l.getGenre().equalsIgnoreCase(genreInput))
            .sorted(Comparator.comparing(Lagu::getJudul))
            .collect(Collectors.toList());

        if (rekomendasi.isEmpty()) {
            System.out.println("Tidak ada lagu dengan genre '" + genreInput + "'.");
            return;
        }

        System.out.println("\nRekomendasi lagu genre '" + genreInput + "' ("
                + rekomendasi.size() + " lagu):");
        tampilkanDaftarLagu(new ArrayList<>(rekomendasi),
                "Rekomendasi - " + genreInput);

        System.out.print("Tambahkan semua ke antrian putar? (y/n): ");
        String jawab = scanner.nextLine().trim().toLowerCase();
        if (jawab.equals("y")) {
            for (Lagu l : rekomendasi) {
                mediaPlayer.tambahKeAntrian(l);
            }
            System.out.println(rekomendasi.size() + " lagu ditambahkan ke antrian.");
        }
    }

    // =========================================================
    //  MENU 9: KELOLA PLAYLIST
    // =========================================================
    private static void menuKelolaPlaylist() {
        System.out.println("\n--- KELOLA PLAYLIST ---");
        if (daftarPlaylist.isEmpty()) {
            System.out.println("Belum ada playlist.");
            return;
        }

        System.out.println("Daftar Playlist:");
        for (int i = 0; i < daftarPlaylist.size(); i++) {
            System.out.printf("  %d. %s%n", (i + 1), daftarPlaylist.get(i));
        }
        System.out.print("Pilih playlist (nomor): ");
        int noPl = bacaInt() - 1;
        if (noPl < 0 || noPl >= daftarPlaylist.size()) {
            System.out.println("Nomor tidak valid.");
            return;
        }
        Playlist playlist = daftarPlaylist.get(noPl);

        System.out.println("\nPlaylist dipilih: " + playlist);
        System.out.println("1. Lihat isi playlist");
        System.out.println("2. Hapus lagu dari playlist");
        System.out.println("3. Hapus playlist ini");
        System.out.println("4. Navigasi lagu (prev/next dalam linked list)");
        System.out.print("Pilih: ");
        int sub = bacaInt();

        switch (sub) {
            case 1:
                System.out.println();
                playlist.tampilkan();
                break;

            case 2: {
                playlist.tampilkan();
                System.out.print("Masukkan judul lagu yang dihapus: ");
                String judulHapus = scanner.nextLine().trim();
                if (playlist.hapusLagu(judulHapus)) {
                    System.out.println("Lagu '" + judulHapus + "' berhasil dihapus.");
                } else {
                    System.out.println("Lagu '" + judulHapus + "' tidak ditemukan.");
                }
                break;
            }

            case 3:
                daftarPlaylist.remove(noPl);
                System.out.println("Playlist '" + playlist.getNama() + "' dihapus.");
                break;

            case 4:
                menuNavigasiPlaylist(playlist);
                break;

            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    // Navigasi prev/next dalam custom linked list (Bab 15-16)
    private static void menuNavigasiPlaylist(Playlist playlist) {
        playlist.resetKeCurrent();
        boolean lanjut = true;
        while (lanjut) {
            Lagu laguSaatIni = playlist.getLaguSaatIni();
            if (laguSaatIni == null) {
                System.out.println("Playlist kosong.");
                break;
            }
            System.out.println("\nLagu saat ini: "
                    + laguSaatIni.getJudul() + " - " + laguSaatIni.getArtis());
            System.out.println("  1. Lagu berikutnya (next)");
            System.out.println("  2. Lagu sebelumnya (prev)");
            System.out.println("  3. Putar lagu ini");
            System.out.println("  0. Kembali");
            System.out.print("Pilih: ");
            int nav = bacaInt();
            switch (nav) {
                case 1:
                    if (playlist.majuKeLagu() == null) {
                        System.out.println("Sudah di akhir playlist.");
                    }
                    break;
                case 2:
                    if (playlist.mundurKeLagu() == null) {
                        System.out.println("Sudah di awal playlist.");
                    }
                    break;
                case 3:
                    mediaPlayer.mainkanLagu(laguSaatIni);
                    break;
                case 0:
                    lanjut = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // =========================================================
    //  MENU 10: MEDIA PLAYER
    // =========================================================
    private static void menuMediaPlayer() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println("\n--- MEDIA PLAYER ---");
            mediaPlayer.tampilkanStatus();
            System.out.println("1. Play / Lanjutkan");
            System.out.println("2. Pause");
            System.out.println("3. Stop");
            System.out.println("4. Kembali (lagu sebelumnya)");
            System.out.println("5. Maju (riwayat maju)");
            System.out.println("6. Putar berikutnya dari antrian");
            System.out.println("7. Lihat riwayat putar");
            System.out.println("8. Lihat antrian putar");
            System.out.println("9. Putar lagu dari daftar semua lagu");
            System.out.println("0. Kembali ke menu utama");
            System.out.print("Pilih: ");
            int sub = bacaInt();

            switch (sub) {
                case 1: mediaPlayer.play();              
                    break;
                case 2: mediaPlayer.pause();             
                    break;
                case 3: mediaPlayer.stop();              
                    break;
                case 4: mediaPlayer.kembali();           
                    break;
                case 5: mediaPlayer.maju();              
                    break;
                case 6: mediaPlayer.mainkanBerikutnya(); 
                    break;
                case 7: mediaPlayer.tampilkanRiwayat();  
                    break;
                case 8: mediaPlayer.tampilkanAntrian();  
                    break;

                case 9:
                    tampilkanDaftarLagu(semuaLagu, "Pilih Lagu untuk Dimainkan");
                    System.out.print("Nomor lagu: ");
                    int noLagu = bacaInt() - 1;
                    if (noLagu >= 0 && noLagu < semuaLagu.size()) {
                        mediaPlayer.mainkanLagu(semuaLagu.get(noLagu));
                    } else {
                        System.out.println("Nomor tidak valid.");
                    }
                    break;

                case 0:
                    kembali = true;
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    //  REKURSI: SHUFFLE (Bab 12)
    //  Algoritma Fisher-Yates secara rekursif
    private static void shuffleRekursif(ArrayList<Lagu> list, int n, Random rand) {
        if (n <= 1) {
            return; // base case: satu elemen, tidak perlu ditukar
        }
        int k    = rand.nextInt(n);
        Lagu tmp = list.get(k);
        list.set(k, list.get(n - 1));
        list.set(n - 1, tmp);
        shuffleRekursif(list, n - 1, rand); // rekursif untuk n-1 elemen
    }

    //  HELPER: Tampilkan daftar lagu dalam bentuk tabel
    private static void tampilkanDaftarLagu(ArrayList<Lagu> daftar, String judul) {
        System.out.println("\n=== " + judul + " (" + daftar.size() + " lagu) ===");
        if (daftar.isEmpty()) {
            System.out.println("  (kosong)");
            return;
        }
        cetakHeaderLagu();
        for (int i = 0; i < daftar.size(); i++) {
            System.out.println(formatBarisSong(i + 1, daftar.get(i)));
        }
        garisPemisah();
    }

    private static void cetakHeaderLagu() {
        garisPemisah();
        System.out.printf("| %-3s | %-33s | %-16s | %-22s | %-12s | %-6s |%n",
                "No", "Judul", "Artis", "Album", "Genre", "Durasi");
        garisPemisah();
    }

    private static String formatBarisSong(int no, Lagu l) {
        return String.format("| %-3d | %-33s | %-16s | %-22s | %-12s | %-6s |",
                no, l.getJudul(), l.getArtis(), l.getNamaAlbum(),
                l.getGenre(), l.getDurasiFormat());
    }

    private static String garisString() {
        return "+-----+-----------------------------------+------------------+"
             + "------------------------+--------------+--------+";
    }

    private static void garisPemisah() {
        System.out.println(garisString());
    }

    // =========================================================
    //  HELPER: Baca input integer dari user
    // =========================================================
    private static int bacaInt() {
        try {
            String baris = scanner.nextLine().trim();
            return Integer.parseInt(baris);
        } catch (NumberFormatException e) {
            return -999; // nilai sentinel jika bukan angka
        }
    }
}