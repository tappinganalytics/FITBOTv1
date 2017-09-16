package ta.bot.fit.restSender;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.send.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ta.bot.fit.domain.dto.MlDataDto;
import ta.bot.fit.frontService.MlDataService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 6/12/16.
 */
@Service
public class FbMsgSender {
    static Logger log = Logger.getLogger(FbMsgSender.class.getName());

    /*********************************
     * Spring Beans Inject
     *********************************/

    @Autowired
    private Environment env;

    private MlDataService mlDataService;
    @Autowired
    public void setMlDataService(MlDataService mlDataService) {
        this.mlDataService = mlDataService;
    }

    /*********************************
     *            Member             *
     ********************************/

    private String PAGE_ACCESS_TOKEN;
    private FacebookClient pageClient;

    /*********************************
     *              Init             *
     ********************************/
    @PostConstruct
    public void initIt() throws Exception {
        PAGE_ACCESS_TOKEN = env.getProperty("PAGE_ACCESS_TOKEN");
        pageClient = new DefaultFacebookClient(PAGE_ACCESS_TOKEN, Version.VERSION_2_6);

    }

    /*********************************
     *       Private Method          *
     ********************************/

    private void sendMessage(IdMessageRecipient recipient, Message message) {
        SendResponse resp = pageClient.publish("me/messages", SendResponse.class,
                Parameter.with("recipient", recipient), // the id or phone recipient
                Parameter.with("message", message)); // one of the messages from above

    }

    /*********************************
     *       Basic FB Method          *
     ********************************/

    public void sendTextMessage(String recipientId, String msg) {
        sendMessage(new IdMessageRecipient(recipientId), new Message(msg));
    }

    public void sendImageMessage(String recipientId) {
        MediaAttachment image =
                new MediaAttachment(MediaAttachment.Type.IMAGE, "https://s3-us-west-1.amazonaws.com/exrx-workout-gif/Alternating+Bird+Dog+(on+exercise+ball)");
        sendMessage(new IdMessageRecipient(recipientId), new Message(image));
    }

    public void sendImageMessage(String recipientId, String gifUrl) {
        MediaAttachment image =
                new MediaAttachment(MediaAttachment.Type.IMAGE, gifUrl);
        sendMessage(new IdMessageRecipient(recipientId), new Message(image));
    }

    public void sendButtonMessage(String recipientId) {
        ButtonTemplatePayload payload = new ButtonTemplatePayload("Workout Menu");

        // build a new button that links to a web url
        WebButton webButton = new WebButton("Google Web", "https://www.google.com");

        // build a button that sends a postback
        PostbackButton postbackButton = new PostbackButton("FitBot Awesome", "FitBot is just awesome!");

        payload.addButton(webButton);
        payload.addButton(postbackButton);

        TemplateAttachment templateAttachment = new TemplateAttachment(payload);
        Message imageMessage = new Message(templateAttachment);

        sendMessage(new IdMessageRecipient(recipientId), imageMessage);
    }

    public void sendGenericMessage(String recipientId) {
        GenericTemplatePayload payload = new GenericTemplatePayload();

        // Create a bubble with a web button
        Bubble firstBubble = new Bubble("Workout bubble");
        WebButton webButton = new WebButton("Google Web", "https://www.google.com");
        firstBubble.addButton(webButton);

        // Create a bubble with a postback button
        Bubble secondBubble = new Bubble("Option bubble");
        PostbackButton postbackButton = new PostbackButton("FitBot Awesome", "FitBot is just awesome!");
        secondBubble.addButton(postbackButton);

        payload.addBubble(firstBubble);
        payload.addBubble(secondBubble);

        TemplateAttachment templateAttachment = new TemplateAttachment(payload);
        Message imageMessage = new Message(templateAttachment);

        sendMessage(new IdMessageRecipient(recipientId), imageMessage);
    }


    /*********************************
     *       FitBot Method          *
     ********************************/


    /**
     Go search Gif by keyword, if none, return no data found, if multiple, return random one.
     */
    public void sendFitGifMessage(String recipientId, String msg) {
        MlDataDto mlData = mlDataService.getSingleRandomGifsByKeyword(msg);
        if(mlData==null) sendTextMessage(recipientId, "Sorry, no data found!");
        else sendImageMessage(recipientId, mlData.getGif());
    }
}
