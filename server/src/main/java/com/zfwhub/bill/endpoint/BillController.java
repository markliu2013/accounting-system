package com.zfwhub.bill.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zfwhub.bill.dto.BaseResponse;
import com.zfwhub.bill.dto.BillAddDto;
import com.zfwhub.bill.dto.BillQueryResultDto;
import com.zfwhub.bill.dto.ResponseSuccess;
import com.zfwhub.bill.query.BillQuery;
import com.zfwhub.bill.service.BillService;

import io.swagger.annotations.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags={"bill"})
@RestController
@RequestMapping("/bills")
public class BillController {
    
    @Autowired
    private BillService billService;
    
    @ApiOperation(value = "上传cvs文件账单", notes = "上传文件必须为固定的csv格式（https://raw.githubusercontent.com/xmindltd/hiring/master/frontend-1/bill.csv），支持多个文件，列名必须与账单对应，顺序不可变。swagger暂不支持多文件上传(https://stackoverflow.com/questions/33933219/does-springfox-swagger2-ui-support-choosing-multiple-files-at-once)，请在postman操作。", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/upload")
    public BaseResponse handleFileUpload(@RequestParam("files") MultipartFile files) {
        billService.addBillByFiles(new MultipartFile[] {files});
        return new BaseResponse(true);
    }
    
    @ApiOperation(value = "账单查询", notes = "多条件查询账单")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "page", dataType = "String", required = false, defaultValue = "0"),
        @ApiImplicitParam(paramType = "query", name = "size", dataType = "String", required = false, defaultValue = "20"),
        @ApiImplicitParam(paramType = "query", name = "sort", dataType = "String", required = false, defaultValue = "amount,desc")
    })
//    @ApiResponses({
//        @ApiResponse(code = 400, message = "Request parameters not filled in"),
//        @ApiResponse(code = 404, message = "No request path or incorrect page Jump path")
//    })
    @GetMapping("/")
    public BaseResponse handleQuery(BillQuery billQuery,@ApiIgnore Pageable page) {
        ResponseSuccess<BillQueryResultDto> response = new ResponseSuccess<>();
        response.setData(billService.query(billQuery, page));
        return response;
    }
    
    @PostMapping("/")
    public BaseResponse add(@RequestBody BillAddDto billAddDto) {
        billService.add(billAddDto);
        return new BaseResponse(true);
    }

}
