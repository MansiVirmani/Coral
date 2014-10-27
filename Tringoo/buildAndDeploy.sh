ant clean build
adb push /home/mansi/IdeaProjects/Tringoo/bin/Tringoo.jar /data/local/tmp
adb push /home/mansi/IdeaProjects/Tringoo/bin/Bundle.jar /data/local/tmp
adb shell uiautomator runtest Tringoo.jar Bundle.jar -c SignUp
