package ta.bot.fit.restController.dto;

import com.restfb.Facebook;
import com.restfb.types.webhook.messaging.MessagingParticipant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MessagingItemForNLP {
  @Facebook
  private MessagingParticipant sender;

  @Facebook
  private MessageItemForNLP message;

  public MessagingParticipant getSender() {
    return sender;
  }

  public void setSender(MessagingParticipant sender) {
    this.sender = sender;
  }

  public MessageItemForNLP getMessage() {
    return message;
  }

  public void setMessage(MessageItemForNLP message) {
    this.message = message;
  }
}
