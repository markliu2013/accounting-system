package com.zfwhub.bill.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zfwhub.bill.dto.BillAddDto;
import com.zfwhub.bill.dto.BillCSVDto;
import com.zfwhub.bill.dto.BillListItemDto;
import com.zfwhub.bill.dto.BillQueryResultDto;
import com.zfwhub.bill.po.Bill;
import com.zfwhub.bill.po.Category;
import com.zfwhub.bill.query.BillQuery;
import com.zfwhub.bill.repository.BillRepository;
import com.zfwhub.bill.service.BillService;

@Service
@Transactional
public class BillServiceImpl implements BillService {
    
    @Autowired
    private BillRepository billRepository;

    @Override
    public BillQueryResultDto query(BillQuery billQuery, Pageable page) {
        Specification<Bill> query = new Specification<Bill>() {
            private static final long serialVersionUID = 618679284937403889L;
            public Predicate toPredicate(Root<Bill> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (billQuery.getType() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), billQuery.getType()));
                }
                if (billQuery.getMinAmount() != null) {
                    predicates.add(criteriaBuilder.ge(root.get("amount"), billQuery.getMinAmount()));
                }
                if (billQuery.getMaxAmount() != null) {
                    predicates.add(criteriaBuilder.le(root.get("amount"), billQuery.getMaxAmount()));
                }
                // 最好是不要直接调用函数
                // 处理时区的问题，临时处理。
                Calendar startCalendar = Calendar.getInstance();
                Calendar endCalendar = Calendar.getInstance();
                
                if (billQuery.getYear() != null) {
                    startCalendar.set(Calendar.YEAR, billQuery.getYear());
                    endCalendar.set(Calendar.YEAR, billQuery.getYear());
                    startCalendar.set(Calendar.DAY_OF_YEAR, 1);
                    endCalendar.set(Calendar.MONTH, 11); // 11 = december
                    endCalendar.set(Calendar.DAY_OF_MONTH, 31);
                }
                if (billQuery.getMonth() != null) {
                    startCalendar.set(Calendar.MONTH, billQuery.getMonth()-1);
                    endCalendar.set(Calendar.MONTH, billQuery.getMonth()-1);
                    startCalendar.set(Calendar.DAY_OF_MONTH, 1);
                    endCalendar.set(Calendar.DATE, startCalendar.getActualMaximum(Calendar.DATE));
                }
                if (billQuery.getDay() != null) {
                    startCalendar.set(Calendar.DATE, billQuery.getDay());
                    endCalendar.set(Calendar.DATE, billQuery.getDay());
                }
                startCalendar.set(Calendar.HOUR_OF_DAY, 0);
                startCalendar.set(Calendar.MINUTE, 0);
                startCalendar.set(Calendar.SECOND, 0);
                startCalendar.set(Calendar.MILLISECOND, 0);
                endCalendar.set(Calendar.HOUR_OF_DAY, 23);
                endCalendar.set(Calendar.MINUTE, 59);
                endCalendar.set(Calendar.SECOND, 59);
                endCalendar.set(Calendar.MILLISECOND, 999);
                if (billQuery.getYear() != null && billQuery.getMonth() != null) {
                    predicates.add(criteriaBuilder.between(root.get("time"), startCalendar.getTime(), endCalendar.getTime()));
                }
                if (billQuery.getCategoryId() != null && billQuery.getCategoryId() != "") {
                    predicates.add(criteriaBuilder.equal(root.get("category"), new Category(billQuery.getCategoryId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<Bill> billPage = billRepository.findAll(query, page);
        Page<BillListItemDto> billListItemDtoPage = billPage.map(BillListItemDto::instanceFromBill);
        BillQueryResultDto billQueryResultDto = new BillQueryResultDto();
        // 不能只统计当前页
        List<Bill> billListForSum = billRepository.findAll(query);
        double in = 0;
        double out = 0;
        for (Bill bill : billListForSum) {
            if (bill.getAmount() != null) {
                if (bill.getType() == 0) {
                     out += bill.getAmount();
                 } else if (bill.getType() == 1) {
                     in += bill.getAmount();
                 }
            }
        }
        billQueryResultDto.setPage(billListItemDtoPage);
        billQueryResultDto.setIn(String.format("%.2f", in));
        billQueryResultDto.setOut(String.format("%.2f", out));
        return billQueryResultDto;
    }
    
    @Override
    public void addBillByFiles(MultipartFile[] files) {
        List<Bill> bills = new ArrayList<>();
        // 检查文件类型，检查文件格式，异常处理。
        for (MultipartFile file : files) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                reader.readLine();//第一行信息，为标题信息 
                String line = null;    
                while((line=reader.readLine())!=null){    
                    String items[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    BillCSVDto billCSVDto = new BillCSVDto(items[0], items[1], items[2], items[3]);
                    //billCSVDtos.add(billCSVDto);
                    Bill bill = new Bill();
                    bill.setType(Integer.valueOf(billCSVDto.getType()));
                    bill.setTime(new Date(Long.valueOf(billCSVDto.getTime())));
                    bill.setCategory(new Category(billCSVDto.getCategory()));
                    bill.setAmount(Double.valueOf(billCSVDto.getAmount()));
                    bills.add(bill);
                }    
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        billRepository.saveAll(bills);
    }

    @Override
    public boolean add(BillAddDto billAddDto) {
        billRepository.save(billAddDto.convertToEntity());
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        billRepository.delete(new Bill(id));
        return true;
    }

}
