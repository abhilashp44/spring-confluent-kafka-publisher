package com.student.tracker.service.impl;

import com.student.tracker.service.StudentPublisherService;
import com.student.tracker.avro.School;
import com.student.tracker.avro.Student;
import com.student.tracker.avro.StudentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the StudentPublisherService interface. This class is responsible for publishing student-related
 * events to a Kafka topic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StudentPublisherServiceImpl implements StudentPublisherService {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * Publishes a student event by sending a message to a Kafka topic. This method constructs a StudentEvent containing
     * a list of students, creates a message with the event payload, and sends it to the specified Kafka topic.
     */
    @Override
    public void publishStudentEvent() {

        // Create a map to hold message headers
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("topic", "source_topic");

        // Create student instances
        Student student1 = new Student(1, "John", new School(1, "School1", "US"));
        Student student2 = new Student(2, "Linda", new School(1, "School2", "Canada"));

        // Create a list of students
        List<Student> students = List.of(student1, student2);

        // Create a student event containing the list of students
        StudentEvent studentEvent = new StudentEvent(students);

        // Create a message with the student event payload and headers
        Message<Object> message =
                MessageBuilder.withPayload((Object) studentEvent).setHeader(KafkaHeaders.TOPIC, "source_topic")
                        .copyHeaders(headerMap).build();

        // Send the message to the Kafka topic and add a callback to handle the result
        this.kafkaTemplate.send(message).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
            @Override
            public void onSuccess(SendResult<Object, Object> result) {

                log.info("Message sent successfully");
            }

            @Override
            public void onFailure(@Nullable Throwable exception) {

                log.error("Error sending message", exception);
            }
        });
    }
}