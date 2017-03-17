package in.geobullet.csci_4176_project.db.Classes;


/* Class that corresponds to the BoardPosterPair database table */

public class BoardPosterPair {

    private int id;
    private int boardId;
    private int posterId;

    public BoardPosterPair() {

    }

    public BoardPosterPair(int id, int boardId, int posterId) {
        this.id = id;
        this.boardId = boardId;
        this.posterId = posterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }

    @Override
    public String toString() {
        return "BoardPosterPair{" +
                "id=" + id +
                ", boardId=" + boardId +
                ", posterId=" + posterId +
                '}';
    }
}
