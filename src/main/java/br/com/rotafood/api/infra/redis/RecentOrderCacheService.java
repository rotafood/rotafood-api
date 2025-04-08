package br.com.rotafood.api.infra.redis;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.order.FullOrderDto;

@Service
public class RecentOrderCacheService {

    private static final Duration TTL = Duration.ofHours(2);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getKey(UUID merchantId) {
        return "order:polling:merchant:" + merchantId;
    }

    public void addOrUpdateRecentOrder(UUID merchantId, FullOrderDto orderDto) {
        List<FullOrderDto> current = getCachedRecentOrders(merchantId);

        if (current == null) {
            current = new ArrayList<>();
        } else {
            current.removeIf(o -> o.id().equals(orderDto.id()));
        }

        current.add(0, orderDto);

        cacheRecentOrders(merchantId, current);
    }


    @SuppressWarnings("unchecked")
    public List<FullOrderDto> getCachedRecentOrders(UUID merchantId) {
        return (List<FullOrderDto>) redisTemplate.opsForValue().get(getKey(merchantId));
    }

    public Long getNextMerchantSequenceFromCache(UUID merchantId) {
        List<FullOrderDto> orders = getCachedRecentOrders(merchantId);
    
        if (orders == null || orders.isEmpty()) {
            return null;
        }
    
        return orders.stream()
            .map(FullOrderDto::merchantSequence)
            .filter(seq -> seq != null)
            .max(Long::compareTo)
            .orElse(0L) + 1;
    }
    


    public void cacheRecentOrders(UUID merchantId, List<FullOrderDto> orders) {
        redisTemplate.opsForValue().set(getKey(merchantId), orders, TTL);
    }

    public void clearRecentOrders(UUID merchantId) {
        redisTemplate.delete(getKey(merchantId));
    }
}
