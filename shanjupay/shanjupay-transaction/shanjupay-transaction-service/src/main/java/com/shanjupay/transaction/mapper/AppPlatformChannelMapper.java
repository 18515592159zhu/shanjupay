package com.shanjupay.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanjupay.transaction.entity.AppPlatformChannel;
import org.springframework.stereotype.Repository;

/**
 * 说明了应用选择了平台中的哪些支付渠道 Mapper 接口
 */
@Repository
public interface AppPlatformChannelMapper extends BaseMapper<AppPlatformChannel> {

}