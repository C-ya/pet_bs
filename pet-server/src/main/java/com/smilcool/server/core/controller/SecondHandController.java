package com.smilcool.server.core.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smilcool.server.core.pojo.dto.Result;
import com.smilcool.server.core.pojo.form.SecondHandAddForm;
import com.smilcool.server.core.pojo.form.SecondHandQueryForm;
import com.smilcool.server.core.pojo.po.SecondHand;
import com.smilcool.server.core.pojo.vo.SecondHandVO;
import com.smilcool.server.core.service.SecondHandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Angus
 * @date 2019/4/1
 */
@Api(tags = "2.5", description = "宠物")
@RestController
@RequestMapping("/second-hand")
public class SecondHandController {

    @Autowired
    private SecondHandService secondHandService;

    @ApiOperation("宠物添加")
    @PostMapping
    public Result<SecondHand> addSecondHand(@RequestBody @Valid SecondHandAddForm form) {
        SecondHand secondHand = secondHandService.addSecondHand(form);
        return Result.success(secondHand);
    }

    @ApiOperation("宠物分页")
    @GetMapping("/page")
    public Result<Page<SecondHandVO>> pageSecondHandVO(Page page, SecondHandQueryForm form) {
        Subject currentUser = SecurityUtils.getSubject();
        Integer userId = (Integer) currentUser.getPrincipal();
        form.setUserId(userId);
        Page<SecondHandVO> secondHandPage = secondHandService.pageSecondHandVO(page, form);
        return Result.success(secondHandPage);
    }

    @ApiOperation("宠物详情")
    @GetMapping("/{id}")
    public Result<SecondHandVO> getSecondHandVO(@PathVariable Integer id) {
        SecondHandVO secondHandVO = secondHandService.getSecondHandVO(id);
        return Result.success(secondHandVO);
    }
    
    @GetMapping("/updateStatus")
    public Result<Integer> updateStatus(Integer id) {
    	secondHandService.updateStatus(id);
        return Result.success(1);
    }
}
