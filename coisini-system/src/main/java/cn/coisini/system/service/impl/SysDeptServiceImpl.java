package cn.coisini.system.service.impl;

import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysDept;
import cn.coisini.model.system.vo.AssginMenuVo;
import cn.coisini.model.system.vo.RouterVo;
import cn.coisini.system.mapper.SysDeptMapper;
import cn.coisini.system.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 部门 服务实现类
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Override
    public List<SysDept> findNodes() {
        return null;
    }

    @Override
    public Result<SysDept> saveDept(SysDept sysDept) {
        return null;
    }

    @Override
    public Result<SysDept> updateDeptById(SysDept sysDept) {
        return null;
    }

    @Override
    public Result<SysDept> removeDeptById(Long id) {
        return null;
    }

    @Override
    public List<SysDept> findDeptByRoleId(Long id) {
        return null;
    }

    @Override
    public Result<SysDept> doAssign(AssginMenuVo menuVo) {
        return null;
    }

    @Override
    public List<RouterVo> findUserDeptList(Long userId) {
        return null;
    }

    @Override
    public List<String> findUserPostList(Long userId) {
        return null;
    }

    @Override
    public Result<SysDept> updateStatus(Long id, Boolean status) {
        return null;
    }
}
