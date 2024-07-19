#Anomaly Detection using Graylog and Influx data points of service .

##Steps to run the code :

Git clone the repository .

Open the project through gradle

Setup SDK -> Java 22.0.1 , Oracle OPEN jdk 22

Create two new directories in the project repository
-> temp
-> RepeatingPatternResults

Install ->
python 3.9.6
pip 21.2.4

In the same repository create a new python virtual environment
With the name myenv using the command ,
python3 -m venv myenv
(Make this virtual environment in the same project’s repository)
In that install the following packages along with the versions ,

click           8.1.7
dnspython       2.6.1
et-xmlfile      1.1.0
joblib          1.4.2
nltk            3.8.1
numpy           2.0.0
openpyxl        3.1.4
pandas          2.2.2
pip             24.0
pymongo         4.8.0
python-dateutil 2.9.0.post0
pytz            2024.1
regex           2024.5.15
scikit-learn    1.5.0
scipy           1.14.0
six             1.16.0
threadpoolctl   3.5.0
tqdm            4.66.4
tzdata          2024.1

Now setup Kafka in docker ,
I have used apache/kafka:3.7.1 image in my docker .

Pull the image into a container , and check if it is running by doing docker ps .
If the image is running
docker  exec -it conatiner_name(space)/bin/bash
In this container create a new Kafka Topic with the name AnomalyDetectionTopic .

For creating topic
sh kafka-topics.sh --create --topic AnomalyDetectionTopic —bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

Now run the AnomalyDetectionApplication file and KafkaConsumer,then you can query on the localhost:8080 






