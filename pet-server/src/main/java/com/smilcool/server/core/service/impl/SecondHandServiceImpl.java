package com.smilcool.server.core.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smilcool.server.common.enums.DicTypeEnum;
import com.smilcool.server.common.exception.SmilcoolException;
import com.smilcool.server.common.util.BeanUtil;
import com.smilcool.server.core.dao.SecondHandMapper;
import com.smilcool.server.core.pojo.form.SecondHandAddForm;
import com.smilcool.server.core.pojo.form.SecondHandQueryForm;
import com.smilcool.server.core.pojo.po.SecondHand;
import com.smilcool.server.core.pojo.vo.SecondHandVO;
import com.smilcool.server.core.service.ResourceService;
import com.smilcool.server.core.service.SecondHandService;
import com.smilcool.server.core.service.UserService;

/**
 * @author Angus
 * @date 2019/4/14
 */
@Service
public class SecondHandServiceImpl implements SecondHandService {

    @Autowired
    private SecondHandMapper secondHandMapper;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @Override
    public SecondHand getSecondHand(Integer id) {
        SecondHand secondHand = secondHandMapper.selectByPrimaryKey(id);
        if (secondHand == null) {
            throw new SmilcoolException("二手交易不存在");
        }
        return secondHand;
    }

    @Override
    public SecondHand addSecondHand(SecondHandAddForm form) {
        // 获取当前登录用户
        Integer currentUserId = userService.currentUserId();
        // 添加资源，获取资源ID
        Integer resourceId = resourceService
                .addResource(currentUserId, DicTypeEnum.SECOND_HAND_CATEGORY.name, form.getSecondHandCategory());
        // 补全信息，添加二手交易
        SecondHand secondHand = BeanUtil.copyProp(form, SecondHand.class);
        secondHand.setUserId(currentUserId);
        secondHand.setResourceId(resourceId);
        secondHandMapper.insertSelective(secondHand);
        return getSecondHand(secondHand.getId());
    }

    @Override
    public Page<SecondHandVO> pageSecondHandVO(Page page, SecondHandQueryForm form) {
        return secondHandMapper.selectSecondHandVOByQueryForm(page, form);
    }

    @Override
    public SecondHandVO getSecondHandVO(Integer id) {
        SecondHandVO secondHandVO = secondHandMapper.selectSecondHandVOByPrimaryKey(id);
        if (secondHandVO == null) {
            throw new SmilcoolException("二手交易不存在");
        }
        return secondHandVO;
    }

	@Override
	public Integer updateStatus(Integer id) {
		SecondHand hand=secondHandMapper.selectByPrimaryKey(id);
		Date currentTime=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		hand.setState(1);
		hand.setStartdate(dateString);
		hand.setEnddate(getMonthlyRepayDate(currentTime,hand.getPrice(),5));
		secondHandMapper.updateByPrimaryKey(hand);
		return 1;
	}
	
    public static String getMonthlyRepayDate(Date startDate, Integer phaseNumber, Integer type) {
  	    try {
			SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd");
			startDate=sdf.parse(sdf.format(startDate));  
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(type, phaseNumber);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	    return null;
    }

}
