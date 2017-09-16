package ta.bot.fit.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ta.bot.fit.persistence.dbdo.MlDataDbDo;

import java.util.List;

/**
 * Created by Joey on 6/13/17.
 */
@Repository
public interface MlDataMapper {
    List<MlDataDbDo> selectGifsByKeyword(@Param("keyword") String keyword);
}
