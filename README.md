# AndroidDeviceName
[![License](http://img.shields.io/:license-apache-blue.svg)](LICENSE)

This is a small library to get the marketing name for an Android device.
It is based on the Google supported devices list found [here](http://storage.googleapis.com/play_public/supported_devices.csv.)

It converts the **android.os.Build.MODEL** value into the device market name based on the above list. For example translates `"SM-G930F"` into `"Galaxy S7"`

## Installing

* Add the library dependency in your **build.gradle**

```
    compile 'com.rednineteen.android:adn:1.0.0'
```

* Initialise the library by calling the below in your Application or main Activity `"onCreate()"` method

```java
    ADNDatabase.init(this);
```

## Usage

##### Get current device market name
```java
    String name = ADNDatabase.getDeviceName();
```
Returns the market name of the current device or returns the value of Build.MODEL if not found.

##### Get device market name for the given model (Build.MODEL)
```java
    String name = ADNDatabase.getDeviceName(String model, String fallback);
```
Returns the market name or fallback if the model is not found in the DB.

    For example:
    ADNDatabase.getDeviceName("SM-G925F", null);
    Returns:
    Galaxy S6 Edge

##### Get device market name including the Brand for the given model (Build.MODEL)
```java
    String name = ADNDatabase.getDeviceName(String model, String fallback, boolean withBrand);
```
Returns the market name including the brand or fallback if the model is not found in the DB.

    For example:
    ADNDatabase.getDeviceName("SM-G925F", null, true);
    Returns:
    Samsung Galaxy S6 Edge
   
##### Get device market name for the given model(Build.MODEL) and codename(Build.DEVICE)
```java
    String name = ADNDatabase.getDeviceName(String model, String codename, String fallback, boolean withBrand);
```
Returns the market name or fallback if the pair model/codename are not found in the DB. Use this if you want a bit more accuracy when searching.
Usually searching by model should be enough but in some rare cases two different devices may have the same model in which case one can use this method instead.

##### Get the device object
You can also get an object representation of the device containing the values from the DB. If the device is not found a `"null"` object is returned.

```java
    ADNDevice device = ADNDatabase.getDevice(String model);
```

##### Making multiple queries
This library uses a pre-populated SQLite db generated using the Google supported devices list and thus an SQLite statement is executed to search for the market name.
By default the below methods will open and close the DB. If you worry about performance or require to make multiple queries within an Activity, Framgent or an Adapter, then you should manually **open** and **close** the DB connection.

You can open the DB on the onStart() method of your activity and make sure to close it on the onStop() method. In this way all the queries will not close the connection and it will remain open.

```java
    @Override
    protected void onStart() {
        super.onStart()
        ADNDatabase.openDB();
    }
    
    @Override
        protected void onStop() {
            super.onStop()
            ADNDatabase.stopDB();
        }
```

## Documentation

For more documentation please check the [wiki](https://github.com/rednineteen/AndroidDeviceName/wiki/Documentation)

## License

    Copyright 2017 Juan Velasquez - Rednineteen
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
