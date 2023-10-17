# 德忆后端框架 DeyiBaseDemo 与 DeyiBaseDemoVue配合使用

1、application中的配置文件请使用DeyiSettingDTO注入获取


```java
@Resource
DeyiSettingDTO deyiSettingDTO;
```


2、创建数据库实体类（都基础BaseEntity）


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


3、关注application的配置，本架构使用mybatisplus、jpa、redis、springsecurity


```
deyi:
  redisUserKey: login-demo #登录存储到redis的key名称
file:
    path: files_${spring.application.name}
  swagger:
    enabled: true #是否开启swagger
token:
    key: deyi #token密钥
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
4、登录密码 admin deyi@2023