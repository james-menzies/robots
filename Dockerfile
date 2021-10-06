FROM debian:stretch-slim

WORKDIR /opt/robots

COPY app/build/image .

CMD ["./bin/app"]