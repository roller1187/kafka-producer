<img src="https://developers.redhat.com/blog/wp-content/uploads/2018/10/Untitled-drawing-4.png" data-canonical-src="https://developers.redhat.com/blog/wp-content/uploads/2018/10/Untitled-drawing-4.png" width="300" height="140" />

# Acrostic Kafka Producer

This purpose of this service is to:
  * Expose a RESTful API for submitting messages to a Kafka topic

---

## Instructions for deploying on OpenShift:
  1. Login to OpenShift:
```sh
oc login <openshift_cluster>
```
  2. Use existing Kafka project created during the deployment of the [kafka-consumer](https://github.com/roller1187/kafka-consumer) service:
```sh
oc project kafka-$(oc whoami)
```
  3. Deploy the service using s2i (Source-2-Image). Don't forget to provide a Kafka topic:
```sh
oc new-app redhat-openjdk18-openshift:1.4~https://github.com/roller1187/kafka-producer.git \
    --env KAFKA_BACKEND_TOPIC=my-topic \
    --env SPRING_KAFKA_BOOTSTRAP_SERVERS=my-cluster-kafka-external-bootstrap.kafka.svc.cluster.local:9094
```
  4. Expose a route to access the producer service outside of OpenShift:
```sh
oc expose svc/kafka-producer
```

---
**NOTE:**

In order to access the producer's API to send a message, use the following syntax:

http://<openshift_producer_route_url>/msg/<message_contents>/<backend_topic_name>

e.g. To send "hello" to backend topic "my-topic-user1" use:
http://kafka-producer-kafka-user1.apps.my-openshift.redhatgov.io/msg/hello/my-topic-user1

---

*Acrostic example:

![Acrostic](https://www.researchgate.net/profile/Andrew_Finch/publication/260593143/figure/fig3/AS:392472879484941@1470584234596/Acrostic-poem-Teaching-points-Spelling-Vocabulary-Dictionary-Holmes-Moulton-2001.png)
