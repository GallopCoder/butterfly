# /bin/bash
# This script run at 00:00
# The Url logs path 
# 日志文件地址
logs_path="/usr/local/nginx/logs/"
# 新建文件夹
file_path=/usr/local/nginx/logs_$(date -d "yesterday" +"%Y")_$(date -d "yesterday" +"%m")/$(date -d "yesterday" +"%d")/
mkdir -p ${file_path}
# 获取logs下所有文件名
files=$(ls $logs_path)

# 循环
for filename in $files
do
 # 复制并清空，如果不想清空可以判断文件，比如：我这因安装nginx人员问题。nginx.pid也放在日志目录下了，所以是nginx.pid的时候要跳过去
 if [ $filename != "nginx.pid" ]; then
  cp ${logs_path}$filename ${file_path}$filename
  echo "" > ${logs_path}$filename
 fi     
done
