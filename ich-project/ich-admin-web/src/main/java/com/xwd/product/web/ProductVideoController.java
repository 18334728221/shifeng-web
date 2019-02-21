package com.xwd.product.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUser;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.product.model.ProductVideo;
import com.xwd.product.service.ProductVideoService;

import java.util.Calendar;

import org.springframework.util.MultiValueMap;

import com.frame.util.CalendarUtils;

/**
 * 产品视频表
 * 
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/product/video")
public class ProductVideoController extends BaseAdminController {

	@Autowired
	private ProductVideoService productVideoService;
	@Autowired
	private LogService logService;

	@RequestMapping("/index")
	public String index() {
		return "/admin/product/productVideo";
	}

	/**
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<ProductVideo> page = productVideoService.findByPageRequest(pageRequest);
		logService.add(request, "查询产品视频");
		this.outPageJson(response, page, true);
	}

	/**
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		ProductVideo entity;
		Long id = this.getLong("id");
		if (id == null) {
			entity = new ProductVideo();
			this.setFieldValues(entity, request, false);
			logService.add(request, "新增产品视频");
		} else {
			entity = productVideoService.get(id);
			if (entity == null) {
				this.outFailureJson(response);
				return;
			}
			logService.add(request, "修改产品视频");
			this.setFieldValues(entity, request, true);
		}
		productVideoService.saveOrUpdate(entity);
		this.outSuccessJson(response);
	}

	/**
	 * 删除对象. 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids = get("ids");
		productVideoService.deleteByIds(ids);
		logService.add(request, "删除产品视频");
		this.outSuccessJson(response);
	}

	/**
	 * 跳转文件上传页
	 * 
	 * @param request
	 * @param response
	 */
	/*@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		return "/product/file/upload";
	}*/

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public String fileUpload(HttpServletRequest request, HttpServletResponse response) {
		Long productCode = Long.valueOf(request.getParameter("productCode"));
		String title = request.getParameter("title");
		Long id = null;
		if (request instanceof MultipartHttpServletRequest) {
			// 转型为MultipartHttpRequest
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获得上传的文件（根据前台的name名称得到上传的文件）
			MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
			List<MultipartFile> dFile = multiValueMap.get("file");
			if (!dFile.isEmpty()) {
				try {
					//LessonPlan entity = this.lessonFacadeService.getLessonPlanService().findUniqueBy("courseId",courseId);
					String path = BaseDataConstant.BASE_SYS_CONFIG_MAP.get(SysConfigConstant.RESOURCE_TEMPORARY_URL_KEY);
					for (MultipartFile file : dFile) {
						//Resource resource = new Resource(); // 创建上传文件对象
						String uuid = Calendar.getInstance().getTimeInMillis() + "";
						// 获取文件名
						String originalFileName = file.getOriginalFilename();
						// 获取文件的后缀
						String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
						// 判断是否是图片
						if (ResourceConstant.IMAGE_SUFFIX.contains(suffix)) {
							ImageServer imageServer = BaseDataConstant
									.getImageServer(ImageServer.IMAGE_SERVER_ANSWER_QUESTION);
							String uploadUrl = imageServer.getImageUrl();
							byte[] imgData = file.getBytes();
							String retJson = ZimgUtils.sendPost(uploadUrl, imgData, suffix);
							int tIndex = retJson.indexOf("md5");
							String imageId = retJson.substring(tIndex + 6, tIndex + 38);
							resource.setPlanId(entity.getId());
							resource.setName(originalFileName);
							resource.setCreateTime(Calendar.getInstance());
							resource.setTitle(title);
							id = lessonFacadeService.getResourceService().save(resource);

							ResourceImage image = new ResourceImage();
							image.setCreateTime(Calendar.getInstance());
							image.setPlanId(entity.getId());
							image.setImageId(imageId);
							image.setImageServerId(imageServer.getId());
							image.setResourceId(id);
							image.setSeq(1);
							lessonFacadeService.getResourceImageService().save(image);
						} else {
							String fname = uuid + "." + suffix;
							String folder = CalendarUtils.convertStrThree(Calendar.getInstance()) + File.separator;
							if (ResourceConstant.PDF_SUFFIX.equals(suffix)) {
								folder += ResourceConstant.PDF_SUFFIX + File.separator;
								resource.setPdfFilePath(folder + fname);
							} else {
								resource.setFilePath(folder + fname);
							}
							File f = new File(path + folder);
							if (!f.exists()) {
								f.mkdirs();
							}
							resource.setPlanId(entity.getId());
							resource.setName(originalFileName);
							resource.setTitle(title);

							f = new File(path + folder + fname);
							if (!f.exists()) {
								f.createNewFile();
							}
							file.transferTo(f);

							id = lessonFacadeService.getResourceService().save(resource);
							resource.setId(id);
							// 将文件加入队列
							//jssdbClient.qPushBackPojo(JssdbTeacherConstant.TEACHER_RESOURCE_FILE_QUEUE_KEY, resource);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (id == null) {
			return null;
		}
		return id + "";
	}*/
}
