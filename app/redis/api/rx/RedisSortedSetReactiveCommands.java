package com.lambdaworks.redis.api.rx;

import java.util.List;
import com.lambdaworks.redis.ScanArgs;
import com.lambdaworks.redis.ScanCursor;
import com.lambdaworks.redis.ScoredValue;
import com.lambdaworks.redis.ScoredValueScanCursor;
import com.lambdaworks.redis.StreamScanCursor;
import com.lambdaworks.redis.ZAddArgs;
import com.lambdaworks.redis.ZStoreArgs;
import com.lambdaworks.redis.output.ScoredValueStreamingChannel;
import com.lambdaworks.redis.output.ValueStreamingChannel;
import rx.Observable;

/**
 * Observable commands for Sorted Sets.
 * 
 * @param <K> Key type.
 * @param <V> Value type.
 * @author Mark Paluch
 * @since 4.0
 * @generated by com.lambdaworks.apigenerator.CreateReactiveApi
 */
public interface RedisSortedSetReactiveCommands<K, V> {

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     * 
     * @param key the key
     * @param score the score
     * @param member the member
     * 
     * @return Long integer-reply specifically:
     * 
     *         The number of elements added to the sorted sets, not including elements already existing for which the score was
     *         updated.
     */
    Observable<Long> zadd(K key, double score, V member);

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     * 
     * @param key the key
     * @param scoresAndValues the scoresAndValue tuples (score,value,score,value,...)
     * @return Long integer-reply specifically:
     * 
     *         The number of elements added to the sorted sets, not including elements already existing for which the score was
     *         updated.
     */
    Observable<Long> zadd(K key, Object... scoresAndValues);

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     * 
     * @param key the key
     * @param scoredValues the scored values
     * @return Long integer-reply specifically:
     * 
     *         The number of elements added to the sorted sets, not including elements already existing for which the score was
     *         updated.
     */
    Observable<Long> zadd(K key, ScoredValue<V>... scoredValues);

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     *
     * @param key the key
     * @param zAddArgs arguments for zadd
     * @param score the score
     * @param member the member
     *
     * @return Long integer-reply specifically:
     *
     *         The number of elements added to the sorted sets, not including elements already existing for which the score was
     *         updated.
     */
    Observable<Long> zadd(K key, ZAddArgs zAddArgs, double score, V member);

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     *
     * @param key the key
     * @param zAddArgs arguments for zadd
     * @param scoresAndValues the scoresAndValue tuples (score,value,score,value,...)
     * @return Long integer-reply specifically:
     *
     *         The number of elements added to the sorted sets, not including elements already existing for which the score was
     *         updated.
     */
    Observable<Long> zadd(K key, ZAddArgs zAddArgs, Object... scoresAndValues);

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     * 
     * @param key the ke
     * @param zAddArgs arguments for zadd
     * @param scoredValues the scored values
     * @return Long integer-reply specifically:
     * 
     *         The number of elements added to the sorted sets, not including elements already existing for which the score was
     *         updated.
     */
    Observable<Long> zadd(K key, ZAddArgs zAddArgs, ScoredValue<V>... scoredValues);

    /**
     * ZADD acts like ZINCRBY
     *
     * @param key the key
     * @param score the score
     * @param member the member
     *
     * @return Long integer-reply specifically:
     *
     *         The total number of elements changed
     */
    Observable<Double> zaddincr(K key, double score, V member);

    /**
     * Get the number of members in a sorted set.
     * 
     * @param key the key
     * @return Long integer-reply the cardinality (number of elements) of the sorted set, or {@literal false} if {@code key}
     *         does not exist.
     */
    Observable<Long> zcard(K key);

    /**
     * Count the members in a sorted set with scores within the given values.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long integer-reply the number of elements in the specified score range.
     */
    Observable<Long> zcount(K key, double min, double max);

    /**
     * Count the members in a sorted set with scores within the given values.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long integer-reply the number of elements in the specified score range.
     */
    Observable<Long> zcount(K key, String min, String max);

    /**
     * Increment the score of a member in a sorted set.
     * 
     * @param key the key
     * @param amount the increment type: long
     * @param member the member type: key
     * @return Double bulk-string-reply the new score of {@code member} (a double precision floating point number), represented
     *         as string.
     */
    Observable<Double> zincrby(K key, double amount, K member);

    /**
     * Intersect multiple sorted sets and store the resulting sorted set in a new key.
     * 
     * @param destination the destination
     * @param keys the keys
     * @return Long integer-reply the number of elements in the resulting sorted set at {@code destination}.
     */
    Observable<Long> zinterstore(K destination, K... keys);

    /**
     * Intersect multiple sorted sets and store the resulting sorted set in a new key.
     * 
     * @param destination the destination
     * @param storeArgs the storeArgs
     * @param keys the keys
     * @return Long integer-reply the number of elements in the resulting sorted set at {@code destination}.
     */
    Observable<Long> zinterstore(K destination, ZStoreArgs storeArgs, K... keys);

    /**
     * Return a range of members in a sorted set, by index.
     * 
     * @param key the key
     * @param start the start
     * @param stop the stop
     * @return V array-reply list of elements in the specified range.
     */
    Observable<V> zrange(K key, long start, long stop);

    /**
     * Return a range of members with scores in a sorted set, by index.
     * 
     * @param key the key
     * @param start the start
     * @param stop the stop
     * @return V array-reply list of elements in the specified range.
     */
    Observable<ScoredValue<V>> zrangeWithScores(K key, long start, long stop);

    /**
     * Return a range of members in a sorted set, by score.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<V> zrangebyscore(K key, double min, double max);

    /**
     * Return a range of members in a sorted set, by score.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<V> zrangebyscore(K key, String min, String max);

    /**
     * Return a range of members in a sorted set, by score.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<V> zrangebyscore(K key, double min, double max, long offset, long count);

    /**
     * Return a range of members in a sorted set, by score.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<V> zrangebyscore(K key, String min, String max, long offset, long count);

    /**
     * Return a range of members with score in a sorted set, by score.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     */
    Observable<ScoredValue<V>> zrangebyscoreWithScores(K key, double min, double max);

    /**
     * Return a range of members with score in a sorted set, by score.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     */
    Observable<ScoredValue<V>> zrangebyscoreWithScores(K key, String min, String max);

    /**
     * Return a range of members with score in a sorted set, by score.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     */
    Observable<ScoredValue<V>> zrangebyscoreWithScores(K key, double min, double max, long offset, long count);

    /**
     * Return a range of members with score in a sorted set, by score.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     */
    Observable<ScoredValue<V>> zrangebyscoreWithScores(K key, String min, String max, long offset, long count);

    /**
     * Return a range of members in a sorted set, by index.
     * 
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     * @param start the start
     * @param stop the stop
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrange(ValueStreamingChannel<V> channel, K key, long start, long stop);

    /**
     * Stream over a range of members with scores in a sorted set, by index.
     * 
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     * @param start the start
     * @param stop the stop
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrangeWithScores(ScoredValueStreamingChannel<V> channel, K key, long start, long stop);

    /**
     * Stream over a range of members in a sorted set, by score.
     * 
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long count of elements in the specified score range.
     */
    Observable<Long> zrangebyscore(ValueStreamingChannel<V> channel, K key, double min, double max);

    /**
     * Stream over a range of members in a sorted set, by score.
     * 
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long count of elements in the specified score range.
     */
    Observable<Long> zrangebyscore(ValueStreamingChannel<V> channel, K key, String min, String max);

    /**
     * Stream over range of members in a sorted set, by score.
     * 
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return Long count of elements in the specified score range.
     */
    Observable<Long> zrangebyscore(ValueStreamingChannel<V> channel, K key, double min, double max, long offset, long count);

    /**
     * Stream over a range of members in a sorted set, by score.
     * 
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return Long count of elements in the specified score range.
     */
    Observable<Long> zrangebyscore(ValueStreamingChannel<V> channel, K key, String min, String max, long offset, long count);

    /**
     * Stream over a range of members with scores in a sorted set, by score.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long count of elements in the specified score range.
     */
    Observable<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<V> channel, K key, double min, double max);

    /**
     * Stream over a range of members with scores in a sorted set, by score.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long count of elements in the specified score range.
     */
    Observable<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<V> channel, K key, String min, String max);

    /**
     * Stream over a range of members with scores in a sorted set, by score.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return Long count of elements in the specified score range.
     */
    Observable<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<V> channel, K key, double min, double max, long offset, long count);

    /**
     * Stream over a range of members with scores in a sorted set, by score.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return Long count of elements in the specified score range.
     */
    Observable<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<V> channel, K key, String min, String max, long offset, long count);

    /**
     * Determine the index of a member in a sorted set.
     * 
     * @param key the key
     * @param member the member type: value
     * @return Long integer-reply the rank of {@code member}. If {@code member} does not exist in the sorted set or {@code key}
     *         does not exist,
     */
    Observable<Long> zrank(K key, V member);

    /**
     * Remove one or more members from a sorted set.
     * 
     * @param key the key
     * @param members the member type: value
     * @return Long integer-reply specifically:
     * 
     *         The number of members removed from the sorted set, not including non existing members.
     */
    Observable<Long> zrem(K key, V... members);

    /**
     * Remove all members in a sorted set within the given indexes.
     * 
     * @param key the key
     * @param start the start type: long
     * @param stop the stop type: long
     * @return Long integer-reply the number of elements removed.
     */
    Observable<Long> zremrangebyrank(K key, long start, long stop);

    /**
     * Remove all members in a sorted set within the given scores.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long integer-reply the number of elements removed.
     */
    Observable<Long> zremrangebyscore(K key, double min, double max);

    /**
     * Remove all members in a sorted set within the given scores.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long integer-reply the number of elements removed.
     */
    Observable<Long> zremrangebyscore(K key, String min, String max);

    /**
     * Return a range of members in a sorted set, by index, with scores ordered from high to low.
     * 
     * @param key the key
     * @param start the start
     * @param stop the stop
     * @return V array-reply list of elements in the specified range.
     */
    Observable<V> zrevrange(K key, long start, long stop);

    /**
     * Return a range of members with scores in a sorted set, by index, with scores ordered from high to low.
     * 
     * @param key the key
     * @param start the start
     * @param stop the stop
     * @return V array-reply list of elements in the specified range.
     */
    Observable<ScoredValue<V>> zrevrangeWithScores(K key, long start, long stop);

    /**
     * Return a range of members in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<V> zrevrangebyscore(K key, double max, double min);

    /**
     * Return a range of members in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<V> zrevrangebyscore(K key, String max, String min);

    /**
     * Return a range of members in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param key the key
     * @param max max score
     * @param min min score
     * @param offset the withscores
     * @param count the null
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<V> zrevrangebyscore(K key, double max, double min, long offset, long count);

    /**
     * Return a range of members in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param key the key
     * @param max max score
     * @param min min score
     * @param offset the offset
     * @param count the count
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<V> zrevrangebyscore(K key, String max, String min, long offset, long count);

    /**
     * Return a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param key the key
     * @param max max score
     * @param min min score
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<ScoredValue<V>> zrevrangebyscoreWithScores(K key, double max, double min);

    /**
     * Return a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param key the key
     * @param max max score
     * @param min min score
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     */
    Observable<ScoredValue<V>> zrevrangebyscoreWithScores(K key, String max, String min);

    /**
     * Return a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param key the key
     * @param max max score
     * @param min min score
     * @param offset the offset
     * @param count the count
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     */
    Observable<ScoredValue<V>> zrevrangebyscoreWithScores(K key, double max, double min, long offset, long count);

    /**
     * Return a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param key the key
     * @param max max score
     * @param min min score
     * @param offset the offset
     * @param count the count
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<ScoredValue<V>> zrevrangebyscoreWithScores(K key, String max, String min, long offset, long count);

    /**
     * Stream over a range of members in a sorted set, by index, with scores ordered from high to low.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param start the start
     * @param stop the stop
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrevrange(ValueStreamingChannel<V> channel, K key, long start, long stop);

    /**
     * Stream over a range of members with scores in a sorted set, by index, with scores ordered from high to low.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param start the start
     * @param stop the stop
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrevrangeWithScores(ScoredValueStreamingChannel<V> channel, K key, long start, long stop);

    /**
     * Stream over a range of members in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     * @param max max score
     * @param min min score
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrevrangebyscore(ValueStreamingChannel<V> channel, K key, double max, double min);

    /**
     * Stream over a range of members in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrevrangebyscore(ValueStreamingChannel<V> channel, K key, String max, String min);

    /**
     * Stream over a range of members in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrevrangebyscore(ValueStreamingChannel<V> channel, K key, double max, double min, long offset, long count);

    /**
     * Stream over a range of members in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrevrangebyscore(ValueStreamingChannel<V> channel, K key, String max, String min, long offset, long count);

    /**
     * Stream over a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<V> channel, K key, double max, double min);

    /**
     * Stream over a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<V> channel, K key, String max, String min);

    /**
     * Stream over a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<V> channel, K key, double max, double min, long offset, long count);

    /**
     * Stream over a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return Long count of elements in the specified range.
     */
    Observable<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<V> channel, K key, String max, String min, long offset, long count);

    /**
     * Determine the index of a member in a sorted set, with scores ordered from high to low.
     * 
     * @param key the key
     * @param member the member type: value
     * @return Long integer-reply the rank of {@code member}. If {@code member} does not exist in the sorted set or {@code key}
     *         does not exist,
     */
    Observable<Long> zrevrank(K key, V member);

    /**
     * Get the score associated with the given member in a sorted set.
     * 
     * @param key the key
     * @param member the member type: value
     * @return Double bulk-string-reply the score of {@code member} (a double precision floating point number), represented as
     *         string.
     */
    Observable<Double> zscore(K key, V member);

    /**
     * Add multiple sorted sets and store the resulting sorted set in a new key.
     *
     * @param destination destination key
     * @param keys source keys
     * @return Long integer-reply the number of elements in the resulting sorted set at {@code destination}.
     */
    Observable<Long> zunionstore(K destination, K... keys);

    /**
     * Add multiple sorted sets and store the resulting sorted set in a new key.
     * 
     * @param destination the destination
     * @param storeArgs the storeArgs
     * @param keys the keys
     * @return Long integer-reply the number of elements in the resulting sorted set at {@code destination}.
     */
    Observable<Long> zunionstore(K destination, ZStoreArgs storeArgs, K... keys);

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param key the key
     * @return ScoredValueScanCursor&lt;V&gt; scan cursor.
     */
    Observable<ScoredValueScanCursor<V>> zscan(K key);

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param key the key
     * @param scanArgs scan arguments
     * @return ScoredValueScanCursor&lt;V&gt; scan cursor.
     */
    Observable<ScoredValueScanCursor<V>> zscan(K key, ScanArgs scanArgs);

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param key the key
     * @param scanCursor cursor to resume from a previous scan, must not be {@literal null}
     * @param scanArgs scan arguments
     * @return ScoredValueScanCursor&lt;V&gt; scan cursor.
     */
    Observable<ScoredValueScanCursor<V>> zscan(K key, ScanCursor scanCursor, ScanArgs scanArgs);

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param key the key
     * @param scanCursor cursor to resume from a previous scan, must not be {@literal null}
     * @return ScoredValueScanCursor&lt;V&gt; scan cursor.
     */
    Observable<ScoredValueScanCursor<V>> zscan(K key, ScanCursor scanCursor);

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @return StreamScanCursor scan cursor.
     */
    Observable<StreamScanCursor> zscan(ScoredValueStreamingChannel<V> channel, K key);

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param scanArgs scan arguments
     * @return StreamScanCursor scan cursor.
     */
    Observable<StreamScanCursor> zscan(ScoredValueStreamingChannel<V> channel, K key, ScanArgs scanArgs);

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     * 
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param scanCursor cursor to resume from a previous scan, must not be {@literal null}
     * @param scanArgs scan arguments
     * @return StreamScanCursor scan cursor.
     */
    Observable<StreamScanCursor> zscan(ScoredValueStreamingChannel<V> channel, K key, ScanCursor scanCursor, ScanArgs scanArgs);

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param channel streaming channel that receives a call for every scored value
     * @param key the key
     * @param scanCursor cursor to resume from a previous scan, must not be {@literal null}
     * @return StreamScanCursor scan cursor.
     */
    Observable<StreamScanCursor> zscan(ScoredValueStreamingChannel<V> channel, K key, ScanCursor scanCursor);

    /**
     * Count the number of members in a sorted set between a given lexicographical range.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long integer-reply the number of elements in the specified score range.
     */
    Observable<Long> zlexcount(K key, String min, String max);

    /**
     * Remove all members in a sorted set between the given lexicographical range.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return Long integer-reply the number of elements removed.
     */
    Observable<Long> zremrangebylex(K key, String min, String max);

    /**
     * Return a range of members in a sorted set, by lexicographical range.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<V> zrangebylex(K key, String min, String max);

    /**
     * Return a range of members in a sorted set, by lexicographical range.
     * 
     * @param key the key
     * @param min min score
     * @param max max score
     * @param offset the offset
     * @param count the count
     * @return V array-reply list of elements in the specified score range.
     */
    Observable<V> zrangebylex(K key, String min, String max, long offset, long count);
}
