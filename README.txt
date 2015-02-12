1、本项目使用Maven内部私有仓库，需修改本应配置指向，具体参考supports/maven/settings.xml 中mirror,profile节点。
2、集成sonar作为代码质量审查管理,命令 mvn sonar:sonar，配置参考settings.xml