package cn.reinforce.web.fly.dao;

import cn.reinforce.web.fly.model.http.FateRequest;
import cn.reinforce.web.fly.model.http.FateRequestPieView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FateRequestMapper {
    int delete(String requestId);

    int save(FateRequest fateRequest);

    int insertSelective(FateRequest fateRequest);

    FateRequest find(String requestId);

    int updateByPrimaryKeySelective(FateRequest fateRequest);

    int update(FateRequest fateRequest);

    List<FateRequest> page(@Param("name") String name, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("url") String url, @Param("error") boolean error, @Param("start") int start, @Param("pageSize") int pageSize);

    long count(@Param("name") String name, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("url") String url, @Param("error") boolean error);

    List<FateRequest> searchError(@Param("start") int start, @Param("pageSize") int pageSize);

    long countError();

    List<FateRequest> searchAreaBlank(@Param("start") int start, @Param("pageSize") int pageSize);

    public void updateArea(@Param("ip") String ip, @Param("area") String area, @Param("operator") String operator, @Param("requestTime") String requestTime);


    public List<FateRequest> searchNot(@Param("name") String name, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("url") String url, @Param("error") boolean error, @Param("start") int start, @Param("pageSize") int pageSize);

    public long countNot(@Param("name") String name, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("url") String url, @Param("error") boolean error);

    public List<FateRequestPieView> statisticsLoginTime();

}