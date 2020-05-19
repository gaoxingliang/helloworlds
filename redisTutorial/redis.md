[TOC]



# install

download from http://download.redis.io/redis-stable.tar.gz.

and then `make`

and copy

```
edward@MacBook-Pro  redis-6.0.1 mkdir bin
edward@MacBook-Pro  redis-6.0.1 cp src/redis-server bin/
edward@MacBook-Pro  redis-6.0.1 cp src/redis-cli bin/

or just
make install
```



Redis cli

```
redis-cli -h host -p port -a password
```



# config

Start with a conf:

```
edward@MacBook-Pro  bin redis-server --help
Usage: ./redis-server [/path/to/redis.conf] [options]
       ./redis-server - (read config from stdin)
       ./redis-server -v or --version
       ./redis-server -h or --help
       ./redis-server --test-memory <megabytes>

Examples:
       ./redis-server (run the server with default conf)
       ./redis-server /etc/redis/6379.conf
       ./redis-server --port 7777
       ./redis-server --port 7777 --replicaof 127.0.0.1 8888
       ./redis-server /etc/myredis.conf --loglevel verbose

Sentinel mode:
       ./redis-server /etc/sentinel.conf --sentinel
```



show conf in redis:

```
CONFIG GET *
```



edit in file or cmd:

```
CONFIG SET confname confvalue
```



# data types

## strings

binary safe like kafka.

```
127.0.0.1:6379> set name "ispoint"
OK
127.0.0.1:6379> get name
"ispoint"

```

| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [SET key value](https://www.tutorialspoint.com/redis/strings_set.htm)  This command sets the value at the specified key. |
|   2   | [GET key](https://www.tutorialspoint.com/redis/strings_get.htm)  Gets the value of a key. |
|   3   | [GETRANGE key start end](https://www.tutorialspoint.com/redis/strings_getrange.htm)  Gets a substring of the string stored at a key. |
|   4   | [GETSET key value ](https://www.tutorialspoint.com/redis/strings_getset.htm) Sets the string value of a key and return its old value. |
|   5   | [GETBIT key offset](https://www.tutorialspoint.com/redis/strings_getbit.htm) Returns the bit value at the offset in the string value stored at the key. |
|   6   | [MGET key1 [key2..\]](https://www.tutorialspoint.com/redis/strings_mget.htm) Gets the values of all the given keys |
|   7   | [SETBIT key offset value ](https://www.tutorialspoint.com/redis/strings_setbit.htm) Sets or clears the bit at the offset in the string value stored at the key |
|   8   | [SETEX key seconds value](https://www.tutorialspoint.com/redis/strings_setex.htm) Sets the value with the expiry of a key |
|   9   | [SETNX key value](https://www.tutorialspoint.com/redis/strings_setnx.htm) Sets the value of a key, only if the key does not exist |
|  10   | [SETRANGE key offset value](https://www.tutorialspoint.com/redis/strings_setrange.htm) Overwrites the part of a string at the key starting at the specified offset |
|  11   | [STRLEN key](https://www.tutorialspoint.com/redis/strings_strlen.htm) Gets the length of the value stored in a key |
|  12   | [MSET key value [key value ...\]](https://www.tutorialspoint.com/redis/strings_mset.htm) Sets multiple keys to multiple values |
|  13   | [MSETNX key value [key value ...\]](https://www.tutorialspoint.com/redis/strings_msetnx.htm)  Sets multiple keys to multiple values, only if none of the keys exist |
|  14   | [PSETEX key milliseconds value](https://www.tutorialspoint.com/redis/strings_psetex.htm) Sets the value and expiration in milliseconds of a key |
|  15   | [INCR key](https://www.tutorialspoint.com/redis/strings_incr.htm) Increments the integer value of a key by one |
|  16   | [INCRBY key increment](https://www.tutorialspoint.com/redis/strings_incrby.htm) Increments the integer value of a key by the given amount |
|  17   | [INCRBYFLOAT key increment](https://www.tutorialspoint.com/redis/strings_incrbyfloat.htm) Increments the float value of a key by the given amount |
|  18   | [DECR key](https://www.tutorialspoint.com/redis/strings_decr.htm) Decrements the integer value of a key by one |
|  19   | [DECRBY key decrement](https://www.tutorialspoint.com/redis/strings_decrby.htm) Decrements the integer value of a key by the given number |
|  20   | [APPEND key value](https://www.tutorialspoint.com/redis/strings_append.htm) Appends a value to a key |







## hashes

```
127.0.0.1:6379> hmset user:1 username gxl passwd test points 100
OK
127.0.0.1:6379> hgetall user:1
1) "username"
2) "gxl"
3) "passwd"
4) "test"
5) "points"
6) "100"
```



## lists

```
127.0.0.1:6379> lpush mylist redis yes it is okay
(integer) 5
127.0.0.1:6379> lrange mylist 0 2
1) "okay"
2) "is"
3) "it"
```

| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [BLPOP key1 [key2 \] timeout](https://www.tutorialspoint.com/redis/lists_blpop.htm)  Removes and gets the first element in a list, or blocks until one is available |
|   2   | [BRPOP key1 [key2 \] timeout](https://www.tutorialspoint.com/redis/lists_brpop.htm)  Removes and gets the last element in a list, or blocks until one is available |
|   3   | [BRPOPLPUSH source destination timeout](https://www.tutorialspoint.com/redis/lists_brpoplpush.htm)  Pops a value from a list, pushes it to another list and returns it; or blocks until one is available |
|   4   | [LINDEX key index](https://www.tutorialspoint.com/redis/lists_lindex.htm)  Gets an element from a list by its index |
|   5   | [LINSERT key BEFORE\|AFTER pivot value](https://www.tutorialspoint.com/redis/lists_linsert.htm)  Inserts an element before or after another element in a list |
|   6   | [LLEN key](https://www.tutorialspoint.com/redis/lists_llen.htm)  Gets the length of a list |
|   7   | [LPOP key](https://www.tutorialspoint.com/redis/lists_lpop.htm)  Removes and gets the first element in a list |
|   8   | [LPUSH key value1 [value2\]](https://www.tutorialspoint.com/redis/lists_lpush.htm)  Prepends one or multiple values to a list |
|   9   | [LPUSHX key value](https://www.tutorialspoint.com/redis/lists_lpushx.htm)  Prepends a value to a list, only if the list exists |
|  10   | [LRANGE key start stop](https://www.tutorialspoint.com/redis/lists_lrange.htm)  Gets a range of elements from a list |
|  11   | [LREM key count value](https://www.tutorialspoint.com/redis/lists_lrem.htm)  Removes elements from a list |
|  12   | [LSET key index value](https://www.tutorialspoint.com/redis/lists_lset.htm)  Sets the value of an element in a list by its index |
|  13   | [LTRIM key start stop](https://www.tutorialspoint.com/redis/lists_ltrim.htm)  Trims a list to the specified range |
|  14   | [RPOP key](https://www.tutorialspoint.com/redis/lists_rpop.htm)  Removes and gets the last element in a list |
|  15   | [RPOPLPUSH source destination](https://www.tutorialspoint.com/redis/lists_rpoplpush.htm)  Removes the last element in a list, appends it to another list and returns it |
|  16   | [RPUSH key value1 [value2\]](https://www.tutorialspoint.com/redis/lists_rpush.htm)  Appends one or multiple values to a list |
|  17   | [RPUSHX key value](https://www.tutorialspoint.com/redis/lists_rpushx.htm)  Appends a value to a list, only if the list exists |



## sets

```
sadd myset okay
sadd myset tes
smembers myset
```



| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [SADD key member1 [member2\]](https://www.tutorialspoint.com/redis/sets_sadd.htm)  Adds one or more members to a set |
|   2   | [SCARD key](https://www.tutorialspoint.com/redis/sets_scard.htm)  Gets the number of members in a set |
|   3   | [SDIFF key1 [key2\]](https://www.tutorialspoint.com/redis/sets_sdiff.htm)  Subtracts multiple sets |
|   4   | [SDIFFSTORE destination key1 [key2\]](https://www.tutorialspoint.com/redis/sets_sdiffstore.htm)  Subtracts multiple sets and stores the resulting set in a key |
|   5   | [SINTER key1 [key2\]](https://www.tutorialspoint.com/redis/sets_sinter.htm)  Intersects multiple sets |
|   6   | [SINTERSTORE destination key1 [key2\]](https://www.tutorialspoint.com/redis/sets_sinterstore.htm)  Intersects multiple sets and stores the resulting set in a key |
|   7   | [SISMEMBER key member](https://www.tutorialspoint.com/redis/sets_sismember.htm)  Determines if a given value is a member of a set |
|   8   | [SMEMBERS key](https://www.tutorialspoint.com/redis/sets_sismember.htm)  Gets all the members in a set |
|   9   | [SMOVE source destination member](https://www.tutorialspoint.com/redis/sets_smove.htm)  Moves a member from one set to another |
|  10   | [SPOP key](https://www.tutorialspoint.com/redis/sets_spop.htm)  Removes and returns a random member from a set |
|  11   | [SRANDMEMBER key [count\]](https://www.tutorialspoint.com/redis/sets_srandmember.htm)  Gets one or multiple random members from a set |
|  12   | [SREM key member1 [member2\]](https://www.tutorialspoint.com/redis/sets_srem.htm)  Removes one or more members from a set |
|  13   | [SUNION key1 [key2\]](https://www.tutorialspoint.com/redis/sets_sunion.htm)  Adds multiple sets |
|  14   | [SUNIONSTORE destination key1 [key2\]](https://www.tutorialspoint.com/redis/sets_sunionstore.htm)  Adds multiple sets and stores the resulting set in a key |
|  15   | [SSCAN key cursor [MATCH pattern\] [COUNT count]](https://www.tutorialspoint.com/redis/sets_sscan.htm)  Incrementally iterates set elements |

## sorted sets

```
zadd mysort 0 redis
zadd mysort 10 mongodb
zadd mysort 5 qq
zrangebyscore mysort 0 15

```

| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [ZADD key score1 member1 [score2 member2\]](https://www.tutorialspoint.com/redis/sorted_sets_zadd.htm)  Adds one or more members to a sorted set, or updates its score, if it already exists |
|   2   | [ZCARD key](https://www.tutorialspoint.com/redis/sorted_sets_zcard.htm)  Gets the number of members in a sorted set |
|   3   | [ZCOUNT key min max](https://www.tutorialspoint.com/redis/sorted_sets_zcount.htm)  Counts the members in a sorted set with scores within the given values |
|   4   | [ ZINCRBY key increment member](https://www.tutorialspoint.com/redis/sorted_sets_zincrby.htm)  Increments the score of a member in a sorted set |
|   5   | [ZINTERSTORE destination numkeys key [key ...\]](https://www.tutorialspoint.com/redis/sorted_sets_zinterstore.htm)  Intersects multiple sorted sets and stores the resulting sorted set in a new key |
|   6   | [ZLEXCOUNT key min max](https://www.tutorialspoint.com/redis/sorted_sets_zlexcount.htm)  Counts the number of members in a sorted set between a given lexicographical range |
|   7   | [ZRANGE key start stop [WITHSCORES\]](https://www.tutorialspoint.com/redis/sorted_sets_zrange.htm)  Returns a range of members in a sorted set, by index |
|   8   | [ZRANGEBYLEX key min max [LIMIT offset count\]](https://www.tutorialspoint.com/redis/sorted_sets_zrangebylex.htm)  Returns a range of members in a sorted set, by lexicographical range |
|   9   | [ZRANGEBYSCORE key min max [WITHSCORES\] [LIMIT]](https://www.tutorialspoint.com/redis/sorted_sets_zrangebyscore.htm)  Returns a range of members in a sorted set, by score |
|  10   | [ZRANK key member](https://www.tutorialspoint.com/redis/sorted_sets_zrank.htm)  Determines the index of a member in a sorted set |
|  11   | [ZREM key member [member ...\]](https://www.tutorialspoint.com/redis/sorted_sets_zrem.htm)  Removes one or more members from a sorted set |
|  12   | [ZREMRANGEBYLEX key min max](https://www.tutorialspoint.com/redis/sorted_sets_zremrangebylex.htm)  Removes all members in a sorted set between the given lexicographical range |
|  13   | [ZREMRANGEBYRANK key start stop](https://www.tutorialspoint.com/redis/sorted_sets_zremrangebyrank.htm)  Removes all members in a sorted set within the given indexes |
|  14   | [ZREMRANGEBYSCORE key min max](https://www.tutorialspoint.com/redis/sorted_sets_zremrangebyscore.htm)  Removes all members in a sorted set within the given scores |
|  15   | [ZREVRANGE key start stop [WITHSCORES\]](https://www.tutorialspoint.com/redis/sorted_sets_zrevrange.htm)  Returns a range of members in a sorted set, by index, with scores ordered from high to low |
|  16   | [ZREVRANGEBYSCORE key max min [WITHSCORES\]](https://www.tutorialspoint.com/redis/sorted_sets_zrevrangebyscore.htm)  Returns a range of members in a sorted set, by score, with scores ordered from high to low |
|  17   | [ZREVRANK key member](https://www.tutorialspoint.com/redis/sorted_sets_zrevrank.htm)  Determines the index of a member in a sorted set, with scores ordered from high to low |
|  18   | [ZSCORE key member](https://www.tutorialspoint.com/redis/sorted_sets_zscore.htm)  Gets the score associated with the given member in a sorted set |
|  19   | [ZUNIONSTORE destination numkeys key [key ...\]](https://www.tutorialspoint.com/redis/sorted_sets_zunionstore.htm)  Adds multiple sorted sets and stores the resulting sorted set in a new key |
|  20   | [ZSCAN key cursor [MATCH pattern\] [COUNT count]](https://www.tutorialspoint.com/redis/sorted_sets_zscan.htm)  Incrementally iterates sorted sets elements and associated scores |

# keys to manage

| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [DEL key](https://www.tutorialspoint.com/redis/keys_del.htm) This command deletes the key, if it exists. |
|   2   | [DUMP key](https://www.tutorialspoint.com/redis/keys_dump.htm)  This command returns a serialized version of the value stored at the specified key. |
|   3   | [EXISTS key](https://www.tutorialspoint.com/redis/keys_exists.htm)  This command checks whether the key exists or not. |
|   4   | [EXPIRE key](https://www.tutorialspoint.com/redis/keys_expire.htm) seconds  Sets the expiry of the key after the specified time. |
|   5   | [EXPIREAT key timestamp](https://www.tutorialspoint.com/redis/keys_expireat.htm)  Sets the expiry of the key after the specified time. Here time is in Unix timestamp format. |
|   6   | [PEXPIRE key milliseconds](https://www.tutorialspoint.com/redis/keys_pexpire.htm)  Set the expiry of key in milliseconds. |
|   7   | [PEXPIREAT key milliseconds-timestamp](https://www.tutorialspoint.com/redis/keys_pexpireat.htm)  Sets the expiry of the key in Unix timestamp specified as milliseconds. |
|   8   | [KEYS pattern](https://www.tutorialspoint.com/redis/keys_keys.htm)  Finds all keys matching the specified pattern. |
|   9   | [MOVE key db](https://www.tutorialspoint.com/redis/keys_move.htm)  Moves a key to another database. |
|  10   | [PERSIST key](https://www.tutorialspoint.com/redis/keys_persist.htm)  Removes the expiration from the key. |
|  11   | [PTTL key](https://www.tutorialspoint.com/redis/keys_pttl.htm)  Gets the remaining time in keys expiry in milliseconds. |
|  12   | [TTL key](https://www.tutorialspoint.com/redis/keys_ttl.htm)  Gets the remaining time in keys expiry. |
|  13   | [RANDOMKEY](https://www.tutorialspoint.com/redis/keys_randomkey.htm)  Returns a random key from Redis. |
|  14   | [RENAME key newkey](https://www.tutorialspoint.com/redis/keys_rename.htm)  Changes the key name. |
|  15   | [RENAMENX key newkey](https://www.tutorialspoint.com/redis/keys_renamenx.htm)  Renames the key, if a new key doesn't exist. |
|  16   | [TYPE key](https://www.tutorialspoint.com/redis/keys_type.htm)  Returns the data type of the value stored in the key. |



# hyper hyper log

Redis HyperLogLog is an algorithm that uses randomization in order to provide an approximation of the number of unique elements in a set  using just a constant, and small amount of memory.

HyperLogLog provides a very good approximation of the cardinality of a set even using a very small amount of memory around 12 kbytes per key  with a standard error of 0.81%. There is no limit to the number of items you can count, unless you approach 264 items.

| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [PFADD key element [element ...\]](https://www.tutorialspoint.com/redis/hyperloglog_pfadd.htm)  Adds the specified elements to the specified HyperLogLog. |
|   2   | [PFCOUNT key [key ...\]](https://www.tutorialspoint.com/redis/hyperloglog_pfcount.htm)  Returns the approximated cardinality of the set(s) observed by the HyperLogLog at key(s). |
|   3   | [PFMERGE destkey sourcekey [sourcekey ...\]](https://www.tutorialspoint.com/redis/hyperloglog_pfmerge.htm)  Merges N different HyperLogLogs into a single one. |



# publish and subscribe

if two client subscirbes same channel, all will receive requests same.

| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [PSUBSCRIBE pattern [pattern ...\]](https://www.tutorialspoint.com/redis/pub_sub_psubscribe.htm)  Subscribes to channels matching the given patterns. |
|   2   | [PUBSUB subcommand [argument [argument ...\]]](https://www.tutorialspoint.com/redis/pub_sub_pubsub.htm)  Tells the state of Pub/Sub system. For example, which clients are active on the server. |
|   3   | [PUBLISH channel message](https://www.tutorialspoint.com/redis/pub_sub_publish.htm)  Posts a message to a channel. |
|   4   | [PUNSUBSCRIBE [pattern [pattern ...\]]](https://www.tutorialspoint.com/redis/pub_sub_punsubscribe.htm)  Stops listening for messages posted to channels matching the given patterns. |
|   5   | [SUBSCRIBE channel [channel ...\]](https://www.tutorialspoint.com/redis/pub_sub_subscribe.htm)  Listens for messages published to the given channels. |
|   6   | [UNSUBSCRIBE [channel [channel ...\]]](https://www.tutorialspoint.com/redis/pub_sub_unsubscribe.htm)  Stops listening for messages posted to the given channels. |



# transactions

```
redis 127.0.0.1:6379> MULTI 
OK 
redis 127.0.0.1:6379> SET tutorial redis 
QUEUED 
redis 127.0.0.1:6379> GET tutorial 
QUEUED 
redis 127.0.0.1:6379> INCR visitors 
QUEUED 
redis 127.0.0.1:6379> EXEC  
```



| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [DISCARD](https://www.tutorialspoint.com/redis/transactions_discard.htm)  Discards all commands issued after MULTI |
|   2   | [EXEC](https://www.tutorialspoint.com/redis/transactions_exec.htm)  Executes all commands issued after MULTI |
|   3   | [MULTI](https://www.tutorialspoint.com/redis/transactions_multi.htm)  Marks the start of a transaction block |
|   4   | [UNWATCH](https://www.tutorialspoint.com/redis/transactions_unwatch.htm)  Forgets about all watched keys |
|   5   | [WATCH key [key ...\]](https://www.tutorialspoint.com/redis/transactions_watch.htm)  Watches the given keys to determine the execution of the MULTI/EXEC block |



# script

```
redis 127.0.0.1:6379> EVAL script numkeys key [key ...] arg [arg ...]
```

| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [EVAL script numkeys key [key ...\] arg [arg ...]](https://www.tutorialspoint.com/redis/scripting_eval.htm)  Executes a Lua script. |
|   2   | [EVALSHA sha1 numkeys key [key ...\] arg [arg ...]](https://www.tutorialspoint.com/redis/scripting_evalsha.htm)  Executes a Lua script. |
|   3   | [SCRIPT EXISTS script [script ...\]](https://www.tutorialspoint.com/redis/scripting_script_exists.htm)  Checks the existence of scripts in the script cache. |
|   4   | [SCRIPT FLUSH](https://www.tutorialspoint.com/redis/scripting_script_flush.htm)  Removes all the scripts from the script cache. |
|   5   | [SCRIPT KILL](https://www.tutorialspoint.com/redis/scripting_script_kill.htm)  Kills the script currently in execution. |
|   6   | [SCRIPT LOAD script](https://www.tutorialspoint.com/redis/scripting_script_load.htm)  Loads the specified Lua script into the script cache. |

```
redis 127.0.0.1:6379> EVAL "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}" 2 key1 
key2 first second  
1) "key1" 
2) "key2" 
3) "first" 
4) "second"
```



# connections

```
redis 127.0.0.1:6379> AUTH "password" 
OK 
redis 127.0.0.1:6379> PING 
PONG 
```



| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [AUTH password](https://www.tutorialspoint.com/redis/connection_auth.htm)  Authenticates to the server with the given password |
|   2   | [ECHO message](https://www.tutorialspoint.com/redis/connection_echo.htm)  Prints the given string |
|   3   | [PING](https://www.tutorialspoint.com/redis/connection_ping.htm)  Checks whether the server is running or not |
|   4   | [QUIT](https://www.tutorialspoint.com/redis/connection_quit.htm)  Closes the current connection |
|   5   | [SELECT index](https://www.tutorialspoint.com/redis/connection_select.htm)  Changes the selected database for the current connection |



# server

Following table lists some basic commands related to Redis server.

| Sr.No | Command & Description                                        |
| :---: | :----------------------------------------------------------- |
|   1   | [BGREWRITEAOF](https://www.tutorialspoint.com/redis/server_bgrewriteaof.htm)  Asynchronously rewrites the append-only file |
|   2   | [BGSAVE](https://www.tutorialspoint.com/redis/server_bgsave.htm)  Asynchronously saves the dataset to the disk |
|   3   | [CLIENT KILL [ip:port\] [ID client-id]](https://www.tutorialspoint.com/redis/server_client_kill.htm)   Kills the connection of a client |
|   4   | [CLIENT LIST](https://www.tutorialspoint.com/redis/server_client_list.htm)  Gets the list of client connections to the server |
|   5   | [CLIENT GETNAME](https://www.tutorialspoint.com/redis/server_client_getname.htm)  Gets the name of the current connection |
|   6   | [CLIENT PAUSE timeout](https://www.tutorialspoint.com/redis/server_client_pause.htm)  Stops processing commands from the clients for a specified time |
|   7   | [CLIENT SETNAME connection-name](https://www.tutorialspoint.com/redis/server_client_setname.htm)  Sets the current connection name |
|   8   | [CLUSTER SLOTS](https://www.tutorialspoint.com/redis/server_cluster_slots.htm)  Gets an array of Cluster slot to node mappings |
|   9   | [COMMAND](https://www.tutorialspoint.com/redis/server_command.htm)  Gets an array of Redis command details |
|  10   | [COMMAND COUNT](https://www.tutorialspoint.com/redis/server_command_count.htm)  Gets total number of Redis commands |
|  11   | [COMMAND GETKEYS](https://www.tutorialspoint.com/redis/server_command_getkeys.htm)  Extracts the keys given a full Redis command |
|  12   | [BGSAVE](https://www.tutorialspoint.com/redis/server_bgsave.htm)  Asynchronously saves the dataset to the disk |
|  13   | [COMMAND INFO command-name [command-name ...\]](https://www.tutorialspoint.com/redis/server_command_info.htm)  Gets an array of specific Redis command details |
|  14   | [CONFIG GET parameter](https://www.tutorialspoint.com/redis/server_config_get.htm)  Gets the value of a configuration parameter |
|  15   | [CONFIG REWRITE](https://www.tutorialspoint.com/redis/server_config_rewrite.htm)  Rewrites the configuration file with the in-memory configuration |
|  16   | [CONFIG SET parameter value](https://www.tutorialspoint.com/redis/server_config_set.htm)  Sets a configuration parameter to the given value |
|  17   | [CONFIG RESETSTAT](https://www.tutorialspoint.com/redis/server_config_resetstat.htm)  Resets the stats returned by INFO |
|  18   | [DBSIZE](https://www.tutorialspoint.com/redis/server_dbsize.htm)  Returns the number of keys in the selected database |
|  19   | [DEBUG OBJECT key](https://www.tutorialspoint.com/redis/server_debug_object.htm)  Gets debugging information about a key |
|  20   | [DEBUG SEGFAULT](https://www.tutorialspoint.com/redis/server_debug_segfault.htm)  Makes the server crash |
|  21   | [FLUSHALL](https://www.tutorialspoint.com/redis/server_flushall.htm)  Removes all the keys from all databases |
|  22   | [FLUSHDB](https://www.tutorialspoint.com/redis/server_flushdb.htm)  Removes all the keys from the current database |
|  23   | [INFO [section\]](https://www.tutorialspoint.com/redis/server_info.htm)  Gets information and statistics about the server |
|  24   | [LASTSAVE](https://www.tutorialspoint.com/redis/server_lastsave.htm)  Gets the UNIX time stamp of the last successful save to the disk |
|  25   | [MONITOR](https://www.tutorialspoint.com/redis/server_monitor.htm)  Listens for all the requests received by the server in real time |
|  26   | [ROLE](https://www.tutorialspoint.com/redis/server_role.htm)  Returns the role of the instance in the context of replication |
|  27   | [SAVE](https://www.tutorialspoint.com/redis/server_save.htm)  Synchronously saves the dataset to the disk |
|  28   | [ SHUTDOWN [NOSAVE\] [SAVE]](https://www.tutorialspoint.com/redis/server_shutdown.htm)  Synchronously saves the dataset to the disk and then shuts down the server |
|  29   | [SLAVEOF host port](https://www.tutorialspoint.com/redis/server_slaveof.htm)  Makes the server a slave of another instance, or promotes it as a master |
|  30   | [SLOWLOG subcommand [argument\]](https://www.tutorialspoint.com/redis/server_showlog.htm)  Manages the Redis slow queries log |
|  31   | [SYNC](https://www.tutorialspoint.com/redis/server_sync.htm)  Command used for replication |
|  32   | [TIME](https://www.tutorialspoint.com/redis/server_time.htm)  Returns the current server time |



# BACKUP

```
save

config get dir

bgsave
```



# benchmark

```
redis-benchmark -n 100000
```

```
edward@MacBook-Pro.local  src ./redis-benchmark --csv
"PING_INLINE","108577.63"
"PING_BULK","109409.20"
"SET","108225.10"
"GET","107991.36"
"INCR","111111.12"
"LPUSH","108695.65"
"RPUSH","110132.16"
"LPOP","108225.10"
"RPOP","106382.98"
"SADD","108577.63"
"HSET","110375.27"
"SPOP","107066.38"
"LPUSH (needed to benchmark LRANGE)","109769.48"
"LRANGE_100 (first 100 elements)","23518.35"
"LRANGE_300 (first 300 elements)","9475.98"

```



# partition

Benefits: (1) more storage , more memory  (2) scale out

Disadvantages: (1) op across partition is usally not supported

(2) transactions 

(3) also possible of a huge key in one slot

(4) more complex ops like backup







# references

[quick guide](https://redis.io/topics/quickstart)

