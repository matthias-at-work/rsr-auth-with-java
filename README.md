## Native RSR Auth library accessed with Java/JNA under Ubuntu ##

### Motivation

RSR Auth Library comes with a .NET wrapper for Windows.
For STING, the RSR Auth Library needs to be accessed with Java under Ubuntu.
This file shows how to use the RSR Auth Library using JNA.


### Steps ###

#### 1. Setup Machine with Java and JNA
- VirutalBox with Ubuntu 18-4

- Install java:
  ````
  apt-get install default-jre
  apt-get install default-jdk
  ````
  
- Download JNA and docu
  - https://github.com/java-native-access/jna
  - http://java-native-access.github.io/jna/5.3.1/javadoc/
  - Placed the downloaded file `jna-5.3.1.jar` into `/home/mats/rsr-verify-test/`

- Reference JNA in CLASSPATH:
  ````
  export CLASSPATH=/home/mats/rsr-verify-test/jna-5.3.1.jar:$CLASSPATH
  ````


#### 2. Get RSR-related stuff
- Get library from rsr-auth. Using `RSRAuthentication_1.3`:
  - From `RSRAuthentication_1.3\Binaries\Linux\TokenAuthenticationLibrary\Debian9.6\libfsrverify_2.4.1.2-0_amd64_debian9_6\data\lib\x86_64-linux-gnu`
  - Copy `libfsrverify.so.1.1.4` to `/home/mats/rsr-verify-test/`

- Check the .h files in `RSRAuthentication_1.3\Binaries\Windows\TokenAuthenticationLibrary\Deployment\Interfaces`. It includes info on the signature and type-definitions of errors.

- Copied RSR-keyfile `fsr-authentication.public.keys' (containing public keys only) into folder `/home/mats/rsr-verify-test/`


#### 3. Write code and run
- Place source-file [RsrAuthTest.java](/RsrAuthTest.java) into the same directory (`/home/mats/rsr-verify-test/`). 

- Adjust code with the corresponding username/token.

- Compile and run
  ````
  javac RsrAuthTest.java
  java RsrAuthTest
  ````


 
