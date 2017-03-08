package in.geobullet.csci_4176_project.db;


/* Class that corresponds to the UserFavorite database table */

public class UserFavorite {

    private int id;
    private int userId;
    private int boardPosterPairId;

    public UserFavorite() {

    }

    public UserFavorite(int id, int userId, int boardPosterPairId) {
        this.id = id;
        this.userId = userId;
        this.boardPosterPairId = boardPosterPairId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBoardPosterPairId() {
        return boardPosterPairId;
    }

    public void setBoardPosterPairId(int boardPosterPairId) {
        this.boardPosterPairId = boardPosterPairId;
    }
}
