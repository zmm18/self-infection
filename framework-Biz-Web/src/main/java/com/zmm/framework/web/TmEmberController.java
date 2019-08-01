package com.zmm.framework.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zmm.framework.bean.Member;
import com.zmm.framework.model.MemberPhoto;
import com.zmm.framework.model.TmEmber;
import com.zmm.framework.service.MemberService;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import top.ibase4j.core.base.AppBaseController;
import top.ibase4j.core.support.Assert;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.SecurityUtil;
import top.ibase4j.core.util.UploadUtil;
import top.ibase4j.core.util.WebUtil;
import top.ibase4j.model.Login;
/**
 * 会员 前端控制器
 * @Name TmEmberController
 * @Author 900045
 * @Created by 2019/8/1 0001
 */
@Controller
@RequestMapping("/app/member/")
@Api(value = "会员管理接口", description = "APP-个人中心-个人信息管理接口")
public class TmEmberController extends AppBaseController<TmEmber, MemberService> {

	@ApiOperation(value = "获取个人基本信息", produces = MediaType.APPLICATION_JSON_VALUE, response = TmEmber.class)
	@RequestMapping(value = "getUserBaseInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public Object getBaseInfo(HttpServletRequest request, String id) {
		Member param = WebUtil.getParameter(request, Member.class);
		Assert.notNull(param.getId(), "ID");
		Object result = service.getBaseInfo(param.getId());
		ModelMap modelMap = new ModelMap();
		return setSuccessModelMap(modelMap, result);
	}

	@ApiOperation(value = "获取个人信息", produces = MediaType.APPLICATION_JSON_VALUE, response = TmEmber.class)
	@RequestMapping(value = "getUserInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public Object get(HttpServletRequest request, String id) {
		Member param = WebUtil.getParameter(request, Member.class);
		Long memberId = getCurrUser(request);
		if (DataUtil.isNotEmpty(memberId)) {
			param.setId(memberId);
		}
		Assert.notNull(param.getId(), "ID");
		TmEmber result = service.getInfo(param.getId());
		result.setPassword(null);
		ModelMap modelMap = new ModelMap();
		return setSuccessModelMap(modelMap, result);
	}

	@PostMapping("modifyUserInfo")
	@ApiOperation(value = "修改个人信息", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object update(HttpServletRequest request, Member member) {
		TmEmber param = WebUtil.getParameter(request, TmEmber.class);
		Long id = getCurrUser(request);
		if (DataUtil.isNotEmpty(id)) {
			member.setId(id);
		}
		Assert.notNull(param.getId(), "ID");
		TmEmber user = service.queryById(param.getId());
		Assert.notNull(user, "MEMBER", param.getId());
		if (StringUtils.isNotBlank(param.getPassword())) {
			if (!param.getPassword().equals(user.getPassword())) {
				param.setPassword(SecurityUtil.encryptPassword(param.getPassword()));
			}
		}
		ModelMap modelMap = new ModelMap();
		return super.update(request, modelMap, param);
	}

	@PostMapping("uploadPhoto")
	@ApiOperation(value = "修改个人头像", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object uploadPhoto(HttpServletRequest request, MemberPhoto param) {
		Long id = getCurrUser(request);
		if (DataUtil.isNotEmpty(id)) {
			param.setMemberId(id);
		}
		Assert.notNull(param.getMemberId(), "ID");
		List<String> avatars = UploadUtil.uploadImage(request, false);
		org.springframework.util.Assert.notEmpty(avatars, "头像数据dataFile不能为空");
		TmEmber member = new TmEmber();
		member.setId(param.getMemberId());
		TmEmber user = service.queryById(member.getId());
		Assert.notNull(user, "MEMBER", member.getId());
		String filePath = UploadUtil.getUploadDir(request) + avatars.get(0);
		String avatar = UploadUtil.remove2FDFS(filePath).getRemotePath();
		member.setAvatar(avatar);
		Long userId = getCurrUser(request);
		member.setUpdateBy(userId);
		member.setUpdateTime(new Date());
		service.update(member);
		Map<String, Object> result = InstanceUtil.newHashMap("bizeCode", 1);
		result.put("avatar", avatar);
		return setSuccessModelMap(new ModelMap(), result);
	}

	@PostMapping("updatePhoneByIdCard")
	@ApiOperation(value = "修改个人手机号", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object updatePhone(HttpServletRequest request, String newPhone, String orderPhone, String idCard,
							  String realname) {
		Map<String, Object> params = WebUtil.getParameter(request);
		Object result = service.updatePhone(params);
		return setSuccessModelMap(new ModelMap(), result);
	}

	@PostMapping("updatePhoneByPhone")
	@ApiOperation(value = "修改个人手机号", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object updatePhone(HttpServletRequest request, Login user, String memberId) {
		Assert.notNull(user.getAccount(), "ACCOUNT");
		Assert.notNull(user.getPassword(), "PASSWORD");
		String password = (String)CacheUtil.getCache().get("CHGINFO_" + user.getAccount());
		if (user.getPassword().equals(password)) {
			TmEmber tMember = new TmEmber();
			tMember.setPhone(user.getAccount());
			tMember.setId(Long.parseLong(memberId));
			return super.update(request, new ModelMap(), tMember);
		}
		return setSuccessModelMap(new ModelMap(), "验证码错误");
	}

	@ApiOperation("实名认证")
	@PostMapping("/authentication")
	public Object authentication(HttpServletRequest request, String memberId, String realName, String idCard) {
		Map<String, Object> parame = WebUtil.getParameter(request);
		Object result = service.authentication(parame);
		return setSuccessModelMap(new ModelMap(), result);
	}

}
