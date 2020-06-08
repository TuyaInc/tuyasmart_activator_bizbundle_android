# 涂鸦智能配网业务包 接入指南

## 功能介绍

业务功能涵盖了目前涂鸦智能所有的 Wi-Fi 设备、ZigBee 设备、蓝牙设备以及支持二维码扫码的设备（例如 GPRS & NB-IOT 设备）等不同类型设备配网前置操作引导和具体入网激活实现

### 一、配网操作引导

基于涂鸦云服务，支持配置不同设备品类信息，提供相应的设备重置引导（即如何操作设备使其进入待配网状态），包括文字、图片以及视频引导配置功能

### 二、Wi-Fi 设备配网

支持 Wi-Fi 智能设备入网连接云服务，Wi-Fi 设备配网主要有 EZ 模式和 AP 模式两种，其中 IPC 设备支持扫二维码方式配网

| 名词       | 解释                                                     |
| --------   | ------------------------------------------------------- |
| EZ 模式      | 又称快连模式，APP 把配网数据包打包到802.11数据包的指定区域中，<br> 发送到周围环境；智能设备的 WiFi 模块处于混杂模式下，<br> 监听捕获网络中的所有报文，按照约定的协议数据格式解析出 APP 发出配网信息包。 |
| AP 模式      | 又称热点模式，手机作为 STA 连接智能设备的热点，双方建立一个 Socket 连接通过约定端口交互数据。 |
| IPC 设备扫码配网      | 摄像头设备通过扫描 APP上的二维码获取配网数据信息 |

### 三、ZigBee 设备配网

支持 ZigBee 网关和子设备配网。

| 名词      | 解释                                                     |
| -------- | -------------------------------------------------------  |
| ZigBee 网关 | 融合 ZigBee 网络中协调器和 Wi-Fi 功能的设备，负责 ZigBee 网络的组建及数据信息存储。 |
| 子设备 | ZigBee 网络中的路由或者终端设备，负责数据转发或者终端控制响应。|

### 四、蓝牙设备配网

涂鸦蓝牙有三条技术线路，主要包括 SingleBLE、SigMesh、TuyaMesh 以及双模设备。

| 名词  | 解释              |
| ----------- | -----------------  |
| SingleBLE     | 通过蓝牙与手机一对一相连的蓝牙单点设备 |
| SigMesh     | 采用蓝牙技术联盟发布的蓝牙拓扑通信|
| TuyaMesh    | 采用涂鸦自研的蓝牙拓扑通信|
| 双模设备     | 支持多协议的设备，即同时具备 Wi-Fi 能力和 BLE 能力的设备|

### 五、扫码配网设备

该类设备上电后即连接了涂鸦云服务，APP 通过扫描设备上的二维码（必须是涂鸦云服务支持的二维码规则，支持固件具体接入方式可咨询涂鸦科技相关商务及项目经理）使能设备去涂鸦云激活绑定

| 名词  | 解释              |
| ----------- | -----------------  |
| GPRS 设备    | 采用 GPRS 通信技术接入网络连接云服务的智能设备 |
| NB-IOT 设备  | 采用窄带物联网 (NarrowBand-InternetofThings) 技术的智能设备 |

### 六、自动发现配网

融合涂鸦智能通用配网技术实现，为用户提供一套快捷配网的功能

## 业务包集成

### 一、集成准备

该业务包是基于 [涂鸦全屋智能SDK](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/zh-hans/) 3.17.0 版本开发的，所以在集成之前，需要做一下准备工作：

1. 集成涂鸦全屋智能 SDK，参考 [集成准备](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/zh-hans/resource/Preparation.html)（包括申请 Tuya AppKey 、 App Secret 和 安全图片的 配置）

2. 实现涂鸦全屋智能 SDK [用户注册](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/zh-hans/resource/User.html) 和 [家庭创建](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/zh-hans/resource/HomeManager.html) 业务

### 二、业务集成

1. 创建工程

  在 Android Studio 中建立你的工程，接入涂鸦全屋智能 SDK 并完成配置
  
2. 配置根目录的 build.gradle 

  ```groovy
	buildscript {
	
	     repositories {
	         maven {
	             url 'https://maven-other.tuya.com/repository/maven-releases/'
	         }
             google()
             jcenter()
	     }
	     dependencies {
	         classpath 'com.tuya.android.module:tymodule-config:0.4.0-SNAPSHOT'
	         classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
	     }
	 }
	
	 allprojects {
	     repositories {
	         maven {
	             url 'https://maven-other.tuya.com/repository/maven-releases/'
	         }
             google()
             jcenter()
	     }
	 }
	
```
  
3. 配置业务模块的 build.gradle

  ```groovy
    apply plugin: 'tymodule-config'
    apply plugin: 'kotlin-android-extensions'
    apply plugin: 'kotlin-android'
	
	defaultConfig {
	    ndk {
	        abiFilters "armeabi-v7a", "arm64-v8a"
	    }
	 }
	 
	dependencies { 
	    implementation fileTree(dir: 'libs', include: ['*.jar'])
	    
        implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
	    
	    implementation 'com.alibaba:fastjson:1.1.67.android'
	    implementation 'com.facebook.fresco:fresco:1.3.0'
	    implementation 'com.google.zxing:core:3.2.1'
	    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
	    implementation 'io.reactivex.rxjava2:rxjava:2.1.7'
	    implementation 'com.airbnb.android:lottie:2.7.0'
	    implementation 'com.google.android:flexbox:0.2.5'
	    
	    implementation 'com.tuya.smart:tuyasmart:3.17.0-beta1'
	    
	    implementation 'com.tuya.smart:tuya-commonbiz-api:1.0.0-SNAPSHOT'
	    implementation 'com.tuya.smart:tuyasmart-qrcode:3.12.0r123-rc.2'
	    
        implementation 'com.tuya.smart:tuyasmart-framework:3.17.0.2r139-external'
        implementation "com.tuya.android.module:tymodule-annotation:0.0.8"
	    
        implementation 'com.tuya.smart:tuyasmart-uispecs:0.0.7'
        implementation 'com.tuya.smart:tuyasmart-base:3.17.0r139-rc.3'
        implementation 'com.tuya.smart:tuyasmart-stencilwrapper:3.17.0.3r139'
        implementation 'com.tuya.smart:tuyasmart-uiadapter:3.13.3r129-rc.4'
        implementation 'com.tuya.android:dimencompat:1.0.1'
        implementation 'com.tuya.smart:tuyasmart-stencilmodel:3.17.0r139-rc.2'
        implementation 'com.tuya.smart:tuyasmart-webcontainer:3.17.6r141-open-rc.1'
    
        implementation 'com.tuya.smart:tuya-config-mesh:1.0.3'
        implementation 'com.tuya.smart:tuya-config-mesh-api:1.0.1'
	    
        implementation 'com.tuya.smart:tuya-config-ble:1.0.1'
        implementation 'com.tuya.smart:tuya-config-ble-api:1.0.1'
	    
        implementation 'com.tuya.smart:tuya-config-api:1.0.1'
        implementation 'com.tuya.smart:tuya-config:1.0.2'
	    
	    implementation 'com.tuya.smart:tuya-scan:1.0.1'
    }
	
	repositories {
	    mavenLocal()
	    jcenter()
	    google()
	}

  ```
  
4. AndroidManifest.xml 设置

  ```xml	
	<uses-permission android:name="android.permission.BLUETOOTH" />
	<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
	<uses-feature
	android:name="android.hardware.bluetooth_le"
	android:required="false" />
		
	<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission
        android:name="android.permission.ACCESS_FINE_LOCATION"
        android:required="false" />
	
  ```

5. 混淆配置

  ```bash
	#fastJson
	-keep class com.alibaba.fastjson.**{*;}
	-dontwarn com.alibaba.fastjson.**
	
	#rx
	-dontwarn rx.**
	-keep class rx.** {*;}
	-keep class io.reactivex.**{*;}
	-dontwarn io.reactivex.**
	-keep class rx.**{ *; }
	-keep class rx.android.**{*;}
	
	#fresco
	-keep class com.facebook.drawee.backends.pipeline.Fresco
	
	#tuya
	-keep class com.tuya.**{*;}
	-dontwarn com.tuya.**
  ```
  
## 功能调用

### 一、功能配置

| 配置项  | 字段名              |  描述        |
| ----------- | -----------------  | ----------------- |
| AP 热点名称配置    | <string name="ap_mode_ssid">SmartLife</string> | AP 配网支持的热点前缀列表，默认配置：SmartLife|
| 是否支持 BLE  | <bool name="is_need_ble_support">true</bool>| 是否支持 BLE 设备配网功能，默认配置：true|
| 是否支持 MESH  | <bool name="is_need_blemesh_support">true</bool>| 是否支持 MESH 设备配网功能，默认配置：true|
| 主题色配置  | <color name="primary_button_bg_color">#FF5A28</color>| 按钮背景配色|
| 主题色配置  | <color name="primary_button_font_color">#FFFFFF</color>| 按钮的文字配色|

### 二、方法调用

```java
  TuyaDeviceActivatorManager.startDeviceActiveAction(activity, homeId, 
		new ITuyaDeviceActiveListener() {
            @Override
            public void onDevicesAdd(List<String> devIds, boolean updateRoomData) {
                
            }
    	});
```

**入参说明**

| 参数         | 说明 |
| ------------ | -------------------------- |
| activity          | activity |
| homeId          | 家庭 id，详情参考涂鸦全屋智能 SDK [家庭创建](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/zh-hans/resource/HomeManager.html) |

**出参说明**

| 参数         | 说明 |
| ------------ | -------------------------- |
| devIds          | 配网成功的设备 id 列表 |
| updateRoomData          | 房间设备信息是否有变更 |
