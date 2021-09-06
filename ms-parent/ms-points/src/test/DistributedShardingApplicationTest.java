import com.laozhang.points.dao.OrderDao;
import com.laozhang.points.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

@SpringBootTest
//@RunWith(SpringRunner.class)
@Slf4j
public class DistributedShardingApplicationTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void insert() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(System.currentTimeMillis());
        orderEntity.setUserId(new Random().nextInt(999));
        orderDao.save(orderEntity);
    }

    @Test
    public void findByOrderId() {
        OrderEntity byOrderId = orderDao.findByOrderId(539507734186799104L);
        log.info("byOrderId={}", byOrderId);
    }

    @Test
    public void findByUserId() {
        List<OrderEntity> byUserId = orderDao.findByUserId(978);
        log.info("byUserId={}", byUserId);
    }

}
