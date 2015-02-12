package com.huake.saas.user.web.api.v1;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.huake.saas.addresslist.entity.AddressList;
import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.service.ImageUploadService;
import com.huake.saas.base.web.controller.BaseApiController;
import com.huake.saas.images.Photo;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

/**
 * 面向租户应用会员注册接口
 * @author laidingqing
 *
 */
@Controller
@RequestMapping(value = "/api/v1/users")
public class TenancyUserApiController extends BaseApiController{
	
	private static final Logger log = LoggerFactory.getLogger(TenancyUserApiController.class);

	@Autowired
	private TenancyService tenancyService;
	
	@Autowired
	private TenancyUserService tenancyUserService;
	
	@Autowired
	private ImageUploadService imageService;
	
	
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;
	
	@Resource(name = "bizMessageSource")
	private MessageSource messageSource;
	
	/**
	 * 注册接口
	 * @param uid
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> register(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestBody TenancyUser user) throws Exception{

		Tenancy tenancy = tenancyService.getTenancy(uid);
		if(tenancy == null){
			throw new AppBizException(messageSource.getMessage("no_found_tenancy",
					new String[] {}, Locale.SIMPLIFIED_CHINESE));
		}
		if(tenancyUserService.isExist(user.getLoginname().trim(),uid))
		{
			throw new AppBizException(messageSource.getMessage("login_name_repetition",
					new String[] {}, Locale.SIMPLIFIED_CHINESE));
		}	
		user.setUid(uid);
        if(user.getName() != null && !user.getName().trim().equals(""))
        	user.setName(user.getLoginname());
		tenancyUserService.save(user);
		
		return SUCCESS_RESULT;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST , produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public TenancyUser login(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestBody TenancyUser user1) throws Exception{
		
		/**
		 * qq登入
		 */
		if(user1.getQqId() != null && !user1.getQqId().trim().equals("")){
			TenancyUser user = tenancyUserService.findByQqId(user1.getQqId().trim());
			if(user == null)
			{
				user1.setLoginname("qq用户");
				user1.setPassword("");
				user1 = tenancyUserService.save(user1);
				return user1;
			}
			return user;
		}
		/**
		 * sina登入
		 */
		if(user1.getSinaId() != null && !user1.getSinaId().trim().equals("")){
			TenancyUser user = tenancyUserService.findBySinaId(user1.getSinaId().trim());
			if(user == null)
			{
				user1.setLoginname("新浪用户");
				user1.setPassword("");
				user1 = tenancyUserService.save(user1);
				return user1;
			}
			return user;
		}
		
		TenancyUser user = tenancyUserService.findByLoginnameAndUid(user1.getLoginname().trim(),uid);
		if(user == null){
			throw new AppBizException(messageSource.getMessage("member_not_found",
					new String[] {}, Locale.SIMPLIFIED_CHINESE));
		}
		log.info("user1pass:" + user1.getPassword() + ",userpass:" + user.getPassword());
		 if(!user1.getPassword().trim().equals(user.getPassword().trim())){
			 throw new AppBizException(messageSource.getMessage("password_do_not_match",
						new String[] {}, Locale.SIMPLIFIED_CHINESE));
		 }
		 if(user.getLastLoginTime() != null){
			 user.setLastLoginTime(new Date());
		 }
		 user.setAddressList(tenancyUserService.findUserAddressList(user.getId()));
		 return user;
	}
	
	/**
	 * 更新个人资料
	 * @param user1
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST , produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public TenancyUser update(
			@RequestBody TenancyUser userNew){
		TenancyUser user = tenancyUserService.updateUserByClient(userNew);
		user.setAddressList(tenancyUserService.findUserAddressList(user.getId()));
		return user;
	}
	
	
	/**
	 * 上传头像
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/avatar", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
    @ResponseStatus(value=HttpStatus.CREATED)
	public TenancyUser uploadImageWithMember(@PathVariable final Long id,  
			MultipartHttpServletRequest request, HttpServletResponse response) throws Exception{

		List<MultipartFile> files = request.getFiles("source");
		
		TenancyUser tenancyUser = tenancyUserService.findById(id);
		if(tenancyUser == null){
			 throw new AppBizException(messageSource.getMessage("member_not_found",
						new String[] {}, Locale.SIMPLIFIED_CHINESE));
		}
		if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your post.");
        }
		List<Photo> result = null;
		for(MultipartFile file : files){
			/*这里只传送一个文件*/
			result = tenancyUserService.uploadPic((CommonsMultipartFile)file, request);
		}
		AddressList addressList = tenancyUserService.findUserAddressList(tenancyUser.getId());
        if(null!=request){
        	String avatar = result.get(2).getFilePath();
        	tenancyUser.setAvatar(imgPath+avatar);
        	tenancyUserService.updateTenancyUser(tenancyUser);
        	if(addressList != null){
        		addressList.setAvatar(imgPath+avatar);
        		tenancyUserService.saveAddressList(addressList);
        	}
        }
        
        tenancyUser.setAddressList(addressList);
		return tenancyUser;
	}
	
	/**
	 * 修改密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pwd", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
    @ResponseStatus(value=HttpStatus.OK)
	public Map<String, String> pwd(@RequestBody TenancyUser user
			) throws Exception{
		TenancyUser tenancyUser = tenancyUserService.findByLoginnameAndUid(user.getLoginname(), user.getUid());
		if(tenancyUser == null){
			return FAILD_RESULT;
		}
		tenancyUser.setPassword(user.getPassword());
		tenancyUserService.updatePwd(tenancyUser);
		return SUCCESS_RESULT;
	}
	/**
	 * 忘記密碼
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/forgetPwd",method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
    @ResponseStatus(value=HttpStatus.OK)
	public TenancyUser forgetPwd(@RequestParam(value="uid",required=true)long uid,@RequestBody TenancyUser tuser){
		
		return tenancyUserService.findUserByCodition(tuser.getName(), uid, tuser.getMobile(),tuser.getLoginname());
	}
}
