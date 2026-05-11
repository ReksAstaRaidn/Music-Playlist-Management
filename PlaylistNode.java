public class PlaylistNode {

    public Lagu        data;
    public PlaylistNode next;
    public PlaylistNode prev;

    public PlaylistNode(Lagu data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}