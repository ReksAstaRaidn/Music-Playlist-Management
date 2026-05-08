package ManajemenPlaylistMusik.Classes;

public class Album extends Lagu {
    
    private String namaAlbum;

    public Album(String judul, int durasi, String namaAlbum) {
        super(judul, durasi);
        this.namaAlbum = namaAlbum;
    }

    public String getNamaAlbum() {
        return namaAlbum;
    }

    @Override
    public String toString() {
        return super.toString() + " - Album: " + namaAlbum;
    }

}
