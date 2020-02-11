package com.ciel.springcloudconsulslave1.controller;

import com.ciel.springcloudconsulslave1.entity.Emp;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * value：不能显示到UI上,tags 说明整个类的作用，会显示在UI上。
 */
@Api(value = "swagger演示",tags = "swagger演示接口")

@RestController
public class Swagger2Controller {


    /**
     * value：说明方法的用途、作用
     * notes：方法的备注说明
     */
    @ApiOperation(value = "swagger第一个方法", notes="swagger第一个方法------------")

    /**
     * name：参数名
     * value：参数的汉字说明，描述信息
     * required：是否必需
     * dataType：参数类型，默认String，其它值dataType="Integer" 或dataType="User" 或 ......
     * defaultValue：参数的默认值
     * paramType：参数放在哪个地方 header --> 请求参数的获取：@RequestHeader
     *                          query --> 请求参数的获取：@RequestParam
     *                          path（用于restful接口）--> 请求参数的获取：@PathVariable
     *                          body（不常用）
     *                          form（不常用）
     *
     */

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "query", required = true, dataType = "String")
    })

    /**
     * code：标准的http响应码，例如404，500，502等
     * message：错误信息，例如“参数错误”
     * response：抛出异常的类
     * 修饰方法，表达一个错误的响应，例如
     */
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })

    @GetMapping("/par")
    public Object par(@RequestParam("name") String name){

        return Map.of("MSG","WELCOME - ".concat(name));
    }


    /**
     * name：参数名
     * value：参数的汉字说明，描述信息
     * required：是否必需
     */
    @ApiOperation(value = "swagger第2个方法", notes="swagger第2个方法------------")
    @GetMapping("/par2")
    public Object par2(@RequestParam("name") @ApiParam(name = "姓名", value = "传入姓名", required = true)String name){

        return Map.of("MSG","WELCOME - ".concat(name));
    }

    @ApiOperation(value = "swagger第3个方法", notes="swagger第3个方法------------")
    @GetMapping("/par3")
    public Object par3(Emp emp){


        return Map.of("MSG","WELCOME - ".concat(emp.getName()));
    }
}
