package org.yuxiang.jin.officeauto.commonutil.pager;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 * 分页实体
 */
public class PageModel {
    //分页中默认一页4条数据
    public static final int PAGE_DEFAULT_SIZE = 4;

    //分页总数据条数
    private long recordCount;

    //当前是第几页
    private int pageIndex;

    //每页展示多少条数据
    private int pageSize = PAGE_DEFAULT_SIZE;

    //总页数
    private int totalPageSize;

    public long getRecordCount() {
        this.recordCount = recordCount <= 0 ? 0 : this.recordCount;
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageIndex() {
        //判断当前页面是否超过了总页数:如果超过了默认把最后一页作为当前页
        this.pageIndex = Math.min(this.pageIndex, this.totalPageSize);
        this.pageIndex = this.pageIndex <= 0 ? 1 : this.pageIndex;
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        this.pageSize = Math.max(pageSize, PAGE_DEFAULT_SIZE);
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageSize() {
        if (this.recordCount <= 0) {
            this.totalPageSize = 0;
        } else {
            this.totalPageSize = (int) ((this.getRecordCount() - 1) / this.getPageSize() + 1);
        }
        return this.totalPageSize;
    }

    public int getFirstLimitParam() {
        return (this.getPageIndex() - 1) * this.getPageSize();
    }

}
