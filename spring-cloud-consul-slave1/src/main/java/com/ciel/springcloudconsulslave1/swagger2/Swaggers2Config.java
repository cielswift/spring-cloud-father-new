package com.ciel.springcloudconsulslave1.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2

//@EnableSwagger2Doc
public class Swaggers2Config {

    /**
     * http://127.0.0.1:4010/slave1/swagger-ui.html
     *
     *
     *
     * @Api：修饰整个类，描述 Controller 的作用
     *
     * 说明：用在请求的类上，表示对类的说明
     *
     * 常用参数：
     *
     * tags="说明该类的作用，非空时将覆盖 value 的值"
     * value="描述类的作用"
     * 其他参数：
     *
     * description 对 api 资源的描述，在 1.5 版本后不再支持
     * basePath 基本路径可以不配置，在 1.5 版本后不再支持
     * position 如果配置多个 Api 想改变显示的顺序位置，在 1.5 版本后不再支持
     * produces 设置 MIME 类型列表（output），例："application/json, application/xml"，默认为空
     * consumes 设置 MIME 类型列表（input），例："application/json, application/xml"，默认为空
     * protocols 设置特定协议，例：http， https， ws， wss
     * authorizations 获取授权列表（安全声明），如果未设置，则返回一个空的授权值。
     * hidden 默认为 false，配置为 true 将在文档中隐藏
     * @ApiOperation：描述一个类的一个方法，或者说一个接口
     *
     * 说明：用在请求的方法上，说明方法的用途、作用
     *
     * 常用参数：
     *
     * value="说明方法的用途、作用"
     * notes="方法的备注说明"
     * 其他参数：
     *
     * tags 操作标签，非空时将覆盖value的值
     * response 响应类型（即返回对象）
     * responseContainer 声明包装的响应容器（返回对象类型）。有效值为 "List", "Set" or "Map"。
     * responseReference 指定对响应类型的引用。将覆盖任何指定的response（）类
     * httpMethod 指定HTTP方法，"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" and "PATCH"
     * position 如果配置多个Api 想改变显示的顺序位置，在1.5版本后不再支持
     * nickname 第三方工具唯一标识，默认为空
     * produces 设置MIME类型列表（output），例："application/json, application/xml"，默认为空
     * consumes 设置MIME类型列表（input），例："application/json, application/xml"，默认为空
     * protocols 设置特定协议，例：http， https， ws， wss。
     * authorizations 获取授权列表（安全声明），如果未设置，则返回一个空的授权值。
     * hidden 默认为false， 配置为true 将在文档中隐藏
     * responseHeaders 响应头列表
     * code 响应的HTTP状态代码。默认 200
     * extensions 扩展属性列表数组
     *
     * @ApiParam：单个参数描述
     * 说明：用在请求的方法上，表示一组参数说明；@ApiImplicitParam：用在 @ApiImplicitParams 注解中，指定一个请求参数的各个方面
     *
     * 常用参数：
     *
     * name：参数名，参数名称可以覆盖方法参数名称，路径参数必须与方法参数一致
     * value：参数的汉字说明、解释
     * required：参数是否必须传，默认为 false （路径参数必填）
     * paramType：参数放在哪个地方
     *      header 请求参数的获取：@RequestHeader
     *      query 请求参数的获取：@RequestParam
     *      path（用于 restful 接口）--> 请求参数的获取：@PathVariable
     *      body（不常用）
     *      form（不常用）
     * dataType：参数类型，默认 String，其它值 dataType="Integer"
     * defaultValue：参数的默认值
     * 其他参数（@ApiImplicitParam）：
     *
     * allowableValues 限制参数的可接受值。1.以逗号分隔的列表 2.范围值 3.设置最小值/最大值
     * access 允许从API文档中过滤参数。
     * allowMultiple 指定参数是否可以通过具有多个事件接受多个值，默认为 false
     * example 单个示例
     * examples 参数示例。仅适用于 BodyParameters
     *
     * @ApiModel：用对象来接收参数
     *  说明：用于响应类上，表示一个返回响应数据的信息（这种一般用在 POST 创建的时候，使用 @RequestBody 这样的场景，请求参数无法使用 @ApiImplicitParam 注解进行描述的时候）；@ApiModelProperty：用在属性上，描述响应类的属性
     *
     * 其他参数(@ApiModelProperty)：
     *
     * value 此属性的简要说明。
     * name 允许覆盖属性名称
     * allowableValues 限制参数的可接受值。1.以逗号分隔的列表 2.范围值 3.设置最小值/最大值
     * access 允许从 API 文档中过滤属性。
     * notes 目前尚未使用。
     * dataType 参数的数据类型。可以是类名或者参数名，会覆盖类的属性名称。
     * required 参数是否必传，默认为 false
     * position 允许在类中对属性进行排序。默认为 0
     * hidden 允许在 Swagger 模型定义中隐藏该属性。
     * example 属性的示例。
     * readOnly 将属性设定为只读。
     * reference 指定对相应类型定义的引用，覆盖指定的任何参数值
     * @ApiProperty：用对象接收参数时，描述对象的一个字段
     * @ApiResponse：HTTP 响应其中 1 个描述
     * @ApiResponses：HTTP 响应整体描述
     * @ApiIgnore：使用该注解忽略这个API
     * @ApiError：发生错误返回的信息
     * @ApiImplicitParam：一个请求参数
     * @ApiImplicitParams：多个请求参数
     *
     */

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.ciel.springcloudconsulslave1.controller"))
                .paths(PathSelectors.any())
                .build();
//        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
    }


    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Spring Boot 使用 Swagger2 构建 RESTful API//////////////////////")
                //创建人
                .contact(new Contact("夏培鑫", "http://127.0.0.1:9411/zipkin/", ""))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述 *********************************")
                .build();
    }
}
