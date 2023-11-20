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


# working with rbac

```shell
-m post -a apisix-test -u gjxd001 -p 03eoPmGhfJLb -h "X-Provider-ID=B_181524" -d "@D:\code\helloworlds\apisix-hmac-clienttest\example_req.json" -h "Content-Type=application/json" -t "http://devicbc.sichuancredit.cn:89/auth/token" "http://devicbc.sichuancredit.cn:89/rawdata"
```
