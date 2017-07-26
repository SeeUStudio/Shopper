package ${basePackage}.web;
import ${comseeu}.core.Result;
import ${comseeu}.core.ResultGenerator;
import ${comseeu}.oauth.OAuthType;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("/api${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/update")
    public Result update(${modelNameUpperCamel} ${modelNameLowerCamel}, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if ( ${modelNameLowerCamel} != null ){
            // 因为只能修改自己的数据，所以默认改为 uid
            ${modelNameLowerCamel}.setUid(uid);
        }else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam("id") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        if( ${modelNameLowerCamel} == null ){
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }
}
