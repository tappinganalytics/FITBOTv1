package ta.bot.fit.domain;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ta.bot.fit.da.MlDataDa;
import ta.bot.fit.domain.dto.MlDataDto;

import java.util.List;

/**
 * Created by Joey on 8/12/17. Modified on 2017/09/16 again!
 */
@Service
public class MlDataHelper {
    static Logger log = Logger.getLogger(MlDataHelper.class.getName());

    /*********************************
     * Spring Beans Inject
     *********************************/
    private MlDataDa mlDataDa;
    @Autowired
    public void setMlDataDa(MlDataDa mlDataDa) {
        this.mlDataDa = mlDataDa;
    }


    /*********************************
     *              Init             *
     ********************************/
//    @PostConstruct
//    public void initIt() throws Exception {
//        MAX_DOWNLOAD_PO = Integer.parseInt(env.getProperty("PO_MAX_DOWNLOAD_TIMES"));
//    }

    /***********************
     *  GENERAL FUNCTIONS
     **********************/

    public List<MlDataDto> getGifsByKeyword(String keyword){
        return mlDataDa.getGifsByKeyword(keyword);
    }
}
