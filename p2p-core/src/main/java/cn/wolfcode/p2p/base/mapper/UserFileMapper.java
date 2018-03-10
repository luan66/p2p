package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.UserFile;
import cn.wolfcode.p2p.base.query.UserFileQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFileMapper {
    int insert(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    List<UserFile> selectAll();

    int updateByPrimaryKey(UserFile record);

    /**
     * 查询有分类的:
     * @param userId
     * @return
     */
    List<UserFile> queryHashType(@Param("userId") Long userId, @Param("idType") boolean isType);

    /**
     * 总条数
     * @param qo
     * @return
     */
    int queryForCount(UserFileQueryObject qo);

    /**
     * 总数据
     * @param qo
     * @return
     */
    List<UserFile> queryForList(UserFileQueryObject qo);
}