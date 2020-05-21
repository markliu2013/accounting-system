package com.zfwhub.bill.dto;

import org.springframework.data.domain.Page;

public class BillQueryResultDto {

    private Page<BillListItemDto> page;
    private String in;
    private String out;

    public Page<BillListItemDto> getPage() {
        return page;
    }

    public void setPage(Page<BillListItemDto> page) {
        this.page = page;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    

}
