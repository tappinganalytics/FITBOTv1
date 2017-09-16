package ta.bot.fit.domain.dto;

import ta.bot.fit.persistence.dbdo.MlDataDbDo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 8/12/17.
 */
public class MlDataDto {
    private long id;
    private String exerciseName;
    private String gif;

    public MlDataDto() {
    }

    public MlDataDto(MlDataDbDo x) {
        this.id = x.getId();
        this.exerciseName = x.getExerciseName();
        this.gif = x.getGif();
    }

    public static List<MlDataDto> getDtoListFromDbDo(List<MlDataDbDo> oldList){
        List<MlDataDto> ret = new ArrayList<>(oldList.size());
        for(MlDataDbDo x : oldList){
            MlDataDto y = new MlDataDto(x);
            ret.add(y);
        }
        return ret;
    }

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
