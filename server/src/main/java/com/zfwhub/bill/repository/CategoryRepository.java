package com.zfwhub.bill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zfwhub.bill.po.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
