package ta.bot.fit.persistence.dbdo;

/**
 * Created by Joey on 8/12/17.
 */
public class MlDataDbDo {
    private static final long serialVersionUID = 1L;
    private long id;
    private String exerciseName;
    private String gif;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }
}
