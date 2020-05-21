package com.zfwhub.bill.service;


import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.zfwhub.bill.dto.BillAddDto;
import com.zfwhub.bill.dto.BillQueryResultDto;
import com.zfwhub.bill.query.BillQuery;

public interface BillService {
    
    void addBillByFiles(MultipartFile[] files);
    
    BillQueryResultDto query(BillQuery billQuery, Pageable page);
    
    boolean add(BillAddDto billAddDto);

}
