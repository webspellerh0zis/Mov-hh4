package qlsl.androiddesign.util.commonutil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import qlsl.androiddesign.constant.SoftwareConstant;
import qlsl.androiddesign.entity.commonentity.Pager;

public class PagerUtils {

	/**
	 * 对已有List进行分页截取<br/>
	 * 每页固定为15条<br/>
	 */
	public static <T> Map<String, Object> split(List<T> list, int pageNo) {
		int pageSize = 15;
		int totalCount = list.size();
		int totalPage = totalCount / pageSize;
		int start = pageNo * pageSize;
		int end = (pageNo + 1) * pageSize;
		int size = list.size();
		Map<String, Object> map = new HashMap<String, Object>();
		List<T> split_list = list.subList(start, end <= size ? end : size);
		Pager pager = new Pager();
		pager.setPageNo(pageNo);
		pager.setPageSize(pageSize);
		pager.setTotalCount(totalCount);
		pager.setTotalPage(totalPage == 0 ? 0 : (totalCount % pageSize == 0 ? totalPage - 1 : totalPage));
		map.put("pager", pager);
		map.put("list", split_list);
		return map;
	}

	/**
	 * 创建Pager<br/>
	 */
	public static Pager createPager(int pageNo, int pageSize, int totalCount, int totalPage) {
		Pager pager = new Pager();
		pager.setPageNo(pageNo);
		pager.setPageSize(pageSize);
		pager.setTotalCount(totalCount);
		pager.setTotalPage(totalPage);
		return pager;
	}

	/**
	 * 创建Pager<br/>
	 */
	public static Pager createPager(int page, JSONObject jo) {
		int pageNo = page;
		int pageSize = SoftwareConstant.PAGER_SIZE;
		int totalCount = jo.getInteger("count");
		int totalPage = totalCount / SoftwareConstant.PAGER_SIZE;
		totalPage = (totalPage == 0 ? 0 : (totalCount % pageSize == 0 ? totalPage - 1 : totalPage));
		Pager pager = createPager(pageNo, pageSize, totalCount, totalPage);
		return pager;
	}

	/**
	 * 创建Pager<br/>
	 */
	public static Pager createPager(int page, int totalCount) {
		int pageNo = page;
		int pageSize = SoftwareConstant.PAGER_SIZE;
		int totalPage = totalCount / SoftwareConstant.PAGER_SIZE;
		totalPage = (totalPage == 0 ? 0 : (totalCount % pageSize == 0 ? totalPage - 1 : totalPage));
		Pager pager = createPager(pageNo, pageSize, totalCount, totalPage);
		return pager;
	}
}
