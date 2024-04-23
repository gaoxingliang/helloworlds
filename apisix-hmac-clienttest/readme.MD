# about

translate the hmac implementation of apisix to java:
apisix code: [hmac-auth.lua](https://github.com/apache/apisix/blob/master/apisix/plugins/hmac-auth.lua)
the apisix consumer hmac-auth plugin conf test:

```json
{
  "username": "test_consumer",
  "plugins": {
    "hmac-auth": {
      "_meta": {
        "disable": false
      },
      "access_key": "abcd1234",
      "algorithm": "hmac-sha256",
      "encode_uri_params": false,
      "secret_key": "1234abcd",
      "signed_headers": [
        "X-Provider-ID",
        "Content-Type"
      ],
      "validate_request_body": true
    }
  }
}
```

The hmac request test: [HmacGetRequestTest](./src/main/java/io/github/gaoxingliang/HmacGetRequestTest.java) & [HmacPostRequestTest](./src/main/java/io/github/gaoxingliang/HmacPostRequestTest.java)
<br>
Use data api gateway: [ApiGateWayTest](./src/test/java/TestCases.java)
<br>
TianfuCredit api sm test case: [TianfuCreditSMTestCases](./src/test/java/TianfuCreditSMTestCases.java) 
<br>
TianfuCredit api rsa test case: [TianfuCreditTestCases](./src/test/java/TianfuCreditTestCases.java)