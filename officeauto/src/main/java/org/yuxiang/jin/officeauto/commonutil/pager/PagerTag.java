package org.yuxiang.jin.officeauto.commonutil.pager;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 * 自定义分页标签
 */
public class PagerTag extends SimpleTagSupport {

    //定义请求URL中的占位符常量
    private static final String TAG = "{0}";

    //当前页码
    private int pageIndex;

    //每页显示的数量
    private int pageSize;

    //总记录条数
    private int recordCount;

    //请求URL page.action?pageIndex={0}
    private String submitUrl;

    //样式
    private String style = "sabrosus";

    //定义总页数
    private int totalPage = 0;

    //在页面上引用自定义标签就会触发一个标签处理类
    @Override
    public void doTag() throws JspException, IOException {
        System.out.println("===============================");
        System.out.println("-" + pageIndex + "-" + pageSize);
        //定义StringBuilder，用来拼接字符串
        StringBuilder res = new StringBuilder();
        res.append("<center>\n" +
                "\t\t<p style=\"text-align: center;\">\n" +
                "\t\t\t<!--设计导航-->\n" +
                "\t\t\t<nav class=\"nav form-inline\">\n" +
                "\t\t\t\t <ul class=\"pagination alin\">");
        //定义它拼接中间的页码
        StringBuilder str = new StringBuilder();
        //判断总记录条数
        if (recordCount > 0) {
            /*
             * 需要显示分页标签，计算出总页数、需要分多少页
             * 1500条数据，每页15条，1500/15=100页
             * */
            totalPage = (this.recordCount - 1) / this.pageSize + 1;
            //判断上一页或下一页需不需要加a标签
            //首页
            if (this.pageIndex == 1) {
                str.append("<li class=\"disable\"><a href=\"#\">上一页</a></li>");

                //计算中间的页码
                this.calcPage(str);

                //下一页需不需要a标签
                if (this.pageIndex == totalPage) {
                    //只有一页
                    str.append("<li class=\"disable\"><a href=\"#\">下一页</a></li>");
                } else {
                    String tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex + 1));
                    str.append("<li><a href='").append(tempUrl).append("'>下一页</a></li>");
                }
            }
            //尾页
            else if (this.pageIndex == this.totalPage) {
                String tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex - 1));
                str.append("<l1><a href='").append(tempUrl).append("'>上一页</a></li>");

                //计算中间的页码
                this.calcPage(str);

                str.append("<li class=\"disable\"><a href=\"#\">下一页</a></li>");
            }
            //中间页
            else {
                String tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex - 1));
                str.append("<l1><a href='").append(tempUrl).append("'>上一页</a></li>");

                //计算中间的页码
                this.calcPage(str);

                str.append("<l1><a href='").append(tempUrl).append("'>下一页</a></li>");
            }
            res.append(str);

            //开始条数
            int startNum = (this.pageIndex - 1) * this.pageSize + 1;
            //结束条数
            int endNum = (this.pageIndex == this.totalPage) ? recordCount : this.pageIndex * this.pageSize;

            res.append("<li><a style=\"background-color:#D4D4D4;\" href=\"#\">共<font color='red'>").append(this.recordCount).append("</font>条记录，当前显示")
                    .append(startNum).append("-").append(endNum).append("条记录</a>&nbsp;</li>");
            res.append("<div class=\"input-group\">\n" +
                    "\t\t\t\t\t\t\t\t\t      <input id='pager_jump_page_size' value='").append(this.pageIndex).append("' type=\"text\" style=\"width: 60px;text-align: center\" class=\"form-control\" placeholder=\"").append(this.pageIndex).append("\"\">\n" +
                    "\t\t\t\t\t\t\t\t\t      <span class=\"input-group-btn\">\n" +
                    "\t\t\t\t\t\t\t\t\t        <button class=\"btn btn-info\" id='pager-jump-btn' type=\"button\">GO</button>\n" +
                    "\t\t\t\t\t\t\t\t\t      </span>\n" +
                    "\t\t\t\t\t   \t\t\t\t </div>");
            res.append("<script type='text/javascript'>");
            res.append("   document.getElementById('pager_jump_btn').onclick = function(){");
            res.append("      var page_size = document.getElementById('pager_jump_page_size').value;");
            res.append("      if (!/^[1-9]\\d*$/.test(page_size) || page_size < 1 || page_size > ").append(this.totalPage).append("){");
            res.append("          alert('请输入[1-").append(this.totalPage).append("]之间的页码！');");
            res.append("      }else{");
            res.append("         var submit_url = '").append(this.submitUrl).append("';");
            res.append("         window.location = submit_url.replace('").append(TAG).append("', page_size);");
            res.append("      }");
            res.append("}");
            res.append("</script>");

        } else {
            res.append("<li><a style=\"background-color:#D4D4D4;\" href=\"#\">总共<font color='red'>0</font>条记录，当前显示0-0条记录。</a>&nbsp;</li>");
        }
        res.append("</ul></nav></p></center>");
        this.getJspContext().getOut().print(res.toString());
    }

    //计算中间页码的方法
    private void calcPage(StringBuilder str) {
        //判断总页数
        if (this.totalPage <= 11) {
            //一次性显示全部页码
            for (int i = 1; i <= totalPage; i++) {
                if (this.pageIndex == i) {
                    //当前页码
                    str.append("<li class=\"active\"><a href=\"#\">").append(i).append("</a></li>");
                } else {
                    String tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
                    str.append("<li><a href='").append(tempUrl).append("'>").append(i).append("</a></li>");
                }
            }
        } else {
            //靠首页近些
            if (this.pageIndex <= 8) {
                for (int i = 1; i <=10 ; i++) {
                    if (this.pageIndex == i) {
                        //当前页码
                        str.append("<li class=\"active\"><a href=\"#\">").append(i).append("</a></li>");
                    } else {
                        String tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
                        str.append("<li><a href='").append(tempUrl).append("'>").append(i).append("</a></li>");
                    }
                }
                str.append("<li><a href=\"#\">...</a></li>");
                String tempUrl = this.submitUrl.replace(TAG, String.valueOf(this.totalPage));
                str.append("<li><a href='").append(tempUrl).append("'>").append("</a></li>");
            }
            //靠尾页近些
            else if (this.pageIndex + 8 >= this.totalPage) {
                String tempUrl = this.submitUrl.replace(TAG, String.valueOf(1));
                str.append("<li><a href='").append(tempUrl).append("'>1</a></li>");
                str.append("<li><a href=\"#\">...</a></li>");
                for (int i = this.totalPage - 10; i <= totalPage; i++) {
                    if (this.pageIndex == i) {
                        //当前页码
                        str.append("<li class=\"active\"><a href=\"#\">").append(i).append("</a></li>");
                    } else {
                        tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
                        str.append("<li><a href='").append(tempUrl).append("'>").append(i).append("</a></li>");
                    }
                }
            }
            //在中间
            else {
                String tempUrl = this.submitUrl.replace(TAG, String.valueOf(1));
                str.append("<li><a href='").append(tempUrl).append("'>1</a></li>");
                str.append("<li><a href=\"#\">...</a></li>");
                for (int i = this.pageIndex - 4; i <= this.pageIndex + 4; i++) {
                    if (this.pageIndex == i) {
                        //当前页码
                        str.append("<li class=\"active\"><a href=\"#\">").append(i).append("</a></li>");
                    } else {
                        tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
                        str.append("<li><a href='").append(tempUrl).append("'>").append(i).append("</a></li>");
                    }
                }
                str.append("<li><a href=\"#\">...</a></li>");
                tempUrl = this.submitUrl.replace(TAG, String.valueOf(this.totalPage));
                str.append("<li><a href='").append(tempUrl).append("'>").append(this.totalPage).append("</a></li>");
            }
        }
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public void setSubmitUrl(String submitUrl) {
        this.submitUrl = submitUrl;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
