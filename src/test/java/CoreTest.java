import com.sdyc.service.exapi.impl.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/11  19:02
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Discription:
 *
 * Modify:      2018/1/11  19:02
 * Author:
 * </pre>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        BinanceServiceImplTest.class,
        GateIoServiceImplTest.class,
        HuobiproServiceImplTest.class,
        OkexServiceImplTest.class,
        ZbServiceImplTest.class
})
public class CoreTest {



}
