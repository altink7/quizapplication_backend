package at.technikum.springrestbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Parent Controller, holds values which should apply for all child Controllers <br>
 * e.g: CrossOrigin destination
 */
@RestController
@CrossOrigin //for CORS policy - allows all origins - can be used to restrict access to certain origins
public abstract class Controller {
}
