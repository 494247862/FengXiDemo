package com.fengxi.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fengxi.auth.annotation.NotUseResultVO;
import com.fengxi.auth.vo.luckysheet.Celldata;
import com.fengxi.auth.vo.luckysheet.SheetOption;
import com.fengxi.auth.vo.luckysheet.V;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * excel导出控制器
 */
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
