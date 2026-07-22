package ee.nikolas.backend0626;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableKafka
public class Backend0626Application {

	public static void main(String[] args) {
		SpringApplication.run(Backend0626Application.class, args);
	}

}

// 1. T 09.06 Controller, Repository, Entity, veateated
// 2. N 11.06 Service, Kontrollid, Util, Dto
// 3. E 15.06 Frontend
// 4. K 17.06 Websocket, Autentimine
// 5. E 22.06 Rollid, Smart ID
// 6. E 29.06 Cachemine, Cron
// 7. K 01.07 Docker, ModelMapper
// 8. K 08.07 Unit Testing
// 9. T 14.07 17.30-20.45 Integration testing. email. erinevad keskkonnad
//10. K 15.07 Render (database, backend, frontend)
//11. E 20.07 pakiautomaadid (Omniva) + makse (EveryPay)
//12. K 22.07 Kafka (message - async)
//13. E 27.07 OAuth Google
//14. K 29.07
//15. T 04.08
//16. N 06.08
//17. T 11.08
//18. T 18.08 --> poolik päev, lõpuprojekt