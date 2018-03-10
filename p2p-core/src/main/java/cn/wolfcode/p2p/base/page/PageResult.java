package cn.wolfcode.p2p.base.page;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * 分页查询结果:
 * @author mm
 */
@Getter
@Setter
public class PageResult {
	//用户传入:
	private int currentPage;
	private int pageSize;
	//查询:
	private int totalSize;
	private List<?> list;
	//计算
	private int prevPage;
	private int nextPage;
	private int totalPage;

	//处理总页数:方式springboot中分页出错:
	public int getTotalPage(){
		return totalPage == 0 ? 1 : totalPage;
	}
	//没有结果构造器
	public PageResult(int pageSize) {
		this(1,pageSize,0,Collections.EMPTY_LIST);
	}

	//有结果构造器:
	public PageResult(int currentPage, int pageSize, int totalSize, List list) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.list = list;
		//计算:
		this.totalPage = this.totalSize % this.pageSize == 0 ? 
						 this.totalSize / this.pageSize : 
						 this.totalSize / this.pageSize + 1;
		this.prevPage = this.currentPage - 1 >= 1 ? this.currentPage - 1 : 1;
		this.nextPage = this.currentPage + 1 <= this.totalPage ? this.currentPage + 1 : this.totalPage;
	}
	
}
