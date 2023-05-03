package cn.coisini.system.mapper;

import cn.coisini.model.system.pojo.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: xiaoxiang
 * @Description: 部门
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    @Select("select d.id, d.parent_id, d.tree_path, d.dept_name, d.sort_value, d.leader, d.phone, d.email, d.status," +
            "(select dept_name from sys_dept where id = d.parent_id) parent_name " +
            "from sys_dept d " +
            "where d.id = #{deptId}")
    SysDept findDeptById(@Param("deptId") Long deptId);
}
