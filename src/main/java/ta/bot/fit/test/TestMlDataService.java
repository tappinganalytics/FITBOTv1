package ta.bot.fit.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ta.bot.fit.domain.dto.MlDataDto;
import ta.bot.fit.frontService.MlDataService;

/**
 * Created by Joey on 8/12/17.
 */
@Controller
public class TestMlDataService {

    static Logger log = Logger.getLogger(TestMlDataService.class.getName());

    /*********************************
     * Spring Beans Inject
     *********************************/

    private MlDataService mlDataService;
    @Autowired
    public void setMlDataService(MlDataService mlDataService) {
        this.mlDataService = mlDataService;
    }

    /****************************
     *        Web API          *
     ****************************/

//    @RequestMapping(value = "/testGifDb")
//    public ModelAndView testGifDb() {
//        ModelAndView mv = new ModelAndView();
//        MlDataDto mlData = mlDataService.getSingleRandomGifsByKeyword("safety");
//        log.debug("************************************* ID: "+mlData.getId());
//        log.debug("************************************* Exercise Name: "+mlData.getExerciseName());
//        log.debug("************************************* Gif Url: "+mlData.getGif());
//        mv.setViewName("main");
//        return mv;
//    }
}
