# 风息开发框架

# 后端(FengXiDemo)

**运行环境：JDK1.8 mysql redis**

**使用框架：Springboot2 springSecurity mybatisplus jpa swagger**

### 1、application中的配置文件请使用CommonSettingDTO注入获取

```java
@Resource
CommonSettingDTO commonSettingDTO;
```


### 2、创建数据库实体类（都基础BaseEntity）


```java
@Data
@Entity
@ApiModel("按钮实体类")
@TableName("deyi_button")
public class DeyiButton extends BaseEntity {

@TableField(value = "button_code")
@ApiModelProperty(value = "按钮编号")
private String buttonCode;

@TableField(value = "button_name")
@ApiModelProperty(value = "按钮名称")
private String buttonName;

@TableField(value = "menu_id")
@ApiModelProperty(value = "按钮所属菜单Id")
private Long menuId;
}
```


### 3、关注application的配置，本架构使用mybatisplus、jpa、redis、springsecurity


```
common:
  redisUserKey: login-demo #登录存储到redis的key名称
file:
    path: files_${spring.application.name}
  swagger:
    enabled: true #是否开启swagger
token:
    key: fengxi #token密钥
single: false #是否单点登录
timeout: 480 #分钟 过期时间
notAuthUrls: #不用鉴权的url，注意逗号
      /swagger-ui.html,
      /v2/api-docs,
      /swagger-resources/configuration/ui,
      /swagger-resources,
      /swagger-resources/configuration/security,
      /swagger-resources/**,
      /webjars/**
```
### 4、本框架整合了webstocket，根据用户id发送,为了适应分布式，采用redis的消息队列接收信息后发送
```java
WebSocketServer.sendMessageByUserIdForRedis(id,"hahahah");
```

### 5、导出excel(结合LuckySheet文档字段使用)
```java
@RestController
@RequestMapping("/excel")
@Api(tags = "excel导出控制器")
public class ExcelController {

    @ApiOperation("测试导出")
    @PostMapping("/export")
    @NotUseResultVO(isUse = false)
    public String export() throws InterruptedException {
        SheetOption sheetOption = new SheetOption();
        sheetOption.setName("1");
        sheetOption.setIndex("1");
        sheetOption.setStatus(1);
        sheetOption.setHide(0);
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10; j++) {
                Celldata celldata = new Celldata();
                celldata.setR(String.valueOf(i));
                celldata.setC(String.valueOf(j));

                V v = new V();
                v.setV("测试数据");
                celldata.setV(v);

                sheetOption.getCelldata().add(celldata);
            }
        }
        List<SheetOption> list = new ArrayList<SheetOption>();
        list.add(sheetOption);
        return JSONObject.toJSONString(list);
    }

}
```
### 6、登录密码 admin admin

# 前端(FengXiDemoVue)

**运行环境：node.js 14**

**前端通过vue-element-admin而来**

### 1、新增页面
新编写的页面建议放入 **src/views**中，通用组件则放入**src/components**，编写完页面后在**router/index.js**中加入路由，再往前端页面 **权限管理/菜单管理** 配置对应的数据，然后配置对应的角色权限显示页面

### 2、按钮权限
该框架可以把前端显示权限细化到组件，如在前端编写组件属性时加入
```
v-if="$authButton('addChild')"
```
然后在前端 **菜单管理** 对应的页面中进行按钮配置即可，**注意authButton中的参数为按钮编号**

### 3、webstocket使用
使用范式在 **src\layout\components\Navbar.vue**
本框架采用监听一个全局变量 **(this.$root.webStocketMessage)** 的方式来驱动后端访问前端，如有更好的方式可自行修改。

### 4、excel显示和导出
整合了开源组件luckysheet并进行自行修改加入了导出功能，前端样式 **src\views\ExcelExample\index.vue** 数据格式可参考后端接口 **/excel/export** 导出功能无需额外编写，方便后端开发时可进行数据调整。