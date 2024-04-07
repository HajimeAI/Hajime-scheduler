# Hajime-scheduler Overview
Hajime-scheduler serves as the nerve center of the Hajime network, responsible for a variety of critical functions that ensure the smooth operation of the network. Here's an overview of the key features of Hajime-scheduler and their significance:

## VPN IP Allocation
The Hajime-scheduler is in charge of assigning VPN IP addresses to each Hajimebot node within the network. 

## Task Scheduling
Hajime-scheduler intelligently schedules tasks based on the demands of the network and the resources available at each node. 


## Receiving Workload Reports from Nodes
Hajime-scheduler collects workload reports from individual nodes, enabling real-time monitoring of the network's operational status.


## Operational Statistics
By analyzing the workload reports and other operational data, Hajime-scheduler provides detailed performance reports of the network. 

Please note that not all features have been implemented during the POC phase.




# Prerequisites
- java11
- mysql
- maven 3.9.6+
- nginx

# Installation
```
# package
mvn clean -DskipTests=true package -P prod

# prepare database
see doc/databbase/hajime.sql

# config 
application.yml
application-dev.yml
application-prod.yml

# start

nohup java -jar ai-hajimebot-1.0.0.jar >/dev/null 2>&1 &


```
