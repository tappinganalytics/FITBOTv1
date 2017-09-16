package ta.bot.fit.restController.dto;

import com.restfb.Facebook;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class WebhookEntryForNLP {

  @Facebook
  private List<MessagingItemForNLP> messaging = new ArrayList<MessagingItemForNLP>();

  public List<MessagingItemForNLP> getMessaging() {
    return messaging;
  }

  public void setMessaging(List<MessagingItemForNLP> messaging) {
    this.messaging = messaging;
  }
}
