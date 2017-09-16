package ta.bot.fit.restController.dto;

import com.restfb.Facebook;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class NLPItem {
    @Facebook
    private double confidence;

    @Facebook
    private String value;

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
