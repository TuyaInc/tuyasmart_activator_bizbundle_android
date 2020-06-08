# Tuya Smart Network Configuration Biz Bundle Access Guide

## Features

The business functions cover all the Wi-Fi devices, ZigBee devices, Bluetooth devices, QR code scanning devices (such as GPRS & NB-IOT devices) 
and other types of devices currently equipped with TuyaSmart APP.

### 1. Wi-Fi Devices

Support Wi-Fi smart devices to connect to the cloud service.
The Wi-Fi network configuration mainly includes two network configuration modes, namely, EZ mode and AP mode. And the camera device supports scanning QR code Configuration network.

| Attributes       | Description                                                     |
| --------   | ------------------------------------------------------- |
| EZ mode      | Also known as the fast connection mode, the APP packs the network data packets into the designated area of the 802.11 data packets and sends them to the surrounding environment; the Wi-Fi module of the smart device is in the promiscuous model and monitors the capture network All the packets in the parsing, according to the agreed protocol data format, parse out the network information packet sent by the APP. |
| AP mode      | Also known as hotspot mode, the mobile phone connects the smart device's hotspot. The two parties establish a Socket connection to exchange data through the agreed port. |
| Camera scan code configuration network | The camera device obtains the distribution data information by scanning the QR code on the APP. |

### 2. ZigBee Devices

Support ZigBee gateway and sub-device network Configuration.

| Attributes | Description |
| -------- | -------------------------------------------------------  |
| ZigBee | ZigBee technology is a short-range, low-complexity, low-power, low-speed, low-cost two-way wireless communication technology. It is mainly used for data transmission between various electronic devices with short distances, low power consumption and low transmission rates, as well as typical applications with periodic data, intermittent data and low response time data transmission.|
| ZigBee Gateway | The device that integrates the coordinator and WiFi functions in the ZigBee network is responsible for the establishment of the ZigBee network and the storage of data information.|
| sub-device | Routing or terminal equipment in ZigBee network, responsible for data forwarding or terminal control response.|

### 3. Bluetooth Devices

Tuya Bluetooth has three technical lines, including SingleBLE, SigMesh, TuyaMesh, and dual-mode devices.

| Attributes  | Description              |
| ----------- | -----------------  |
| SingleBLE   | Bluetooth single point device connected one-to-one with mobile phone via Bluetooth |
| SigMesh     | Adopts the Bluetooth topology communication released by the Bluetooth Technology Alliance |
| TuyaMesh    | Adopting Tuya's self-developed Bluetooth topology communication |
| Dual-mode Device     | Support multi-protocol devices, ie devices with both Wi-Fi and BLE capabilities |

### 4. Scanning code network Configuration Devices

This type of device is connected to the Tuya cloud service after being powered on. The APP scans the QR code on the device (must be the QR code rule supported by the Tuya cloud service, and supports specific firmware access methods. Consult Tuya Technology-related business and project managers ) Enable the device to activate the binding of Tuya Cloud

| Attributes  | Description              |
| ----------- | -----------------  |
| GPRS Device    | Smart devices that use GPRS communication technology to access the network and connect to cloud services |
| NB-IOT Device  | Smart device adopting NarrowBand-Internet of Things technology |

### 5. Automatic network discovery

Integrated with graffiti intelligent universal Configuration network technology to provide users with a set of fast Configuration network functions

## Integrate Biz Bundle

### 1. Preparation for Integration

The Biz Bundle is based on [Tuya Smart SDK](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/en/) version 3.17.0, so before integration, you need to do some preparations:

- Integrate the Tuya Smart SDK for the whole house, refer to [Integrated documentation](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/en/resource/Preparation.html) (including the application of tuya App ID and App Secret, security picture configuration related environment )

- Implement the Tuya Smart SDK [User registration](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/en/resource/User.html) and [Family Management](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/en/resource/HomeManager.html) 

### 2. Integrate Biz Bundle

- Create Project

  Build your project in the Android Studio and integrate Tuyasmart HomeSDK
  
- Configure the root build.gradle


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
	         classpath 'com.android.tools.build:gradle:3.1.4'
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

- Configure the module build.gradle


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
	    
        //kotlin
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
    
        implementation 'com.tuya.smart:tuya-config-mesh:1.0.1'
        implementation 'com.tuya.smart:tuya-config-mesh-api:1.0.1'
	    
        implementation 'com.tuya.smart:tuya-config-ble:1.0.1'
        implementation 'com.tuya.smart:tuya-config-ble-api:1.0.1'
	    
        implementation 'com.tuya.smart:tuya-config-api:1.0.1'
        implementation 'com.tuya.smart:tuya-config:1.0.1'
	    
	    implementation 'com.tuya.smart:tuya-scan:1.0.1'
    }
	
	repositories {
	    mavenLocal()
	    jcenter()
	    google()
	}

  ```
  
- Configure AndroidManifest.xml


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

- Configure the progurad


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
  
## Function call

### 1. Function configuration

| Configuration item  | Field name              |  Description        |
| ----------- | -----------------  | ----------------- |
| AP hotspot name configuration  | <string name="ap_mode_ssid">SmartLife</string> | List of hotspot prefixes supported by AP configuration, default configuration: SmartLife |
| Support BLE  | <bool name="is_need_ble_support">true</bool> | Whether to support the network function of BLE devices, the default configuration: true |
| Support MESH  | <bool name="is_need_blemesh_support">true</bool> | Whether to support the network function of MESH devices, the default configuration: true |
| Theme color configuration  | <color name="primary_button_bg_color">#FF5A28</color> | Button background color |
| Theme color configuration  | <color name="primary_button_font_color">#FFFFFF</color> | Button text color |

### 2. Method call

```java
  TuyaDeviceActivatorManager.startDeviceActiveAction(activity, homeId, 
		new ITuyaDeviceActiveListener() {
            @Override
            public void onDevicesAdd(List<String> devIds, boolean updateRoomData) {
                
            }
    	});
```

**Parameters**

| Parameters         | Description |
| ------------ | -------------------------- |
| activity        | activity |
| homeId          | Family ID, For details, please refer to Tuya Smart SDK [Home Creation](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/en/resource/HomeManager.html) |

**Parameters**

| Parameters         | Description |
| ------------ | -------------------------- |
| devIds          | List of device IDs with successful network configuration |
| updateRoomData          | Whether the room equipment information has been changed |

