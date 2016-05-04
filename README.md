

Raspberry Pi SETUP
Configure WIFI
    pi@raspberrypi:~ $ sudo cat /etc/wpa_supplicant/wpa_supplicant.conf
    country=GB
    ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
    update_config=1

    network={
    ssid="<YOUR_SSID>"
    scan_ssid=1
    psk="<PLAINTEXT_PASSWORD>"
    proto=RSN
    key_mgmt=WPA-PSK
    pairwise=TKIP
    group=TKIP
    }

    pi@raspberrypi:~ $ sudo ifdown wlan0 ; sudo ifup wlan0

Upgrade All packages
    sudo apt-get -y update

Install Java:
    sudo apt-get install -y oracle-java8-jdk

Install I2c Tools and setup RTC Support [see this article|https://learn.adafruit.com/adafruits-raspberry-pi-lesson-4-gpio-setup/configuring-i2c]

    sudo apt-get install -y python-smbus
    sudo apt-get install -y i2c-tools
    # sudo raspi-config

    pi@raspberrypi:~ $ sudo cat /etc/modules
    # /etc/modules: kernel modules to load at boot time.
    #
    # This file contains the names of kernel modules that should be loaded
    # at boot time, one per line. Lines beginning with "#" are ignored.
    i2c-dev

    pi@raspberrypi:~ $ cat /boot/config.txt
    #...
    dtparam=i2c_arm=on


    sudo shutdown -r now


