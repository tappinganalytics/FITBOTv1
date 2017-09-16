package ta.bot.fit.restController.dto;

import com.restfb.Facebook;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MessageItemForNLP {
  @Facebook
  private String text;

  @Facebook
  private NLPObject nlp;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public NLPObject getNlp() {
    return nlp;
  }

  public void setNlp(NLPObject nlp) {
    this.nlp = nlp;
  }
}
