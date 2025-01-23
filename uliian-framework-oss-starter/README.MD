# OSS模块

该模块提供对象存储相关的功能，目前支持阿里云OSS、腾讯云COS、七牛云。

其中阿里云的签名为自己实现，腾讯云和七牛云的签名为SDK实现，为了避免不必要的空间占用，如果需要
使用腾讯云和七牛云的功能，请在`build.gradle`中引入对应的依赖。

### 腾讯云SDK引入
```groovy
implementation 'com.qcloud:cos_api:5.6.54'
```

### 七牛云SDK引入
```groovy
implementation 'com.qiniu:qiniu-java-sdk:7.17.0'
```

## 配置
```yaml
framework:
  oss:
    type: aliyun|tencent|qiniu  # 存储类型
    bucket-name: bucket # 存储桶名称
    access-key-id: fdsafdas   # 访问密钥ID
    access-key-secret: fdsafdas # 访问密钥密钥
    upload-end-point: https://wwww.zzz.ccc/ # 上传地址
    download-end-point: https://wwww.zzz.ccc/ # 下载地址
    region: cn-hangzhou # 区域
    path: "/test" # controller路径
```