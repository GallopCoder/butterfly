package com.ali.retail.db.mongo.api;


import com.ali.retail.db.mongo.entity.WholeTongue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/mongo")
public class MongoDBApi {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBApi.class);
    @Resource
    private MongoTemplate template;

    @PostMapping("/save")
    public void save(WholeTongue tongue) {
        template.save(tongue);
    }

    @GetMapping("/all")
    public List<WholeTongue> find() {
        return template.findAll(WholeTongue.class, "whole_tongue");
    }

}
