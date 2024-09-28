package site.dittotrip.ditto_trip.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  public void set(String key, Object value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public String get(String key) {
    String result = (String) redisTemplate.opsForValue().get(key);
    return result == null ? "" : result;
  }

  public void delete(String key) {
    redisTemplate.delete(key);
  }

  public void setex(String key, Object value, long timeout) {
    redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
  }

  /**
   * ZSet
   *  - key : CATEGORY_RANKING, SPOT_RANKING
   */
  public void addIfAbsent(String key, String value) {
    redisTemplate.opsForZSet().addIfAbsent(key, value, 0);
  }

  public void incrementScore(String key, String value) {
    redisTemplate.opsForZSet().incrementScore(key, value, 1);
  }

  public List<String> getRankingList(String key, int size) {
    Set<Object> keywords = redisTemplate.opsForZSet().reverseRange(key, 0, size - 1);
    if (keywords != null) {
      return keywords.stream().map(s -> (String) s).collect(Collectors.toList());
    } else {
      return null;
    }
  }

}