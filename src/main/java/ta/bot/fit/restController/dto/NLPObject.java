package ta.bot.fit.restController.dto;

import com.restfb.Facebook;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class NLPObject {
    @Facebook
    private NLPEntities entities;

    public NLPEntities getEntities() {
        return entities;
    }

    public void setEntities(NLPEntities entities) {
        this.entities = entities;
    }
}
