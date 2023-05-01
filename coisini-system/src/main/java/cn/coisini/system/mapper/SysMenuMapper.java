package cn.coisini.system.mapper;

import cn.coisini.model.system.pojo.SysMenu;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 菜单
 */
@Mapper
public interface SysMenuMapper extends MPJBaseMapper<SysMenu> {
    List<SysMenu> findMenuListUserId(@Param("userId") Long userId);
}
