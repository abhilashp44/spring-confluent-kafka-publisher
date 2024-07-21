package com.student.tracker.service;

/**
 * Service interface for publishing student-related events.
 */
public interface StudentPublisherService {

    /**
     * Publishes a student event. The implementation of this method should handle the logic for publishing events
     * related to students, such as creation, update, or deletion events.
     */
    void publishStudentEvent();
}
