CasWechat v2.0 
========================================

[English description](https://github.com/caspar-chen/caswechat/blob/2.0/README.md)

CasWechat 是一个简单易用的java微信公众平台sdk

你可以使用CasWechat 快速构建微信公众平台项目，并且很方便与现有的项目集成

目前2.0分支的接口还不够完善，在持续更新中，可以切换到master分支（3年前的版本）

希望更多的人来贡献代码，完善CasWechat

## 如何使用
你可以参考 [demo](https://github.com/caspar-chen/caswechat/tree/2.0/caswechat-demo) 

修改配置文件 [wechat.properties](https://github.com/caspar-chen/caswechat/blob/2.0/caswechat-demo/src/resource/properties/wechat.properties)

在配置文件中修改你的 token, appId 和  appSecret，其他的不要修改，是微信url地址。

```gradle
token = 你的 token ,和你在微信公众平台后台配置的token要一样
appId = 你的appId
appSecret = 你的appSecret
```
Ok,现在就可以运行demo项目中 [test](https://github.com/caspar-chen/caswechat/tree/2.0/caswechat-demo/src/main/java/com/caspar/caswechat/demo/test) 文件夹下面相应的测试方法查看效果了

## 依赖说明
- Redis , 用于缓存 accessToken 和 js tickets , 因为这些接口一天只能请求200次，有效期为两小时，所以必须缓存
- Spring , bean管理

## License
CasWechat 基于 MIT 开源协议, 完全开源，可以任意复制，修改和使用。 具体可以查看 [LICENSE](https://github.com/caspar-chen/caswechat/blob/2.0/LICENSE.TXT) 文件.