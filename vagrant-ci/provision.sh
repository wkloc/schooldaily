echo "Starting PostgreSQL"

APP_DB_USER=pgssonaruser
APP_DB_PASS=pgssonarpass

# Database Name
APP_DB_NAME=sonarqube

print_db_usage () {
  echo "PostgreSQL database has been setup and can be accessed on port 25432"
  echo "  Host: localhost"
  echo "  Port: 25432"
  echo "  Database: $APP_DB_NAME"
  echo "  Username: $APP_DB_USER"
  echo "  Password: $APP_DB_PASS"
  echo ""
  echo "Admin access to postgres user via VM:"
  echo "  vagrant ssh"
  echo "  sudo su - postgres"
  echo ""
  echo "psql access to app database user via VM:"
  echo "  vagrant ssh"
  echo "  sudo su - postgres"
  echo "  PGUSER=$APP_DB_USER PGPASSWORD=$APP_DB_PASS psql -p 5432 -h localhost $APP_DB_NAME"  
}

print_jenkins_usage () {
  echo "Jenkins CI server has been setup and can be accessed on port 6060"
  echo "  Host: localhost"
  echo "  Port: 6060"  
}

print_sonar_usage () {
  echo "Sonarqube server has been setup and can be accessed on port 9000"
  echo "  Host: localhost"
  echo "  Port: 9000"  
}

print_cloud_config_usage () {
  echo "Schooldaily cloud config server has been setup and can be accessed on port 8440"
  echo "  Host: localhost"
  echo "  Port: 8440"  
}

cat << EOF | sudo su postgres -c "psql -p 5432"
-- Create the database user:
CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';

-- Create the database:
CREATE DATABASE $APP_DB_NAME WITH OWNER=$APP_DB_USER
                                  LC_COLLATE='en_US.utf8'
                                  LC_CTYPE='en_US.utf8'
                                  ENCODING='UTF8'
                                  TEMPLATE=template0;
\q
EOF

sudo su postgres
service postgresql restart

echo "Successfully started PostgreSQL."

sudo sed -i 's/#sonar.jdbc.username=/sonar.jdbc.username=pgssonaruser/g' /opt/sonar/conf/sonar.properties
sudo sed -i 's/#sonar.jdbc.password=/sonar.jdbc.password=pgssonarpass/g' /opt/sonar/conf/sonar.properties
sudo sed -i 's$#sonar.jdbc.url=jdbc:postgresql://localhost/sonar$sonar.jdbc.url=jdbc:postgresql://localhost:5432/sonarqube$g' /opt/sonar/conf/sonar.properties
sudo sed -i 's$#sonar.web.javaOpts=-Xmx512m -Xms128m -XX:+HeapDumpOnOutOfMemoryError$sonar.web.javaOpts=-Xmx512m -Xms256m -XX:+HeapDumpOnOutOfMemoryError -Djava.net.preferIPv4Stack=true$g' /opt/sonar/conf/sonar.properties
sudo sed -i 's$#sonar.web.context=$#sonar.web.context=$g' /opt/sonar/conf/sonar.properties
sudo sed -i 's$#sonar.web.port=9000$sonar.web.port=9000$g' /opt/sonar/conf/sonar.properties

sudo service sonar start

echo "Successfully started SobnarQube."


#CLOUD CONFIG SERVER

echo "Install JCE Unlimited Strength Jurisdiction Policy Files"

sudo add-apt-repository ppa:webupd8team/java
sudo apt update
sudo apt install oracle-java8-unlimited-jce-policy 

echo "Install and run cloud config server for schooldaily."

git clone https://github.com/wkloc/schooldailyServerCloudConfig.git
cd schooldailyServerCloudConfig/
mvn clean install
cp /home/vagrant/schooldailyServerCloudConfig/target/serverconfig-0.0.1-SNAPSHOT.jar /home/vagrant

#run config server and suppress all logs and errors
#sudo java -jar /home/vagrant/serverconfig-0.0.1-SNAPSHOT.jar > /dev/null 2>&1

#prepare init.d scripts
sudo su root
cat /dev/null > /etc/init.d/cloudconfsrv
cat << EOF > /etc/init.d/cloudconfsrv
#!/bin/sh
SERVICE_NAME=CloudConfSrv
PATH_TO_JAR=/home/vagrant/serverconfig-0.0.1-SNAPSHOT.jar
PID_PATH_NAME=/tmp/CloudConfSrv-pid
case \$1 in
    start)
        echo "Starting \$SERVICE_NAME ..."
        if [ ! -f \$PID_PATH_NAME ]; then
            nohup java -jar \$PATH_TO_JAR /tmp 2>> /home/vagrant/cloudsrvconf.log >> /home/vagrant/cloudsrvconf.log &
                        echo \$! > \$PID_PATH_NAME
            echo "\$SERVICE_NAME started ..."
        else
            echo "\$SERVICE_NAME is already running ..."
        fi
    ;;
    stop)
        if [ -f \$PID_PATH_NAME ]; then
            PID=\$(cat \$PID_PATH_NAME);
            echo "\$SERVICE_NAME stoping ..."
            kill \$PID;
            echo "\$SERVICE_NAME stopped ..."
            rm \$PID_PATH_NAME
        else
            echo "\$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
        if [ -f \$PID_PATH_NAME ]; then
            PID=\$(cat \$PID_PATH_NAME);
            echo "\$SERVICE_NAME stopping ...";
            kill \$PID;
            echo "\$SERVICE_NAME stopped ...";
            rm \$PID_PATH_NAME
            echo "\$SERVICE_NAME starting ..."
            nohup java -jar \$PATH_TO_JAR /tmp 2>> /home/vagrant/cloudsrvconf.log >> /home/vagrant/cloudsrvconf.log &
                        echo \$! > \$PID_PATH_NAME
            echo "\$SERVICE_NAME started ..."
        else
            echo "\$SERVICE_NAME is not running ..."
        fi
    ;;
esac
EOF

sudo chmod +x /etc/init.d/cloudconfsrv
sudo service cloudconfsrv start

echo "Successfully cloud config server for schooldaily."

echo "Create Jenkins Jobs"

sudo service jenkins stop 

sudo apt-get install unzip

sudo cp /home/vagrant/schooldaily_jobs.zip /var/lib/jenkins
sudo rm -r /var/lib/jenkins/jobs
cd /var/lib/jenkins
sudo unzip schooldaily_jobs.zip
sudo rm schooldaily_jobs.zip
sudo chown -R jenkins /var/lib/jenkins/jobs

sudo service jenkins start

echo "Successfully created Jenkins Jobs"

print_db_usage
print_jenkins_usage
print_sonar_usage
print_cloud_config_usage