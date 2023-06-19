# about
translate the hmac implementation of apisix to java:
apisix code: [hmac-auth.lua](https://github.com/apache/apisix/blob/master/apisix/plugins/hmac-auth.lua)
the apisix consumer hmac-auth plugin conf test:
```json
{
    "_meta": {
    "disable": false
    },
    "access_key": "abcd1234",
    "algorithm": "hmac-sha256",
    "encode_uri_params": false,
    "secret_key": "1234abcd",
    "signed_headers": [
    "X-Provider-ID", "Content-Type"
    ],
    "validate_request_body": false
}
```