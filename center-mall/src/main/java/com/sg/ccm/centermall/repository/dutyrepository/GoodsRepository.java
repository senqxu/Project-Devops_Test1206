package com.sg.ccm.centermall.repository.dutyrepository;

import com.sg.ccm.equipment.model.duty.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsRepository
    extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods> {
}
