package com.hmdp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmdp.entity.Voucher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Program Monkey
 *
 */
public interface VoucherMapper extends BaseMapper<Voucher> {

    /**
     * @param shopId 商品id
     * @return 优惠券列表
     */
    List<Voucher> queryVoucherOfShop(@Param("shopId") Long shopId);
}
