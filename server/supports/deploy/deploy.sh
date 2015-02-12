#!/bin/sh 
# deploy.sh: 自动部署应用中心脚本. 

appcenter_home=/home/www/source/appCenter
jetty_home=/home/www/jetty_app_center

echo "checkout last mast source code..."
cd $appcenter_home
git checkout master
git pull origin master
cd $appcenter_home/server
echo "package appcenter"
mvn clean package -Dmaven.test.skip=true
echo "stop jetty"
cd $jetty_home
$jetty_home/bin/jetty.sh stop
rm -Rf $jetty_home/webapps/root/*
cp $appcenter_home/server/target/appportal.war $jetty_home/webapps/root/
cd $jetty_home/webapps/root
jar -xvf appportal.war
$jetty_home/bin/jetty.sh start