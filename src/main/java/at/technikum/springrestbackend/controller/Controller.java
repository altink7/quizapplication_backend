package at.technikum.springrestbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Parent Controller, holds values which should apply for all child Controllers <br>
 * e.g: CrossOrigin destination
 */
@RestController
@CrossOrigin
public abstract class Controller {
}
