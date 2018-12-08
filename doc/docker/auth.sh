#!/bin/sh

export AUTH=jar/ly-auth.jar

export prd=--spring.profiles.active=prd

export AUTH_free=-Xmx500m


export AUTH_port=13000


case "$1" in

start)
        ## 启动AUTH
        echo "--------开始启动AUTH---------------"
        nohup java $AUTH_free -jar $AUTH $prd >/dev/null 2>&1 &
        AUTH_pid=`lsof -i:$AUTH_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$AUTH_pid" ]
            do
              AUTH_pid=`lsof -i:$AUTH_port|grep "LISTEN"|awk '{print $2}'`
            done
        echo "AUTH pid is $AUTH_pid"
        echo "---------AUTH success 启动成功-----------"

        echo "===startAll success==="
        ;;

 stop)
        P_ID=`ps -ef | grep -w $AUTH | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===AUTH process not exists or stop success"
        else
            kill -9 $P_ID
            echo "AUTH killed success"
        fi
        echo "===stop success==="
        ;;

restart)
        $0 stop
        sleep 2
        $0 start
        echo "===restart success==="
        ;;

restart)
        $0 stop
        sleep 2
        $0 start
        echo "===restart success==="
        ;;
esac
exit 0