package com.webservices.restfulwebservice.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class VersioningController {

	// path versioning
	@GetMapping("v1/person")
	public PersonV1 getPersonV1() {
		return new PersonV1(new Name("Jahnavi", "Thacker"));
	}

	@GetMapping("v2/person")
	public PersonV2 getPersonV2() {
		return new PersonV2("Jahnavi Thacker");
	}

	//req parameter versioning
	@GetMapping(value = "/person/param", params = "version=1")
	public PersonV1 param1() {
		return new PersonV1(new Name("Jahnavi", "Thacker"));
	}

	@GetMapping(value = "/person/param", params = "version=2")
	public PersonV2 param2() {
		return new PersonV2("Jahnavi Thacker");
	}

	// header (media type versioning)
	@GetMapping(value = "/person/header", headers = "HEADER-VERSION=1")
	public PersonV1 header1() {
		return new PersonV1(new Name("Jahnavi", "Thacker"));
	}

	@GetMapping(value = "/person/header", headers = "HEADER-VERSION=2")
	public PersonV2 header2() {
		return new PersonV2("Jahnavi Thacker");
	}

	// produces (custom headers versioning)
	@GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v1+json")
	public PersonV1 produces1() {
		return new PersonV1(new Name("Jahnavi", "Thacker"));
	}

	@GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v2+json")
	public PersonV2 produces2() {
		return new PersonV2("Jahnavi Thacker");
	}
}
