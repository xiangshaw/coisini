package cn.coisini.system.mapper;

import cn.coisini.model.system.pojo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xiaoxiang
 * @Description: 用户表
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
