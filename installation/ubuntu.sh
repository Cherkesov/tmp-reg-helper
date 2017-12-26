#!/usr/bin/env bash

sudo apt-get update
sudo apt-get install -y default-jre

mkdir -p ~/tpl-reg-helper
cp ./blank-form.xls ~/tpl-reg-helper/blank-form.xls
cp ./tpl-reg-helper-1.0-SNAPSHOT.jar ~/tpl-reg-helper/tpl-reg-helper-1.0-SNAPSHOT.jar

rm -f ~/tpl-reg-helper/tpl-reg-helper.desktop

cat <<EOT >> ~/tpl-reg-helper/tpl-reg-helper.desktop
[Desktop Entry]
Version=1.0
Name=Template Reg Helper
Comment=Fill guest\'s registration form
Exec=bash -c 'java -jar ~/tpl-reg-helper/tpl-reg-helper-1.0-SNAPSHOT.jar;exit'
Icon=utilities-terminal
Terminal=true
Type=Application
Categories=Application;
EOT

chmod +x ~/tpl-reg-helper/tpl-reg-helper.desktop

