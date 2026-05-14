import java.util.ArrayList;

public class Playlist {

    private String nama;
    private PlaylistNode head;
    private PlaylistNode tail;
    private PlaylistNode current; //penunjuk lagu saat ini
    private int ukuran;

    public Playlist(String nama) {
        this.nama    = nama;
        this.head    = null;
        this.tail    = null;
        this.current = null;
        this.ukuran  = 0;
    }

    // Tambah lagu di akhir linked list
    public void tambahLagu(Lagu lagu) {
        PlaylistNode node = new PlaylistNode(lagu);
        if (head == null) {
            head    = node;
            tail    = node;
            current = node;
        } else {
            tail.next  = node;
            node.prev  = tail;
            tail       = node;
        }
        ukuran++;
    }

    // Hapus lagu berdasarkan judul, kembalikan true jika berhasil
    public boolean hapusLagu(String judul) {
        PlaylistNode curr = head;
        while (curr != null) {
            if (curr.data.getJudul().equalsIgnoreCase(judul)) {
                if (curr.prev != null) {
                    curr.prev.next = curr.next;
                } else {
                    head = curr.next;
                }
                if (curr.next != null) {
                    curr.next.prev = curr.prev;
                } else {
                    tail = curr.prev;
                }
                // Pindahkan pointer current jika sedang di node ini
                if (current == curr) {
                    current = (curr.next != null) ? curr.next : curr.prev;
                }
                ukuran--;
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    // Navigasi maju (prev -> next)
    public Lagu majuKeLagu() {
        if (current != null && current.next != null) {
            current = current.next;
            return current.data;
        }
        return null;
    }

    // Navigasi mundur (next -> prev)
    public Lagu mundurKeLagu() {
        if (current != null && current.prev != null) {
            current = current.prev;
            return current.data;
        }
        return null;
    }

    // Reset pointer ke awal
    public void resetKeCurrent() {
        current = head;
    }

    public Lagu getLaguSaatIni() {
        return (current != null) ? current.data : null;
    }

    // Konversi linked list ke ArrayList untuk kebutuhan lain 
    public ArrayList<Lagu> toDaftarLagu() {
        ArrayList<Lagu> list = new ArrayList<>();
        PlaylistNode curr = head;
        while (curr != null) {
            list.add(curr.data);
            curr = curr.next;
        }
        return list;
    }

    public String getNama() { 
        return nama;
    }
    
    public int    getUkuran() {
        return ukuran;
    }
    
    public boolean kosong() { 
        return ukuran == 0;
    }

    public void tampilkan() {
        System.out.println("Playlist : " + nama + " (" + ukuran + " lagu)");
        if (kosong()) {
            System.out.println("  (kosong)");
            return;
        }
        garis();
        System.out.printf("| %-3s | %-33s | %-16s | %-6s |%n",
                "No", "Judul", "Artis", "Durasi");
        garis();
        PlaylistNode curr = head;
        int no = 1;
        while (curr != null) {
            // Tanda * menunjukkan posisi current
            String tanda = (curr == current) ? "*" : " ";
            System.out.printf("|%s%-3d | %-33s | %-16s | %-6s |%n",
                    tanda, no++,
                    curr.data.getJudul(),
                    curr.data.getArtis(),
                    curr.data.getDurasiFormat());
            curr = curr.next;
        }
        garis();
        System.out.println("(* = posisi saat ini)");
    }

    private void garis() {
        System.out.println("+------+-----------------------------------+------------------+--------+");
    }

    @Override
    public String toString() {
        return "\"" + nama + "\" [" + ukuran + " lagu]";
    }
}