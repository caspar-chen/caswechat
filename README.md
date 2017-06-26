
CasWechat v2.0 
========================================

[中 文 文 档](https://github.com/caspar-chen/caswechat/blob/2.0/README_CN.md)

CasWechat is an easy to use Java framework for wechat Public Platform applications.

You can use caswechat to quickly and easily develop wechat public platform applications.

The current 2.0 branch interface is still incomplete, in the ongoing update. You can switch to the master branch.

Very much hope you contribute your code in caswechat.

## Getting started
You can refer to our [demo](https://github.com/caspar-chen/caswechat/tree/2.0/caswechat-demo) to provide rapid development

Modify your own information in the [wechat.properties](https://github.com/caspar-chen/caswechat/blob/2.0/caswechat-demo/src/resource/properties/wechat.properties)

Modify your token, appId and appSecret, and the other should not be modified

```gradle
token = your token ,Match the token you fill in the backstage  of the wechat
appId = your appId
appSecret = your appSecret
```
OK, now you can run the [test](https://github.com/caspar-chen/caswechat/tree/2.0/caswechat-demo/src/main/java/com/caspar/caswechat/demo/test) folder inside the main method to test

## Dependency instructions
- Redis , Used to cache accessToken and js tickets
- Spring

## License
caswechat is under the MIT license. See the [LICENSE](https://github.com/caspar-chen/caswechat/blob/2.0/LICENSE.TXT) file for details.