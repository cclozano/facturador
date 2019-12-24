

mvn install:install-file -Dfile=exksoap2-1.0.2.jar -DgroupId=com.local -DartifactId=exksoap2 -Dversion=1.1.7 -Dpackaging=jar > mvn.log

mvn install:install-file -Dfile=MITyCLibXADES-1.1.7.jar -DgroupId=com.local -DartifactId=MITyCLibXADES -Dversion=1.1.7 -Dpackaging=jar >> mvn.log  
mvn install:install-file -Dfile=MITyCLibAPI-1.1.7.jar -DgroupId=com.local -DartifactId=MITyCLibAPI -Dversion=1.1.7 -Dpackaging=jar >> mvn.log  

mvn install:install-file -Dfile=MITyCLibCert-1.1.7.jar -DgroupId=com.local -DartifactId=MITyCLibCert -Dversion=1.1.7 -Dpackaging=jar >> mvn.log  
mvn install:install-file -Dfile=MITyCLibCrypt-1.1.7.jar -DgroupId=com.local -DartifactId=MITyCLibCrypt -Dversion=1.1.7 -Dpackaging=jar >> mvn.log  
mvn install:install-file -Dfile=MITyCLibOCSP-1.1.7.jar -DgroupId=com.local -DartifactId=MITyCLibOCSP -Dversion=1.1.7 -Dpackaging=jar >> mvn.log  
mvn install:install-file -Dfile=MITyCLibPolicy-1.1.7.jar -DgroupId=com.local -DartifactId=MITyCLibPolicy -Dversion=1.1.7 -Dpackaging=jar >> mvn.log  
mvn install:install-file -Dfile=MITyCLibTrust-1.1.7.jar -DgroupId=com.local -DartifactId=MITyCLibTrust -Dversion=1.1.7 -Dpackaging=jar >> mvn.log  
mvn install:install-file -Dfile=MITyCLibTSA-1.1.7.jar -DgroupId=com.local -DartifactId=MITyCLibTSA -Dversion=1.1.7 -Dpackaging=jar >> mvn.log  

mvn install:install-file -Dfile=xmlsec-1.4.2-ADSI-1.0.jar -DgroupId=com.local -DartifactId=xmlsec-1.4.2-ADSI-1.0 -Dversion=1.1.7 -Dpackaging=jar >> mvn.log  
mvn install:install-file -Dfile=xmlsec-1.4.2-ADSI-1.1.jar -DgroupId=com.local -DartifactId=xmlsec -Dversion=1.1.7 -Dpackaging=jar >> mvn.log

