<%--
  Created by IntelliJ IDEA.
  User: Hmh
  Date: 2018/10/21
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <a class="easyui-linkbutton" onclick="importItems()">一键导入商品数据到索引库</a>
</div>
<script type="text/javascript">
    function importItems() {
        $.post("/index/import",null,function(data){
            if (data.status == 200) {
                $.messager.alert('提示','商品数据导入完成！');
            } else {
                $.messager.alert('提示','商品数据导入失败！');
            }
        });
    }
</script>
