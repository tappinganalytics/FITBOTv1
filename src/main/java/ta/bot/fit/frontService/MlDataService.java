package ta.bot.fit.frontService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ta.bot.fit.domain.MlDataHelper;
import ta.bot.fit.domain.dto.MlDataDto;

import java.util.List;
import java.util.Random;

/**
 * Created by Joey on 7/17/17.
 */
@Service
public class MlDataService {
    static Logger log = Logger.getLogger(MlDataService.class.getName());

    /*********************************
     * Spring Beans Inject
     *********************************/
    private MlDataHelper mlDataHelper;
    @Autowired
    public void setMlDataHelper(MlDataHelper mlDataHelper) {
        this.mlDataHelper = mlDataHelper;
    }


    /*********************************
     *              Init             *
     ********************************/
//    @PostConstruct
//    public void initIt() throws Exception {
//        MAX_DOWNLOAD_QUOTATION = Integer.parseInt(env.getProperty("SALES_QUOTATION_MAX_DOWNLOAD_TIMES"));
//    }

    /***********************
     *  GENERAL FUNCTIONS
     **********************/

    public MlDataDto getSingleRandomGifsByKeyword(String keyword){
        List<MlDataDto> mlDataList = mlDataHelper.getGifsByKeyword(keyword);
        int size = mlDataList.size();
        if(size==0) return null;
        else if(size==1) return mlDataList.get(0);
        int max = size-1;
        int min = 0;
        Random seed = new Random();
        int index = seed.nextInt((max - min) + 1) + min;
        return mlDataList.get(index);
    }


}
