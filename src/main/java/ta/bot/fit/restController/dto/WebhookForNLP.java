package ta.bot.fit.restController.dto;

import com.restfb.Facebook;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class WebhookForNLP {

  @Facebook("entry")
  private List<WebhookEntryForNLP> entryList = new ArrayList<WebhookEntryForNLP>();

  public List<WebhookEntryForNLP> getEntryList() {
    return entryList;
  }

  public void setEntryList(List<WebhookEntryForNLP> entryList) {
    this.entryList = entryList;
  }
}

