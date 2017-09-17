package ta.bot.fit.restController;

import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.messaging.MessageItem;
import com.restfb.types.webhook.messaging.MessagingAttachment;
import com.restfb.types.webhook.messaging.MessagingItem;
import com.restfb.types.webhook.messaging.PostbackItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ta.bot.fit.restController.dto.*;
import ta.bot.fit.restSender.FbMsgSender;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Joey on 4/9/17.
 */
@RestController
@RequestMapping(value = "/chatBot")
@PropertySource("classpath:config.properties")
public class RestFbChatBotController {

    static Logger log = Logger.getLogger(RestFbChatBotController.class.getName());

    private String VERIFY_TOKEN;
    private String MODE_SUBSCRIBE;

    /*********************************
     * Spring Beans Inject
     *********************************/
    @Autowired
    private Environment env;


    private FbMsgSender fbMsgSender;
    @Autowired
    public void setFbMsgSender(FbMsgSender fbMsgSender){
        this.fbMsgSender = fbMsgSender;
    }

    /*********************************
     *              Init             *
     ********************************/
    @PostConstruct
    public void initIt() throws Exception {
        VERIFY_TOKEN = env.getProperty("VERIFY_TOKEN");
        MODE_SUBSCRIBE = env.getProperty("MODE_SUBSCRIBE");
//        log.debug("$$$$$$ VERIFY_TOKEN="+VERIFY_TOKEN);
//        log.debug("$$$$$$ MODE_SUBSCRIBE="+MODE_SUBSCRIBE);
    }

    /*********************************
     *              API             *
     ********************************/

    @RequestMapping(value = "/webhook", method= RequestMethod.GET)
    public String verify(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") String challenge,
            @RequestParam("hub.verify_token") String verifyToken) {
        if(!verifyToken.equals(VERIFY_TOKEN)) {
            log.debug("############################ Wrong token: "+verifyToken);
            return null;
        }else if(!mode.equals(MODE_SUBSCRIBE)){
            log.debug("############################ Wrong mode: "+mode);
            return null;
        }
        return challenge;
    }

    @RequestMapping(value = "/webhook", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void verify(@RequestBody String pushedJsonAsString) {
        JsonMapper mapper = new DefaultJsonMapper();

        /**
         * NLP for Greeting ------ START  ------
         */

        WebhookForNLP webhooknlp =
                mapper.toJavaObject(pushedJsonAsString, WebhookForNLP.class);
        log.debug("pushedJsonAsString "+ pushedJsonAsString);
        log.debug("webhooknlp "+ webhooknlp.toString());

        for(WebhookEntryForNLP entry : webhooknlp.getEntryList()) {
            if(entry!=null){
                for(MessagingItemForNLP messaging : entry.getMessaging()){
                    if(messaging.getMessage()!=null){
                        if (receivedNLPMessage(messaging) == true)
                            return;
                    }
                }
            }
        }

        /**
         * NLP for Greeting ------ END  ------
         */

        /**
         * Normal Web-hook ------ START  ------
         */

        WebhookObject webhookObject =
                mapper.toJavaObject(pushedJsonAsString, WebhookObject.class);
        if(webhookObject.getObject().equals("page")){
            for(WebhookEntry entry : webhookObject.getEntryList()){
                if(entry!=null){
                    String pageId = entry.getId();
                    Date timeOfEvent = entry.getTime();

                    for(MessagingItem messaging : entry.getMessaging()){
                        if(messaging.getMessage()!=null){
                            receivedMessage(messaging);
                        }
                    }
                }
            }
        }
    }

    private void receivedMessage(MessagingItem messaging){
        String senderId = messaging.getSender().getId();
        String recipientId = messaging.getRecipient().getId();
        Date timeOfMessage = messaging.getTimestamp();
        MessageItem message = messaging.getMessage();

        String messageId = message.getMid();
        String messageText = message.getText();
        List<MessagingAttachment> messageAttachments = message.getAttachments();
        PostbackItem postback = messaging.getPostback();

        if(messageText != null){
            if(messageText.length()>0) {
                if(messageText.equals("button"))fbMsgSender.sendButtonMessage(senderId);
                else if(messageText.equals("bubble"))fbMsgSender.sendGenericMessage(senderId);
//                else if(messageText.equals("welcome"))fbMsgSender.sendWelcomeMessage(senderId);
//                else if(messageText.equals("menu")) fbMsgSender.sendPersistentMenu(senderId);
                else fbMsgSender.sendFitGifMessage(senderId, messageText);
            } else fbMsgSender.sendTextMessage(senderId, "Blank message...");
        }else if(messageAttachments!=null){
//            fbMsgSender.sendImageMessage(senderId);
            fbMsgSender.sendTextMessage(senderId, "Thank you!");
        }else if(postback!=null){
            fbMsgSender.sendTextMessage(senderId, "Got your option: "+postback.getPayload());
        }
    }




    /****************************************************************
     * NLP Functions
     ****************************************************************/

    private boolean saveNLPinMap(NLPItem item, String key, double lowConfidence, Map map)
    {
        if (item.getValue().equals("true") && item.getConfidence() > lowConfidence)
        {
            log.debug(key + " confidence " + item.getConfidence());
            map.put(key, item.getConfidence());
            return true;
        }
        return false;
    }

    private boolean receivedNLPMessage(MessagingItemForNLP messaging){
        String senderId = messaging.getSender().getId();
        MessageItemForNLP message = messaging.getMessage();
        if (message!=null) {
            NLPObject nlpobject = message.getNlp();
            if (nlpobject != null) {
                NLPEntities nlpentities = nlpobject.getEntities();
                Map<String, Double> map;
                map = new HashMap<String, Double>();
                if (nlpentities != null) {
                    for (NLPItem greetings : nlpentities.getGreetings()) {
                        if (saveNLPinMap(greetings, "greetings", 0.8, map))
                            break;
                    }
                    for (NLPItem bye : nlpentities.getBye()) {
                        if (saveNLPinMap(bye, "bye", 0.7, map))
                            break;
                    }
                    for (NLPItem thanks : nlpentities.getThanks()) {
                        if (saveNLPinMap(thanks, "thanks", 0.8, map))
                            break;
                    }
                    if (!map.isEmpty()) {
                        String maxKey = Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
                        if (maxKey.equals("greetings")) {
                            fbMsgSender.sendTextMessage(senderId, "Hello! I am FitBot.");
                            fbMsgSender.sendTextMessage(senderId, "What exercise would you like to do?");
                        } else if (maxKey.equals("thanks")) {
                            fbMsgSender.sendTextMessage(senderId, "I'm glad to help! Is there anything else you would like to know?");
                        } else if (maxKey.equals("bye")) {
                            fbMsgSender.sendTextMessage(senderId, "Stay strong and keep training! See you next time!");
                        } else
                            return false;
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /****************************************************************
     * NLP Functions  ------ END --------
     ***************************************************************/

}
