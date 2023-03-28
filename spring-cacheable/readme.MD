# a example of redis cache
`docker run -p 6379:6379 --name redis -d redis`
with spring annotation `@Cacheable` and another implementation of `@NegativeCacheable`
<br>
**Normal cache** path:
```
 if found in cache :
    return 
 else:
    call logic and put into cache
```

**Negative cache** path:
```
  success := call logic
  if success:
    put into cache
  else:
    return cache value  
```