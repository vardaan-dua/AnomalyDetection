# Anomaly Detection using Graylog and Influx Data Points of Service

## Steps to Run the Code

1. **Clone the Repository**
   ```bash
   git clone https://github.com/vardaan-dua/AnomalyDetection.git
   ```

2. **Open the Project through Gradle in IntelliJ**

3. **Setup SDK**
   - Java 22.0.1
   - Oracle Open JDK 22

4. **Create Two New Directories in the Project Repository**
   - `temp`
   - `RepeatingPatternResults`

5. **Install Python and Pip**
   - Python 3.9.6
   - Pip 21.2.4

6. **Create a New Python Virtual Environment**
   In the same repository, create a new Python virtual environment named `myenv`:
   ```bash
   python3 -m venv myenv
   ```

7. **Install Required Python Packages**
   Activate the virtual environment and install the following packages:
   ```bash
   source myenv/bin/activate
   pip3 install click==8.1.7 dnspython==2.6.1 et-xmlfile==1.1.0 joblib==1.4.2 nltk==3.8.1 numpy==2.0.0 openpyxl==3.1.4 pandas==2.2.2 pip==24.0 python-dateutil==2.9.0.post0 pytz==2024.1 regex==2024.5.15 scikit-learn==1.5.0 scipy==1.14.0 six==1.16.0 threadpoolctl==3.5.0 tqdm==4.66.4 tzdata==2024.1
   ```

8. **Setup Kafka in Docker**
   - Use the `apache/kafka:3.7.1` image in Docker.
   - Pull the image into a container and verify it's running:
     ```bash
     docker ps
     ```
   - If the image is running, enter the container:
     ```bash
     docker exec -it <container_name> /bin/bash
     ```

9. **Create a New Kafka Topic**
   Inside the container, create a new Kafka topic named `AnomalyDetectionTopic`:
   Perform this in /opt/kafka/bin folder
   ```bash
   sh kafka-topics.sh --create --topic AnomalyDetectionTopic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
   ```

10. **Setup the Paths of Various Parameters/Logs in Constants.java file**
    
11. **Run the AnomalyDetectionApplication and KafkaConsumer**
    - Run the `AnomalyDetectionApplication` file.
    - Run the `KafkaConsumer`.
    - You can now query on `localhost:8080`.
12. **Sample Queries**
    - localhost:8080/AnomalyDetection/2024-07-05T06:22:26.220Z/2024-07-05T15:38:00.000Z
    - localhost:8080/ListMostOccurringPatterns/2024-07-05T06:22:26.220Z/2024-07-05T12:38:00.000Z
    - localhost:8080/MostOccuringPattern/2024-07-05T06:30:00.000Z/2024-07-05T06:40:00.000Z
    - localhost:8080/MostOccuringPatternExcluding/2024-07-05T06:30:00.000Z/2024-07-05T06:40:00.000Z/get health check http, hello , hey
