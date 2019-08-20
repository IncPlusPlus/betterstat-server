package io.github.incplusplus.thermostat.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ConnectionTestController
{
	private static final String template = "Hello, %s";
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping("/hello-world")
	@ResponseBody
	public Greeting sayHello(@RequestParam(name="name", required = false, defaultValue = "Stranger") String name)
	{
		return new Greeting(counter.incrementAndGet(),String.format(template,name));
	}
}
