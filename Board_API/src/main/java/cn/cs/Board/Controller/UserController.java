package cn.cs.Board.Controller;

import javax.servlet.http.HttpSession;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.OpenOption;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.cs.Board.dao.model.User;
import cn.cs.Board.service.IUserService;
import net.coobird.thumbnailator.Thumbnails;


@RestController
public class UserController {
	
	@Autowired
    private IUserService userService;
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);
	private static final HttpSession ServletActionContext = null;

	
	@Value("${cn.cs.Board.uploadPath}")
	private String uploadPath;


	@RequestMapping(value= {"/login"},method = {RequestMethod.POST})	
	public JSONObject login(@RequestBody JSONObject params,HttpServletRequest request)
	{
		logger.info(params.toJSONString()+"is logining");
		JSONObject o=new JSONObject();
		User u=this.userService.getUser(params.getString("loginName"), params.getString("password"));
		if(u!=null)
		{
			HttpSession session=request.getSession();//这就是session的创建
            session.setAttribute("user", u);//session set user 
            logger.info("session创建完成 : "+session);
            o.put("flag","1");
            o.put("user", u);
            logger.info("login success");
		}else {
			o.put("flag","0");
			 logger.info("login fail");
		}
		return o;
	}
	
	@RequestMapping(value= {"/reg"},method = {RequestMethod.POST})
	public JSONObject reg(@RequestBody JSONObject params)
	{		
		logger.info(params.toString()+"is registering");
		JSONObject o=new JSONObject();
		if(this.userService.checkUserName(params.getString("loginName"))) {
			boolean result=this.userService.register(params.getString("loginName"), params.getString("username"), params.getString("password"));
			if(result){
				 o.put("flag","1");
				 logger.info("registration success");
			}else {
				 o.put("flag","0");
				 logger.info("registration failure error");
			}
		}
		else
		{
			o.put("flag","0");
			logger.info("username repeat. error");
		}
		return o;
	}
	
	@RequestMapping(value= {"/check"},method = {RequestMethod.POST})
	public JSONObject check(@RequestBody JSONObject params) {
		JSONObject o=new JSONObject();
		boolean result=this.userService.checkUserName(params.getString("loginName"));
		System.out.println("check name : "+params.toString());
		if(result){			
			 o.put("flag","1");
			 logger.info("username okey");
		}else {
			 o.put("flag","0");
			 logger.info("username repeat. error");
		}
		return o;
	}
	
	@RequestMapping(value= {"/update"},method = {RequestMethod.POST})
	public JSONObject update(@RequestBody User record,HttpServletRequest request) {
		JSONObject o=new JSONObject();
		HttpSession session = request.getSession();
		User u=(User) session.getAttribute("user");
		if(u!=null )
		{
			if(u.getId()==record.getId()) {
				logger.info("update new info of user  ");
				boolean result=this.userService.update(record);
				if(result){
					 o.put("flag","1");
					 logger.info("update success");
				}else {
					 o.put("flag","0");
					 logger.info("update failure");
				}
			}			
		}
		else {
			 o.put("flag","0");
			 logger.info("can't get the user msg in session or update is not the registration one ");
		}
		return o;
	}
	
	@RequestMapping(value= {"/upload"},method = {RequestMethod.POST})
	public JSONObject upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		HttpSession session = request.getSession();
		User u=(User) session.getAttribute("user");
		if(u!=null)
		{
			try {				
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				String tempName=file.getOriginalFilename();
				String suffixName=tempName.substring(tempName.lastIndexOf("."));
				 logger.info("the upload file's suffixName is  "+suffixName);
				if(!suffixName.equalsIgnoreCase(".jpg") && !suffixName.equalsIgnoreCase(".png"))
				{
					JSONObject o =new JSONObject();
					o.put("flag", "后缀名错误");
					logger.info("后缀名错误");
					return o;
				}
				if(file.getSize()>104857600l)
				{
					JSONObject o =new JSONObject();
					o.put("flag", "图片过大");
					logger.info("图片过大");
					return o;
				}
				//lastIndexOf指的是最后一个。出现的下index
				//substring(int index),从这个index(从0开始)。unhappy substring(2)=happy
				String name=uuid+suffixName;
				logger.info("will be stored as name : "+name);												
				
				File row=new File(uploadPath+"\\"+name);
				File SLT=new File(uploadPath+"\\"+uuid+"_SLT"+suffixName);
				
				logger.info("will be stored in : "+uploadPath);	
				file.transferTo(row);
				logger.info("_SLT will be stored in : "+uploadPath+"\\"+uuid+"_SLT"+suffixName);
				//缩略图
				if(file.getSize()>1000000l && file.getSize()<10000000l) {					
					Thumbnails.of(row).scale(0.3).toFile(SLT);
				}
				else if(file.getSize()>10000000l && file.getSize()<100000000l){
					Thumbnails.of(row).scale(0.03).toFile(SLT);
				}
				else {
					Thumbnails.of(row).scale(1).toFile(SLT);
				}
				
				JSONObject o =new JSONObject();
				o.put("flag", "success");
				o.put("pic",uuid+"_SLT"+suffixName);
				logger.info("file upload complete");
				return o;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.info("error");
				e.printStackTrace();
			}
		}
		JSONObject o =new JSONObject();
		o.put("flag", "用户没有登陆");
		logger.info("can't get the user msg in session");
		return o;
	}
	
	@RequestMapping(value= {"/images/{imageName}"},method = {RequestMethod.GET})
	public void getImage(@PathVariable String imageName,HttpServletResponse response) {
		logger.info("request "+imageName);
		if(imageName!=null)
		{
			File file = new File(uploadPath, imageName);
			if(file.exists())
			{
				response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                   logger.info("下载成功");
                   
                   }catch (Exception e) {
                       e.printStackTrace();
                   } finally {
                       if (bis != null) {
                           try {
                               bis.close();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                       if (fis != null) {
                           try {
                               fis.close();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                   }
                }
			logger.info("finished");
		}		
	}
		
}
