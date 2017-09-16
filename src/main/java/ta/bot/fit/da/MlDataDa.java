package ta.bot.fit.da;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ta.bot.fit.domain.dto.MlDataDto;
import ta.bot.fit.persistence.mapper.MlDataMapper;

import java.util.List;

/**
 * Created by Joey on 7/17/17.
 */
@Repository
public class MlDataDa {
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MlDataDa.class.getName());

    /**
     * Spring Beans Injection
     */
    @Autowired
    private MlDataMapper mlDataMapper;

    /**********************
     *
     * DA METHODS
     *
     *********************/

    public List<MlDataDto> getGifsByKeyword(String keyword){
        return MlDataDto.getDtoListFromDbDo(mlDataMapper.selectGifsByKeyword("%"+keyword+"%"));
    }


}
