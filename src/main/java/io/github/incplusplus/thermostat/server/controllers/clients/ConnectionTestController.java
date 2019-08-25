package io.github.incplusplus.thermostat.server.controllers.clients;

import io.github.incplusplus.thermostat.models.Greeting;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ConnectionTestController
{
	private static final String template = "Hello, %s";
	private final AtomicLong counter = new AtomicLong();
	
	@ApiOperation(value = "Says hello back to you", notes = "Change the name parameter for a personalized greeting!")
	@GetMapping("/hello-world")
	@ResponseBody
	public Greeting sayHello(@RequestParam(name="name", required = false, defaultValue = "Stranger") String name)
	{
		return new Greeting(counter.incrementAndGet(),String.format(template,name));
	}
}
