if [ $# -lt 1 ]
then
       echo ' '
       echo 'USAGE: START | STOP | RESTART '
       echo 'Where START - to Start CW Core System Application'
       echo '      STOP - to Stop CW Core System Application'
       echo '      RESTART - Restart running CW Core System Application'
	echo ' '
       echo ' '
       exit 0
fi

action=$1
retval=0

StatusCount=`ps -ef | grep "org.jpos.q2.Q2"| grep -v grep | wc -l`
# echo `ps -ef | grep "org.jpos.q2.Q2" | grep -v grep | wc -l`
# echo $isRunning
if [ $StatusCount -ge 1 ]
then
       isRunning=1
else
       isRunning=0
fi


case $action in
       "START"|"start")
              if [ $isRunning -eq 0 ]
              then
                     echo 'INFO: Starting CW Core System'
                     CLASSPATH=`echo lib/*.jar | tr ' ' ':'`:cfg:$CLASSPATH
		     	CLASSPATH=$CLASSPATH:`echo deploy/*/lib/*.jar | tr ' ' ':'`
  			#$JAVA_HOME/bin/java -server -Dlog.encode=false -cp $CLASSPATH org.jpos.q2.Q2 -r >> /dev/null &
			java -server -Dlog.encode=false -cp $CLASSPATH org.jpos.q2.Q2 -r >> /dev/null &

                     sleep 8
                     echo 'INFO: CW Core System Application Started'
                     retval=0
              else
                     echo 'ERROR: CW Core System Application is already running'
                     retval=0
              fi
              ;;
       "STOP"|"stop")
              if [ $isRunning -eq 1 ]
              then
                     echo 'INFO: Stopping CW Core System Application'
                     ps -ef | grep "org.jpos.q2.Q2"| grep -v grep | while read grepString
                     do
                           jarpid=`echo $grepString | cut -d" " -f2`
                           kill -9 $jarpid > /dev/null
                     done
                     sleep 8
                     echo 'INFO: CW Core System Application Stopped'
                     retval=0
              else
                     echo 'ERROR: CW Core System Application is already stopped'
                     retval=0
              fi
              ;;
       "RESTART"|"restart")
              if [ $isRunning -eq 1 ]
              then
                     echo 'INFO: Stopping CW Core System Application'
                     ps -ef | grep "org.jpos.q2.Q2"| grep -v grep | while read grepString
                     do
                           jarpid=`echo $grepString | cut -d" " -f2`
                           kill -9 $jarpid > /dev/null
                     done
                     sleep 8
                     echo 'INFO: CW Core System Application Stopped'
                     sleep 2
                     echo 'INFO: Now Starting CW Core System Application again'
                     CLASSPATH=`echo lib/*.jar | tr ' ' ':'`:cfg:$CLASSPATH
		     	CLASSPATH=$CLASSPATH:`echo deploy/*/lib/*.jar | tr ' ' ':'`
  			$JAVA_HOME/bin/java -server -Dlog.encode=false -cp $CLASSPATH org.jpos.q2.Q2 -r >> /dev/null &
                     sleep 8
                     echo 'INFO: CW Core System Application Started'
                     retval=0
              else
                     echo 'ERROR: CW Core System Application is already stopped'
                     retval=1
              fi
              ;;
       
       *)
              echo "ERROR: Unsupporgted Option Entered"
              retval=0
              ;;
esac
exit $retval
