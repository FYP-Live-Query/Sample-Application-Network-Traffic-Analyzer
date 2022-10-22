## Network Traffic Analyzer - Backend

### Run the Backend
```
mvn spring-boot:run
```
### To visualize metrics

1. **Run Prometheus Locally**

- Follow  to https://prometheus.io/download/ download prometheus
- Replace local prometheus.yaml with the _prometheus.yaml_ file in backend/src/main/resources/prometheus/prometheus.yml
- Go to the local prometheus directory and execute ./prometheus --config.file=./prometheus.yml command to start prometheus
- Check whether http://localhost:8081/actuator/prometheus is working

2. **Run Garafana locally**

- Follow **Install .deb package** section in https://grafana.com/docs/grafana/latest/setup-grafana/installation/debian/ to download
- Follow **Start the server** section in https://grafana.com/docs/grafana/latest/setup-grafana/installation/debian/ to start grafana in localhost:3000
- Import _dashboard.json_ in backend/src/main/resources/Dashboards/dashboard.json to grafana to visualize metrics
