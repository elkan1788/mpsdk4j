# Mpsdk4j-v2

**MPSDK4J** 升级改造中,敬请期待！

## JCE 使用说明
| 文件名称 | JDK版本 |
| ------ | ------: |
| jce_policy-6.zip | 1.6 |
| UnlimitedJCEPolicyJDK7.zip | 1.7 |
| jce_policy-8.zip | 1.8 |

下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt，如果安装JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件；如果安装了JDK，将两jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件