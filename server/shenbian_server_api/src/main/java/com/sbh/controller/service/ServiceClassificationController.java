package com.sbh.controller.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbh.model.service.ServiceClassificationInfo;
import com.sbh.service.service.IServiceClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/28.
 */
@Controller
public class ServiceClassificationController {
    @Autowired
    private IServiceClassificationService serviceClassificationService;

    /**
     * 根据分类ID查询分类信息
     * groupId:0:文章，服务，1：一级分类，2:二级分类 3：三级分类
     *
     *
     * @param groupId
     * @return
     */
    @RequestMapping("v1/service/queryClassification")
    @ResponseBody
    public ResponseEntity queryClassifications(String groupId){
        List<ServiceClassificationInfo>  classificationInfos = serviceClassificationService.queryClassifications(groupId);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("data",classificationInfos);
        map.put("status ","0000");
        map.put("success","查询成功");
        String jsonStr = null;
        try {
             jsonStr =  mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            map.put("errors","查询超时,请稍后再试");
            e.printStackTrace();
        }
        return  ResponseEntity.ok(jsonStr);
    }

}
