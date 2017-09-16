package ta.bot.fit.restController.dto;

import com.restfb.Facebook;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class NLPEntities {
    @Facebook
    private List<NLPItem > greetings = new ArrayList<NLPItem>();

    @Facebook
    private List<NLPItem > bye = new ArrayList<NLPItem>();

    @Facebook
    private List<NLPItem > thanks = new ArrayList<NLPItem>();

    public List<NLPItem> getGreetings() {
        return greetings;
    }

    public void setGreetings(List<NLPItem> greetings) {
        this.greetings = greetings;
    }

    public List<NLPItem> getBye() {
        return bye;
    }

    public void setBye(List<NLPItem> bye) {
        this.bye = bye;
    }

    public List<NLPItem> getThanks() {
        return thanks;
    }

    public void setThanks(List<NLPItem> thanks) {
        this.thanks = thanks;
    }
}
