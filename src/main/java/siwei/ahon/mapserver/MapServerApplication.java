package siwei.ahon.mapserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "siwei.ahon.mapserver.mapper")
public class MapServerApplication {
    public static void main(String[] args) {
          SpringApplication.run(MapServerApplication.class, args);
        System.out.println("http://localhost:8080");
     }
}
