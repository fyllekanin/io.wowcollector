FROM nginx

RUN apt-get update && apt-get install -y openjdk-17-jdk

COPY ui/dist/ /ui/
COPY target/quarkus-app/ /app/
COPY docker/nginx /etc/nginx/conf.d

RUN apt-get clean && rm -rf /var/lib/apt/lists/*

EXPOSE 80

# Start Nginx when the container runs
ENTRYPOINT sh -c 'service nginx start && java -jar /app/quarkus-run.jar'