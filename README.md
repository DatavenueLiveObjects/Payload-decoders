# Live Objects : Javascript decoder test framework 

******************************************************************************************
- 06/03/2017 v1.0.0 : first version : includes samples for scripted binary and csv decoders
- 06/02/2018 v1.2.0 : updated samples and user manual
- 06/06/2018 v1.2.1 : the decode function is deprecated and replaced with the new formatAndDecode function
- 14/09/2018 v1.2.2 : includes the latest version of the decoding library
- 20/02/2019 v1.2.3 : includes the latest version of the decoding library
- 12/11/2020 v1.2.4 : includes the latest version of the decoding library
- 28/09/2021 v2.0.0 : includes the latest version of the decoding library (classic and split decoders)
- 05/08/2022 v2.1.0 : includes the latest version of the decoding library (profiling added)
******************************************************************************************

## Overview : 
The Live Objects Javascript decoder test framework helps you create and test the javascript payload decoders for your IoT devices, 
in order to ease your integration with the Live Objects service.

Javascript samples for binary decoding are also provided for a quick startup. 

JUnit tests and assertions help you validate the payload decoding.

When your javascript decoder is ready, you can contact the Orange Live Objects team for the script acceptance (validation) and provisioning. 
Please prefer sending these items to <liveobjects.decoder@orange.com> without attachment in your email. 
**Use file transfer services or repository to provide a link in your message.**


******************************************************************************************
Pre-requisite : in order to use this framework, the following components must be installed on your computer :
- java 8
- maven 3
******************************************************************************************
Content :
- lo-js-decoder-test-framework-samples directory : a maven project containing Javascript decoder samples. 
The samples can be run in your favourite IDE (Eclipse, IntelliJ) or using the "mvn test" command.

- lib directory: contains a jar with utility libraries for the lo-js-decoder-test-framework-samples project. This library must be installed in your maven repository with the command (to be run from the lib directory) :

```script
mvn install:install-file -Dfile="<your absolute path to lo-js-decoder-test-framework-utils-3.0.1-SNAPSHOT-jar-with-dependencies.jar>" -DgroupId="com.orange.lo" -DartifactId="lo-js-decoder-test-framework-utils" -Dversion="3.0.1-SNAPSHOT" -Dpackaging="jar"
```
- decoder-preliminary-acceptance [See WIKI](https://github.com/DatavenueLiveObjects/Payload-decoders/wiki/Guidelines-for-script-development#preliminary-acceptance-testing) : *(**Node.js** and **npm** installation required)* in order to test your javascript decoder for compatibility with ES5 and other requirements, please use the script in the following way:
```bash
cd decoder-preliminary-acceptance
npm install
node main.js -f <path-to-javascript-decoder>
```

- [See Github Wiki for user manual]( https://github.com/DatavenueLiveObjects/Payload-decoders/wiki )
******************************************************************************************
## License : 

This software is under the [BSD-3-Clause]( https://github.com/DatavenueLiveObjects/Payload-decoders/blob/master/LICENSE.md)
