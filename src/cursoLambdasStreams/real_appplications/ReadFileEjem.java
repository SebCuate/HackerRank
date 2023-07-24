package cursoLambdasStreams.real_appplications;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadFileEjem {

	public static void main(String[] args) {
		
		Path file = Paths.get("src\\main\\resources\\util.txt");
		
		try(
			Stream<String> lines = 
				Files.lines(file).onClose(
					() -> System.out.println("Closing reader")
				)
		){
			lines.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
