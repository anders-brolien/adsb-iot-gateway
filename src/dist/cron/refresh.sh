#!/bin/bash
cd /home/pi/download
wget -N adsb-iot-gateway.s3-website-us-west-2.amazonaws.com/adsb-iot-gateway.zip &>status.out


if grep -q 'not retrieving' "status.out";then
   echo "No change"
else
  echo "Change, installing"
  cd /home/pi/
  rm -rf adsb-iot-gateway.old
  mv adsb-iot-gateway adsb-iot-gateway.old
  unzip download/adsb-iot-gateway.zip
  chmod a+x adsb-iot-gateway/bin/adsb-iot-gateway 
  service adsb-gateway restart
fi
