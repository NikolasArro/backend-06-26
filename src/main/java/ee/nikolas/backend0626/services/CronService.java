package ee.nikolas.backend0626.services;

import ee.nikolas.backend0626.entity.Order;
import ee.nikolas.backend0626.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CronService {

    // * ---> sekundid 0-59
    // * * ---> minutid 0-59
    // * * * ---> tunnid 0-59
    // * * * * ---> kuus olev päev 1-31
    // * * * * * ---> kuus 1-12 või Jan-Dec
    // * * * * * * ---> nädalapäev 0-7, kus 0 ja 7 on mõlemad pühapäevad

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 8-20 * * 1-5")
    public void printEverySecond() {
        Date current = new Date();
        Date after = new Date(current.getTime() + TimeUnit.HOURS.toMillis(24));
        Date before = new Date(current.getTime() + TimeUnit.HOURS.toMillis(25));
        List<Order> ordersToBeReminder = orderRepository.findByCreatedAfterAndCreatedBefore(after, before);
    }

    // iga kuu viimasel tööpäeval kell 17:00
    @Scheduled(cron = "* * 17 LW * *")
    public void sendWorkReport() {

    }
}
